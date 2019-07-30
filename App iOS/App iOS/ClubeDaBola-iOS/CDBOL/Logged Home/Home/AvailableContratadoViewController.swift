//
//  AvailableContratadoViewController.swift
//  CDBOL
//
//  Created by Paulo Leon Aquino da Rosa on 21/12/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit
import Cosmos
import NVActivityIndicatorView

class AvailableContratadoViewController: UIViewController, NVActivityIndicatorViewable {
    
    @IBOutlet weak var sendButton: UIButton!{
        didSet{
            sendButton.layer.cornerRadius = 5
            sendButton.layer.masksToBounds = true
        }
    }
    @IBOutlet weak var firstLabel: UILabel!
    @IBOutlet weak var yesFirstButton: UIButton!{
        didSet{
            yesFirstButton.layer.masksToBounds = true
            yesFirstButton.layer.cornerRadius = 5
            yesFirstButton.layer.borderColor = UIColor.firstColorGradient.cgColor
            yesFirstButton.tintColor = UIColor.firstColorGradient
            yesFirstButton.layer.borderWidth = 1
        }
    }
    @IBOutlet weak var noFirstButton: UIButton!{
        didSet{
            noFirstButton.layer.masksToBounds = true
            noFirstButton.layer.cornerRadius = 5
            noFirstButton.layer.borderColor = UIColor.firstColorGradient.cgColor
            noFirstButton.tintColor = UIColor.firstColorGradient
            noFirstButton.layer.borderWidth = 1
            
        }
    }
    
    @IBOutlet weak var secondLabel: UILabel!
    @IBOutlet weak var yesSecondButton: UIButton!{
        didSet{
            yesSecondButton.layer.masksToBounds = true
            yesSecondButton.layer.cornerRadius = 5
            yesSecondButton.layer.borderColor = UIColor.firstColorGradient.cgColor
            yesSecondButton.tintColor = UIColor.firstColorGradient
            yesSecondButton.layer.borderWidth = 1
        }
    }
    @IBOutlet weak var noSecondButton: UIButton!{
        didSet{
            noSecondButton.layer.masksToBounds = true
            noSecondButton.layer.cornerRadius = 5
            noSecondButton.layer.borderColor = UIColor.firstColorGradient.cgColor
            noSecondButton.tintColor = UIColor.firstColorGradient
            noSecondButton.layer.borderWidth = 1
            
        }
    }
    @IBOutlet weak var thirdLabel: UILabel!
    @IBOutlet weak var cosmosFirst: CosmosView!
    var flagFirstAnwser:Bool = false
    var flagSecondAnwser:Bool = false

    var match: HistoricMatch?

    override func viewDidLoad() {
        super.viewDidLoad()
        setUI()

    }
    
    override func viewWillDisappear(_ animated: Bool) {
        self.tabBarController?.tabBar.isHidden = false
    }
 
    func setUI() {
        self.title = "Avaliação"
        self.navigationController?.navigationBar.setGradientBackground()
        self.navigationController?.navigationBar.tintColor = .white
        let textAttributes = [NSAttributedStringKey.foregroundColor:UIColor.white]
        self.navigationController?.navigationBar.titleTextAttributes = textAttributes
        self.tabBarController?.tabBar.isHidden = true
        setButtons()
        setLabels()
    }
    
    func setLabels() {
        if let match = self.match, let tipoJogo = match.tipoJogo {
            if tipoJogo == "G" {
                self.firstLabel.text = "O goleiro se apresentou no horário da partida?"
                self.secondLabel.text = "O goleiro usou o uniforme do clube da bola?"
                self.thirdLabel.text = "Como você avalia a atuação do goleiro?"
            }else {
                self.firstLabel.text = "O árbitro se apresentou no horário da partida?"
                self.secondLabel.text = "O árbitro usou o uniforme do clube da bola?"
                self.thirdLabel.text = "Como você avalia a atuação do árbitro?"
            }
        }
    }
    
    func setButtons() {
        yesFirstButton.addTargetClosure { (_) in
            self.flagFirstAnwser = true
            self.noFirstButton.backgroundColor = .clear
            self.noFirstButton.tintColor = UIColor.firstColorGradient
            self.yesFirstButton.tintColor = .white
            self.yesFirstButton.backgroundColor = UIColor.firstColorGradient
        }
        
        noFirstButton.addTargetClosure { (_) in
            self.flagFirstAnwser = false
            self.yesFirstButton.backgroundColor = .clear
            self.yesFirstButton.tintColor = UIColor.firstColorGradient
            self.noFirstButton.tintColor = .white
            self.noFirstButton.backgroundColor = UIColor.firstColorGradient
        }
        yesSecondButton.addTargetClosure { (_) in
            self.flagSecondAnwser = true
            self.noSecondButton.backgroundColor = .clear
            self.noSecondButton.tintColor = UIColor.firstColorGradient
            self.yesSecondButton.tintColor = .white
            self.yesSecondButton.backgroundColor = UIColor.firstColorGradient
        }
        
        noSecondButton.addTargetClosure { (_) in
            self.flagSecondAnwser = false
            self.yesSecondButton.backgroundColor = .clear
            self.yesSecondButton.tintColor = UIColor.firstColorGradient
            self.noSecondButton.tintColor = .white
            self.noSecondButton.backgroundColor = UIColor.firstColorGradient
        }
    }
    
    func rateMatchs() {
        self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
        let api = MatchManager()
        api.rateMatch(rate: getRate(), successHandler: { (recentMatchs) in
            self.stopAnimating()
            self.createChoiceAlert(title: "Avaliação Finalizada", message: "Obrigado pela avaliação!", successButton: { _ in self.dismiss(animated: true, completion: nil)
                self.navigationController?.popViewController(animated: true)
            })
           
        }, errorHandler: { (error) in
            self.stopAnimating()
            self.createAlert(title: "Erro", message: "Ops! Tivemos um erro para finalizar sua avaliação")
        })
    }
    
    func getRate() -> Rate {
        if match?.contratante != nil {
            if let mat = match, let partidaId = mat.id {
                return Rate(partida: partidaId, notaContratante: nil, notaContratado: getNotes())
            }
        }
        return Rate(partida: "nil", notaContratante: nil, notaContratado: nil)
    }
    func getNotes() -> Double {
        var count = 0.0
        if flagFirstAnwser {
            count = count + 2
        } else {
            count = count + 1
        }
        if flagSecondAnwser {
            count = count + 2
        } else {
            count = count + 1
        }
        switch cosmosFirst.rating {
        case 5.0:
            count = count + 6
        case 4.0:
            count = count + 4.8
        case 3.0:
            count = count + 3.6
        case 2.0:
            count = count + 2.4
        case 1.0:
            count = count + 1.2
        default:
            count = count + 1
        }
        return count
    }
    
    @IBAction func sendRate(_ sender: Any) {
        rateMatchs()
    }
}
