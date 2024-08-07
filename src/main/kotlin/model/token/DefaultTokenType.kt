package model.token

enum class DefaultTokenType : TokenType {
    // Palabras clave
    LET {
        override fun getName() = "LET"
    },
    PRINTLN {
        override fun getName() = "PRINTLN"
    },

    // Tipos
    NUMBER {
        override fun getName() = "NUMBER"
    },
    STRING {
        override fun getName() = "STRING"
    },

    // Operadores
    PLUS {
        override fun getName() = "PLUS"
    },
    MINUS {
        override fun getName() = "MINUS"
    },
    MULTIPLY {
        override fun getName() = "MULTIPLY"
    },
    DIVIDE {
        override fun getName() = "DIVIDE"
    },

    // Símbolos
    SEMICOLON {
        override fun getName() = "SEMICOLON"
    },
    ASSIGN {
        override fun getName() = "ASSIGN"
    },

    // Identificadores y literales
    IDENTIFIER {
        override fun getName() = "IDENTIFIER"
    },
    NUMBER_LITERAL {
        override fun getName() = "NUMBER_LITERAL"
    },
    STRING_LITERAL {
        override fun getName() = "STRING_LITERAL"
    },

    // Fin de archivo
    EOF {
        override fun getName() = "EOF"
    }
}
