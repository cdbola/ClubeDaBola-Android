//
//  AvaiableContratanteViewController.swift
//  CDBOL
//
//  Created by Bruno Faganello Neto on 02/09/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit
import Cosmos
import NVActivityIndicatorView


class AvaiableContratanteViewController: UIViewController, NVActivityIndicatorViewable {
    @IBOutlet weak var sendButton: UIButton!{
        didSet{
            sendButton.layer.cornerRadius = 5
            sendButton.layer.masksToBounds = true
        }
    }
    @IBOutlet weak var yesButton: UIButton!{
        didSet{
            yesButton.layer.masksToBounds = true
            yesButton.layer.cornerRadius = 5
            yesButton.layer.borderColor = UIColor.firstColorGradient.cgColor
            yesButton.tintColor = UIColor.firstColorGradient
            yesButton.layer.borderWidth = 1
        }
    }
    @IBOutlet weak var noButton: UIButton!{
        didSet{
            noButton.layer.masksToBounds = true
            noButton.layer.cornerRadius = 5
            noButton.layer.borderColor = UIColor.firstColorGradient.cgColor
            noButton.tintColor = UIColor.firstColorGradient
            noButton.layer.borderWidth = 1
            
        }
    }
    var goodAnwser:Bool = false
    @IBOutlet weak var cosmosFirst: CosmosView!
    @IBOutlet weak var cosmosSecond: CosmosView!
    var match: HistoricMatch?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        yesButton.addTargetClosure { (_) in
            self.goodAnwser = true
            self.noButton.backgroundColor = .clear
            self.noButton.tintColor = UIColor.firstColorGradient
            self.yesButton.tintColor = .white
            self.yesButton.backgroundColor = UIColor.firstColorGradient
        }
        
        noButton.addTargetClosure { (_) in
            self.goodAnwser = false
            self.yesButton.backgroundColor = .clear
            self.yesButton.tintColor = UIColor.firstColorGradient
            self.noButton.tintColor = .white
            self.noButton.backgroundColor = UIColor.firstColorGradient
        }
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
    }

    func rateMatchs() {
        self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
        let api = MatchManager()
        api.rateMatch(rate: getRate(), successHandler: { (recentMatchs) in
            self.stopAnimating()
            self.createChoiceAlert(title: "Avaliação Finalizada", message: "Obrigado pela avaliação!", successButton: { _ in
                self.dismiss(animated: true, completion: nil)
                self.navigationController?.popViewController(animated: true)
            })
            
        }, errorHandler: { (error) in
            self.stopAnimating()
            self.createAlert(title: "Erro", message: "Ops! Tivemos um erro para finalizar sua avaliação")
        })
    }
    
    func getRate() -> Rate {
        if match?.contratado != nil {
            if let mat = match, let partidaId = mat.id {
                return Rate(partida: partidaId, notaContratante: getNotes(), notaContratado: nil)
            }
        }
        return Rate(partida: "nil", notaContratante: nil, notaContratado: nil)
    }
    func getNotes() -> Double {
        var count = 0.0
        if goodAnwser {
            count = count + 2
        } else {
            count = count + 1
        }
        switch cosmosFirst.rating {
        case 5.0:
            count = count + 4
        case 4.0:
            count = count + 3.2
        case 3.0:
            count = count + 2.4
        case 2.0:
            count = count + 1.6
        case 1.0:
            count = count + 0.8
        default:
            count = count + 1
        }
        switch cosmosSecond.rating {
        case 5.0:
            count = count + 4
        case 4.0:
            count = count + 3.2
        case 3.0:
            count = count + 2.4
        case 2.0:
            count = count + 1.6
        case 1.0:
            count = count + 0.8
        default:
            count = count + 1
        }
        return count
    }
    
    @IBAction func sendRate(_ sender: Any) {
        rateMatchs()
    }

}
