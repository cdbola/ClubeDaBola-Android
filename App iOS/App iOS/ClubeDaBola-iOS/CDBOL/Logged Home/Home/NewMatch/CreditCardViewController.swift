//
//  CreditCardViewController.swift
//  CDBOL
//
//  Created by Bruno Faganello Neto on 11/08/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit

class CreditCardViewController: UITableViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = "Cartão de Crédito"
        self.navigationItem.rightBarButtonItem = UIBarButtonItem(title: "Concluir", style: .done, target: self, action: #selector(addTapped))
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    @objc func addTapped(sender: AnyObject) {
        print("hjxdbsdhjbv")
    }
    
}
