import java.io.File
import java.io.IOException

fun readInt(n: Int): Int {
    while (true){
        print("Введіть число k: ")
        try {
            val num = readln().toInt()
            if (num !in 0..<n) throw IllegalStateException("Введений стовпчик має бути від 0 до ${n - 1}")
            return num
        } catch (e: NumberFormatException) {
            println("Введене значення не є цифрою.")
        } catch (e: IllegalStateException) {
            println(e.message)
        }
    }
}

fun formatMatrix(matrix: List<List<Int>>) =
    matrix.joinToString("\n") { row -> row.joinToString("\t") }

fun main(args: Array<String>){
    //generateMatrix(10, 10)
    var n = 0
    var matrix = mutableListOf<List<Int>>()
    try {
        File("input.csv").forEachLine { line ->
            val row = line.split(",").map{ it.trim().toInt() }
            if (n == 0) n = row.size
            else if (row.size != n) throw IllegalArgumentException("Матриця містить нерівні рядки")
            matrix.add(row)
        }
    } catch (e: IOException) {
        println("Помилка читання файлу: ${e.message}")
        return
    } catch (e: SecurityException) {
        println("Недостатньо прав доступу для читання файлу")
        return
    } catch (e: NumberFormatException) {
        println("Матриця містить не число: ${e.message}")
    } catch (e: IllegalArgumentException) {
        println(e.message)
    }

    println("Початкова (вхідна) матриця:")
    println(formatMatrix(matrix))
    val k = readInt(n)
    matrix = matrix.sortedBy { it[k] }.toMutableList()
    println("Матриця, відсортована за $k рядком:")
    println(formatMatrix(matrix))

    try {
        File("output.csv").writeText(formatMatrix(matrix))
    } catch (e: IOException) {
        println("Помилка запису до файлу: ${e.message}")
    } catch (e: SecurityException) {
        println("Недостатньо прав доступу для запису до файлу")
    }
}

fun generateMatrix(m: Int, n:Int) {
    val `if` = File("input.csv")
    val sb = StringBuilder()
    for (rowIndex in 1..m) {
        val row = mutableListOf<Int>()
        for (colIndex in 1..n)
            row.add((1..<100).random())
        sb.appendLine(row.joinToString(","))
    }
    try {
        `if`.writeText(sb.toString())
    } catch (e: IOException) {
        println("Помилка запису до файлу: ${e.message}")
    } catch (e: SecurityException) {
        println("Недостатньо прав доступу для запису до файлу")
    }
}
