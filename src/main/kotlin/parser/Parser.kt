package parser

import model.ast.ASTNode
import model.token.Token

class Parser {
    private val astNodes = mutableListOf<ASTNode>()
    private val astFactory = BasicASTFactory()

    fun execute(tokens: List<Token>): List<ASTNode> {
        val tokenLines = splitIntoLines(tokens)
        for (lineTokens in tokenLines) {
            if (astFactory.canHandle(lineTokens)) {
                astNodes.add(astFactory.createAST(lineTokens))
            } else {
                throw Exception("Can't handle this sentence")
            }
        }
        return astNodes
    }

    private fun splitIntoLines(tokens: List<Token>): List<List<Token>> {
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
