//
//  CreatePersonBankViewController.swift
//  CDBOL
//
//  Created by Paulo Rosa on 29/10/2018.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit
import InputMask
import RxSwift
import NVActivityIndicatorView

class CreatePersonBankViewController: UIViewController, NVActivityIndicatorViewable {

    @IBOutlet weak var viewStepOne: UIView! {
        didSet {
            RoundedHelper.roundViewNoShadow(view: viewStepOne)
        }
    }
    @IBOutlet weak var viewStepTwo: UIView! {
        didSet {
            RoundedHelper.roundViewNoShadow(view: viewStepTwo)
        }
    }
    @IBOutlet weak var viewStepThree: UIView! {
        didSet {
            RoundedHelper.roundViewNoShadow(view: viewStepThree)
        }
    }
    @IBOutlet weak var bank: UITextField!
    @IBOutlet weak var agency: UITextField!
    @IBOutlet weak var account: UITextField!
    @IBOutlet weak var cpf: UITextField!
    @IBOutlet weak var nextFormButton: UIBarButtonItem! {
        didSet {
            nextFormButton.action = #selector(submitCreate)
            nextFormButton.target = self
        }
    }
    
    var listener = MaskedTextFieldDelegate()
    var completed:Bool = false
    var cpfText:String = ""
    
    var pickerViewBank = UIPickerView()
    var pickerDatasource: PickerBankData!

    var isLoading = true
    var transactionModel = PersonModel()
    let disposeBag = DisposeBag()

    override func viewDidLoad() {
        super.viewDidLoad()
        setUI()
        setBankType()
        bindUI()
    }

    func setUI() {
        self.navigationController?.navigationBar.setGradientBackground()
        self.navigationController?.navigationBar.tintColor = .white
        let textAttributes = [NSAttributedStringKey.foregroundColor:UIColor.white]
        self.navigationController?.navigationBar.titleTextAttributes = textAttributes
        
        listener.delegate = self
        listener.affineFormats = ["[000]{.}[000]{.}[000]{-}[00]"]
        cpf.delegate = listener
        
    }
    
    func bindUI()  {
        pickerDatasource.choose.asObservable().subscribe(onNext: { [self] value in
            if !self.isLoading {
                self.bank.text = value.name
            }
        }).disposed(by: disposeBag)
        isLoading = false
    }
    
    func setBankType(){
        pickerDatasource = PickerBankData()
        pickerViewBank.delegate = pickerDatasource
        bank.inputView = pickerViewBank
    }

    @objc func submitCreate() {
        if validateForm() {
            setTransaction()
            self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
            if transactionModel.type == TypePerson.GoalKeeper {
                setGoalKeeper()
            } else {
                setReferee()
            }
        }
    }
    
    func setTransaction() {
        if let account = self.account.text, let agency = self.agency.text, let bank = self.bank.text, let cpf = self.cpf.text {
            self.transactionModel.bank = bank
            self.transactionModel.account = account
            self.transactionModel.agency = agency
            self.transactionModel.cpf = cpf
        }
    }
    
    func setGoalKeeper() {
        let api = PersonManager()
        let goalKeeper = GoalKeeper(_id: nil, personModel: self.transactionModel, contratante: Defaults.getUser._id!, celular: "MOCK", avatar: "Mock", notificacoes: true, raio: 10)
        api.requestNewGoalKeeper(goalkeeper: goalKeeper, successHandler: { (value) in
            self.setWallet(api: api)
        }, errorHandler: { (error) in
            self.stopAnimating()
            self.createAlert(title: "Erro", message: "Ops! Tivemos um erro com essa solicitação")
        })
    }
    
    func setReferee() {
        let api = PersonManager()
        let referee = GoalKeeper(_id: nil, personModel: self.transactionModel, contratante: Defaults.getUser._id!, celular: "MOCK", avatar: "Mock", notificacoes: true, raio: 10)
        api.requestNewReferee(goalkeeper: referee, successHandler: { (value) in
            self.setWallet(api: api)
        }, errorHandler: { (error) in
            self.stopAnimating()
            self.createAlert(title: "Erro", message: "Ops! Tivemos um erro com essa solicitação")
        })
    }
    
    func setWallet(api: PersonManager) {
        let contratanteId = Defaults.getUser._id
        if let contId = contratanteId {
            api.requestNewWallet(contratanteId: contId, successHandler:{ (value) in
                self.stopAnimating()
                self.dismiss(animated: true, completion: {})
            }, errorHandler: { (error) in
                self.stopAnimating()
                self.createAlert(title: "Erro", message: "Ops! Tivemos um erro com essa solicitação")
            })
        }
    }
    
    func validateForm() -> Bool {
        if bank.text != "" && agency.text != "" && account.text != "" && cpf.text != "" {
            return true
        } else {
            createAlert(title: "Campos vazios", message: "Você deve preencher todos os campos!")
            return false
        }
    }
}

extension CreatePersonBankViewController: MaskedTextFieldDelegateListener {
    func textField(_ textField: UITextField, didFillMandatoryCharacters complete: Bool, didExtractValue value: String) {
            completed = complete
            cpfText = value
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        guard let text = textField.text else { return true }
        if textField.tag == 2 {
            let newLength = text.count + string.count - range.length
            return newLength <= 4
        } else if textField.tag == 3 {
            let newLength = text.count + string.count - range.length
            return newLength <= 6
        } else if textField.tag == 4 {
            let newLength = text.count + string.count - range.length
            return newLength <= 11
        }
        return true
    }
}
