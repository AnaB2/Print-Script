package lexer

import lexer.TokenClassifierStrategy

class KeywordClassifier(private val keywords : Set<String>) : TokenClassifierStrategy {
    override fun classify(tokenValue: String): Boolean {
        return keywords.contains(tokenValue);
    }
}