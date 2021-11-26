package lab.square.antennafeaturelocator.core;

import java.io.File;
import java.util.List;
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
		
		return false;
	}
	
	
}
