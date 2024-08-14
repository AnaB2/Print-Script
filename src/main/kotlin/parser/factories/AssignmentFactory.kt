package parser

import model.ast.ASTNode
import model.ast.BasicNode
import model.token.Token
import model.token.TokenType

class AssignmentFactory : ASTFactory {
    override fun createAST(tokens: List<Token>): ASTNode {
        val root = BasicNode(token = tokens.find { it.getType() == TokenType.ASSIGN }!!)
        val leftTokens = tokens.takeWhile { it.getValue() != "=" }
        val rightTokens = tokens.drop(leftTokens.size + 1)

        root.setLeft(createDeclarationNode(leftTokens))
        root.setRight(createOperationNode(rightTokens))
        return root
    }

    private fun createDeclarationNode(tokens: List<Token>): ASTNode {
        val root = BasicNode(token = tokens.first())
        val identifierToken = tokens.find { it.getType() == TokenType.IDENTIFIER }!!
        val dataTypeToken = tokens.find { it.getType() == TokenType.DATA_TYPE }!!

        root.setLeft(BasicNode(token = identifierToken))
        root.setRight(BasicNode(token = dataTypeToken))
        return root
    }

    private fun createOperationNode(tokens: List<Token>): ASTNode {
        if (tokens.size == 1) {
            return BasicNode(token = tokens[0])
        }
        for (token in tokens) {
            if (isAdditionOrSubtraction(token)) {
                val tree = BasicNode(token = token)
                tree.setLeft(createOperationNode(tokens.subList(0, tokens.indexOf(token))))
                tree.setRight(createOperationNode(tokens.subList(tokens.indexOf(token) + 1, tokens.size)))
                return tree
            }
        }
        for (token in tokens) {
            if (isMultiplicationOrDivision(token)) {
                val tree = BasicNode(token = token)
                tree.setLeft(createOperationNode(tokens.subList(0, tokens.indexOf(token))))
                tree.setRight(createOperationNode(tokens.subList(tokens.indexOf(token) + 1, tokens.size)))
                return tree
            }
        }
        throw Exception("Error in operation")
    }

    override fun canHandle(tokens: List<Token>): Boolean {
        return tokens.any { it.getType() == TokenType.ASSIGN }
    }

    private fun isMultiplicationOrDivision(token: Token) = token.getValue() == "*" || token.getValue() == "/"
    private fun isAdditionOrSubtraction(token: Token) = token.getValue() == "+" || token.getValue() == "-"
}
