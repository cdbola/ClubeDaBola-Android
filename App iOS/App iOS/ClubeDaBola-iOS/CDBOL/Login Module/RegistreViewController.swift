//
//  RegistreViewController.swift
//  CDBOL
//
//  Created by Bruno Faganello Neto on 09/06/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit
import SkyFloatingLabelTextField
import InputMask
import NVActivityIndicatorView

class RegistreViewController: UITableViewController, NVActivityIndicatorViewable {
    
    @IBOutlet weak var confirmButton: UIButton!
    @IBOutlet weak var cancelButton: UIButton!
    @IBOutlet weak var nameField: SkyFloatingLabelTextField!
    @IBOutlet weak var nickNameField: SkyFloatingLabelTextField!
    @IBOutlet weak var birthdayField: SkyFloatingLabelTextField!
    @IBOutlet weak var emailField: SkyFloatingLabelTextField!
    @IBOutlet weak var passwordField: SkyFloatingLabelTextField!
    var listener:MaskedTextFieldDelegate = MaskedTextFieldDelegate()
    var completed:Bool = false
    var birthdayText:String = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setUI()
        setConfirmButton()
    }
    
    public func setUI() {
        self.navigationController?.navigationBar.setGradientBackground()
        cancelButton.addTargetClosure { (_) in
            self.navigationController?.popViewController(animated: true)
        }
        listener.delegate = self
        listener.affineFormats = ["[00]{/}[00]{/}[0000]"]
        birthdayField.delegate = listener
    }

    public func setConfirmButton() {
        confirmButton.addTargetClosure { (_) in
            if self.nameField.text!.isEmpty || self.nickNameField.text!.isEmpty || !self.completed || !self.isValidEmail(testStr: self.emailField.text!) || self.passwordField.text!.isEmpty{
                self.createAlert(title: "Cadastro Incompleto", message: "Preencha todos os campos corretamente")
                return
            }

            if let nick = self.nickNameField.text, let email = self.emailField.text, let name = self.nameField.text,
                let pass = self.passwordField.text {
                self.signUp(nickName: nick, birthday: self.birthdayText, email: email, name: name, password: pass)
            }
        }
    }
    
    private func signUp(nickName: String,
                        birthday: String,
                        email: String,
                        name: String,
                        password: String) {
        let api = LoginManagerLocal()
        self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
        api.requestNewUser(user: self.createUser(nickName: nickName,
                                                 birthday: self.birthdayText,
                                                 email: email,
                                                 name: name,
                                                 password: password),
                           successHandler: { (value) in
                            self.login(api: api, email: email, password: password)
        }) { (error) in
            self.stopAnimating()
            self.createAlert(title: "Ops!", message: "Parece que tem alguem já usando esse email ou apelido")
        }
    }
    
    private func login(api: LoginManagerLocal, email: String, password: String) {
        api.requestLogin(email: email, password: password, successHandler: { (value) in
            self.saveUser(value: value)
            self.stopAnimating()
            self.sendNotification(api: api)
            self.getHirer()
        }, errorHandler: { (error) in
            self.stopAnimating()
            self.createAlert(title: "Usuário Criado", message: "Porem infelizmente não conseguimos logar, tente logar com seu novo usuario")
        })
    }
    
    private func sendNotification(api:LoginManagerLocal){
        api.requestPostNotification(user: UserSingleton.shared, successHandler: { (value) in
            
        }) { (error) in
            
        }
    }

    private func saveUser(value:User) {
        UserSingleton.shared.Id = value.contratante.id
        UserSingleton.shared.NomeCompleto = value.contratante.nome
        UserSingleton.shared.Email = value.contratante.email
        UserSingleton.shared.Apelido = value.contratante.apelido
        UserSingleton.shared.Token = value.token
        createUserDefaults(value: value)
    }
    
    func createUserDefaults(value: User) {
        Defaults.clearUserData()
        Defaults.saveUser(value.token,value.contratante.id)
    }

    public func createUser(nickName: String, birthday: String, email: String,
                           name: String, password: String) -> NewUser {
        let user = NewUser(nome: name, apelido: nickName, imagem: "user.png", dataNascimento: birthday, email: email, senha: password)
        return user
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
                let storyboard = UIStoryboard(name: "Main", bundle: nil)
                let controller = storyboard.instantiateViewController(withIdentifier: "RequestLocationViewController") as! RequestLocationViewController
                self.present(controller, animated: true, completion: nil)
                
            }, errorHandler: { (error) in
                self.stopAnimating()
                self.createAlert(title: "Erro", message: "Ops! Tivemos um erro com seus dados, tente novamente")
            })
        }
    }
}

extension RegistreViewController: MaskedTextFieldDelegateListener {
    func textField(_ textField: UITextField, didFillMandatoryCharacters complete: Bool, didExtractValue value: String) {
        completed = complete
        birthdayText = value
    }
    
    func isValidEmail(testStr:String) -> Bool {
        let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"
        
        let emailTest = NSPredicate(format:"SELF MATCHES %@", emailRegEx)
        return emailTest.evaluate(with: testStr)
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
}
