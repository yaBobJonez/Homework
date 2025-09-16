fun main() {
    var a: Int
    var b: Int
    while (true) {
        try {
            print("Ділене: ")
            a = readln().toInt()
            print("Дільник: ")
            b = readln().toInt()
            check(a > 0 && b > 0)
            break
        } catch (_: NumberFormatException) {
            println("Некоректний формат введення.")
        } catch (_: IllegalStateException) {
            println("a та b мають бути натуральними числами.")
        }
    }
    var quotient = 0
    while (a >= b) {
        a -= b
        ++quotient
    }
    println("Частка: $quotient")
}
