import kotlin.math.min

enum class Rate(val value: Double) {
    DOMESTIC_FREE_LIMIT(75.0),
    DOMESTIC_RATE(0.10),
    INTERNATIONAL_RATE(0.15),
    INTERNATIONAL_MAX(50.0)
}

enum class Currency(val rate: Double) {
    USD(1.0),
    EUR(0.85),
    PLN(3.8)
}

class Order(private val amount: Double, private val isInternational: Boolean, private val currency: Currency) {

    fun calculateShippingCost(): Double {
        val convertedAmount = amount / currency.rate
        val baseCost = if (isInternational) internationalShippingCost(convertedAmount) else domesticShippingCost(convertedAmount)
        return baseCost * currency.rate * applyDiscount(convertedAmount)
    }

    private fun domesticShippingCost(orderAmount: Double): Double {
        return if (orderAmount >= Rate.DOMESTIC_FREE_LIMIT.value) 0.0 else orderAmount * Rate.DOMESTIC_RATE.value
    }

    private fun internationalShippingCost(orderAmount: Double): Double {
        return min(Rate.INTERNATIONAL_MAX.value, orderAmount * Rate.INTERNATIONAL_RATE.value)
    }

    private fun applyDiscount(orderAmount: Double): Double {
        return if (orderAmount > 100) 0.9 else 1.0
    }
}

fun getUserInput(): Triple<Double, Boolean, Currency> {
    var isValid = false
    var orderAmount = 0.0
    var isInternational = false
    var currency = Currency.USD

    while (!isValid) {
        try {
            println("Enter the order amount:")
            orderAmount = readLine()!!.toDouble()
            isValid = true
        } catch (e: NumberFormatException) {
            println("Invalid input. Please enter a valid number.")
        }
    }

    println("Is this an international order? (Yes/No)")
    isInternational = readLine()!!.toLowerCase() == "yes"

    println("Enter the currency (USD/EUR/PLN):")
    currency = Currency.valueOf(readLine()!!.toUpperCase())

    return Triple(orderAmount, isInternational, currency)
}

fun main(args: Array<String>) {
    val (orderAmount, isInternational, currency) = getUserInput()
    val order = Order(orderAmount, isInternational, currency)
    val cost = order.calculateShippingCost()
    println("Shipping cost: ${String.format("%.2f", cost)} ${currency.name}")
}
