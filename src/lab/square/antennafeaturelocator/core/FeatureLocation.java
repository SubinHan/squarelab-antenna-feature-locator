package lab.square.antennafeaturelocator.core;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class FeatureLocation {
	private final File sourceFile;
	private final Stack<String> featureExpressions;
	private final int lineStart;
	private final int lineEnd;
	private List<String> sources;
	
	
	/**
	 * 
	 * @param sourceFile
	 * @param featureExpressions The stack will be cloned.
	 * @param lineStart
	 * @param lineEnd
	 */
	public FeatureLocation(File sourceFile, Stack<String> featureExpressions, int lineStart, int lineEnd) {
		this.sourceFile = sourceFile;
		this.featureExpressions = (Stack<String>) featureExpressions.clone();
		this.lineStart = lineStart;
		this.lineEnd = lineEnd;
	}
	
	public File getSourceFile() {
		return sourceFile;
	}
	public Stack<String> getFeatureExpressions() {
		return featureExpressions;
	}
	public int getLineStart() {
		return lineStart;
	}
	public int getLineEnd() {
		return lineEnd;
	}
	public List<String> getSources() {
		return sources;
	}
	public void setSources(List<String> sources) {
		this.sources = sources;
	}
	public boolean isFeatureLocationOf(String feature) {
		Map<String, Boolean> featureSet = new HashMap<String, Boolean>();
		featureSet.put(feature, true);
		return FeatureExpressionParser.evaluate(this.expressionToString(), featureSet);
	}
	public String expressionToString() {
		String toReturn = "";

		while (!featureExpressions.isEmpty()){
			String popped = featureExpressions.pop();
			if(toReturn.isBlank()) {
				toReturn = popped;
			}
			else {
				toReturn = and(popped, toReturn);
			}
		}
		
		return toReturn;
	}
	private String and(String expression1, String expression2) {
		if (expression1.isBlank())
			return expression2;
		if (expression2.isBlank())
			return expression1;
		return "(" + expression1 + ")&(" + expression2 + ")";
	}
	
	
}
