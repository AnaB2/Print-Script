import lexer.Lexer
import lexer.TokenClassifier
import model.ast.ASTNode
import model.token.Token
import parser.Parser
import kotlin.test.Test
import kotlin.test.assertEquals

class InterpreterIntegrationTest {

    private val classifier = TokenClassifier("1.0")
    private val lexer = Lexer(classifier)
    private val parser = Parser()
    private val interpreter = Interpreter()

    @Test
    fun `test assignment`() {
        val tokens = lexer.execute("x = 5;")
        val ast = parser.execute(tokens)
        interpreter.interpret(ast)
        assertEquals(5, interpreter.variables["x"])
    }
}