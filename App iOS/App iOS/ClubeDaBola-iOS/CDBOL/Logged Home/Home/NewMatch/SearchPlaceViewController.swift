//
//  SearchPlaceViewController.swift
//  CDBOL
//
//  Created by Bruno Faganello Neto on 01/09/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit

protocol SearchPlaceViewControllerDelegate:class {
    func retriveLocation(candidate:Candidate)
}


class SearchPlaceViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    weak var delegate:SearchPlaceViewControllerDelegate?
    
    @IBOutlet weak var searchAddress: UISearchBar!{
        didSet{
            searchAddress.placeholder = "Procure pelo endereço ou nome do local"
        }
    }
    @IBOutlet weak var tableView: UITableView!{
        didSet{
            tableView.delegate = self
            tableView.dataSource = self
            tableView.tableFooterView = UIView()
        }
    }
        
    var googleAutocomplete = GoogleAutocomplete()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationController?.navigationBar.setGradientBackground()
        self.navigationController?.navigationBar.tintColor = .white
        searchAddress.delegate = self
        self.title = "Nome do Local"
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    
     func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        
        cell.textLabel?.text = googleAutocomplete.candidates[indexPath.row].formattedAddress
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.googleAutocomplete.candidates.count
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        delegate?.retriveLocation(candidate: googleAutocomplete.candidates[indexPath.row])
        self.navigationController?.popViewController(animated: true)
    }
}



extension SearchPlaceViewController:UISearchBarDelegate{
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        if searchText.count > 4 {
            self.executeAction()
        }
    }
    
    @objc func executeAction() {
       let gooleAPi = GoogleAPImanager()
        gooleAPi.getSuggestionPlace(with: searchAddress.text!) { (value, error) in
            if error == nil{
                self.googleAutocomplete = value!
                print(self.googleAutocomplete.candidates.count)
                self.tableView.reloadData()
            }
        }
    }
}
