//
//  PokerViewController.swift
//  CDBOL
//
//  Created by Paulo Leon Aquino da Rosa on 15/01/19.
//  Copyright © 2019 Faganello. All rights reserved.
//

import UIKit
import WebKit

class PokerViewController: UIViewController, WKNavigationDelegate  {
    @IBOutlet weak var webView: WKWebView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        guard let url = URL(string: "https://poker.esp.br") else { return }
        webView.navigationDelegate = self
        webView.load(URLRequest(url: url))
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func webView(_ webView: WKWebView, decidePolicyFor navigationAction: WKNavigationAction, decisionHandler: @escaping (WKNavigationActionPolicy) -> Void) {
        decisionHandler(.allow)
    }
    
    @IBAction func cancel(_ sender: Any) {
        self.navigationController?.dismiss(animated: true, completion: nil)
    }
}
