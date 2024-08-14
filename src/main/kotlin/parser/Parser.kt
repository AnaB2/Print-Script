package parser

import model.ast.ASTNode
import model.token.Token

class Parser {
    private val astNodes = mutableListOf<ASTNode>()
    private val factories = listOf<ASTFactory>(
        AssignmentFactory(),
        ConditionalFactory(),
        PrintlnFactory(),
        DeclarationFactory()
    )

    fun execute(tokens: List<Token>): List<ASTNode> {
        val lines = split(tokens)
        for (line in lines) {
            val factory = factories.find { it.canHandle(line) }
            if (factory != null) {
                astNodes.add(factory.createAST(line))
            } else {
                throw Exception("Can't handle this sentence")
            }
        }
        return astNodes
    }

    private fun split(tokens: List<Token>): List<List<Token>> {
        val lines = mutableListOf<List<Token>>()
        var currentLine = mutableListOf<Token>()
        for (token in tokens) {
            if (token.getValue() != ";" && token.getValue() != "\n") {
                currentLine.add(token)
            } else {
                lines.add(currentLine)
                currentLine = mutableListOf()
            }
        }
        if (currentLine.isNotEmpty()) {
            lines.add(currentLine)
        }
        if (lines.isEmpty()) {
            throw Exception("Error: Not valid code.")
        }
        return lines
    }
}
