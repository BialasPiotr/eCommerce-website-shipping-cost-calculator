import kotlin.math.min

enum class Rate(val value: Double) {
    DOMESTIC_FREE_LIMIT(75.0),
    DOMESTIC_RATE(0.10),
    INTERNATIONAL_RATE(0.15),
    INTERNATIONAL_MAX(50.0)
}

fun domesticShippingCost(orderAmount: Double): Double {
    return if (orderAmount >= Rate.DOMESTIC_FREE_LIMIT.value) 0.0 else orderAmount * Rate.DOMESTIC_RATE.value
}

fun internationalShippingCost(orderAmount: Double): Double {
    return min(Rate.INTERNATIONAL_MAX.value, orderAmount * Rate.INTERNATIONAL_RATE.value)
}

fun shippingCost(orderAmount: Double, isInternational: Boolean): Double {
    return if (isInternational) internationalShippingCost(orderAmount) else domesticShippingCost(orderAmount)
}

fun getUserInput(): Pair<Double, Boolean> {
    var isValid = false
    var orderAmount = 0.0
    var isInternational = false

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

    return Pair(orderAmount, isInternational)
}

fun main(args: Array<String>) {
    val (orderAmount, isInternational) = getUserInput()
    val cost = shippingCost(orderAmount, isInternational)
    println("Shipping cost: $cost")
}
