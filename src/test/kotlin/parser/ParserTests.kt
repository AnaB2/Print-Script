package parser

import model.token.Position
import model.token.Token
import model.token.TokenType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ParserTests {

    @Test
    fun `test simple assignment`() {
        val tokens = listOf(
            Token(TokenType.KEYWORD, "let", Position(0, 0), Position(1, 3)),
            Token(TokenType.IDENTIFIER, "var", Position(0, 4), Position(1, 9)),
            Token(TokenType.DECLARATOR, ":", Position(0, 10), Position(1, 11)),
            Token(TokenType.DATA_TYPE, "string", Position(0, 12), Position(1, 18)),
            Token(TokenType.ASSIGNATION, "=", Position(0, 19), Position(1, 20)),
            Token(TokenType.LITERAL, "hola", Position(0, 21), Position(1, 27)),
            Token(TokenType.OPERATOR, "+", Position(0, 213), Position(1, 27)),
            Token(TokenType.LITERAL, "1", Position(0, 21234), Position(1, 27)),
            Token(TokenType.OPERATOR, "*", Position(0, 21234), Position(1, 27)),
            Token(TokenType.LITERAL, "15", Position(0, 21234), Position(1, 27)),
            Token(TokenType.PUNCTUATOR, ";", Position(0, 28), Position(1, 29)),
        )

        val parser = Parser()
        val abstractSyntaxTrees = parser.execute(tokens)

        assertEquals(1, abstractSyntaxTrees.size)

        val ast = abstractSyntaxTrees[0]
        val leftNode = ast.getLeft()
        val rightNode = ast.getRight()

        assertEquals("=", ast.getToken().getValue())
        assertEquals("let", leftNode?.getToken()?.getValue())
        val declarationTokens = leftNode?.getRight()
        assertEquals(":", declarationTokens?.getToken()?.getValue())
        assertEquals("var", declarationTokens?.getLeft()?.getToken()?.getValue())
        assertEquals("string", declarationTokens?.getRight()?.getToken()?.getValue())
        assertEquals("+", rightNode?.getToken()?.getValue())
        assertEquals("hola", rightNode?.getLeft()?.getToken()?.getValue())
        assertEquals("*", rightNode?.getRight()?.getToken()?.getValue())
        assertEquals("15", rightNode?.getRight()?.getRight()?.getToken()?.getValue())
        assertEquals("1", rightNode?.getRight()?.getLeft()?.getToken()?.getValue())
    }

    @Test
    fun `test simple println`() {
        val tokens = listOf(
            Token(TokenType.FUNCTION, "println", Position(0, 0), Position(1, 6)),
            Token(TokenType.PUNCTUATOR, "(", Position(0, 7), Position(1, 8)),
            Token(TokenType.LITERAL, "Hello", Position(0, 9), Position(1, 15)),
            Token(TokenType.OPERATOR, "+", Position(0, 16), Position(1, 15)),
            Token(TokenType.LITERAL, "world", Position(0, 16), Position(1, 15)),
            Token(TokenType.PUNCTUATOR, ")", Position(0, 22), Position(1, 23)),
            Token(TokenType.PUNCTUATOR, ";", Position(0, 24), Position(1, 25)),
        )

        val parser = Parser()
        val abstractSyntaxTrees = parser.execute(tokens)

        val ast = abstractSyntaxTrees[0]
        val rightNode = ast.getRight()

        assertEquals("println", ast.getToken().getValue())
        assertEquals("Hello", rightNode?.getLeft()?.getToken()?.getValue())
        assertEquals("world", rightNode?.getRight()?.getToken()?.getValue())
    }

    @Test
    fun `test println with arguments`() {
        val tokens = listOf(
            Token(TokenType.FUNCTION, "println", Position(0, 0), Position(1, 6)),
            Token(TokenType.PUNCTUATOR, "(", Position(0, 7), Position(1, 8)),
            Token(TokenType.LITERAL, "Hello", Position(0, 9), Position(1, 15)),
            Token(TokenType.OPERATOR, "+", Position(0, 16), Position(1, 15)),
            Token(TokenType.LITERAL, "world", Position(0, 16), Position(1, 15)),
            Token(TokenType.OPERATOR, "+", Position(0, 16), Position(1, 15)),
            Token(TokenType.LITERAL, "!", Position(0, 16), Position(1, 15)),
            Token(TokenType.PUNCTUATOR, ")", Position(0, 22), Position(1, 23)),
            Token(TokenType.PUNCTUATOR, ";", Position(0, 24), Position(1, 25)),
        )

        val parser = Parser()
        val abstractSyntaxTrees = parser.execute(tokens)

        val ast = abstractSyntaxTrees[0]
        val rightNode = ast.getRight()

        assertEquals("println", ast.getToken().getValue())
        assertEquals("Hello", rightNode?.getLeft()?.getToken()?.getValue())
        assertEquals("+", rightNode?.getToken()?.getValue())
        assertEquals("+", rightNode?.getRight()?.getToken()?.getValue())
        assertEquals("!", rightNode?.getRight()?.getRight()?.getToken()?.getValue())
        assertEquals("world", rightNode?.getRight()?.getLeft()?.getToken()?.getValue())
    }

    @Test
    fun `test multiple assignments`() {
        val tokens = listOf(
            Token(TokenType.KEYWORD, "let", Position(0, 0), Position(1, 3)),
            Token(TokenType.IDENTIFIER, "var", Position(0, 4), Position(1, 9)),
            Token(TokenType.DECLARATOR, ":", Position(0, 10), Position(1, 11)),
            Token(TokenType.DATA_TYPE, "string", Position(0, 12), Position(1, 18)),
            Token(TokenType.ASSIGNATION, "=", Position(0, 19), Position(1, 20)),
            Token(TokenType.LITERAL, "hola", Position(0, 21), Position(1, 27)),
            Token(TokenType.PUNCTUATOR, ";", Position(0, 28), Position(1, 29)),
            Token(TokenType.IDENTIFIER, "var", Position(1, 0), Position(1, 3)),
            Token(TokenType.ASSIGNATION, "=", Position(1, 4), Position(1, 5)),
            Token(TokenType.LITERAL, "chau", Position(1, 6), Position(1, 11)),
            Token(TokenType.PUNCTUATOR, ";", Position(1, 12), Position(1, 13)),
        )

        val parser = Parser()
        val abstractSyntaxTrees = parser.execute(tokens)

        assertEquals(2, abstractSyntaxTrees.size)

        val ast = abstractSyntaxTrees[0]
        val leftNode = ast.getLeft()
        val rightNode = ast.getRight()

        assertEquals("=", ast.getToken().getValue())
        assertEquals("let", leftNode?.getToken()?.getValue())
        val declarators = leftNode?.getRight()
        assertEquals(":", declarators?.getToken()?.getValue())
        assertEquals("var", declarators?.getLeft()?.getToken()?.getValue())
        assertEquals("string", declarators?.getRight()?.getToken()?.getValue())
        assertEquals("hola", rightNode?.getToken()?.getValue())

        val ast2 = abstractSyntaxTrees[1]
        val leftNode2 = ast2.getLeft()
        val rightNode2 = ast2.getRight()

        assertEquals("=", ast2.getToken().getValue())
        assertEquals("var", leftNode2?.getToken()?.getValue())
        assertEquals("chau", rightNode2?.getToken()?.getValue())
    }

    @Test
    fun `test variable declaration`() {
        val tokens = listOf(
            Token(TokenType.KEYWORD, "let", Position(0, 0), Position(1, 3)),
            Token(TokenType.IDENTIFIER, "x", Position(0, 4), Position(1, 5)),
            Token(TokenType.DECLARATOR, ":", Position(0, 6), Position(1, 7)),
            Token(TokenType.DATA_TYPE, "number", Position(0, 8), Position(1, 14)),
            Token(TokenType.PUNCTUATOR, ";", Position(0, 15), Position(1, 16)),
        )

        val parser = Parser()
        val abstractSyntaxTrees = parser.execute(tokens)

        assertEquals(1, abstractSyntaxTrees.size)

        val ast = abstractSyntaxTrees[0]
        val rightNode = ast.getRight()

        assertEquals("let", ast.getToken().getValue())
        assertEquals(":", rightNode?.getToken()?.getValue())
        assertEquals("x", rightNode?.getLeft()?.getToken()?.getValue())
        assertEquals("number", rightNode?.getRight()?.getToken()?.getValue())
    }
}
