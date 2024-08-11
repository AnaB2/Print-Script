import model.ast.ASTNode
import model.token.TokenType

class Interpreter {
    val variables = mutableMapOf<String, Any>()

    fun interpret(ast: List<ASTNode>) {
        for (node in ast) {
            evaluate(node)
        }
    }

    fun evaluate(node: ASTNode): Any? {
        val token = node.getToken()

        return when (token.getType()) {
            TokenType.LITERAL -> {
                token.getValue().toIntOrNull() ?: token.getValue()
            }
            TokenType.IDENTIFIER -> {
                variables[token.getValue()] ?: throw RuntimeException("Variable '${token.getValue()}' not defined")
            }
            TokenType.OPERATOR -> {
                val leftValue = evaluate(node.getLeft()!!) ?: throw RuntimeException("Invalid left operand")
                val rightValue = evaluate(node.getRight()!!) ?: throw RuntimeException("Invalid right operand")

                if (leftValue is Int && rightValue is Int) {
                    when (token.getValue()) {
                        "+" -> leftValue + rightValue
                        "-" -> leftValue - rightValue
                        "*" -> leftValue * rightValue
                        "/" -> {
                            if (rightValue == 0) throw RuntimeException("Division by zero")
                            leftValue / rightValue
                        }
                        else -> throw RuntimeException("Unknown operator: ${token.getValue()}")
                    }
                } else {
                    throw RuntimeException("Unsupported operand types for operator '${token.getValue()}'")
                }
            }
            TokenType.ASSIGNATION -> {
                val identifierNode = node.getLeft() ?: throw RuntimeException("Missing identifier")
                val value = evaluate(node.getRight()!!) ?: throw RuntimeException("Invalid assignment")
                variables[identifierNode.getToken().getValue()] = value
                value
            }
            TokenType.KEYWORD -> {
                when (token.getValue()) {
                    "print" -> {
                        val value = evaluate(node.getLeft()!!)
                        println(value)
                        value
                    }
                    else -> throw RuntimeException("Unknown keyword: ${token.getValue()}")
                }
            }
            TokenType.BOOLEAN -> {
                when (token.getValue()) {
                    "true" -> true
                    "false" -> false
                    else -> throw RuntimeException("Unknown boolean value: ${token.getValue()}")
                }
            }
            else -> throw RuntimeException("Unknown node type: ${token.getType()}")
        }
    }
}
