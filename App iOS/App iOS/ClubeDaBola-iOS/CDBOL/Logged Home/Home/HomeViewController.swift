//
//  HomeViewController.swift
//  CDBOL
//
//  Created by Bruno Faganello Neto on 11/08/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit
import NVActivityIndicatorView

class HomeViewController: UIViewController, NVActivityIndicatorViewable {

    @IBOutlet weak var segmentedControl: UISegmentedControl!
    @IBOutlet weak var newContratationButton: UIButton!{
        didSet{
            newContratationButton.layer.cornerRadius = 5
            newContratationButton.layer.masksToBounds = true
        }
    }
    @IBOutlet weak var tableView: UITableView!{
        didSet{
            tableView.separatorStyle = .none
            tableView.delegate = self
            tableView.dataSource = self
        }
    }
    var matchs = [Match]()
    var player = false
    var refuseOrAccept = 0 //0 - refuse, 1 - accept
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationController?.navigationBar.setGradientBackground()
        self.navigationController?.navigationBar.tintColor = .white
        self.newContratationButton.addTargetClosure { (_) in
            self.createAlert()
        }
        
        getPartidasRecentes()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.tabBarController?.tabBar.isHidden = false
    }
    
    @IBAction func clickedSegmented(_ sender: UISegmentedControl) {
        if segmentedControl.selectedSegmentIndex == 0{
            player = false
            getPartidasRecentes()
        } else {
            player = true
           getPartidasContratado()
        }
        self.tableView.reloadData()
    }
    
    func getPartidasRecentes() {
        self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
        let api = MatchManager()
        if let id = Defaults.getUser._id {
            api.getMatchs(contratanteId: id, successHandler: { (recentMatchs) in
                self.stopAnimating()
                self.matchs = recentMatchs
                self.tableView.reloadData()
                return
            }, errorHandler: { (error) in
                self.stopAnimating()
                self.createAlert(title: "Erro", message: "Ops! Tivemos um erro ao recuperar suas partidas")
                return
            })
        }
        if let idSingleton = UserSingleton.shared.Id {
            api.getMatchs(contratanteId: idSingleton, successHandler: { (recentMatchs) in
                self.stopAnimating()
                self.matchs = recentMatchs
                self.tableView.reloadData()
                return
            }, errorHandler: { (error) in
                self.stopAnimating()
                self.createAlert(title: "Erro", message: "Ops! Tivemos um erro ao recuperar suas partidas")
                return
            })
        }
    }
    
    func getPartidasContratado() {
        self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
        let api = MatchManager()
        api.getHiredMatchs(contratanteId: Defaults.getUser._id!, successHandler: { (hiredMatch) in
            self.stopAnimating()
            self.matchs = hiredMatch.partidas
            self.tableView.reloadData()
        }, errorHandler: { (error) in
            self.stopAnimating()
            self.createAlert(title: "Erro", message: "Ops! Tivemos um erro ao recuperar suas partidas")
        })
    }
    
    func deletePartidas(partidaId: String) {
        self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
        let api = MatchManager()
        api.deleteMatchs(contratanteId: partidaId, successHandler: { (recentMatchs) in
            self.stopAnimating()
            self.getPartidasRecentes()
        }, errorHandler: { (error) in
            self.stopAnimating()
            self.createAlert(title: "Erro", message: "Ops! Tivemos um erro para cancelar essa partida")
        })
    }
    
    func aceitarPartida(partidaId: String, contratanteId: String) {
        self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
        let api = MatchManager()
        api.acceptMatch(partidaId: partidaId,contratanteId: contratanteId, successHandler: { (recentMatchs) in
            self.stopAnimating()
            self.getPartidasContratado()
        }, errorHandler: { (error) in
            self.stopAnimating()
            self.createAlert(title: "Erro", message: "Ops! Tivemos um erro para aceitar essa partida")
        })
    }
    
    func recusarPartida(partidaId: String) {
        self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
        let api = MatchManager()
        api.refuseMatch(partidaId: partidaId, successHandler: { (recentMatchs) in
            self.stopAnimating()
            self.getPartidasContratado()
        }, errorHandler: { (error) in
            self.stopAnimating()
            self.createAlert(title: "Erro", message: "Ops! Tivemos um erro para cancelar essa partida")
        })
    }
    
    func createAlert() {
        let alert = UIAlertController(title: "", message: "Nova Contratação", preferredStyle: .actionSheet)
        
        alert.addAction(UIAlertAction(title: "Goleiro", style: .default , handler:{ (UIAlertAction)in
            print("User click Approve button")
            let storyboard = UIStoryboard(name: "Logged", bundle: nil)
            let controller = storyboard.instantiateViewController(withIdentifier: "NewMatchViewController") as! NewMatchViewController
            controller.type = TypePerson.GoalKeeper
            self.navigationController?.pushViewController(controller, animated: true)
        }))
        
        alert.addAction(UIAlertAction(title: "Árbitro", style: .default , handler:{ (UIAlertAction)in
            print("User click Edit button")
            let storyboard = UIStoryboard(name: "Logged", bundle: nil)
            let controller = storyboard.instantiateViewController(withIdentifier: "NewMatchViewController") as! NewMatchViewController
            controller.type = TypePerson.Referee
            self.navigationController?.pushViewController(controller, animated: true)
        }))
        
        alert.addAction(UIAlertAction(title: "Cancelar", style: .cancel, handler:{ (UIAlertAction)in
            print("User click Dismiss button")
            alert.dismiss(animated: true, completion: {})
        }))
        
        self.present(alert, animated: true, completion: {
            print("completion block")
        })
    }
    
}

extension HomeViewController: UITableViewDelegate, UITableViewDataSource{
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.matchs.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! MatchTableViewCell
        cell.isPlayer = self.player
        cell.delegate = self
        if !player{
            cell.firstButton.setTitle("Cancelar Partida", for: .normal)
            cell.firstButton.layer.masksToBounds = true
            cell.firstButton.layer.cornerRadius = 5
            cell.firstButton.layer.borderColor = UIColor.firstColorGradient.cgColor
            cell.firstButton.layer.borderWidth = 1
            cell.firstButton.backgroundColor = .white
            cell.firstButton.tintColor = UIColor.firstColorGradient
        }else{
            if matchs[indexPath.row].contratado != nil {
                cell.buttonCancel()
            } else {
               cell.buttonAccept()
            }
        }
        cell.configCell(model: matchs[indexPath.row])
        return cell
    }
}

extension HomeViewController: MatchTableViewCellDelegate {
    func firstButtonClicked(partidaId: String, contratado: String?) {
       if !player{
            self.createChoiceAlert(title: "Excluir Partida",
                               message: "Deseja excluir essa partida?",
                               successButton: {_ in
            self.deletePartidas(partidaId: partidaId)})
        }else{
            if contratado != nil {
                self.createChoiceAlert(title: "Recusar Partida",
                                       message: "Deseja recusar essa partida?",
                                       successButton: {_ in
                                        self.recusarPartida(partidaId: partidaId)})
            } else {
                self.createChoiceAlert(title: "Aceitar Partida",
                                       message: "Deseja aceitar essa partida?",
                                       successButton: {_ in
                                        self.aceitarPartida(partidaId: partidaId, contratanteId: Defaults.getUser._id!)
                })
        }
        
        }
    }

}
