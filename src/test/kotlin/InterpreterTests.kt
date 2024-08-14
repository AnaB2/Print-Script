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
            right = LeafNode(Token(TokenType.LITERAL, "5", Position(1, 5), Position(1, 6))),
            token = Token(TokenType.ASSIGNATION, "=", Position(1, 3), Position(1, 4))
        )

        interpreter.interpret(listOf(node))
        assertEquals(5, interpreter.variables["x"])
    }

    @Test
    fun testAddition() {
        val additionNode = BasicNode(
            left = LeafNode(Token(TokenType.LITERAL, "5", Position(1, 1), Position(1, 2))),
            right = LeafNode(Token(TokenType.LITERAL, "10", Position(1, 5), Position(1, 7))),
            token = Token(TokenType.OPERATOR, "+", Position(1, 3), Position(1, 4))
        )

        val result = interpreter.evaluate(additionNode)
        assertEquals(15, result)
    }

    @Test
    fun testPrint() {
        val printNode = PrintNode(
            child = LeafNode(Token(TokenType.LITERAL, "42", Position(1, 7), Position(1, 9))),
            token = Token(TokenType.KEYWORD, "print", Position(1, 1), Position(1, 6))
        )

        // Captura la salida estándar para verificar que se imprime correctamente
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        interpreter.interpret(listOf(printNode))
        assertEquals("42\n", outputStream.toString())
    }
}
