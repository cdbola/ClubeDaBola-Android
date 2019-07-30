//
//  SearchPlayerTableViewCell.swift
//  CDBOL
//
//  Created by Bruno Faganello Neto on 22/12/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit
import SDWebImage

class SearchPlayerTableViewCell: UITableViewCell {

    @IBOutlet weak var userName: UILabel!
    @IBOutlet weak var userImage: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
    
    func populate(url:String,name:String){
        userName.text = name
        userImage.sd_setImage(with: URL(string:url), placeholderImage: UIImage(named: "placeholder.png"), options: .highPriority, completed: nil)
    }

}
