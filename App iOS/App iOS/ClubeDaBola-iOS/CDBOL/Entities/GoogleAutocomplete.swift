//
//  GoogleAutocomplete.swift
//  B2BMobile
//
//  Created by Bruno Faganello on 21/05/18.
//  Copyright © 2018 XP Investimentos. All rights reserved.
//

import UIKit

class GoogleAutocomplete: Codable {
    let candidates: [Candidate]
    let status: String
    
    init(candidates: [Candidate], status: String) {
        self.candidates = candidates
        self.status = status
    }
    
    init() {
        self.status = ""
        self.candidates = [Candidate]()
    }
}

class Candidate: Codable {
    let formattedAddress: String
    let geometry: Geometry
    let name: String
    let rating: Int
    
    enum CodingKeys: String, CodingKey {
        case formattedAddress = "formatted_address"
        case geometry, name, rating
    }
    
    init(formattedAddress: String, geometry: Geometry, name: String, rating: Int) {
        self.formattedAddress = formattedAddress
        self.geometry = geometry
        self.name = name
        self.rating = rating
    }
}

class Geometry: Codable {
    let location: Location
    let viewport: Viewport
    
    init(location: Location, viewport: Viewport) {
        self.location = location
        self.viewport = viewport
    }
}

class Location: Codable {
    let lat, lng: Double
    
    init(lat: Double, lng: Double) {
        self.lat = lat
        self.lng = lng
    }
}

class Viewport: Codable {
    let northeast, southwest: Location
    
    init(northeast: Location, southwest: Location) {
        self.northeast = northeast
        self.southwest = southwest
    }
}

