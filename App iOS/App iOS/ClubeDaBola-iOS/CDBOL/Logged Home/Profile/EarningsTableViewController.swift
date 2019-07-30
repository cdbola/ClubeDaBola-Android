//
//  EarningsTableViewController.swift
//  CDBOL
//
//  Created by Paulo Rosa on 28/11/2018.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit
import NVActivityIndicatorView

class EarningsTableViewController: UIViewController, NVActivityIndicatorViewable {

    fileprivate let reuseIdentifier: String = "extractCell"

    @IBOutlet weak var tableView: UITableView! {
        didSet{
            tableView.dataSource = self
        }
    }
    @IBOutlet weak var saldoTotal: UILabel!
    @IBOutlet weak var saldoPendente: UILabel!
    @IBOutlet weak var saldoDisponivel: UILabel!
    var wallet: WalletDetail?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setUI()
        getWalletDetail()
    }

    func setUI() {
        self.tableView.tableFooterView = UIView()
        self.navigationController?.navigationBar.setGradientBackground()
        self.navigationController?.navigationBar.tintColor = .white
        let textAttributes = [NSAttributedStringKey.foregroundColor:UIColor.white]
        self.navigationController?.navigationBar.titleTextAttributes = textAttributes
    }
    
    func getWalletDetail() {
        self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
        let api = PersonManager()
        api.getWallet(contratanteId: Defaults.getUser._id!, successHandler: { (walletDetail) in
            self.stopAnimating()
            self.wallet = walletDetail
            self.setWalletDetail(walletDetail: walletDetail)
            self.tableView.reloadData()
        }, errorHandler: { (error) in
            self.stopAnimating()
            self.createAlert(title: "Erro", message: "Ops! Tivemos um erro ao seu saldo")
        })
    }
    
    func withdrawWallet() {
        self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
        let api = PersonManager()
        api.withdrawWallet(contratanteId: Defaults.getUser._id!, successHandler: { (walletDetail) in
            self.stopAnimating()
            self.createChoiceAlert(title: "Resgate", message: "Resgate confirmado com sucesso", successButton:  { _ in
                self.dismiss(animated: true, completion: nil)
                self.navigationController?.popViewController(animated: true)
            })
        }, errorHandler: { (error) in
            self.stopAnimating()
            self.createAlert(title: "Erro", message: "Ops! o resgate não pode ser realizado")
        })
    }
    
    func setWalletDetail(walletDetail: WalletDetail) {
        if let valorTotal = walletDetail.valorTotal {
            let saldoTotal = formatValor(valor: valorTotal).format(numberStyle: .currency, locale: "pt_BR".toLocale)
            self.saldoTotal.text = "Você já recebeu \(saldoTotal ?? "R$ 0,00") no clube da bola"
        }
        if let valorDisponivel = walletDetail.valorDisponivel {
            self.saldoDisponivel.text = formatValor(valor: valorDisponivel).format(numberStyle: .currency, locale: "pt_BR".toLocale)
        }
        if let valorPendente = walletDetail.valorPendente {
            self.saldoPendente.text = formatValor(valor: valorPendente).format(numberStyle: .currency, locale: "pt_BR".toLocale)
        }

    }
    
    func formatValor(valor: Int) -> Double {
        if valor < 10 {
            return Double(valor)
        }
        let valorString = "\(valor)"
        let count = valorString.count - 2
        let prefix = String(valorString.prefix(count))
        let sufix = String(valorString.suffix(2))
        
        let novoValor = "\(prefix).\(sufix)"
        return Double(novoValor) ?? 0.0
    }

    @IBAction func withdraw(_ sender: Any) {
        withdrawWallet()
    }
    @IBAction func cancel(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
}


extension EarningsTableViewController: UITableViewDataSource {
   
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: reuseIdentifier, for: indexPath) as! EarningsTableViewCell
        if let walletDetail = self.wallet, let extrato = walletDetail.extrato {
            cell.configureCell(extrato: extrato[indexPath.row])
        }
        return cell
    }
    
  
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if let walletDetail = self.wallet, let extrato = walletDetail.extrato {
            return extrato.count
        }
        return 0
    }
    
    func tableView(_ tableView: UITableView!, titleForHeaderInSection section: Int) -> String!{
        return "Extrato"
    }
    
}
