//
//  PaymentsTableViewCell.swift
//  CDBOL
//
//  Created by Paulo Leon Aquino da Rosa on 18/12/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit

class PaymentsTableViewCell: UITableViewCell {
    
    @IBOutlet weak var type: UILabel!
    @IBOutlet weak var desc: UITextField!


    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
