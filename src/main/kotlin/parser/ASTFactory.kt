package parser

import model.ast.ASTNode
import model.token.Token

interface ASTFactory {
    fun createAST(tokens: List<Token>): ASTNode

    fun canHandle(tokens: List<Token>): Boolean
}