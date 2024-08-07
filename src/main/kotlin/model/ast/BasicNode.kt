package model.ast

import model.token.Token

open class BasicNode(
    private var left: ASTNode? = null,
    private var right: ASTNode? = null,
    private var body: List<ASTNode>? = null,
    private var token: Token
) : ASTNode {

    override fun getLeft(): ASTNode? {
        return this.left
    }

    override fun getRight(): ASTNode? {
        return this.right
    }

    override fun getToken(): Token {
        return this.token
    }

    override fun getBody(): List<ASTNode>? {
        return this.body
    }

    override fun isLeaf(): Boolean {
        return left == null && right == null && body == null
    }

    fun setLeft(left: ASTNode) {
        this.left = left
    }

    fun setRight(right: ASTNode) {
        this.right = right
    }
}
