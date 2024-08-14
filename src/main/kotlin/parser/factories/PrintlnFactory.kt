package parser.factories

import model.ast.ASTNode
import model.ast.BasicNode
import model.ast.PrintNode
import model.token.Token
import parser.ASTFactory

class PrintlnFactory : ASTFactory {
    override fun createAST(tokens: List<Token>): ASTNode {
        val root = PrintNode(token = tokens.find { it.getValue() == "println" }!!)
        val rightTokens = tokens.subList(2, tokens.size - 1)
        root.setRight(createAssignedTree(rightTokens))
        return root
    }

    override fun canHandle(tokens: List<Token>): Boolean {
        return tokens.any { it.getValue() == "println" }
    }

    private fun createAssignedTree(tokens: List<Token>): ASTNode {
        return createOperationAST(tokens)
    }

    private fun createOperationAST(tokens: List<Token>): ASTNode {
        if (tokens.size == 1) {
            return BasicNode(token = tokens[0])
        }
        for (token in tokens) {
            if (isAdditionOrSubtraction(token)) {
                val tree = BasicNode(token = token)
                tree.setLeft(createOperationAST(tokens.subList(0, tokens.indexOf(token))))
                tree.setRight(createOperationAST(tokens.subList(tokens.indexOf(token) + 1, tokens.size)))
                return tree
            }
        }
        for (token in tokens) {
            if (isMultiplicationOrDivision(token)) {
                val tree = BasicNode(token = token)
                tree.setLeft(createOperationAST(tokens.subList(0, tokens.indexOf(token))))
                tree.setRight(createOperationAST(tokens.subList(tokens.indexOf(token) + 1, tokens.size)))
                return tree
            }
        }
        throw Exception("Error in operation")
    }

    private fun isMultiplicationOrDivision(token: Token) = token.getValue() == "*" || token.getValue() == "/"
    private fun isAdditionOrSubtraction(token: Token) = token.getValue() == "+" || token.getValue() == "-"
}
