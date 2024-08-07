package model

import model.token.DefaultTokenType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LexerTest {

    @Test
    fun testLexSimpleInput() {
        val input = """let x = 10;"""
        val lexer = Lexer(input)
        val tokens = lexer.lex()

        val expectedTokens = listOf(
            Token(Pair(1, 1), Pair(1, 4), DefaultTokenType.LET, "let"),
            Token(Pair(1, 5), Pair(1, 6), DefaultTokenType.IDENTIFIER, "x"),
            Token(Pair(1, 7), Pair(1, 8), DefaultTokenType.ASSIGN, "="),
            Token(Pair(1, 9), Pair(1, 11), DefaultTokenType.NUMBER_LITERAL, "10"),
            Token(Pair(1, 11), Pair(1, 12), DefaultTokenType.SEMICOLON, ";"),
            Token(Pair(1, 12), Pair(1, 12), DefaultTokenType.EOF, "")
        )

        assertEquals(expectedTokens, tokens)
    }

    @Test
    fun testLexStringLiteral() {
        val input = """let greeting = "Hello, world!";"""
        val lexer = Lexer(input)
        val tokens = lexer.lex()

        val expectedTokens = listOf(
            Token(Pair(1, 1), Pair(1, 4), DefaultTokenType.LET, "let"),
            Token(Pair(1, 5), Pair(1, 13), DefaultTokenType.IDENTIFIER, "greeting"),
            Token(Pair(1, 14), Pair(1, 15), DefaultTokenType.ASSIGN, "="),
            Token(Pair(1, 16), Pair(1, 30), DefaultTokenType.STRING_LITERAL, "\"Hello, world!\""),
            Token(Pair(1, 30), Pair(1, 31), DefaultTokenType.SEMICOLON, ";"),
            Token(Pair(1, 31), Pair(1, 31), DefaultTokenType.EOF, "")
        )

        assertEquals(expectedTokens, tokens)
    }

    @Test
    fun testLexOperators() {
        val input = """let result = 5 + 3 - 2 * 4 / 1;"""
        val lexer = Lexer(input)
        val tokens = lexer.lex()

        val expectedTokens = listOf(
            Token(Pair(1, 1), Pair(1, 4), DefaultTokenType.LET, "let"),
            Token(Pair(1, 5), Pair(1, 11), DefaultTokenType.IDENTIFIER, "result"),
            Token(Pair(1, 12), Pair(1, 13), DefaultTokenType.ASSIGN, "="),
            Token(Pair(1, 14), Pair(1, 15), DefaultTokenType.NUMBER_LITERAL, "5"),
            Token(Pair(1, 16), Pair(1, 17), DefaultTokenType.PLUS, "+"),
            Token(Pair(1, 18), Pair(1, 19), DefaultTokenType.NUMBER_LITERAL, "3"),
            Token(Pair(1, 20), Pair(1, 21), DefaultTokenType.MINUS, "-"),
            Token(Pair(1, 22), Pair(1, 23), DefaultTokenType.NUMBER_LITERAL, "2"),
            Token(Pair(1, 24), Pair(1, 25), DefaultTokenType.MULTIPLY, "*"),
            Token(Pair(1, 26), Pair(1, 27), DefaultTokenType.NUMBER_LITERAL, "4"),
            Token(Pair(1, 28), Pair(1, 29), DefaultTokenType.DIVIDE, "/"),
            Token(Pair(1, 30), Pair(1, 31), DefaultTokenType.NUMBER_LITERAL, "1"),
            Token(Pair(1, 31), Pair(1, 32), DefaultTokenType.SEMICOLON, ";"),
            Token(Pair(1, 32), Pair(1, 32), DefaultTokenType.EOF, "")
        )

        assertEquals(expectedTokens, tokens)
    }
}
