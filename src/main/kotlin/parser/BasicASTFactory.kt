package parser

import model.ast.ASTNode
import model.ast.BasicNode
import model.ast.LeafNode
import model.ast.PrintNode
import model.token.Token
import model.token.TokenType

class BasicASTFactory : ASTFactory {
    override fun createAST(tokens: List<Token>): ASTNode {
        return when {
            tokens.any { it.getType() == TokenType.ASSIGN } -> createAssignationAST(tokens)
            tokens.any { it.getType() == TokenType.CONDITIONAL && it.getValue() == "if" } -> createConditionalAST(tokens)
            tokens.any { it.getValue() == "println" } -> createPrintlnAST(tokens)
            tokens.any { it.getType() == TokenType.KEYWORD } -> createDeclarationAST(tokens)
            tokens.any { it.getType() == TokenType.FUNCTION } -> createFunctionAST(tokens)
            else -> throw Exception("Can't handle this sentence")
        }
    }

    override fun canHandle(tokens: List<Token>): Boolean {
        return tokens.any { it.getType() in listOf(TokenType.ASSIGN, TokenType.CONDITIONAL, TokenType.FUNCTION, TokenType.KEYWORD) }
    }

    private fun createAssignationAST(tokens: List<Token>): ASTNode {
        val root = BasicNode(token = tokens.find { it.getType() == TokenType.ASSIGN }!!)
        val leftTokens = getLeftTokens(tokens)
        val rightTokens = getRightTokens(tokens, leftTokens)

        root.setLeft(BasicNode(token = leftTokens.first()))
        if (rightTokens.size > 1) {
            val right = createAssignedTree(rightTokens)
            root.setRight(right)
        } else {
            root.setRight(BasicNode(token = rightTokens[0]))
        }
        return root
    }

    private fun createConditionalAST(tokens: List<Token>): ASTNode {
        val rootConditional = BasicNode(token = tokens.find { it.getValue() == "if" }!!)
        val parser = Parser()
        val indexIf = tokens.indexOfFirst { it.getValue() == "if" }
        val indexElse = tokens.indexOfFirst { it.getValue() == "else" }

        val ifBody = tokens.subList(indexIf + 2, if (indexElse == -1) tokens.size else indexElse)
        val elseBody = if (indexElse != -1) tokens.subList(indexElse + 1, tokens.size) else emptyList()

        rootConditional.setLeft(parser.execute(ifBody).first())
        if (elseBody.isNotEmpty()) {
            rootConditional.setRight(parser.execute(elseBody).first())
        }
        return rootConditional
    }

    private fun createPrintlnAST(tokens: List<Token>): ASTNode {
        val root = PrintNode(token = tokens.find { it.getValue() == "println" }!!)
        val rightTokens = tokens.subList(2, tokens.size - 1)
        root.setRight(createAssignedTree(rightTokens))
        return root
    }

    private fun createDeclarationAST(tokens: List<Token>): ASTNode {
        val root = BasicNode(token = tokens.find { it.getType() == TokenType.KEYWORD }!!)
        val identifierToken = tokens.find { it.getType() == TokenType.IDENTIFIER }!!
        val dataTypeToken = tokens.find { it.getType() == TokenType.DATA_TYPE }!!

        root.setLeft(BasicNode(token = identifierToken))
        root.setRight(BasicNode(token = dataTypeToken))
        return root
    }

    private fun createFunctionAST(tokens: List<Token>): ASTNode {
        val root = BasicNode(token = tokens.find { it.getType() == TokenType.FUNCTION }!!)
        val rightTokens = tokens.filter { it.getType() != TokenType.FUNCTION && it.getType() != TokenType.PARENTHESIS }

        if (rightTokens.size > 1) {
            val right = createAssignedTree(rightTokens)
            root.setRight(right)
        } else {
            root.setRight(BasicNode(token = rightTokens[0]))
        }
        return root
    }

    private fun getLeftTokens(tokens: List<Token>) = tokens.takeWhile { it.getValue() != "=" }

    private fun getRightTokens(tokens: List<Token>, leftTokens: List<Token>) = tokens.drop(leftTokens.size + 1)

    private fun createAssignedTree(tokens: List<Token>): ASTNode {
        if (tokens.any { it.getType() == TokenType.FUNCTION }) {
            return createFunctionAST(tokens)
        }
        return createOperationAST(tokens)
    }

    private fun createOperationAST(tokens: List<Token>): ASTNode {
        if (tokens.size == 1) {
            return LeafNode(tokens[0])
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
