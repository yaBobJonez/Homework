import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DigitsTest {
    @Test
    fun `constructor accepts digits`() {
        val d = Digits("123456")
        assertEquals("123456", d.data)
    }

    @Test
    fun `constructor throws on non-digits`() {
        val ex = assertThrows<IllegalArgumentException> {
            Digits("12a34bra")
        }
        assertEquals("Digits має містити цифри", ex.message)
    }

    @Test
    fun `constructor throws on empty string`() {
        val ex = assertThrows<IllegalArgumentException> {
            Digits("")
        }
        assertEquals("Digits має містити цифри", ex.message)
    }

    @Test
    fun `shift with negative n throws`() {
        val d = Digits("12345")
        val ex = assertThrows<IllegalArgumentException> {
            d.shift(-1)
        }
        assertEquals("Аргумент має бути невідʼємним цілим числом", ex.message)
    }

    @Test
    fun `shift with zero does not change string`() {
        val d = Digits("987654")
        d.shift(0)
        assertEquals("987654", d.data)
    }

    @Test
    fun `shift with n less than string length works`() {
        val d = Digits("123456")
        d.shift(2)
        assertEquals("561234", d.data)
    }

    @Test
    fun `shift with n equals to string length does not change string`() {
        val d = Digits("123")
        d.shift(3)
        assertEquals("123", d.data)
    }

    @Test
    fun `shift with n greater than string length returns original string`() {
        val d = Digits("789")
        d.shift(5)
        assertEquals("789", d.data)
    }
}
