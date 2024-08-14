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

        return when (token.fetchType()) {
            TokenType.NUMBERLITERAL -> {
                token.fetchValue().toIntOrNull() ?: throw RuntimeException("Invalid number literal: ${token.fetchValue()}")
            }
            TokenType.STRINGLITERAL -> {
                token.fetchValue()
            }
            TokenType.IDENTIFIER -> {
                variables[token.fetchValue()] ?: throw RuntimeException("Variable '${token.fetchValue()}' not defined")
            }
            TokenType.OPERATOR -> {
                val leftValue = evaluate(node.getLeft()!!) ?: throw RuntimeException("Invalid left operand")
                val rightValue = evaluate(node.getRight()!!) ?: throw RuntimeException("Invalid right operand")

                if (token.fetchValue() == "+") {
                    when {
                        leftValue is Int && rightValue is Int -> leftValue + rightValue
                        leftValue is String && rightValue is String -> leftValue + rightValue
                        else -> throw RuntimeException("Unsupported operand types for operator '${token.fetchValue()}'")
                    }
                } else {
                    if (leftValue is Int && rightValue is Int) {
                        when (token.fetchValue()) {
                            "-" -> leftValue - rightValue
                            "*" -> leftValue * rightValue
                            "/" -> {
                                if (rightValue == 0) throw RuntimeException("Division by zero")
                                leftValue / rightValue
                            }
                            else -> throw RuntimeException("Unknown operator: ${token.fetchValue()}")
                        }
                    } else {
                        throw RuntimeException("Unsupported operand types for operator '${token.fetchValue()}'")
                    }
                }
            }
            TokenType.ASSIGNATION -> {
                val identifierNode = node.getLeft() ?: throw RuntimeException("Missing identifier")
                val value = evaluate(node.getRight()!!) ?: throw RuntimeException("Invalid assignment")
                variables[identifierNode.getToken().fetchValue()] = value
                value
            }
            TokenType.KEYWORD -> {
                when (token.fetchValue()) {
                    "print" -> {
                        val value = evaluate(node.getLeft()!!)
                        println(value)
                        value
                    }
                    else -> throw RuntimeException("Unknown keyword: ${token.fetchValue()}")
                }
            }
            TokenType.BOOLEAN -> {
                when (token.fetchValue()) {
                    "true" -> true
                    "false" -> false
                    else -> throw RuntimeException("Unknown boolean value: ${token.fetchValue()}")
                }
            }
            else -> throw RuntimeException("Unknown node type: ${token.fetchType()}")
        }
    }


}
