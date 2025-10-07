import kotlin.math.abs

open class Strings(protected var string: String) {
    val length: Int
        get() = string.length
    val data: String
        get() = string

    open fun shift(n: Int) {
        if (n >= 0)
            string = " ".repeat(n) + string
        else
            string += " ".repeat(abs(n))
    }
}