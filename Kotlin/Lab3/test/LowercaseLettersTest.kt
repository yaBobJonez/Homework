import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class LowercaseLettersTest {
    @Test
    fun `constructor accepts lowercase letters`() {
        val s = LowercaseLetters("latynyciaкирилиця")
        assertEquals("latynyciaкирилиця", s.data)
    }

    @ParameterizedTest
    @ValueSource(strings = [
        "ABC",
        "abc123",
        "abc!$%",
        "AbcD",
        "",
        "abc def"
    ])
    fun `constructor throws on invalid symbols`(input: String) {
        val ex = assertThrows<IllegalArgumentException> {
            LowercaseLetters(input)
        }
        assertEquals("LowercaseLetters має містити малі літери", ex.message)
    }

    @Test
    fun `shift with negative n throws`() {
        val s = LowercaseLetters("abcd")
        val ex = assertThrows<IllegalArgumentException> {
            s.shift(-1)
        }
        assertEquals("Аргумент має бути невідʼємним цілим числом", ex.message)
    }

    @Test
    fun `shift with zero does not change string`() {
        val s = LowercaseLetters("abcd")
        s.shift(0)
        assertEquals("abcd", s.data)
    }

    @Test
    fun `shift with n less than string length works`() {
        val s = LowercaseLetters("abcd")
        s.shift(2)
        assertEquals("cdab", s.data)
    }

    @Test
    fun `shift with n equals to string length returns original string`() {
        val s = LowercaseLetters("abcd")
        s.shift(4)
        assertEquals("abcd", s.data)
    }

    @Test
    fun `shift with n greater than string length works`() {
        val s = LowercaseLetters("abcd")
        s.shift(6)
        assertEquals("abcd", s.data)
    }
}
