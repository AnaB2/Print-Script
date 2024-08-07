package model.ast

import model.token.Token

class LeafNode(private var token: Token) : ASTNode {
    override fun isLeaf(): Boolean {
        return true
    }

    override fun getToken(): Token {
        return this.token
    }
}