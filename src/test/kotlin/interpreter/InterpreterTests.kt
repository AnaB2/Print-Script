package interpreter

import Interpreter
import model.ast.*
import model.token.Position
import model.token.Token
import model.token.TokenType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class InterpreterTests {

    private lateinit var interpreter: Interpreter

    @BeforeEach
    fun setUp() {
        interpreter = Interpreter()
    }

    @Test
    fun testAssignment() {
        val node = BasicNode(
            left = LeafNode(Token(TokenType.IDENTIFIER, "x", Position(1, 1), Position(1, 2))),
            right = LeafNode(Token(TokenType.NUMBERLITERAL, "5", Position(1, 5), Position(1, 6))),
            token = Token(TokenType.ASSIGNATION, "=", Position(1, 3), Position(1, 4))
        )

        interpreter.interpret(listOf(node))
        assertEquals(5, interpreter.variables["x"])
    }


    @Test
    fun testAddition() {
        val additionNode = BasicNode(
            left = LeafNode(Token(TokenType.NUMBERLITERAL, "5", Position(1, 1), Position(1, 2))),
            right = LeafNode(Token(TokenType.NUMBERLITERAL, "10", Position(1, 5), Position(1, 7))),
            token = Token(TokenType.OPERATOR, "+", Position(1, 3), Position(1, 4))
        )

        val result = interpreter.evaluate(additionNode)
        assertEquals(15, result)
    }


    @Test
    fun testStringConcatenation() {
        val concatNode = BasicNode(
            left = LeafNode(Token(TokenType.STRINGLITERAL, "Hello", Position(1, 1), Position(1, 6))),
            right = LeafNode(Token(TokenType.STRINGLITERAL, " World", Position(1, 7), Position(1, 13))),
            token = Token(TokenType.OPERATOR, "+", Position(1, 6), Position(1, 7))
        )

        val result = interpreter.evaluate(concatNode)
        assertEquals("Hello World", result)
    }


}
