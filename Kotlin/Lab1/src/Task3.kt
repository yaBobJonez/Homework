fun main() {
    var a: Int
    var b: Int
    while (true) {
        try {
            print("Перший множник: ")
            a = readln().toInt()
            print("Другий множник: ")
            b = readln().toInt()
            check(a > 0 && b > 0)
            break
        } catch (_: NumberFormatException) {
            println("Некоректний формат введення.")
        } catch (_: IllegalStateException) {
            println("a та b мають бути натуральними числами.")
        }
    }
    var product = 0
    for (i in 1..b)
        product += a
    println("Добуток: $product")
}
