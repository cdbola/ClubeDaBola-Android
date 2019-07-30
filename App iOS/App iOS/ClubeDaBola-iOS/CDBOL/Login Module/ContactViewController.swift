//
//  ContactViewController.swift
//  CDBOL
//
//  Created by Bruno Faganello Neto on 09/06/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit

class ContactViewController: UITableViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        self.tableView.tableFooterView = UIView()
        self.navigationController?.navigationBar.setGradientBackground()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        self.navigationController?.navigationBar.setGradientBackground(colors: [.black])
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        guard let url = URL(string: "https://cdbola.com.br") else { return }
        UIApplication.shared.open(url)
    }
}
