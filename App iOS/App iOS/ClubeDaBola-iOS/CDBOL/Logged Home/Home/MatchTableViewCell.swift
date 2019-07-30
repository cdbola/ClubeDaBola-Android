//
//  MatchTableViewCell.swift
//  CDBOL
//
//  Created by Bruno Faganello Neto on 12/08/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit
protocol MatchTableViewCellDelegate:class {
    func firstButtonClicked(partidaId: String, contratado: String?)
}
class MatchTableViewCell: UITableViewCell {

    @IBOutlet weak var borderView: UIView!{
        didSet{
            borderView.layer.masksToBounds = true
            borderView.layer.cornerRadius = 5
            borderView.layer.borderWidth = 1
            borderView.layer.borderColor = UIColor.lightGray.cgColor
        }
    }
    
    @IBOutlet weak var firstButton: UIButton!{
        didSet{
            firstButton.setTitle("Cancelar Convocação", for: .normal)
            firstButton.layer.masksToBounds = true
            firstButton.layer.cornerRadius = 5
            firstButton.layer.borderColor = UIColor.firstColorGradient.cgColor
            firstButton.layer.borderWidth = 1
            firstButton.tintColor = UIColor.firstColorGradient
        }
    }
    
    @IBOutlet weak var secondButton: UIButton!{
        didSet{
            
            secondButton.titleLabel?.text = "Aceitar Convocação"
            secondButton.layer.masksToBounds = true
            secondButton.layer.cornerRadius = 5
            secondButton.layer.borderColor = UIColor.firstColorGradient.cgColor
            secondButton.layer.borderWidth = 0
            secondButton.backgroundColor = UIColor.firstColorGradient
            secondButton.tintColor = .white
        }
    }
    
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var statusLabel: UILabel!
    @IBOutlet weak var infoImage: UIImageView!
    @IBOutlet weak var typeMatchLabel: UILabel!
    @IBOutlet weak var genderLabel: UILabel!
    @IBOutlet weak var localPartida: UILabel!
    @IBOutlet weak var dateMatchLabel: UILabel!
    @IBOutlet weak var hourLabel: UILabel!
    @IBOutlet weak var obsLabel: UILabel!
    weak var delegate:MatchTableViewCellDelegate?
    var partidaId: String!
    var contratado: String?
    
    var isPlayer:Bool = false
    
    override func awakeFromNib() {
        super.awakeFromNib()
        firstButton.addTargetClosure { (_) in
            self.delegate?.firstButtonClicked(partidaId: self.partidaId, contratado: self.contratado)
        }
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
    
    public func configCell(model:Match){
        self.typeMatchLabel.text = "Tipo de Jogo: " + (model.tipoJogo )
        self.genderLabel.text = "Gênero: " + (model.genero)
        self.localPartida.text = "Local: " + (model.endereco.lagradouro)
        self.dateMatchLabel.text = "Data: " + (model.data)
        self.hourLabel.text = self.setDate(model: model)
        self.obsLabel.text = "Observações: " + (model.observacoes)
        self.partidaId = model.id!
        self.contratado = model.contratado
        
        if model.contratado == nil{
            statusLabel.text = "Pendente"
        }else{
            statusLabel.text = "Jogador Aceito"
        }
        
        if model.tipoPessoa == "G"{
            if model.aleatorio {
                infoImage.image = UIImage(named: "Luva Laranja")
                nameLabel.text = "Goleiro Aleatório"
            }
            else{
                infoImage.image = UIImage(named: "Luva Dourada")
                nameLabel.text = "Goleiro Específico"
            }
        }else{
            if model.aleatorio {
                infoImage.image = UIImage(named: "Arbitro laranja")
                nameLabel.text = "Árbitro Aleatório"

            }
            else{
                infoImage.image = UIImage(named: "Arbitro Dourado")
                nameLabel.text = "Árbitro Específico"
            }
        }

    }
    
    func buttonCancel() {
        firstButton.setTitle("Cancelar Convocação", for: .normal)
        firstButton.layer.masksToBounds = true
        firstButton.layer.cornerRadius = 5
        firstButton.layer.borderColor = UIColor.firstColorGradient.cgColor
        firstButton.layer.borderWidth = 1
        firstButton.backgroundColor = .white
        firstButton.tintColor = UIColor.firstColorGradient
    }
    
    func buttonAccept() {
        firstButton.setTitle("Aceitar Convocação", for: .normal)
        firstButton.layer.masksToBounds = true
        firstButton.layer.cornerRadius = 5
        firstButton.layer.borderColor = UIColor.firstColorGradient.cgColor
        firstButton.layer.borderWidth = 0
        firstButton.backgroundColor = UIColor.firstColorGradient
        firstButton.tintColor = .white
    }
    
    
    private func setDate(model:Match) -> String{
        let dateString = model.data
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "dd/MM/yyyy"
        let date = dateFormatter.date(from: dateString)
        
        
        let calendar = Calendar.current
     //   let newDate = calendar.date(byAdding: .minute, value: Int(model.duracao ?? "0")!, to: date!)

        return "Horário: " + (model.comeca) + " às " + model.termina
    }

}
