package parser.factories

import model.ast.ASTNode
import model.ast.BasicNode
import model.token.Token
import model.token.TokenType
import parser.ASTFactory

class DeclarationFactory : ASTFactory {
    override fun createAST(tokens: List<Token>): ASTNode {
        val root = BasicNode(token = tokens.find { it.getType() == TokenType.KEYWORD }!!)
        val identifierToken = tokens.find { it.getType() == TokenType.IDENTIFIER }!!
        val dataTypeToken = tokens.find { it.getType() == TokenType.DATA_TYPE }!!

        root.setLeft(BasicNode(token = identifierToken))
        root.setRight(BasicNode(token = dataTypeToken))
        return root
    }

    override fun canHandle(tokens: List<Token>): Boolean {
        return tokens.any { it.getType() == TokenType.KEYWORD }
    }
}
