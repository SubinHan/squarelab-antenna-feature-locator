package lab.square.antennafeaturelocator.core;

import java.util.ArrayList;
import java.util.List;

public class FeatureExpressionTokenizer {

	public static List<String> tokenize(String featureExpression) {
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
				tokens.add(c + "");
				break; 

			case '!':
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
		
		if(sb.length() > 0) {
			tokens.add(sb.toString());
		}
		
		return tokens;
	}
	
}
