package parser

import model.ast.ASTNode
import model.ast.BasicNode
import model.token.Token
import model.token.TokenType

class ConditionalFactory : ASTFactory {
    override fun createAST(tokens: List<Token>): ASTNode {
        val root = BasicNode(token = tokens.find { it.getType() == TokenType.CONDITIONAL }!!)
        val indexIf = tokens.indexOfFirst { it.getValue() == "if" }
        val indexElse = tokens.indexOfFirst { it.getValue() == "else" }

        val ifBody = tokens.subList(indexIf + 2, if (indexElse == -1) tokens.size else indexElse)
        val elseBody = if (indexElse != -1) tokens.subList(indexElse + 1, tokens.size) else emptyList()

        val ifAST = parseTokens(ifBody)
        root.setLeft(ifAST)

        if (elseBody.isNotEmpty()) {
            val elseAST = parseTokens(elseBody)
            root.setRight(elseAST)
        }
        return root
    }

    override fun canHandle(tokens: List<Token>): Boolean {
        return tokens.any { it.getType() == TokenType.CONDITIONAL && it.getValue() == "if" }
    }

    private fun parseTokens(tokens: List<Token>): ASTNode {
        val subParser = Parser()
        val astNodes = subParser.execute(tokens)
        return if (astNodes.size == 1) {
            astNodes[0]
        } else {
            val root = BasicNode(token = tokens[0])
            astNodes.forEach { root.setRight(it) }
            root
        }
    }
}
