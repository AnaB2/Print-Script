package model.ast

interface AbstractSyntaxTree {
    fun getLeft() : AbstractSyntaxTree?
    fun getRigth() : AbstractSyntaxTree?
    fun isLeaf() : Boolean
    fun getBody() : List<AbstractSyntaxTree>?
}