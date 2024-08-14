package parser.factories

import model.ast.ASTNode
import model.ast.BasicNode
import model.token.Token
import model.token.TokenType
import parser.ASTFactory

class AssignmentFactory : ASTFactory {
    override fun createAST(tokens: List<Token>): ASTNode {
        val root = BasicNode(token = tokens.find { it.getType() == TokenType.ASSIGN }!!)
        val leftTokens = tokens.takeWhile { it.getValue() != "=" }
        val rightTokens = tokens.drop(leftTokens.size + 1)

        root.setLeft(BasicNode(token = leftTokens.first()))
        root.setRight(BasicNode(token = rightTokens.first()))
        return root
    }

    override fun canHandle(tokens: List<Token>): Boolean {
        return tokens.any { it.getType() == TokenType.ASSIGN }
    }
}
