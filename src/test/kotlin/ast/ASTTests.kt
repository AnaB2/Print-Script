package ast

import model.ast.BasicNode
import model.token.DefaultTokenType
import model.token.Token
import model.token.TokenPosition
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ASTTests {

    @Test
    fun `test print node with right child`() {
        val printToken = Token(DefaultTokenType.FUNCTION, "print", TokenPosition(0, 0), TokenPosition(0, 5))
        val literalToken = Token(DefaultTokenType.STRING_LITERAL, "Hello World", TokenPosition(0, 0), TokenPosition(0, 5))

        val printNode = BasicNode(token = printToken)
        val stringNode = BasicNode(token = literalToken)

        printNode.setRight(stringNode)

        assertEquals(stringNode, printNode.getRight())
    }

    @Test
    fun `test assignment node`() {
        val assignToken = Token(DefaultTokenType.ASSIGN, "=", TokenPosition(1, 1), TokenPosition(1, 6))
        val literalToken = Token(DefaultTokenType.NUMBER_LITERAL, "6", TokenPosition(2, 2), TokenPosition(3, 3))
        val identifierToken = Token(DefaultTokenType.IDENTIFIER, "x", TokenPosition(1, 1), TokenPosition(1, 1))

        val identifierNode = BasicNode(token = identifierToken)
        val literalNode = BasicNode(token = literalToken)
        val assignNode = BasicNode(token = assignToken, left = identifierNode, right = literalNode)

        assertEquals("6", assignNode.getRight()?.getToken()?.getValue())
        assertEquals("x", assignNode.getLeft()?.getToken()?.getValue())
    }

    @Test
    fun `test println node with expression`() {
        val printlnToken = Token(DefaultTokenType.FUNCTION, "println", TokenPosition(1, 1), TokenPosition(1, 6))
        val operatorToken = Token(DefaultTokenType.OPERATOR, "-", TokenPosition(2, 2), TokenPosition(3, 3))
        val identifierXToken = Token(DefaultTokenType.IDENTIFIER, "x", TokenPosition(1, 1), TokenPosition(1, 1))
        val identifierYToken = Token(DefaultTokenType.IDENTIFIER, "y", TokenPosition(1, 1), TokenPosition(1, 1))

        val identifierXNode = BasicNode(token = identifierXToken)
        val identifierYNode = BasicNode(token = identifierYToken)
        val operatorNode = BasicNode(token = operatorToken, left = identifierXNode, right = identifierYNode)
        val printlnNode = BasicNode(token = printlnToken, right = operatorNode)

        assertEquals("-", printlnNode.getRight()?.getToken()?.getValue())
        assertEquals("x", printlnNode.getRight()?.getLeft()?.getToken()?.getValue())
        assertEquals("y", printlnNode.getRight()?.getRight()?.getToken()?.getValue())
    }
}
