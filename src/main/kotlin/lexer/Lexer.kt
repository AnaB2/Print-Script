package lexer

import model.token.Position
import model.token.Token
import model.token.TokenType
import java.util.regex.Matcher
import java.util.regex.Pattern

class Lexer(private val classifier: TkClassifier) {


    fun execute(input: String): List<Token> {
        val tokens = mutableListOf<Token>()
        var currentRow = 1

        // Split input into lines to handle row positions
        val lines = input.lines()
        for (line in lines) {
            // Process each line
            val matcher = createMatcher(line)
            while (matcher.find()) {
                val tokenValue = matcher.group()
                val tokenType = classifier.classify(tokenValue)

                if (tokenType != TokenType.UNKNOWN) {
                    val startPos = Position(currentRow, matcher.start() + 1)
                    val endPos = Position(currentRow, matcher.end())
                    tokens.add(Token( startPos, endPos,tokenType, tokenValue,))
                }
            }
            currentRow++
        }
        return tokens
    }

    private fun createMatcher(input: String): Matcher {
        val patternBuilder = StringBuilder()
        for ((type, strategy) in classifier.getStrategyMap()) {
            if (strategy is RegexTokenClassifier) {
                patternBuilder.append("(${strategy.regex.pattern})|")
            }
        }
        val pattern = Pattern.compile(patternBuilder.toString().removeSuffix("|"))
        return pattern.matcher(input)
    }


}