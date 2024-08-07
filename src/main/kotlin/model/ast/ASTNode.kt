package model.ast

import model.token.Token

interface ASTNode {
    fun getLeft(): ASTNode? = null

    fun getRight(): ASTNode? = null

    fun getToken(): Token

    fun getBody(): List<ASTNode>? = null

    fun isLeaf(): Boolean
}