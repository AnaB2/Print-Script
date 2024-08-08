package model.ast

import model.token.Token

class PrintNode(
    child: ASTNode? = null,
    token: Token
) : BasicNode(child, token = token)