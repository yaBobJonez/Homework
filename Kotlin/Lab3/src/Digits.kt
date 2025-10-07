class Digits(string: String) : Strings(string) {
    init {
        require(string.matches(Regex("[0-9]+"))) { "Digits має містити цифри" }
    }

    override fun shift(n: Int) {
        require(n >= 0) { "Аргумент має бути невідʼємним цілим числом" }
        if (n == 0 || string.length < 2) return
        string = string.takeLast(n) + string.dropLast(n)
    }
}