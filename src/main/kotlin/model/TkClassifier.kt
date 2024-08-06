package model

import model.token.TokenType

class TkClassifier( private val typeMap: Map<String, TokenType> ) {

    fun classify(char: String) : TokenType {
        return typeMap[char] ?: TokenType.UNKNOWN

    }


}