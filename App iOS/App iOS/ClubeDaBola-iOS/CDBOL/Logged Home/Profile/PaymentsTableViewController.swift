//
//  PaymentsTableViewController.swift
//  CDBOL
//
//  Created by Paulo Rosa on 15/12/2018.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit
import NVActivityIndicatorView

class PaymentsTableViewController: UITableViewController, NVActivityIndicatorViewable {

    @IBOutlet weak var cpf: UITextField!
    @IBOutlet weak var creditCard: UITextField!
    @IBOutlet weak var validate: UITextField!
    @IBOutlet weak var cvc: UITextField!
    @IBOutlet weak var name: UITextField!
    @IBOutlet weak var birthdayDate: UITextField!
    @IBOutlet weak var phone: UITextField!
    @IBOutlet weak var city: UITextField!
    @IBOutlet weak var address: UITextField!
    @IBOutlet weak var complement: UITextField!
    @IBOutlet weak var district: UITextField!
    @IBOutlet weak var number: UITextField!
    @IBOutlet weak var cep: UITextField!
    @IBOutlet weak var uf: UITextField!
    @IBOutlet weak var finishButton: UIBarButtonItem!
    @IBOutlet weak var email: UITextField!
    
    let viewModel = PaymentsViewModel()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setUI()
    }
    
    func setUI() {
        self.navigationController?.navigationBar.setGradientBackground()
        self.navigationController?.navigationBar.tintColor = .white
        let textAttributes = [NSAttributedStringKey.foregroundColor:UIColor.white]
        self.navigationController?.navigationBar.titleTextAttributes = textAttributes
    }

    @IBAction func cancelPressed(_ sender: Any) {
        self.dismiss(animated: true, completion: {})
    }
    
    @IBAction func concludedPressed(_ sender: Any) {
        if validateForm() {
            customerCall()
        } else {
            self.createAlert(title: "Campos Inválidos", message: "É necessario preencher todos os campos")
        }
    }
    
    func validateForm() -> Bool {
        if cpf.text != "" && creditCard.text != "" && validate.text != "" &&
           cvc.text != "" && name.text != "" && birthdayDate.text != "" &&
           phone.text != "" && city.text != "" && address.text != "" &&
           district.text != "" && complement.text != "" && email.text != "",
           number.text != "" && cep.text != "" && uf.text != "" {
            return true
        }
        return false
    }
    
    func customerCall() {
        self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
        let api = CustomerManager()
        let customer = setCustomer()
        api.setCustomer(customer: customer, successHandler: { _ in
            self.stopAnimating()
            self.createChoiceAlert(title: "Sucesso", message: "Cartão cadastrado com sucesso!", successButton: {_ in
                self.dismiss(animated: true, completion: nil)
                self.navigationController?.popViewController(animated: true)
            })

        }, errorHandler: { (error) in
            self.stopAnimating()
            self.createAlert(title: "Erro", message: "Ops! Tivemos um erro com essa solicitação")
        })
    }
    
    func setCustomer() -> Customer {
        let cpfNoMask = viewModel.cpfNoMask(cpf: cpf.text!)
        let tax = TaxDocument(type: "CPF", number: cpfNoMask)
        
        let phoneArray = viewModel.phoneSplit(validate: self.phone.text!)
        let phone = Phone(countryCode: "55", areaCode: phoneArray[0], number: phoneArray[1])
        
        let cep = viewModel.cepNoMask(cep: self.cep.text!)
        
        let address = IngAddress(city: city.text!,
                                 district: district.text!,
                                 street: self.address.text!,
                                 streetNumber: number.text!,
                                 zipCode: cep, state: uf.text!,
                                 country: "BRA", complement: complement.text!)
        
        let birthday = viewModel.dateChangeMask(date: self.birthdayDate.text!)
        
        let holder = Holder(fullname: name.text!, birthdate: birthday, taxDocument: tax, billingAddress: address, phone: phone)
        let creditCard = viewModel.validateCard(holder: holder, validate: validate.text!,
                                                number: self.creditCard.text!, cvc: cvc.text!)
        let funding = FundingInstrument(method: "CREDIT_CARD", creditCard: creditCard)
        return Customer(ownID: Defaults.getUser._id!,
                                 fullname: name.text!,
                                 email: self.email.text!,
                                 birthDate: birthday,
                                 taxDocument: tax,
                                 phone: phone,
                                 shippingAddress: address,
                                 fundingInstrument: funding)
    }
}
