//
//  EditProfileTableViewController.swift
//  CDBOL
//
//  Created by Paulo Rosa on 22/12/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit
import JMMaskTextField_Swift
import NVActivityIndicatorView

class EditProfileTableViewController: UITableViewController, NVActivityIndicatorViewable {


    @IBOutlet weak var imageProfile: UIImageView!
    @IBOutlet weak var name: UITextField!
    @IBOutlet weak var nickname: UITextField!
    @IBOutlet weak var date: JMMaskTextField!
    @IBOutlet weak var email: UITextField!
    @IBOutlet weak var finishButton: UIButton!
    override func viewDidLoad() {
        super.viewDidLoad()
        setUI()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        getHirer()
    }

    
    func setUI() {
        self.tableView.tableFooterView = UIView()
        self.tabBarController?.tabBar.isHidden = true
        self.navigationController?.navigationBar.setGradientBackground()
        self.navigationController?.navigationBar.tintColor = .white
        let textAttributes = [NSAttributedStringKey.foregroundColor:UIColor.white]
        self.navigationController?.navigationBar.titleTextAttributes = textAttributes
    }
    
    func putHirer() {
        let api = HirerManager()
        self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
        api.putHirer(hirer: setHirer(), successHandler: { (value) in
            self.stopAnimating()
            self.createChoiceAlert(title: "Sucesso", message: "Dados Atualizados com sucesso", successButton: { _ in
                self.dismiss(animated: true, completion: nil)
                self.navigationController?.popViewController(animated: true)
                self.tabBarController?.tabBar.isHidden = false

            })
        }, errorHandler: { (error) in
            self.stopAnimating()
            self.createAlert(title: "Erro", message: "Ops! Tivemos um erro com seus dados, tente novamente")
        })
    }
    
    func getHirer() {
        let api = HirerManager()
        self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
        if let contratanteId = Defaults.getUser._id {
            api.getHirer(contratanteId: contratanteId, successHandler: { (value) in
                self.stopAnimating()
                self.loadHirer(hirer: value)
            }, errorHandler: { (error) in
                self.stopAnimating()
                self.createAlert(title: "Erro", message: "Ops! Tivemos um erro com seus dados, tente novamente")
            })
        }
    }
    
    func isValidForm() -> Bool {
        if name.text != "" && nickname.text != "" && date.text != "" {
            return true
        }
        self.createAlert(title: "Erro", message: "Você deve preencher todos os campos")
        return false
    }
    
    func setHirer() -> Hirer{
        return Hirer(id: Defaults.getUser._id!,
                          nome: name.text!,
                          apelido: nickname.text!, dataNascimento: date.text!)
    }
    
    func loadHirer(hirer: Contratante) {
        self.name.text = hirer.nome
        self.nickname.text = hirer.apelido
        self.date.text = hirer.dataNascimento
        self.email.text = hirer.email
        self.imageProfile.sd_setImage(with: URL(string:hirer.avatar ?? ""), placeholderImage: UIImage(named: "placeholder.png"), options: .highPriority, completed: nil)
    }
    
    @IBAction func cancel(_ sender: Any) {
        self.navigationController?.popViewController(animated: true)
        self.tabBarController?.tabBar.isHidden = false
    }
    
    @IBAction func finish(_ sender: Any) {
        if isValidForm() {
            putHirer()
        }
    }
}
