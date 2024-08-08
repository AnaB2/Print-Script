package model.token

class Token(
    private var type: TokenType,
    private var value: String,
    private var initialPosition: Position,
    private var finalPosition: Position,
) {
    fun getType(): TokenType {
        return type
    }

    fun getValue(): String {
        return value
    }

    fun getInitialPosition(): Position {
        return initialPosition
    }

    fun getFinalPosition(): Position {
        return finalPosition
    }
}