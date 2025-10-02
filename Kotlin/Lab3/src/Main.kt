fun perform(obj: Strings) {
    println("Довжина рядка: ${obj.length}")
    val n = readInt("Введіть n — число символів, що зсуваються")
    obj.shift(n)
    println("Вихідний рядок: ${obj.data}")
    println("Довжина рядка: ${obj.length}")
}

fun main() {
    while (true) {
        try {
            when (readInt("0. Вихід\n1. Strings\n2. Digits\n3. LowercaseLetters\nОберіть тип класу")) {
                0 -> break
                1 -> {
                    print("Введіть довільний рядок: ")
                    perform(Strings(readln()))
                }
                2 -> {
                    print("Введіть цифри: ")
                    perform(Digits(readln()))
                }
                3 -> {
                    print("Введіть маленькі літери: ")
                    perform(LowercaseLetters(readln()))
                }
            }
        } catch (e: IllegalArgumentException) {
            println(e.message)
        }
    }
}

fun readInt(message: String): Int {
    while (true) {
        print("$message: ")
        try {
            return readln().toInt()
        } catch (e: NumberFormatException) {
            println("Введене значення не є цілим числом.")
        }
    }
}