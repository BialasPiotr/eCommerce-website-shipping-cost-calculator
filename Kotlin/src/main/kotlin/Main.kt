fun shippingCost(orderAmount: Double, isInternational: Boolean): Double {
    return if (isInternational) {
        minOf(orderAmount * 0.15, 50.0)
    } else {
        if (orderAmount >= 75.0) {
            0.0
        } else {
            orderAmount * 0.10
        }
    }
}

fun main(args: Array<String>) {
    println("Enter the order amount:")
    val orderAmount = readLine()!!.toDouble()
    println("Is this an international order? (Yes/No)")
    val isInternational = readLine()!!.toLowerCase() == "Yes"

    val cost = shippingCost(orderAmount, isInternational)
    println("Shipping cost: $cost")
}
