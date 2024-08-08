package model.ast.ast

import model.ast.ASTNode
import model.ast.BasicNode
import model.token.DefaultTokenType
import model.token.Token
import model.token.TokenPosition

class ASTBuilder {
    fun buildAST(
        lines: List<String>,
        index: Int = 0,
    ): BasicNode? {
        if (index >= lines.size) return null
        val line = lines[index]
        val parts = line.split(",")

        val value = Token(DefaultTokenType.IDENTIFIER, parts[0], TokenPosition(0, 0), TokenPosition(0, 1))

        val node = BasicNode(token = value)

        buildAST(lines, index + 1)?.let { node.setLeft(it) }
        buildAST(lines, index + 2)?.let { node.setRight(it) }

        return node
    }

    fun printAST(
        ast: ASTNode?,
        level: Int = 0,
    ) {
        if (ast == null) return
        println(" ".repeat(level * 2) + ast.getToken().getType().getName() + ": " + ast.getToken().getValue())
        printAST(ast.getLeft(), level + 1)
        printAST(ast.getRight(), level + 1)
    }
}
