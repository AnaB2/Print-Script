package lexer

import lexer.TokenClassifierStrategy

class RegexTokenClassifier(val regex:Regex): TokenClassifierStrategy {
    override fun classify(tokenValue: String): Boolean {
        return regex.matches(tokenValue)
    }
}