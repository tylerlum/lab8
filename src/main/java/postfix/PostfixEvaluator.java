package postfix;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Stack;

/**
 * 
 * @author Sathish Gopalakrishnan
 * 
 * This class contains a method to evaluate an arithmetic expression
 * that is in Postfix notation (or Reverse Polish Notation).
 * See <a href="https://en.wikipedia.org/wiki/Reverse_Polish_notation">Wikipedia</a>
 * for details on the notation.
 *
 */
public class PostfixEvaluator {
	
	private String arithmeticExpr;
	
	/**
	 * This is the only constructor for this class.
	 * It takes a string that represents an arithmetic expression
	 * as input argument.
	 * 
	 * @param expr is a string that represents an arithmetic expression 
	 * <strong>in Postfix notation</strong>.
	 */
	public PostfixEvaluator( String expr ) {
		arithmeticExpr = expr;
	}
	
	/**
	 * This method evaluates the arithmetic expression that 
	 * was passed as a string to the constructor for this class.
	 * 
	 * @return the value of the arithmetic expression
	 * @throws MalformedExpressionException if the provided expression is not
	 * 	a valid expression in Postfix notation
	 */
	public double eval( ) throws MalformedExpressionException {
		// setup scanner and token
		Scanner scanner = new Scanner(arithmeticExpr);
		Stack<Token> arithmeticExprStack = new Stack<>();

		// keep adding tokens to stack
		while (!scanner.isEmpty()) {
			Token currentToken = scanner.getToken();
			scanner.eatToken();

			// if token is operator, then make calculation
			if (isOperator(currentToken)) {
				PostfixExpression postfixExpression = makePostfixExpression(arithmeticExprStack, currentToken);
				Double newTotal = postfixExpression.eval();
				// if no more tokens, return total
				// otherwise, push to stack and continue
				if (scanner.isEmpty()) {
					return newTotal;
				} else {
					arithmeticExprStack.push(new Token(newTotal));
				}
			} else {
				arithmeticExprStack.push(currentToken);
			}
		}
		// only get here if incorrect input
		throw new MalformedExpressionException();
	}

	private Stack<Token> makeArithmeticExprStack(String arithmeticExpr) {
		Scanner scanner = new Scanner(arithmeticExpr);
		Stack<Token> arithmeticExprStack = new Stack<>();
		while (!scanner.isEmpty()) {
			arithmeticExprStack.push(scanner.getToken());
			scanner.eatToken();
		}
		return arithmeticExprStack;
	}

	private PostfixExpression makePostfixExpression(Stack<Token> arithmeticExprStack, Token operator) throws MalformedExpressionException {
		Token t1 = null;
		Token t2 = null;
		if (!arithmeticExprStack.empty()) {
			t2 = arithmeticExprStack.pop();
		}
		if (!arithmeticExprStack.empty()) {
			t1 = arithmeticExprStack.pop();
		}
		if (t1 == null) {
			throw new MalformedExpressionException();
		}
		return new PostfixExpression(t1, t2, operator);
	}

	private boolean isOperator(Token token) {
		if (token.isVariable()) {
			String name = token.getName();
			if (name.equals("+") || name.equals("-") || name.equals("*") || name.equals("/")) {
				return true;
			}
		}
		return false;
	}
}