fun transform1(text: String, d1: Char, d2: Char): String {
    require(text.contains(d1) && text.contains(d2))
    val i1 = text.indexOf(d1)
    val i2 = text.indexOf(d2, i1 + 1)
    return if (i2 == -1) text else text.removeRange(i1 + 1, i2)
}

fun transform2(text: String, d1: Char, d2: Char): String {
    val regex = "(?<=$d1).*?(?=($d2)".toRegex()
    require(regex.matches(text))
    return text.replace(regex, "")
}

fun main() {
    while (true) {
        print("Введіть початковий текст: ")
        val text = readln()
        val d1 = readDigit(1)
        val d2 = readDigit(2)
        try {
            println(transform1(text, d1, d2))
            return
        } catch (e: IllegalArgumentException) {
            println("Початковий текст не містить вказаних цифр.")
        }
    }
}

fun readDigit(n: Int): Char {
    while (true){
        print("Введіть цифру $n: ")
        val d = readln()
        if (d.length == 1 && d[0].isDigit())
            return d[0]
        else println("Введене значення не є цифрою.")
    }
}
