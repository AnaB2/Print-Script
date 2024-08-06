package model.token
// Pair<Int,Int> esta relacionado con guardar (linea, columna) tanto del inicio como del final.
data class Token(
    val start: Position,
    val end: Position,
    val type: TokenType,
    val value: String)


class Position(private val row: Int, private val column:Int)
