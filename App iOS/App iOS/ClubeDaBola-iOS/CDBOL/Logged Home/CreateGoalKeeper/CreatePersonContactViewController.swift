//
//  CreatePersonContactViewController.swift
//  CDBOL
//
//  Created by Paulo Rosa on 29/10/2018.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit
import InputMask


class CreatePersonContactViewController: UIViewController {

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
    
    @IBOutlet weak var Address: UITextField!
    @IBOutlet weak var addressButton: UIButton!
    @IBOutlet weak var phone: UITextField!
    @IBOutlet weak var nextFormButton: UIBarButtonItem!
    
    var transactionModel: PersonModel? = nil
    var listener:MaskedTextFieldDelegate = MaskedTextFieldDelegate()
    var completed:Bool = false
    var phoneText:String = ""


    override func viewDidLoad() {
        super.viewDidLoad()
        setUI()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        self.addressButton.addTargetClosure { (_) in
            let storyboard = UIStoryboard(name: "Logged", bundle: nil)
            let controller = storyboard.instantiateViewController(withIdentifier: "SearchPlaceViewController") as! SearchPlaceViewController
            controller.delegate = self
            self.navigationController?.pushViewController(controller, animated: true)
        }
    }
    
    public func setUI() {
        self.navigationController?.navigationBar.setGradientBackground()
        self.navigationController?.navigationBar.tintColor = .white
        let textAttributes = [NSAttributedStringKey.foregroundColor:UIColor.white]
        self.navigationController?.navigationBar.titleTextAttributes = textAttributes
        listener.delegate = self
        listener.affineFormats = ["{(}[00]{)}[00000]{-}[0000]"]
        phone.delegate = listener
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        setTransaction()
        if segue.identifier == "segueBank"  && validateForm() {
            if let vc = segue.destination as? CreatePersonBankViewController, let transaction = self.transactionModel {
                vc.transactionModel = transaction
            }
        }
    }
    
    func setTransaction() {
        if let address = self.Address.text, let phone = self.phone.text {
            self.transactionModel?.endereco?.lagradouro = address
            self.transactionModel?.telefone = phone
        }
    }

    func validateForm() -> Bool {
        if Address.text != "" && phone.text != ""  {
            return true
        } else {
            createAlert(title: "Campos vazios", message: "Você deve preencher todos os campos!")
            return false
        }
    }

}


extension CreatePersonContactViewController: MaskedTextFieldDelegateListener {
    func textField(_ textField: UITextField, didFillMandatoryCharacters complete: Bool, didExtractValue value: String) {
        completed = complete
        phoneText = value
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
}

extension CreatePersonContactViewController: SearchPlaceViewControllerDelegate{
    func retriveLocation(candidate: Candidate) {
        self.transactionModel?.endereco?.lagradouro = candidate.formattedAddress
        self.transactionModel?.endereco?.loc = LOC(coordinates: [
            candidate.geometry.location.lat, candidate.geometry.location.lng
            ])
        self.Address.text = candidate.formattedAddress
    }
}
