//
//  PaymentsCardTableViewController.swift
//  CDBOL
//
//  Created by Paulo Leon Aquino da Rosa on 19/12/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit
import NVActivityIndicatorView

class PaymentsCardTableViewController: UITableViewController, NVActivityIndicatorViewable {

    @IBOutlet weak var name: UITextField!
    @IBOutlet weak var cpf: UITextField!
    @IBOutlet weak var phone: UITextField!
    @IBOutlet weak var creditCard: UITextField!
    @IBOutlet weak var cvc: UITextField!
    @IBOutlet weak var validate: UITextField!
    @IBOutlet weak var birthday: UITextField!

    let viewModel = PaymentsNewCardViewModel()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        viewModel.customerId = Defaults.getCustomerId
        setUI()
    }
    
    func setUI() {
        self.tableView.tableFooterView = UIView()
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
            customerCardCall()
        } else {
            self.createAlert(title: "Campos Inválidos", message: "É necessario preencher todos os campos")
        }
    }
    
    func validateForm() -> Bool {
        if cpf.text != "" && creditCard.text != "" && validate.text != "" &&
            cvc.text != "" && name.text != "" && birthday.text != "" &&
            phone.text != "" {
            return true
        }
        return false
    }
    
    func setCustomerCreditCard() -> CustomerNewCard {
        let cpfNoMask = viewModel.cpfNoMask(cpf: cpf.text!)
        let tax = TaxDocument(type: "CPF", number: cpfNoMask)
        
        let phoneArray = viewModel.phoneSplit(validate: self.phone.text!)
        let phone = Phone(countryCode: "55", areaCode: phoneArray[0], number: phoneArray[1])
        
        
        let birthday = viewModel.dateChangeMask(date: self.birthday.text!)
        let holder = Holder(fullname: self.name.text!, birthdate: birthday, taxDocument: tax, billingAddress: nil, phone: phone)
        let creditCard = viewModel.validateCard(holder: holder, validate: validate.text!,
                                                number: self.creditCard.text!, cvc: cvc.text!)
       return CustomerNewCard(customerID: viewModel.customerId, creditCard: creditCard)
    }
    
    func customerCardCall() {
        self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
        let api = CustomerManager()
        let customer = setCustomerCreditCard()
        api.setCreditCard(customerCreditCard: customer, successHandler: { _ in
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
    

}
