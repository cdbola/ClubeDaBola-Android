//
//  NewMatchViewController.swift
//  CDBOL
//
//  Created by Bruno Faganello Neto on 11/08/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit
import NVActivityIndicatorView

enum TypePerson {
    case GoalKeeper
    case Referee
}

enum GoalKeeperPrice:Int {
    case GoalKeeperOneHour = 3000
    case GoalKeeperOneHourHalf = 4500
    case GoalKeeperTwoHour = 6000
}

enum RefereePrice:Int {
    case RefereeOneHour = 5000
    case RefereeOneHourHalf = 7500
    case RefereeTwoHour = 10000
}

class NewMatchViewController: UITableViewController,NVActivityIndicatorViewable {

    @IBOutlet weak var typeLabel: UILabel!
    @IBOutlet weak var moneyLabel: UILabel!{
        didSet{
            self.moneyLabel.text = "R$ 0,00"
        }
    }
    @IBOutlet weak var imageType: UIImageView!
    @IBOutlet weak var namePlayer: UILabel!
    
    @IBOutlet weak var addressButton: UIButton!
    
    @IBOutlet weak var searchPlayer: UIButton!
    @IBOutlet weak var creditCardButton: UIButton!
    @IBOutlet weak var contractButton: UIButton!{
        didSet{
            contractButton.layer.cornerRadius = 5
            contractButton.layer.masksToBounds = true
            contractButton.backgroundColor = UIColor.lightGray
            contractButton.isEnabled = false
        }
    }
    
    @IBOutlet weak var gameTypeField: UITextField!{
        didSet{
           gameTypeField.delegate = self
        }
    }
    @IBOutlet weak var genderTypeField: UITextField!{
        didSet{
            genderTypeField.delegate = self
        }
    }
    @IBOutlet weak var dateTimeField: UITextField!{
        didSet{
            dateTimeField.delegate = self
        }
    }
   
    @IBOutlet weak var startTime: UITextField!{
        didSet{
            startTime.delegate = self
        }
    }
    @IBOutlet weak var endTime: UITextField!{
        didSet{
            endTime.isEnabled = false
            endTime.delegate = self
        }
    }
    @IBOutlet weak var observationField: UITextField!{
        didSet{
            observationField.delegate = self
        }
    }
    
    @IBOutlet weak var addressField: UITextField!{
        didSet{
            addressField.delegate = self
            addressField.isEnabled = false
        }
    }
    var address:Candidate?
    
    let pickerGameType = ["SALÃO", "CAMPO" , "SOCIETY" , "AREIA"]
    let pickerGenderType = ["MASCULINO", "FEMININO"]
    
    
    var pickerViewGender = UIPickerView()
    var pickerViewType = UIPickerView()
    var pickerEndType = UIPickerView()
    
    var datePickerView: UIDatePicker = UIDatePicker()
    var hourPickerView: UIDatePicker = UIDatePicker()
    
    var endTimePicker:[String]!
    var type: TypePerson?
    var preferenciaContratante:String?
    var price:Int = 0
    var cvc:String = ""
    var customerID = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = "Nova Contratação"
        self.tabBarController?.tabBar.isHidden = true
        navigationItem.backBarButtonItem = UIBarButtonItem(title: "", style: .plain, target: nil, action: nil)
        self.setGameType()
        self.setGenderType()
        self.setDateType()
        self.setHourTimer()
        self.setupLabelPlayer()
    }
    
    
    
    override func viewDidAppear(_ animated: Bool) {
        self.searchPlayer.addTargetClosure { (_) in
            let storyboard = UIStoryboard(name: "Logged", bundle: nil)
            let controller = storyboard.instantiateViewController(withIdentifier: "SearchPlayerViewController") as! SearchPlayerViewController
            controller.delegate = self
            controller.type = self.type
            self.navigationController?.pushViewController(controller, animated: true)
        }
        
        self.addressButton.addTargetClosure { (_) in
            let storyboard = UIStoryboard(name: "Logged", bundle: nil)
            let controller = storyboard.instantiateViewController(withIdentifier: "SearchPlaceViewController") as! SearchPlaceViewController
            controller.delegate = self
            self.navigationController?.pushViewController(controller, animated: true)
        }
        
        self.creditCardButton.addTargetClosure { (_) in
            if self.customerID != "" {
                let storyboard = UIStoryboard(name: "Payments", bundle: nil)
                let controller = storyboard.instantiateViewController(withIdentifier: "IdHome")
                self.present(controller, animated: true, completion: nil)
            } else {
                let storyboard = UIStoryboard(name: "Payments", bundle: nil)
                let controller = storyboard.instantiateViewController(withIdentifier: "InitialHome")
                self.present(controller, animated: true, completion: nil)
            }
        }
        
        contractButton.addTargetClosure { (_) in
            self.showAlertCVC() 
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        let contratanteId = Defaults.getUser._id
        if let contId = contratanteId {
            let api = HirerManager()
            api.getHirer(contratanteId: contId, successHandler: { (value) in
                self.stopAnimating()
                if let costumer = value.customerId  {
                    self.customerID = value.customerId ?? ""
                    Defaults.saveCustomerId(costumer)
                }
            }, errorHandler: { (error) in
                self.stopAnimating()
                self.createAlert(title: "Erro", message: "Ops! Tivemos um erro com seus dados, tente novamente")
            })
        }
    }
    
    
    func setupLabelPlayer() {
        if type == .GoalKeeper{
            typeLabel.text = "Goleiro"
            imageType.image = UIImage(named: "Luva Laranja")
        }else{
            typeLabel.text = "Árbitro"
            imageType.image = UIImage(named: "Arbitro laranja")
        }
    }
    
    func setGenderType(){
        pickerViewGender.delegate = self
        genderTypeField.inputView = pickerViewGender
    }
    
    func setGameType(){
        pickerViewType.delegate = self
        gameTypeField.inputView = pickerViewType
    }
    
    func setDateType(){
        datePickerView.datePickerMode = .date
        datePickerView.minimumDate = Date()
        datePickerView.locale = Locale(identifier: "pt_BR")
        dateTimeField.inputView = datePickerView
        datePickerView.addTarget(self, action: #selector(NewMatchViewController.handelDatePicker), for: UIControlEvents.valueChanged)
    }
    
    func setHourTimer(){
        hourPickerView.datePickerMode = .time
        hourPickerView.minuteInterval = 30
        hourPickerView.locale = Locale(identifier: "pt_BR")
        startTime.inputView = hourPickerView
        hourPickerView.addTarget(self, action: #selector(NewMatchViewController.handelStartPicker), for: UIControlEvents.valueChanged)
    }
    
    func setEndHour(){
        pickerEndType.delegate = self
        endTime.inputView = pickerEndType
    }
    
    func setPrice(value:Int){
        if type == .GoalKeeper{
            if value == 0{
                price = GoalKeeperPrice.GoalKeeperOneHour.rawValue
                moneyLabel.text = "R$ 30,00"
            }else if value == 1{
                price = GoalKeeperPrice.GoalKeeperOneHourHalf.rawValue
                moneyLabel.text = "R$ 45,00"
            }else if value == 2{
                price = GoalKeeperPrice.GoalKeeperTwoHour.rawValue
                moneyLabel.text = "R$ 60,00"
            }
        }else if type == .Referee{
            if value == 0{
                price = RefereePrice.RefereeOneHour.rawValue
                moneyLabel.text = "R$ 50,00"
            }else if value == 1{
                price = RefereePrice.RefereeOneHourHalf.rawValue
                moneyLabel.text = "R$ 75,00"
            }else if value == 2{
                price = RefereePrice.RefereeTwoHour.rawValue
                moneyLabel.text = "R$ 100,00"
            }
        }
    }
    
    func showAlertCVC(){
        let alert = UIAlertController(title: "Por motivos de segurança não salvamos o seu CVC", message: "Para a compra ser efetuada por favor digite seu CVC", preferredStyle: .alert)
        //2. Add the text field. You can configure it however you need.
        
        alert.addTextField { (textField) in
            
            textField.placeholder = "Digite o CVC do cartão"
            textField.keyboardType = .numberPad
        }
        
        // 3. Grab the value from the text field, and print it when the user clicks OK.
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: { [weak alert] (_) in
            let textField = alert?.textFields![0] // Force unwrapping because we know it exists.
            if textField?.text?.count != 3 {
                self.dismiss(animated: true, completion: nil)
            }else{
                self.cvc = textField!.text!
                self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
                self.sendNewMatch()
            }
            
        }))
        
        alert.addAction(UIAlertAction(title: "Cancelar", style: .cancel, handler: nil))
        
        
        // 4. Present the alert.
        self.present(alert, animated: true, completion: nil)
    }
    
    private func sendNewMatch(){
        var typePerson = ""
        var random = true
        if type == .GoalKeeper{
            typePerson = "G"
        }else{
            typePerson = "A"
        }
        
        if preferenciaContratante == nil{
            random = true
        }else{
            random = false
        }
        
        let newMatch = NewMatch(customerId: self.customerID, contratante: Defaults.getUser._id ?? "", tipoJogo: gameTypeField.text!, genero: genderTypeField.text!, preferenciaContratante: preferenciaContratante, data: dateTimeField.text!, endereco: Endereco(loc: LOC(coordinates: [address!.geometry.location.lat,address!.geometry.location.lat]), bairro: "", complemento: "", lagradouro: address!.formattedAddress, numero: ""), comeca: startTime.text!, termina: endTime.text!, observacoes: observationField.text!, tipoPessoa: typePerson, aleatorio: random, valor: price, cvc: cvc)
        
        let api = MatchManager()
        
        api.createMatch(match: newMatch, successHandler: { (value) in
            self.stopAnimating()
            self.dismiss(animated: true, completion: nil)
            self.navigationController?.popViewController(animated: true)
        }) { (error) in
            self.stopAnimating()
            self.createAlert(title: "Tivemos um problema para criar a partida", message: "Tente mais tarde")
        }
        
    }
}


extension NewMatchViewController:UIPickerViewDataSource, UIPickerViewDelegate {
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView( _ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if pickerView == pickerViewGender{
            return pickerGenderType.count
        }
        
        if pickerView == pickerViewType{
            return pickerGameType.count
        }
        
        if pickerView == pickerEndType{
            return endTimePicker.count
        }
        
        return 0
    }
    
    func pickerView( _ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        
        if pickerView == pickerViewGender{
            return pickerGenderType[row]
        }
        
        if pickerView == pickerViewType{
            return pickerGameType[row]
        }
        
        if pickerView == pickerEndType{
            return endTimePicker[row]
        }
        
        return nil
    }
    
    func pickerView( _ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if pickerView == pickerViewGender{
            self.genderTypeField.text = pickerGenderType[row]
        }
        
        if pickerView == pickerViewType{
            self.gameTypeField.text = pickerGameType[row]
        }
        
        if pickerView == pickerEndType{
            self.endTime.text = endTimePicker[row]
            setPrice(value: row)
        }
        
    }
    
   @objc func handelDatePicker()
    {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "dd-MM-yyyy"
        dateTimeField.text = dateFormatter.string(from: datePickerView.date)
    }
    
    @objc func handelStartPicker()
    {
        self.endTime?.text = nil
        self.moneyLabel.text = "R$ 0,00"
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "HH:mm"
        startTime.text = dateFormatter.string(from: hourPickerView.date)
        self.endTime.isEnabled = true
        
        
        
        let calendar = Calendar.current
        let Halfminutes = calendar.date(byAdding: .minute, value: 60, to: hourPickerView.date)
        
        let OneHour = calendar.date(byAdding: .minute, value: 90, to:hourPickerView.date)
        
        let twoHour = calendar.date(byAdding: .minute, value: 120, to:hourPickerView.date)
        
        
        endTimePicker = [dateFormatter.string(from: Halfminutes!),
                         dateFormatter.string(from: OneHour!),
                         dateFormatter.string(from: twoHour!),]
      self.setEndHour()
    }
    
}

extension NewMatchViewController:UITextFieldDelegate{
    func textFieldDidBeginEditing(_ textField: UITextField) {
       self.verifyFields()
    }
    func textFieldDidEndEditing(_ textField: UITextField) {
        self.verifyFields()
    }
    private func verifyFields(){
        if  gameTypeField.text!.isEmpty || genderTypeField.text!.isEmpty ||
            dateTimeField.text!.isEmpty || startTime.text!.isEmpty || endTime.text!.isEmpty ||
            addressField.text!.isEmpty || self.customerID == ""  {
                contractButton.backgroundColor = UIColor.lightGray
                contractButton.isEnabled = false
        } else {
            contractButton.backgroundColor = UIColor.firstColorGradient
            contractButton.isEnabled = true
        }
    }
}
extension NewMatchViewController: SearchPlaceViewControllerDelegate{
    func retriveLocation(candidate: Candidate) {
        self.address = candidate
        self.addressField.text = candidate.formattedAddress
    }
}

extension NewMatchViewController:SearchPlayerViewControllerDelegate{
    func didChoicePlayer(id: String, name: String) {
        self.preferenciaContratante = id
        self.namePlayer.text = name
    }
    
    
    
}
