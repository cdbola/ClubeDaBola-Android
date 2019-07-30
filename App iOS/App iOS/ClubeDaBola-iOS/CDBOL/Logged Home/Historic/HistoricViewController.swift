//
//  HistoricViewController.swift
//  CDBOL
//
//  Created by Bruno Faganello Neto on 01/09/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit
import NVActivityIndicatorView

class HistoricViewController: UIViewController, NVActivityIndicatorViewable {

    @IBOutlet weak var segmentedControl: UISegmentedControl!
    @IBOutlet weak var tableView: UITableView!{
        didSet{
            tableView.delegate = self
            tableView.dataSource = self
            tableView.separatorStyle = .none
        }
    }
    @IBOutlet weak var historicMonth: UITextField! {
        didSet {
            historicMonth.delegate = self
        }
    }
    @IBAction func clickedSegmented(_ sender: UISegmentedControl) {
        if segmentedControl.selectedSegmentIndex == 0{
            getPartidas()
            flagContrato = 0
        } else {
            getPartidasContratado()
            flagContrato = 1
        }
        historicMonth.text = ""
        self.tableView.reloadData()
    }
    
    var matchs = [HistoricMatch]()
    var flagContrato: Int = 0
    
    override func viewDidLoad() {
        self.navigationController?.navigationBar.setGradientBackground()
        self.navigationController?.navigationBar.tintColor = .white
    }
    
    override func viewWillAppear(_ animated: Bool) {
        segmentedControl.selectedSegmentIndex = 0
        getPartidas()
        flagContrato = 0
        self.tableView.reloadData()
    }
    
    func getPartidas() {
        self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
        let api = MatchManager()
        api.getPastMatchs(contratanteId: Defaults.getUser._id!, successHandler: { (matchs) in
            self.stopAnimating()
            self.matchs = matchs
            self.tableView.reloadData()
        }, errorHandler: { (error) in
            self.stopAnimating()
            self.createAlert(title: "Erro", message: "Ops! Tivemos um erro ao recuperar suas partidas")
        })
    }
    
    func getPartidasContratado() {
        self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
        let api = MatchManager()
        api.getHiredPastMatchs(contratanteId: Defaults.getUser._id!, successHandler: { (hiredMatch) in
            self.stopAnimating()
            self.matchs = hiredMatch
            self.tableView.reloadData()
        }, errorHandler: { (error) in
            self.stopAnimating()
            self.createAlert(title: "Erro", message: "Ops! Tivemos um erro ao recuperar suas partidas")
        })
    }
    
    func dateChangeMask(date: String)  -> String {
        let arrayDate = date.components(separatedBy: "-")
        let dateFinal = "\(arrayDate[1])/\(arrayDate[2])"
        return dateFinal
    }
}


extension HistoricViewController: UITableViewDelegate, UITableViewDataSource{
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.matchs.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! HistoricTableViewCell
        if flagContrato == 0 {
            cell.configCellContratante(model: matchs[indexPath.row])
            if matchs[indexPath.row].notaContratado != nil {
                cell.firstButton.isHidden = true
            } else {
                cell.firstButton.isHidden = false
            }
        } else {
            cell.configCellContratado(model: matchs[indexPath.row])
            if matchs[indexPath.row].notaContratante != nil {
                cell.firstButton.isHidden = true
            } else {
                cell.firstButton.isHidden = false
            }
        }
        cell.delegate = self
        return cell
    }
    
    public func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 329
    }
}

extension HistoricViewController: HistoricTableViewCellDelegate {
    func buttonClicked(match: HistoricMatch) {
        if flagContrato == 0 {
            let viewController = self.storyboard?.instantiateViewController(withIdentifier: "AvailableContratadoViewController") as! AvailableContratadoViewController
            viewController.match = match
            self.navigationController?.pushViewController(viewController, animated: true)
        } else {
            let viewController = self.storyboard?.instantiateViewController(withIdentifier: "AvaiableContratanteViewController") as! AvaiableContratanteViewController
            viewController.match = match
            self.navigationController?.pushViewController(viewController, animated: true)
        }
    }
}

extension HistoricViewController: UITextFieldDelegate {
    public func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return false
    }
    
    public func textFieldShouldBeginEditing(_ textField: UITextField) -> Bool {
        if flagContrato == 0 {
            getPartidas()
        } else {
            getPartidasContratado()
        }
        return true
    }
    
    public func textFieldDidEndEditing(_ textField: UITextField) {
        if textField.text?.count == 7 {
            self.filter(date: textField.text!)
            self.tableView.reloadData()
        } else {
            self.createAlert(title: "Filtro", message: "Não encontramos nada com a sua busca.")
            textField.text = ""
            reloadTableNoFilter()
        }
    }
    
    func filter(date: String) {
        var matchsFilter = [HistoricMatch]()
        for match in matchs {
            if let data = match.data {
                let matchDate = data
                if dateChangeMask(date: matchDate) == date {
                    matchsFilter.append(match)
                }
            }
        }
        if matchsFilter.count != 0 {
            matchs.removeAll()
            matchs = matchsFilter
            self.createAlert(title: "Filtro", message: "Busca realizada com sucesso.")
        } else {
            self.createAlert(title: "Filtro", message: "Não encontramos nada com a sua busca.")
            reloadTableNoFilter()
        }
    }
    
    func reloadTableNoFilter() {
        segmentedControl.selectedSegmentIndex = flagContrato
        if flagContrato == 0 {
            getPartidas()
        } else {
            getPartidasContratado()
        }
        self.tableView.reloadData()
    }
    
}
