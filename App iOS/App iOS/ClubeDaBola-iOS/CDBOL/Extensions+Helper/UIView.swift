//
//  UIView.swift
//  CDBOL
//
//  Created by Bruno Faganello Neto on 09/06/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit

extension UIView {

    func applyGradient(colours: [UIColor] = [UIColor.firstColorGradient, UIColor.secondColorGradient],
                       locations: [NSNumber]? = nil) -> Void {
        
        let gradient: CAGradientLayer = CAGradientLayer()
        gradient.frame = self.bounds
        gradient.startPoint = CGPoint(x: 0, y: 0.5)
        gradient.endPoint = CGPoint(x: 1, y: 0.5)
        gradient.colors = colours.map { $0.cgColor }
        gradient.locations = locations
        self.layer.insertSublayer(gradient, at: 0)
    }
}

extension UIColor{
    
    static var firstColorGradient: UIColor{
        return UIColor(red: 252/255, green: 83/255, blue: 38/255, alpha: 1)
    }
    
    static var secondColorGradient: UIColor{
        return UIColor(red: 242/255, green: 126/255, blue: 35/255, alpha: 1)
    }
}

extension CAGradientLayer {
    
    convenience init(frame: CGRect, colors: [UIColor]) {
        self.init()
        self.frame = frame
        self.colors = []
        for color in colors {
            self.colors?.append(color.cgColor)
        }
        startPoint = CGPoint(x: 0.5, y: 0.5)
        endPoint = CGPoint(x: 1, y: 0.5)
    }
    
    func createGradientImage() -> UIImage? {
        
        var image: UIImage? = nil
        UIGraphicsBeginImageContext(bounds.size)
        if let context = UIGraphicsGetCurrentContext() {
            render(in: context)
            image = UIGraphicsGetImageFromCurrentImageContext()
        }
        UIGraphicsEndImageContext()
        return image
    }
    
}

extension UINavigationBar {
    
    func setGradientBackground(colors: [UIColor] = [UIColor.firstColorGradient,
                                                    UIColor.secondColorGradient]) {
        
        var updatedFrame = bounds
        updatedFrame.size.height += self.frame.origin.y
        let gradientLayer = CAGradientLayer(frame: updatedFrame, colors: colors)
        
        setBackgroundImage(gradientLayer.createGradientImage(), for: UIBarMetrics.default)
    }
}
