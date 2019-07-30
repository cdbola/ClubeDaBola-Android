//
//  RankViewController.swift
//  CDBOL
//
//  Created by Bruno Faganello Neto on 01/09/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit
import NVActivityIndicatorView

class RankViewController: UIViewController, NVActivityIndicatorViewable {

    
    @IBOutlet weak var segmentedControl: UISegmentedControl!
    
    @IBOutlet weak var tableView: UITableView! {
        didSet {
            tableView.tableFooterView = UIView()
        }
    }
    var goleiroDataSource: GoleiroRankingDataSource?
    var juizDataSource: JuizRankingDataSource?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationController?.navigationBar.setGradientBackground()
        self.navigationController?.navigationBar.tintColor = .white
    }
    
    override func viewWillAppear(_ animated: Bool) {
        segmentedControl.selectedSegmentIndex = 0
        getRanking(type: .GoalKeeper)
    }

    @IBAction func clickedSegmented(_ sender: UISegmentedControl) {
        if segmentedControl.selectedSegmentIndex == 0{
            getRanking(type: .GoalKeeper)
        } else {
            getRanking(type: .Referee)
        }
    }
    
    func setDatasource(type: TypePerson, model: RankingPerson) {
        self.goleiroDataSource = GoleiroRankingDataSource()
        self.juizDataSource = JuizRankingDataSource()
        if type == .GoalKeeper {
            if let goleiros = model.goleiros {
                self.goleiroDataSource?.model = orderRanking(model: goleiros)
                self.tableView.delegate = self.goleiroDataSource
                self.tableView.dataSource = self.goleiroDataSource
                if goleiros.count == 0 {
                    self.createAlert(title: "Ranking", message: "lista vazia")
                }
            }
        } else {
            if let juiz = model.arbitros {
                self.juizDataSource?.model = orderRanking(model: juiz)
                self.tableView.delegate = self.juizDataSource
                self.tableView.dataSource = self.juizDataSource
                if juiz.count == 0 {
                    self.createAlert(title: "Ranking", message: "lista vazia")
                }
            }
        }
        self.tableView.reloadData()
    }
    
    func getRanking(type: TypePerson) {
        self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
        let api = PersonManager()
        api.getRanking(successHandler: { (rankingPersons) in
            self.stopAnimating()
            self.setDatasource(type: type, model: rankingPersons)
        }, errorHandler: { (error) in
            self.stopAnimating()
            self.createAlert(title: "Erro", message: "Ops! Tivemos um erro ao recuperar nosso ranking")
        })
    }
    
    func orderRanking(model: [Arbitro]) -> [Arbitro] {
        let arrayMap = model.sorted(by: {($0.media ?? 0) < ($1.media  ?? 0) })
        return arrayMap.reversed()
    }
        
}

