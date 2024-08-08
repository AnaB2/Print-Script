package ast

import model.ast.BasicNode
import model.token.Token
import model.token.Position
import model.token.TokenType
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ASTTests {

    @Test
    fun `test print node with right child`() {
        val printToken = Token(TokenType.FUNCTION, "print", Position(0, 0), Position(0, 5))
        val literalToken = Token(TokenType.TYPE_OF_DATA, "Hello World", Position(0, 0), Position(0, 5))

        val printNode = BasicNode(token = printToken)
        val stringNode = BasicNode(token = literalToken)

        printNode.setRight(stringNode)

        assertEquals(stringNode, printNode.getRight())
    }

    @Test
    fun `test assignment node`() {
        val assignToken = Token(TokenType.ASSIGNATION, "=", Position(1, 1), Position(1, 6))
        val literalToken = Token(TokenType.TYPE_OF_DATA, "6", Position(2, 2), Position(3, 3))
        val identifierToken = Token(TokenType.IDENTIFIER, "x", Position(1, 1), Position(1, 1))

        val identifierNode = BasicNode(token = identifierToken)
        val literalNode = BasicNode(token = literalToken)
        val assignNode = BasicNode(token = assignToken, left = identifierNode, right = literalNode)

        assertEquals("6", assignNode.getRight()?.getToken()?.getValue())
        assertEquals("x", assignNode.getLeft()?.getToken()?.getValue())
    }

    @Test
    fun `test println node with expression`() {
        val printlnToken = Token(TokenType.FUNCTION, "println", Position(1, 1), Position(1, 6))
        val operatorToken = Token(TokenType.OPERATOR, "-", Position(2, 2), Position(3, 3))
        val identifierXToken = Token(TokenType.IDENTIFIER, "x", Position(1, 1), Position(1, 1))
        val identifierYToken = Token(TokenType.IDENTIFIER, "y", Position(1, 1), Position(1, 1))

        val identifierXNode = BasicNode(token = identifierXToken)
        val identifierYNode = BasicNode(token = identifierYToken)
        val operatorNode = BasicNode(token = operatorToken, left = identifierXNode, right = identifierYNode)
        val printlnNode = BasicNode(token = printlnToken, right = operatorNode)

        assertEquals("-", printlnNode.getRight()?.getToken()?.getValue())
        assertEquals("x", printlnNode.getRight()?.getLeft()?.getToken()?.getValue())
        assertEquals("y", printlnNode.getRight()?.getRight()?.getToken()?.getValue())
    }
}
