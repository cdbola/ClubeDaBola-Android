//
//  HistoricTableViewCell.swift
//  CDBOL
//
//  Created by Bruno Faganello Neto on 02/09/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit

protocol HistoricTableViewCellDelegate:class {
    func buttonClicked(match: HistoricMatch)
}

class HistoricTableViewCell: UITableViewCell {
    @IBOutlet weak var borderView: UIView!{
        didSet{
            borderView.layer.masksToBounds = true
            borderView.layer.cornerRadius = 5
            borderView.layer.borderWidth = 1
            borderView.layer.borderColor = UIColor.lightGray.cgColor
        }
    }
    
    @IBOutlet weak var infoImage: UIImageView!
    
    @IBOutlet weak var firstButton: UIButton!{
        didSet{
            firstButton.layer.masksToBounds = true
            firstButton.layer.cornerRadius = 5
            firstButton.layer.borderColor = UIColor.firstColorGradient.cgColor
            firstButton.layer.borderWidth = 0
            firstButton.backgroundColor = UIColor.firstColorGradient
            firstButton.tintColor = .white
        }
    }
 
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var statusLabel: UILabel!
    @IBOutlet weak var typeMatchLabel: UILabel!
    @IBOutlet weak var genderLabel: UILabel!
    @IBOutlet weak var localPartida: UILabel!
    @IBOutlet weak var dateMatchLabel: UILabel!
    @IBOutlet weak var hourLabel: UILabel!
    @IBOutlet weak var obsLabel: UILabel!
    weak var delegate:HistoricTableViewCellDelegate?
    var model: HistoricMatch!


    override func awakeFromNib() {
        super.awakeFromNib()
        firstButton.addTargetClosure { (_) in
            self.delegate?.buttonClicked(match: self.model)
        }
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
    
    public func configCellContratante(model: HistoricMatch){
        if let contratado = model.contratado {
            self.nameLabel.text = "\(contratado.nome ?? "contratado")"
        }
    
        if let nota = model.notaContratante {
            self.statusLabel.text = "Avaliação: \(nota)"
        }else{
            self.statusLabel.text = "Avaliação: não avaliado"
        }
        self.model = model
        self.typeMatchLabel.text = "Tipo de Jogo: " + (model.tipoJogo!)
        self.genderLabel.text = "Gênero: " + (model.genero!)
        self.localPartida.text = "Local: " + (model.endereco!.lagradouro)
        self.dateMatchLabel.text = "Data: " + (model.data!)
        self.hourLabel.text = "Horário: " + model.comeca! + " às " + model.termina!
        self.obsLabel.text = "Observações: " + (model.observacoes ?? "")
        
        if model.tipoPessoa == "G"{
            if model.aleatorio ?? false{
                infoImage.image = UIImage(named: "Luva Laranja")

            }
            else{
                infoImage.image = UIImage(named: "Luva Dourada")
            }
        }else{
            if model.aleatorio ?? false{
                infoImage.image = UIImage(named: "Arbitro laranja")
            }
            else{
                infoImage.image = UIImage(named: "Arbitro Dourado")

            }
        }
    }
    
    public func configCellContratado(model: HistoricMatch){
        if let contrante = model.contratante {
            self.nameLabel.text = "\(contrante.nome ?? "contrante")"
        }
        if let nota = model.notaContratado {
            self.statusLabel.text = "Avaliação: \(nota)"
        }else{
            self.statusLabel.text = "Avaliação: não avaliado"
        }
        self.model = model
        self.typeMatchLabel.text = "Tipo de Jogo: " + (model.tipoJogo!)
        self.genderLabel.text = "Gênero: " + (model.genero!)
        self.localPartida.text = "Local: " + (model.endereco!.lagradouro)
        self.dateMatchLabel.text = "Data: " + (model.data!)
        self.hourLabel.text = "Horário: " + model.comeca! + " às " + model.termina!
        self.obsLabel.text = "Observações: " + (model.observacoes ?? "")
        if model.tipoPessoa == "G"{
            if model.aleatorio ?? false{
                infoImage.image = UIImage(named: "Luva Laranja")
            }
            else{
                infoImage.image = UIImage(named: "Luva Dourada")
            }
        }else{
            if model.aleatorio ?? false{
                infoImage.image = UIImage(named: "Arbitro laranja")
            }
            else{
                infoImage.image = UIImage(named: "Arbitro Dourado")
            }
        }
        
    }

}
