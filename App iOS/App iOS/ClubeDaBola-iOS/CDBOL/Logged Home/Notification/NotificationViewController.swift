//
//  NotificationViewController.swift
//  CDBOL
//
//  Created by Paulo Leon Aquino da Rosa on 23/12/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit
import NVActivityIndicatorView

class NotificationViewController: UIViewController, NVActivityIndicatorViewable {

    @IBOutlet weak var switchRadius: UISwitch!
    @IBOutlet weak var radius: UILabel!
    @IBOutlet weak var slider: UISlider!
    var personNote: GoalKeeper?
    var type: TypePerson = .GoalKeeper
    var radiusIntSlider: Int = 5
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setUI()
        self.slider.addTarget(self, action: #selector(NotificationViewController.valueChange), for: UIControlEvents.valueChanged)
        getHirer()
    }
    
    func setUI() {
        self.navigationController?.navigationBar.setGradientBackground()
        self.navigationController?.navigationBar.tintColor = .white
        let textAttributes = [NSAttributedStringKey.foregroundColor:UIColor.white]
        self.navigationController?.navigationBar.titleTextAttributes = textAttributes
    }
    
    func getHirer() {
        let api = HirerManager()
        self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
        let contratanteId = Defaults.getUser._id
        if let contId = contratanteId {
            api.getHirer(contratanteId: contId, successHandler: { (value) in
                self.stopAnimating()
                if let person = value.goleiro {
                    self.personNote = person
                    self.type = .GoalKeeper
                    self.setData(person: person)
                }
                if let person = value.arbitro {
                    self.personNote = person
                    self.type = .Referee
                    self.setData(person: person)
                }
            }, errorHandler: { (error) in
                self.stopAnimating()
                self.createAlert(title: "Erro", message: "Ops! Tivemos um erro com seus dados, tente novamente")
            })
        }
    }
    
    func postGoalKeeper() {
        let api = PersonManager()
        self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
        if let personNotification = self.personNote {
            let person = getNotificationPerson(person: personNotification)
            api.notificationGoalkeeper(notificationPerson: person, successHandler: { (value) in
                self.stopAnimating()
                self.createChoiceAlert(title: "Notificação", message: "Notificação Atualizada!", successButton: { _ in
                    self.dismiss(animated: true, completion: nil)
                    self.navigationController?.popViewController(animated: true)
                })
            }, errorHandler: { (error) in
                self.stopAnimating()
                self.createAlert(title: "Erro", message: "Ops! Tivemos um erro com seus dados, tente novamente")
            })
        }
    }
    
    func postReferee() {
        let api = PersonManager()
        self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
        if let personNotification = self.personNote {
            let person = getNotificationPerson(person: personNotification)
            api.notificationReferee(notificationPerson: person, successHandler: { (value) in
                self.stopAnimating()
                self.createChoiceAlert(title: "Notificação", message: "Notificação Atualizada!", successButton: { _ in
                    self.dismiss(animated: true, completion: nil)
                    self.navigationController?.popViewController(animated: true)
                })
            }, errorHandler: { (error) in
                self.stopAnimating()
                self.createAlert(title: "Erro", message: "Ops! Tivemos um erro com seus dados, tente novamente")
            })
        }
    }
    
    func getNotificationPerson(person: GoalKeeper) -> NotificationPerson {
        return NotificationPerson(id: person._id, notificacoes: switchRadius.isOn, raio: radiusIntSlider)
    }
    
    func setData(person: GoalKeeper) {
        self.switchRadius.isOn = person.notificacoes
        self.radius.text = "\(person.raio) km"
        self.slider.value = Float(person.raio)
    }

    
    @objc func valueChange(){
        let value = self.slider.value
        self.radius.text = "\(Int(value)) km"
        self.radiusIntSlider = Int(value)
    }

    @IBAction func cancel(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
    
    @IBAction func finish(_ sender: Any) {
        if type == .GoalKeeper {
            postGoalKeeper()
        } else {
            postReferee()
        }
    }
    
    
}
