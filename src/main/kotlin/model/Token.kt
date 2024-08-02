package model
// Pair<Int,Int> esta relacionado con guardar (linea, columna) tanto del inicio como del final.
data class Token(val start: Pair<Int,Int> , val end: Pair<Int,Int>, val type: TokenType, val value: String){





}