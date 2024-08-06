package model.token

interface TokenType {
    fun getName(): String
}
// Ejemplo de enum
//enum class TokenType {
//    // Palabras clave
//    LET, PRINTLN,
//
//    // Tipos
//    NUMBER, STRING,
//
//    // Operadores
//    PLUS, MINUS, MULTIPLY, DIVIDE,
//
//    // Símbolos
//    SEMICOLON, COLON, ASSIGN, LPAREN, RPAREN,
//
//    // Identificadores y literales
//    IDENTIFIER, NUMBER_LITERAL, STRING_LITERAL,
//
//    // Fin de archivo
//    EOF
//}