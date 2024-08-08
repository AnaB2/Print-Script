package lexer

import model.token.TokenType

class TkClassifier(private val version:String) {

    private val strategyMap: MutableMap<TokenType, TokenClassifierStrategy> = mutableMapOf();


    init {
        initializeStrategies()
    }

    // Inicializa las estrategias en el mapa según la versión
    private fun initializeStrategies() {
        when (version) {
            "1.0" -> initializeVersion10Strategies()
            else -> throw IllegalArgumentException("Unsupported version: $version")
        }
    }

    // Configura las estrategias para la versión 1.0
    private fun initializeVersion10Strategies() {
        strategyMap[TokenType.KEYWORD] = KeywordClassifier(setOf("let"))
        strategyMap[TokenType.PARENTHESIS] = RegexTokenClassifier("\\(|\\)".toRegex())
        strategyMap[TokenType.TYPE_OF_DATA] = RegexTokenClassifier("(string|number)".toRegex())
        strategyMap[TokenType.OPERATOR] = RegexTokenClassifier("[+\\-*/%=><!&|^~]*".toRegex())
        strategyMap[TokenType.LITERAL] = RegexTokenClassifier("""(?:"([^"]*)"|'([^']*)'|(\d+(?:\.\d+)?))""".toRegex())
        strategyMap[TokenType.IDENTIFIER] = RegexTokenClassifier("""(?<!['"])[a-zA-Z][a-zA-Z0-9_]*(?!['"])""".toRegex())
        strategyMap[TokenType.DECLARATOR] = RegexTokenClassifier("(:)".toRegex())
        strategyMap[TokenType.ASSIGNATION] = RegexTokenClassifier("(=)".toRegex())
        strategyMap[TokenType.PUNCTUATOR] = RegexTokenClassifier("[,;{}\\[\\]\r\n].*".toRegex())
        strategyMap[TokenType.FUNCTION] = RegexTokenClassifier("println".toRegex())
    }

    fun classify(input:String):TokenType{
        for((type,strategy) in strategyMap){
            if(strategy.classify(input)){
                return type;
            }
        }

        return TokenType.UNKNOWN
    }


    fun getStrategyMap(): Map<TokenType, TokenClassifierStrategy> {
        return strategyMap
    }








}