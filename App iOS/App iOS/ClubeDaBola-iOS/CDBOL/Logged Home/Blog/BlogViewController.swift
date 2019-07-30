//
//  BlogViewController.swift
//  CDBOL
//
//  Created by Bruno Faganello Neto on 11/08/18.
//  Copyright © 2018 Faganello. All rights reserved.
//

import UIKit
import WebKit

class BlogViewController: UIViewController,WKNavigationDelegate {
    @IBOutlet weak var webView: WKWebView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        guard let url = URL(string: "https://blog.cdbola.com.br") else { return }
        webView.navigationDelegate = self
        webView.load(URLRequest(url: url))
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func webView(_ webView: WKWebView, decidePolicyFor navigationAction: WKNavigationAction, decisionHandler: @escaping (WKNavigationActionPolicy) -> Void) {
        decisionHandler(.allow)
    }
}
