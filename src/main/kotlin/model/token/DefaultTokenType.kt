package model.token

enum class DefaultTokenType : TokenType {
    // Palabras clave
    LET,
    PRINTLN,

    // Tipos
    NUMBER,
    STRING,

    // Operadores
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,

    // Símbolos
    SEMICOLON,
    ASSIGN,

    // Identificadores y literales
    IDENTIFIER,
    NUMBER_LITERAL,
    STRING_LITERAL,

    // Fin de archivo
    EOF,

    // Funciones
    FUNCTION,

    // Operadores
    OPERATOR;

    override fun getName(): String {
        return name
    }
}
