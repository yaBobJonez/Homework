fun main() {
    while (true) {
        val a: Float
        val b: Float
        try {
            print("Введіть число a: ")
            a = readln().toFloat()
            print("Введіть число b: ")
            b = readln().toFloat()
            val op = if (a == b) '='
                else if (a < b) '<'
                else '>'
            println("$a $op $b")
            break
        } catch (_: NumberFormatException) {
            println("Некоректний формат введення.")
        }
    }
}
