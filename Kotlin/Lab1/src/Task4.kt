fun Int.pow(exponent: Int): Double {
    require(exponent >= 0) { "Exponent cannot be negative for Int.pow" }
    if (exponent == 0)
        return 1.0
    var result = 1.0
    for (i in 1..exponent)
        result *= this
    return result
}

fun main() {
    var N: Int
    while (true) {
        print("Введіть N: ")
        try {
            N = readln().toInt()
            break
        } catch (_: NumberFormatException) {
            println("Некоректний формат введення.")
        }
    }
    var sum = 0.0
    for (n in 1..N) {
        var fact = 1
        for (i in n downTo 1)
            fact *= i
        sum += (3.pow(n) * fact) / n.pow(n)
        println("$n\t$sum")
    }
}
