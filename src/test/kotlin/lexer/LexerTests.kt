package lexer

import model.token.Position
import model.token.Token
import model.token.TokenType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LexerTests {
    private val tkClassifier = TkClassifier("1.0")
    private val lexer = Lexer(tkClassifier)


    @Test
    fun `test first instruction`() {
        val input = "let name : string = \"Joe\"; "

        val result = listOf<Token>(
            Token(TokenType.KEYWORD, "let", Position(0, 0), Position(0, 3)),
            Token(TokenType.IDENTIFIER, "name", Position(0, 4), Position(0, 8)),
            Token(TokenType.DECLARATOR, ":", Position(0, 9), Position(0, 10)),
            Token(TokenType.TYPE_OF_DATA, "string", Position(0, 11), Position(0, 17)),
            Token(TokenType.ASSIGNATION, "=", Position(0, 18), Position(0, 19)),
            Token(TokenType.STRINGLITERAL, "Joe", Position(0, 20), Position(0, 25)),
            Token(TokenType.PUNCTUATOR, ";", Position(0, 25), Position(0, 26))

        )
        assertEquals(result, lexer.execute(input))


    }

    @Test
    fun `test simple if statement`() {
        val input = "if (a > 5) println(a);"
        val result = listOf(
            Token(TokenType.CONDITIONAL, "if", Position(0, 0), Position(0, 2)),
            Token(TokenType.PARENTHESIS, "(", Position(0, 3), Position(0, 4)),
            Token(TokenType.IDENTIFIER, "a", Position(0, 4), Position(0, 5)),
            Token(TokenType.OPERATOR, ">", Position(0, 6), Position(0, 7)),
            Token(TokenType.NUMBERLITERAL, "5", Position(0, 8), Position(0, 9)),
            Token(TokenType.PARENTHESIS, ")", Position(0, 9), Position(0, 10)),
            Token(TokenType.FUNCTION, "println", Position(0, 11), Position(0, 18)),
            Token(TokenType.PARENTHESIS, "(", Position(0, 18), Position(0, 19)),
            Token(TokenType.IDENTIFIER, "a", Position(0, 19), Position(0, 20)),
            Token(TokenType.PARENTHESIS, ")", Position(0, 20), Position(0, 21)),
            Token(TokenType.PUNCTUATOR, ";", Position(0, 21), Position(0, 22))
        )
        assertEquals(result, lexer.execute(input))
    }



    @Test
    fun `test if statement with assignment`() {
        val input = "if (x < 10) { y = 5; }"
        val result = listOf(
            Token(TokenType.CONDITIONAL, "if", Position(0, 0), Position(0, 2)),
            Token(TokenType.PARENTHESIS, "(", Position(0, 3), Position(0, 4)),
            Token(TokenType.IDENTIFIER, "x", Position(0, 4), Position(0, 5)),
            Token(TokenType.OPERATOR, "<", Position(0, 6), Position(0, 7)),
            Token(TokenType.NUMBERLITERAL, "10", Position(0, 8), Position(0, 10)),
            Token(TokenType.PARENTHESIS, ")", Position(0, 10), Position(0, 11)),
            Token(TokenType.PUNCTUATOR, "{", Position(0, 12), Position(0, 13)),
            Token(TokenType.IDENTIFIER, "y", Position(0, 14), Position(0, 15)),
            Token(TokenType.ASSIGNATION, "=", Position(0, 16), Position(0, 17)),
            Token(TokenType.NUMBERLITERAL, "5", Position(0, 18), Position(0, 19)),
            Token(TokenType.PUNCTUATOR, ";", Position(0, 19), Position(0, 20)),
            Token(TokenType.PUNCTUATOR, "}", Position(0, 21), Position(0, 22))
        )
        assertEquals(result, lexer.execute(input))
    }


    @Test
    fun `test multi-line input with new lines`() {
        val input = "let a : number = 12;\n" +
                "let b : number = 4;\n" +
                "a = a / b;\n"


        val result = listOf(
            Token(TokenType.KEYWORD, "let", Position(0, 0), Position(0, 3)),
            Token(TokenType.IDENTIFIER, "a", Position(0, 4), Position(0, 5)),
            Token(TokenType.DECLARATOR, ":", Position(0, 6), Position(0, 7)),
            Token(TokenType.TYPE_OF_DATA, "number", Position(0, 8), Position(0, 14)),
            Token(TokenType.ASSIGNATION, "=", Position(0, 15), Position(0, 16)),
            Token(TokenType.NUMBERLITERAL, "12", Position(0, 17), Position(0, 19)),
            Token(TokenType.PUNCTUATOR, ";", Position(0, 19), Position(0, 20)),

            Token(TokenType.KEYWORD, "let", Position(1, 0), Position(1, 3)),
            Token(TokenType.IDENTIFIER, "b", Position(1, 4), Position(1, 5)),
            Token(TokenType.DECLARATOR, ":", Position(1, 6), Position(1, 7)),
            Token(TokenType.TYPE_OF_DATA, "number", Position(1, 8), Position(1, 14)),
            Token(TokenType.ASSIGNATION, "=", Position(1, 15), Position(1, 16)),
            Token(TokenType.NUMBERLITERAL, "4", Position(1, 17), Position(1, 18)),
            Token(TokenType.PUNCTUATOR, ";", Position(1, 18), Position(1, 19)),

            Token(TokenType.IDENTIFIER, "a", Position(2, 0), Position(2, 1)),
            Token(TokenType.ASSIGNATION, "=", Position(2, 2), Position(2, 3)),
            Token(TokenType.IDENTIFIER, "a", Position(2, 4), Position(2, 5)),
            Token(TokenType.OPERATOR, "/", Position(2, 6), Position(2, 7)),
            Token(TokenType.IDENTIFIER, "b", Position(2, 8), Position(2, 9)),
            Token(TokenType.PUNCTUATOR, ";", Position(2, 9), Position(2, 10)),


        )

        assertEquals(result, lexer.execute(input))
    }

    @Test
    fun `test sum `() {

        val input = "(2 + 3);"
        val result =
            listOf(
                Token(TokenType.PARENTHESIS, "(", Position(0, 0), Position(0, 1)),
                Token(TokenType.NUMBERLITERAL, "2", Position(0, 1), Position(0, 2)),
                Token(TokenType.OPERATOR, "+", Position(0, 3), Position(0, 4)),
                Token(TokenType.NUMBERLITERAL, "3", Position(0, 5), Position(0, 6)),
                Token(TokenType.PARENTHESIS, ")", Position(0, 6), Position(0, 7)),
                Token(TokenType.PUNCTUATOR, ";", Position(0, 7), Position(0, 8)),
            )
        assertIterableEquals(result, lexer.execute(input))
    }



    @Test
    fun `test decimals`() {
        val input = "1.2"
        val result =
            listOf(
               Token(TokenType.NUMBERLITERAL, "1.2", Position(0, 0), Position(0, 3)),
            )
        assertEquals(result, lexer.execute(input))
    }



    @Test
    fun `test largeOperation`() {
        val input = "123 + 2 * 3 - 4 / 5"
        val result =
            listOf(
                Token(TokenType.NUMBERLITERAL, "123", Position(0, 0), Position(0, 3)),
               Token(TokenType.OPERATOR, "+", Position(0, 4), Position(0, 5)),
                Token(TokenType.NUMBERLITERAL, "2", Position(0, 6), Position(0, 7)),
                Token(TokenType.OPERATOR, "*", Position(0, 8), Position(0, 9)),
                Token(TokenType.NUMBERLITERAL, "3", Position(0, 10), Position(0, 11)),
               Token(TokenType.OPERATOR, "-", Position(0, 12), Position(0, 13)),
                Token(TokenType.NUMBERLITERAL, "4", Position(0, 14), Position(0, 15)),
                Token(TokenType.OPERATOR, "/", Position(0, 16), Position(0, 17)),
                Token(TokenType.NUMBERLITERAL, "5", Position(0, 18), Position(0, 19)),
            )

        assertEquals(result, lexer.execute(input))
    }







}



