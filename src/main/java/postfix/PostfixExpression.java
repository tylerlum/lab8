package postfix;

public class PostfixExpression {
    private Token t1;
    private Token t2;
    private Token operator;

    public PostfixExpression(Token t1, Token t2, Token operator) throws MalformedExpressionException {
        this.t1 = t1;
        this.t2 = t2;
        this.operator = operator;

        if (!t1.isDouble() || !t2.isDouble() || operator.isDouble()) {
            throw new MalformedExpressionException();
        }
    }

    public Token getT1(){ return t1; }
    public Token getT2(){ return t2; }
    public Token getOperator(){ return operator; }

    public Double eval() throws MalformedExpressionException {
        if (operator.equals("+")) {
            return t1.getValue() + t2.getValue();
        } else if (operator.equals("-")) {
            return t1.getValue() - t2.getValue();
        } else if (operator.equals("*")) {
            return t1.getValue() * t2.getValue();
        } else if (operator.equals("/")) {
            return t1.getValue() / t2.getValue();
        } else {
            throw new MalformedExpressionException();
        }
    }

}
