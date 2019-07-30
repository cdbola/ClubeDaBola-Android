package cdbol.br.com.clubedabola.model

data class CreditCardRequest(
    val creditCard: CreditCard,
    val customerId: String
)

data class CreditCard(
    val cvv: String,
    val expirationMonth: String,
    val expirationYear: String,
    val holder: Holder,
    val number: String
)

data class Holder(
        val birthdate: String,
        val fullname: String,
        val phone: Phone,
        val taxDocument: TaxDocument,
        val billingAddress: BillingAddress?
)

data class Phone(
        val countryCode: String,
        val areaCode: String,
        val number: String
)

data class TaxDocument(
    val number: String,
    val type: String
)

data class CustomerRequest(
        val ownId: String,
        val fullname: String,
        val email: String,
        val birthDate: String,
        val taxDocument: TaxDocument,
        val phone: Phone,
        val fundingInstrument: FundingInstrument,
        val shippingAddress: ShippingAddress

)

data class FundingInstrument(
        val creditCard: CreditCard,
        val method: String
)

data class BillingAddress(
        val city: String,
        val district: String,
        val street: String,
        val streetNumber: String,
        val zipCode: String,
        val state: String,
        val country: String

)

data class ShippingAddress(
        val city: String,
        val complement: String,
        val district: String,
        val street: String,
        val streetNumber: String,
        val zipCode: String,
        val state: String,
        val country: String

)
