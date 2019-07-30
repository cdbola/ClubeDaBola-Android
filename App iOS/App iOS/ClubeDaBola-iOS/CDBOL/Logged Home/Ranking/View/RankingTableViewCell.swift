
//
//  RankingTableViewCell.swift
//  CDBOL
//
//  Created by Paulo Rosa on 22/12/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit
import SDWebImage

class RankingTableViewCell: UITableViewCell {

    @IBOutlet weak var rankCount: UILabel!
    @IBOutlet weak var imagePerson: UIImageView!
    @IBOutlet weak var name: UILabel!
    @IBOutlet weak var address: UILabel!
    @IBOutlet weak var points: UILabel!
    @IBOutlet weak var games: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    public func configCell(model:Arbitro, orderInt: Int){
        self.rankCount.text = "\(orderInt)º"
        self.imagePerson.sd_setImage(with: URL(string:model.avatar ?? ""), placeholderImage: UIImage(named: "placeholder.png"), options: .highPriority, completed: nil)
        self.name.text = "\(model.nome ?? "")"
        self.address.text = "\(model.endereco?.lagradouro ?? "")"
        self.points.text = "\(model.media ?? 0.0)pts"
        self.games.text = "Jogos: \(model.partidas ?? 0)"
    }

}
