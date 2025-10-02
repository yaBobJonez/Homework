import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class StringsTest {
    @Test
    fun `length property is correct`() {
        val s = Strings("sample")
        assertEquals(6, s.length)
    }

    @Test
    fun `data returns original string`() {
        val s = Strings("meow")
        assertEquals("meow", s.data)
    }

    @Test
    fun `shift with positive n prepends spaces`() {
        val s = Strings("something")
        s.shift(3)
        assertEquals("   something", s.data)
        assertEquals(12, s.length)
    }

    @Test
    fun `shift with negative n appends spaces`() {
        val s = Strings("data")
        s.shift(-2)
        assertEquals("data  ", s.data)
        assertEquals(6, s.length)
    }

    @Test
    fun `shift with zero does not modify string`() {
        val s = Strings("forever the same")
        s.shift(0)
        assertEquals("forever the same", s.data)
        assertEquals(16, s.length)
    }
}
