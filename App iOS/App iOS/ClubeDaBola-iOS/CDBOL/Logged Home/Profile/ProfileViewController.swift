//
//  PerfilViewController.swift
//  CDBOL
//
//  Created by Paulo Rosa on 12/09/2018.
//  Copyright © 2018 Faganello. All rights reserved.
//

import Foundation
import UIKit
import RxSwift
import NVActivityIndicatorView
import Alamofire

class ProfileViewController: DesignableViewController, NVActivityIndicatorViewable,UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    typealias createPerson = (String, Bool)
    @IBOutlet weak var imageProfile: UIImageView!
    @IBOutlet weak var nameProfile: UILabel!
    @IBOutlet weak var emailProfile: UILabel!
    @IBOutlet weak var editProfile: UIButton! {
        didSet {
            editProfile.layer.borderWidth = 1.0
            editProfile.layer.borderColor = UIColor.white.cgColor
            editProfile.layer.cornerRadius = 20.0
        }
    }
    @IBOutlet weak var tableView: UITableView! {
        didSet {
            tableView.tableFooterView = UIView()
        }
    }
    var dataCreate: CreateProfileDataSource!
    var dataProfile: ProfileDataSource!
    var customerId: String?
    var flagDataSourceFirstTime: Bool = false
    var flagDataSourceProfileFirstTime: Bool = false
    var myImage:UIImage?
    
    let disposeBag = DisposeBag()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let tapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(imageTapped(tapGestureRecognizer:)))
        imageProfile.isUserInteractionEnabled = true
        imageProfile.addGestureRecognizer(tapGestureRecognizer)
    }
    
    @objc func imageTapped(tapGestureRecognizer: UITapGestureRecognizer){
        let tappedImage = tapGestureRecognizer.view as! UIImageView
        if UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceType.photoLibrary){
            let imagePicker = UIImagePickerController()
            imagePicker.delegate = self
            imagePicker.allowsEditing = true
            imagePicker.sourceType = UIImagePickerControllerSourceType.photoLibrary
            self.present(imagePicker, animated: true, completion: nil)
        }
    }
    
   @objc func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
        if let pickedImage = info[UIImagePickerControllerOriginalImage] as? UIImage {
            myImage = pickedImage
            imageProfile.image = pickedImage
           
        }
        picker.dismiss(animated: true, completion: nil)
    }
    
    
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(true, animated: animated)
        if myImage != nil{
            
            self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
            Alamofire.upload(multipartFormData:{ multipartFormData in
                multipartFormData.append(UIImageJPEGRepresentation(self.myImage!, 0.01)!, withName: "file", fileName: "\(UUID().uuidString).jpg", mimeType: "image/jpeg")},
                             usingThreshold:UInt64.init(),
                             to:"https://cdbola.herokuapp.com/api/v1/contratantes/\(Defaults.getUser._id!)/atualizarAvatar",
                             method:.patch,
                             headers:["Authorization": Defaults.getUser.token!],
                             encodingCompletion: { encodingResult in
                                switch encodingResult {
                                case .success(let upload, _, _):
                                    self.stopAnimating()
                                    self.myImage = nil
                                    self.setUI()
                                case .failure(let encodingError):
                                    self.stopAnimating()
                                    self.myImage = nil
                                    self.createAlert(title: "Não foi possível enviar sua imagem", message: "Tente mais tarde")
                                    self.setUI()
                                }
            })
        } else{
            setUI()
        }
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(false, animated: animated)
    }
    
    @IBAction func editProfile(_ sender: UIButton) {
    }
    @IBAction func pokerButton(_ sender: Any) {
        guard let url = URL(string: "https://poker.esp.br") else { return }
        UIApplication.shared.open(url)
    }
    
    func setUI() {
        getHirer()
    }
    
    func bindUI() {
        dataCreate.choose.asObservable().subscribe(onNext: { [self] value in
            if !self.flagDataSourceFirstTime {
                self.routerCreate(index: value)
            }
            self.flagDataSourceFirstTime = false
        }).disposed(by: disposeBag)
        
        dataProfile.choose.asObservable().subscribe(onNext:{ value in
            if !self.flagDataSourceProfileFirstTime {
                self.routerProfile(index: value)
            }
            self.flagDataSourceProfileFirstTime = false
        }).disposed(by: disposeBag)
    }
    
    func setDatasource(isPerson: Bool) {
        dataProfile = ProfileDataSource()
        dataCreate = CreateProfileDataSource()
        if isPerson {
            tableView.delegate = dataProfile
            tableView.dataSource = dataProfile
        } else {
            tableView.delegate = dataCreate
            tableView.dataSource = dataCreate
        }
        self.tableView.reloadData()
        flagDataSourceFirstTime = true
        flagDataSourceProfileFirstTime = true
        bindUI()
    }
    
    func getHirer() {
        let api = HirerManager()
        self.startAnimating(CGSize(width: 50.0, height: 50.0),  type: NVActivityIndicatorType(rawValue: 32))
        let contratanteId = Defaults.getUser._id
        if let contId = contratanteId {
            api.getHirer(contratanteId: contId, successHandler: { (contratante) in
                self.stopAnimating()
                self.setUIHirer(hirer: contratante)
                if let costumer = contratante.customerId  {
                    self.customerId = costumer
                    Defaults.saveCustomerId(costumer)
                }
                if contratante.goleiro != nil || contratante.arbitro != nil {
                    self.setDatasource(isPerson: true)
                }else {
                    self.setDatasource(isPerson: false)
                }
            }, errorHandler: { (error) in
                self.stopAnimating()
                self.createAlert(title: "Erro", message: "Ops! Tivemos um erro com seus dados, tente novamente")
            })
        }
    }
    
    func setUIHirer(hirer: Contratante) {
        self.imageProfile.sd_setImage(with: URL(string:hirer.avatar ?? ""), placeholderImage: UIImage(named: "placeholderWhite"), options: .highPriority, completed: nil)
        self.nameProfile.text = hirer.nome
        self.emailProfile.text = hirer.email
    }
    
    func createAlert() {
        let alert = UIAlertController(title: "", message: "Quero virar jogador", preferredStyle: .actionSheet)
        
        alert.addAction(UIAlertAction(title: "Goleiro", style: .default , handler:{ (UIAlertAction)in
            let storyboard = UIStoryboard(name: "CreateGoalKeeper", bundle: nil)
            let vc = storyboard.instantiateViewController(withIdentifier: "InitialHome") as UIViewController
            let create = "GOALKEEPER"
            UserDefaults.standard.set(create, forKey: "createPerson")
            self.present(vc, animated: true, completion: nil)
        }))
        
        alert.addAction(UIAlertAction(title: "Árbitro", style: .default , handler:{ (UIAlertAction)in
            let storyboard = UIStoryboard(name: "CreateGoalKeeper", bundle: nil)
            let vc = storyboard.instantiateViewController(withIdentifier: "InitialHome") as UIViewController
            let type = "Referee"
            UserDefaults.standard.set(type, forKey: "createPerson")
            self.present(vc, animated: true, completion: nil)
        }))
        
        alert.addAction(UIAlertAction(title: "Cancelar", style: .cancel, handler:{ (UIAlertAction)in
            print("User click Dismiss button")
            alert.dismiss(animated: true, completion: {})
        }))
        
        self.present(alert, animated: true, completion: {
            print("completion block")
        })
    }
    func routerCreate(index: Int) {
        switch index {
        case 0:
            if customerId != nil {
                let storyboard = UIStoryboard(name: "Payments", bundle: nil)
                let controller = storyboard.instantiateViewController(withIdentifier: "IdHome")
                self.present(controller, animated: true, completion: nil)
            } else {
                let storyboard = UIStoryboard(name: "Payments", bundle: nil)
                let controller = storyboard.instantiateViewController(withIdentifier: "InitialHome")
                self.present(controller, animated: true, completion: nil)
            }
        case 1:
            let urlString = "whatsapp://send?text=Conheça o Clube da Bola: https://cdbola.com.br"
            
            let urlStringEncoded = urlString.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)
            
            let url = URL(string: urlStringEncoded!)
            
            if UIApplication.shared.canOpenURL(url!) {
                UIApplication.shared.open(url!, options: [:], completionHandler: nil)
            }else{
                self.createAlert(title: "Sem WhatsApp", message: "")
            }
        case 2:
            self.createAlert()
        case 3:
            guard let url = URL(string: "https://cdbola.com.br") else { return }
            UIApplication.shared.open(url)
        case 4:
            Defaults.clearAll()
            print(Defaults.getUser.token!)
            let storyboard = UIStoryboard(name: "Main", bundle: nil)
            let controller = storyboard.instantiateViewController(withIdentifier: "InitialHome")
            self.present(controller, animated: true, completion: nil)
        default:
            return
        }
    }
    
    func routerProfile(index: Int) {
        switch index {
        case 0:
            let storyboard = UIStoryboard(name: "Earnings", bundle: nil)
            let controller = storyboard.instantiateViewController(withIdentifier: "InitialHome")
            self.present(controller, animated: true, completion: nil)
        case 1:
            if customerId != nil {
                let storyboard = UIStoryboard(name: "Payments", bundle: nil)
                let controller = storyboard.instantiateViewController(withIdentifier: "IdHome")
                self.present(controller, animated: true, completion: nil)
            } else {
                let storyboard = UIStoryboard(name: "Payments", bundle: nil)
                let controller = storyboard.instantiateViewController(withIdentifier: "InitialHome")
                self.present(controller, animated: true, completion: nil)
            }
        case 2:
            let urlString = "whatsapp://send?text=Conheça o Clube da Bola: https://cdbola.com.br"
            
            let urlStringEncoded = urlString.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)
            
            let url = URL(string: urlStringEncoded!)
            
            if UIApplication.shared.canOpenURL(url!) {
                UIApplication.shared.open(url!, options: [:], completionHandler: nil)
            }else{
                self.createAlert(title: "Sem WhatsApp", message: "")
            }
        case 3:
            let storyboard = UIStoryboard(name: "Notification", bundle: nil)
            let controller = storyboard.instantiateViewController(withIdentifier: "InitialHome")
            self.present(controller, animated: true, completion: nil)
        case 4:
            guard let url = URL(string: "https://cdbola.com.br") else { return }
            UIApplication.shared.open(url)
        case 5:
            Defaults.clearUserData()
            Defaults.clearCustomerId()
            let storyboard = UIStoryboard(name: "Main", bundle: nil)
            let controller = storyboard.instantiateViewController(withIdentifier: "InitialHome")
            self.present(controller, animated: true, completion: nil)
        default:
            return
        }
    }
    

    
}

