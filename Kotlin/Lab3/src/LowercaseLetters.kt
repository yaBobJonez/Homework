class LowercaseLetters(string: String) : Strings(string) {
    init {
        require(string.matches(Regex("\\p{Ll}+")))
            { "LowercaseLetters має містити малі літери" }
    }

    override fun shift(n: Int) {
        require(n >= 0) { "Аргумент має бути невідʼємним цілим числом" }
        if (n == 0 || string.length < 2) return
        string = string.drop(n) + string.take(n)
    }
}