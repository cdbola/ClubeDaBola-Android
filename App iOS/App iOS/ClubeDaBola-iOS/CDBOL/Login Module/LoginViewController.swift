//
//  LoginViewController.swift
//  CDBOL
//
//  Created by Bruno Faganello Neto on 09/06/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit
import SkyFloatingLabelTextField
import NVActivityIndicatorView
import FBSDKCoreKit
import FBSDKLoginKit

class LoginViewController: UIViewController, NVActivityIndicatorViewable {
    
    @IBOutlet weak var loginButton: UIButton! {
        didSet {
            loginButton.layer.cornerRadius = 5
            loginButton.layer.masksToBounds = true
        }
    }
    @IBOutlet weak var facebooLoginButton: UIButton! {
        didSet {
            facebooLoginButton.layer.cornerRadius = 5
            facebooLoginButton.layer.masksToBounds = true
            facebooLoginButton.layer.borderWidth = 1
            facebooLoginButton.layer.borderColor = UIColor.gray.cgColor
            facebooLoginButton.backgroundColor = .clear
        }
    }
    @IBOutlet weak var mailField: SkyFloatingLabelTextField!
    @IBOutlet weak var passwordField: SkyFloatingLabelTextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setLoginButton()
        
        facebooLoginButton.addTargetClosure { (_) in
            var newUser:NewUser = NewUser()
            var name = ""
            var email = ""
            
            let loginManager = FBSDKLoginManager()
            loginManager.logOut()
            loginManager.logIn(withReadPermissions: ["public_profile","email","user_friends"], from: self, handler: { (loginResult, error) in
                if error != nil {
                    self.createAlert(title: "Problemas para logar com o Facebook", message: "Tente novamente")
                }else if (loginResult?.isCancelled ?? true){
                    self.createAlert(title: "Problemas para logar com o Facebook", message: "Tente novamente")
                }else {
                    FBSDKGraphRequest(graphPath: "me", parameters: ["fields" : "email"])?.start(completionHandler: { (connection, result, error) in
                        if error != nil {
                            self.createAlert(title: "Problemas para logar com o Facebook", message: "Tente novamente")
                        }
                        else {
                            guard let info = result as? [String: Any] else { return }
                            if let emailAux = info["email"] as? String{
                                email = emailAux
                            }else{
                                return
                            }
                            
                            FBSDKGraphRequest(graphPath: "me", parameters: ["fields":"name"])?.start(completionHandler: { (connection, result, error) in
                                guard let info = result as? [String: Any] else { return }
                                if let nameAux = info["name"] as? String{
                                   name = nameAux
                                    newUser = NewUser(nome: name, apelido: nameAux, imagem: "\(name).png", dataNascimento: "01/01/1990", email: email)
                                    let api = LoginManagerLocal()
                                    api.requestNewUserFacebook(user: newUser, successHandler: { (response) in
                                        self.createUserDefaults(value: response)
                                        api.requestPostNotification(user: self.createSingletonUser(value: response), successHandler: { (value) in
                                           self.getHirer()
                                        }, errorHandler: { (error) in
                                           self.createAlert(title: "Campos Inválidos", message: "Email ou senha estão errados")
                                        })
                                    }, errorHandler: { (error) in
                                        self.createAlert(title: "Campos Inválidos", message: "Email ou senha estão errados")
                                    })
                                }
                                else {
                                    return
                                }
                            })
                        }
                    })
                }
            })
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.navigationBar.setGradientBackground(colors: [.black])
    }

    func setLoginButton() {
        loginButton.applyGradient()
        loginButton.addTargetClosure { (_) in
            let api = LoginManagerLocal()
            self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
            if let email = self.mailField.text, let password = self.passwordField.text {
                api.requestLogin(email: email, password: password, successHandler: { (value) in
                    self.createUserDefaults(value: value)
                    api.requestPostNotification(user: self.createSingletonUser(value: value), successHandler: { (value) in
                        self.getHirer()
                    }, errorHandler: { (error) in
                        self.createAlert(title: "Campos Inválidos", message: "Email ou senha estão errados")
                    })
                    
                    
                }, errorHandler: { (error) in
                    self.stopAnimating()
                    self.createAlert(title: "Campos Inválidos", message: "Email ou senha estão errados")
                })

            }
        }
    }
    
    func getHirer() {
        let api = HirerManager()
        self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
        let contratanteId = Defaults.getUser._id
        if let contId = contratanteId {
            api.getHirer(contratanteId: contId, successHandler: { (value) in
                self.stopAnimating()
                if let costumer = value.customerId  {
                    Defaults.saveCustomerId(costumer)
                }
                self.stopAnimating()
                let storyboard = UIStoryboard(name: "Logged", bundle: nil)
                let controller = storyboard.instantiateViewController(withIdentifier: "InitialHome") as! CDTabBarViewController
                self.present(controller, animated: true, completion: nil)
                
            }, errorHandler: { (error) in
                self.stopAnimating()
                self.createAlert(title: "Erro", message: "Ops! Tivemos um erro com seus dados, tente novamente")
            })
        }
    }
    
    func createSingletonUser(value: User) -> UserSingleton{
        UserSingleton.shared.Id = value.contratante.id
        UserSingleton.shared.NomeCompleto = value.contratante.nome
        UserSingleton.shared.Email = value.contratante.email
        UserSingleton.shared.Apelido = value.contratante.apelido
        UserSingleton.shared.Token = value.token
        return UserSingleton.shared
    }
    
    func createUserDefaults(value: User) {
        Defaults.clearAll()
        Defaults.saveUser(value.token,value.contratante.id)
    }
}

extension UIViewController{
    func createAlert(title:String, message:String){
        let alert = UIAlertController(title: title, message: message, preferredStyle: UIAlertControllerStyle.alert)
        alert.addAction(UIAlertAction(title: "Ok", style: .default, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }
    
    func createChoiceAlert(title:String, message:String, successButton: @escaping (UIAlertAction) -> Void) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: UIAlertControllerStyle.alert)
        alert.addAction(UIAlertAction(title: "Confirmar", style: .default, handler: successButton))
        alert.addAction(UIAlertAction(title: "Cancelar", style: .cancel, handler: nil))
        self.present(alert, animated: true, completion: nil)
    }
}

extension LoginViewController: UITextFieldDelegate {
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }

}
