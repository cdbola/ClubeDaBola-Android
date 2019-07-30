//
//  RequestLocationViewController.swift
//  CDBOL
//
//  Created by Bruno Faganello Neto on 09/06/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit
import CoreLocation

class RequestLocationViewController: UIViewController {

    @IBOutlet weak var requestButton: UIButton!
    var locationManager: CLLocationManager!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationController?.navigationBar.isHidden = true
        self.navigationController?.interactivePopGestureRecognizer?.isEnabled = false
        requestButton.addTargetClosure { (_) in
            self.locationManager = CLLocationManager()
            self.locationManager.requestWhenInUseAuthorization()
            self.locationManager.startUpdatingLocation()
            self.locationManager.delegate = self
            
        }
    }
    
    override func viewDidLayoutSubviews() {
        requestButton.applyGradient()
        requestButton.layer.cornerRadius = 5
        requestButton.layer.masksToBounds = true
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
}

extension RequestLocationViewController: CLLocationManagerDelegate {
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        let storyboard = UIStoryboard(name: "Logged", bundle: nil)
        let controller = storyboard.instantiateViewController(withIdentifier: "InitialHome") as! CDTabBarViewController
        self.present(controller, animated: true, completion: nil)
    }

}
