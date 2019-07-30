//
//  CreatePersonMeasure.swift
//  CDBOL
//
//  Created by Paulo Rosa on 25/10/2018.
//  Copyright © 2018 Faganello. All rights reserved.
//

import Foundation
import UIKit
import RxSwift

class CreatePersonMeasure: UIViewController {

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

    @IBOutlet weak var glovesField: UITextField!
    @IBOutlet weak var tshirtField: UITextField!
    @IBOutlet weak var genderField: UITextField!
    @IBOutlet weak var nextFormButton: UIBarButtonItem! {
        didSet {
            // Mock - true
            //nextFormButton.isEnabled = true
        }
    }
    
    var pickerViewGender = UIPickerView()
    var pickerDatasource: PickerGenderData!
    
    var pickerViewTshirt = UIPickerView()
    var pickerDatasourceShirt: PickerShirtData!
    
    var pickerViewGloves = UIPickerView()
    var pickerDatasourceGloves: PickerGlovesData!
    
    var isLoading = true
    let transactionModel = PersonModel()
    let disposeBag = DisposeBag()

    
    override func viewDidLoad() {
        super.viewDidLoad()
        setUI()
        setGenderType()
        setTshirtType()
        setGlovesType()
        bindUI()
        if let type = UserDefaults.standard.string(forKey: "createPerson") {
            self.transactionModel.setType(typeString: type)
        }
    }
    
    func setUI() {
        self.navigationController?.navigationBar.setGradientBackground()
        self.navigationController?.navigationBar.tintColor = .white
        let textAttributes = [NSAttributedStringKey.foregroundColor:UIColor.white]
        self.navigationController?.navigationBar.titleTextAttributes = textAttributes
    }
    
    func bindUI()  {
        pickerDatasource.choose.asObservable().distinctUntilChanged().subscribe(onNext: { [self] value in
            if !self.isLoading {
                self.genderField.text = value
                self.transactionModel.genero = value
                self.validateForm()
            }
        }).disposed(by: disposeBag)
        
        pickerDatasourceShirt.choose.asObservable().distinctUntilChanged().subscribe(onNext: { [self] value in
            if !self.isLoading {
                self.tshirtField.text = value
                self.transactionModel.tamanhoCamiseta = value
                self.validateForm()
            }
        }).disposed(by: disposeBag)
        
        pickerDatasourceGloves.choose.asObservable().distinctUntilChanged().subscribe(onNext: { [self] value in
            if !self.isLoading {
                self.glovesField.text = value
                self.transactionModel.tamanhoLuva = value
                self.validateForm()
            }
        }).disposed(by: disposeBag)
        isLoading = false
    }

    func setGenderType(){
        pickerDatasource = PickerGenderData()
        pickerViewGender.delegate = pickerDatasource
        genderField.inputView = pickerViewGender
    }
    
    func setTshirtType(){
        pickerDatasourceShirt = PickerShirtData()
        pickerViewTshirt.delegate = pickerDatasourceShirt
        tshirtField.inputView = pickerViewTshirt
    }
    
    func setGlovesType()  {
        pickerDatasourceGloves = PickerGlovesData()
        pickerViewGloves.delegate = pickerDatasourceGloves
        glovesField.inputView = pickerViewGloves
    }
    
    func validateForm() {
        if glovesField.text != "" && tshirtField.text != "" && genderField.text != "" {
            nextFormButton.isEnabled = true
        }
    }
    
    @IBAction func cancelButtonPressed(_ sender: Any) {
        self.dismiss(animated: true, completion: {})
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "segueContato"{
            if let vc = segue.destination as? CreatePersonContactViewController {
                vc.transactionModel = self.transactionModel
            }
        }
    }
}

extension CreatePersonMeasure: UITextFieldDelegate {
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
}
