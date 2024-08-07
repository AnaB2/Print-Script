package model.token

class Token(
    private var type: TokenType,
    private var value: String,
    private var initialPosition: TokenPosition,
    private var finalPosition: TokenPosition,
) {
    fun getType(): TokenType {
        return type
    }

    fun getValue(): String {
        return value
    }

    fun getInitialPosition(): TokenPosition {
        return initialPosition
    }

    fun getFinalPosition(): TokenPosition {
        return finalPosition
    }
}