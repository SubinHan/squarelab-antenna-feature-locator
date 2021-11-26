package lab.square.antennafeaturelocator.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FeatureExpressionParser {

	private Stack<String> expressionStack;
	private String featureExpression;

	public FeatureExpressionParser(String featureExpressions) {
		this.featureExpression = featureExpressions;
		this.expressionStack = stringToStack(featureExpressions);
	}

	public FeatureExpressionParser(Stack<String> featureExpressions) {
		this.expressionStack = featureExpressions;
		this.featureExpression = stackToString(featureExpressions);
	}

	private Stack<String> stringToStack(String featureExpression) {
		
		List<String> tokens = tokenize(featureExpression);
		
		for (String token : tokens) {
			System.out.println(token);
		}

		return null;
	}

	private String stackToString(Stack<String> featureExpressions) {
		String toReturn = "";

		for (String feature : featureExpressions) {
			if (toReturn.isBlank())
				toReturn = feature;
			else
				toReturn = "(" + toReturn + ")&(" + feature + ")";
		} 

		return toReturn;
	}
	
	private List<String> tokenize(String featureExpression) {
		List<String> tokens = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < featureExpression.length(); i++) {
			char c = featureExpression.charAt(i);
			switch (c) {
			case ' ':
				if (sb.length() == 0)
					continue;
				sb.append(c);
				break;

			case '(':
			case ')':
				if (sb.length() > 0) {
					tokens.add(sb.toString());
					sb.setLength(0);
				}
				break;

			case '&':
			case '|':
				if(sb.length() > 0) {
					tokens.add(sb.toString().trim());
					sb.setLength(0);
				}
				tokens.add(c + "");
				break;

			default:
				sb.append(c);
				break;
			}
		}
		
		return tokens;
	}

	public static void main(String[] args) {
		new FeatureExpressionParser("(CallButtons)&(DirectedCall)&Sabbath|Replace)");
	}

}
