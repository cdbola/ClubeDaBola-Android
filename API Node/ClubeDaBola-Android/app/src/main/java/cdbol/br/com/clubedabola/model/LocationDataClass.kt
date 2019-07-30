
package cdbol.br.com.clubedabola.model

import com.squareup.moshi.Json


data class LocResult(
        @field:Json(name= "results") val results: List<Result>,
        @field:Json(name= "status") val status: String
)

data class Result(
        @field:Json(name= "address_components")  val address_components: List<AddressComponent>,
        @field:Json(name= "formatted_address") val formatted_address: String,
        @field:Json(name= "geometry") val geometry: Geometry?,
        @field:Json(name= "partial_match") val partial_match: Boolean?,
        @field:Json(name= "place_id") val place_id: String,
        @field:Json(name= "types") val types: List<String>
)

data class Geometry(
        @field:Json(name= "bounds") val bounds: Bounds?,
        @field:Json(name= "location") val location: Location?,
        @field:Json(name= "location_type") val location_type: String,
        @field:Json(name= "viewport") val viewport: Viewport?
)

data class Bounds(
        @field:Json(name= "northeast") val northeast: Northeast?,
        @field:Json(name= "southwest") val southwest: Southwest?
)

data class Southwest(
        @field:Json(name= "lat") val lat: Double,
        @field:Json(name= "lng") val lng: Double
)

data class Northeast(
        @field:Json(name= "lat") val lat: Double,
        @field:Json(name= "lng") val lng: Double
)

data class Location(
        @field:Json(name= "lat") val lat: Double,
        @field:Json(name= "lng") val lng: Double
)

data class Viewport(
        @field:Json(name= "northeast") val northeast: Northeast?,
        @field:Json(name= "southwest") val southwest: Southwest?
)

data class AddressComponent(
        @field:Json(name= "long_name") val long_name: String,
        @field:Json(name= "short_name") val short_name: String,
        @field:Json(name= "types") val types: List<String>
)