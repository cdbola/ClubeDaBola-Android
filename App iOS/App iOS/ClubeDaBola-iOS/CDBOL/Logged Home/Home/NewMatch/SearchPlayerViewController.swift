//
//  SearchPlayerViewController.swift
//  CDBOL
//
//  Created by Bruno Faganello Neto on 11/08/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit

protocol SearchPlayerViewControllerDelegate:class {
    func didChoicePlayer(id:String,name:String)
}


class SearchPlayerViewController: UIViewController {

    var type: TypePerson?
    weak var delegate:SearchPlayerViewControllerDelegate?
    var contratantes = [Contratante]()
    
    @IBOutlet weak var searchText: UISearchBar!{
        didSet{
            searchText.delegate = self
            searchText.placeholder = "Pesquise pelo Apelido"
        }
    }
    @IBOutlet weak var tableView: UITableView!{
        didSet{
            tableView.delegate = self
            tableView.dataSource = self
            tableView.tableFooterView = UIView()
        }
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
    self.navigationController?.navigationBar.setGradientBackground()
    self.navigationController?.navigationBar.tintColor = .white
        self.navigationItem.title = "Escolher Jogador"
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        
    }
}

extension SearchPlayerViewController:UITableViewDelegate,UITableViewDataSource{
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return contratantes.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! SearchPlayerTableViewCell
        cell.populate(url: contratantes[indexPath.row].avatar ?? "", name: contratantes[indexPath.row].apelido)
        return cell
        
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        delegate?.didChoicePlayer(id: contratantes[indexPath.row].id, name: contratantes[indexPath.row].apelido)
        self.navigationController?.popViewController(animated: true)
    }
}

extension SearchPlayerViewController:UISearchBarDelegate{
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        if searchText.count > 3 {
            self.executeAction()
        }
    }
    
    @objc func executeAction() {
        let api = PersonManager()
        
        if type == .GoalKeeper{
            api.getGoalKeeperNickname(nickname: searchText.text!, successHandler: { (value) in
                self.contratantes = value
                self.tableView.reloadData()
            }) { (error) in
                self.createAlert(title: "Problemas para trazer o jogador", message: "Tente mais tarde")
            }
        }else{
            api.getReferreNickname(nickname: searchText.text!, successHandler: { (value) in
                self.contratantes = value
                self.tableView.reloadData()
            }) { (error) in
                self.createAlert(title: "Problemas para trazer o jogador", message: "Tente mais tarde")
            }
        }
    }
}

