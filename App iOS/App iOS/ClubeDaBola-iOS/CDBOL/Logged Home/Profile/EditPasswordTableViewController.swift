//
//  EditPasswordTableViewController.swift
//  CDBOL
//
//  Created by Paulo Rosa on 22/12/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit
import NVActivityIndicatorView

class EditPasswordTableViewController: UITableViewController, NVActivityIndicatorViewable {

    @IBOutlet weak var newPassword: UITextField!
    @IBOutlet weak var confirmNewPassword: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setUI()
    }
    
    func setUI() {
        self.tableView.tableFooterView = UIView()
        self.navigationController?.navigationBar.setGradientBackground()
        self.navigationController?.navigationBar.tintColor = .white
        let textAttributes = [NSAttributedStringKey.foregroundColor:UIColor.white]
        self.navigationController?.navigationBar.titleTextAttributes = textAttributes
    }
    
    func isValidForm() -> Bool {
        if newPassword.text != "" && confirmNewPassword.text != "" {
            if newPassword.text == confirmNewPassword.text {
                return true
            }
        }
        self.createAlert(title: "Erro", message: "Suas senhas não são iguais")
        return false
    }
    
    func resetPass() {
        let api = HirerManager()
        self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
        if let contratanteId = Defaults.getUser._id, let pass = self.newPassword.text {
            api.resetPass(contratanteId: contratanteId, password: pass, successHandler: { (value) in
                self.stopAnimating()
                self.createChoiceAlert(title: "Sucesso", message: "Senha atualizada com sucesso", successButton: { _ in
                    self.dismiss(animated: true, completion: nil)
                    self.navigationController?.popViewController(animated: true)
                })
            }, errorHandler: { (error) in
                self.stopAnimating()
                self.createAlert(title: "Erro", message: "Ops! Tivemos um erro com seus dados, tente novamente")
            })
        }
    }

    @IBAction func cancel(_ sender: Any) {
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction func finish(_ sender: Any) {
        if isValidForm() {
            resetPass()
        }
    }
    

}
