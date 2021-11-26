package lab.square.antennafeaturelocator.core;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

public class FeatureLocator {
	
	public Collection<FeatureLocation> analyze(String javaSourceFilePath) throws IOException {

		Collection<FeatureLocation> featureLocations = new ArrayList<FeatureLocation>();
		Stack<LocationInfo> infoStack = new Stack<LocationInfo>();

		File javaSourceFile = new File(javaSourceFilePath);
		FileReader fileReader = new FileReader(javaSourceFile);
		LineNumberReader lineReader = new LineNumberReader(fileReader);

		String content = Files.readString(Paths.get(javaSourceFilePath));
		String line;
		Stack<String> stackedExpressions = new Stack<String>();
		while ((line = lineReader.readLine()) != null) {
			System.out.println("    " + line);

			if (line.contains("//#if")) {
				int startLine = lineReader.getLineNumber();
				String featureExpression;
				featureExpression = line.substring(5);

				System.out.println(featureExpression + startLine);
				LocationInfo locationInfo = new LocationInfo(featureExpression, startLine);
				infoStack.add(locationInfo);
				stackedExpressions.push(featureExpression);
			} else if (line.contains("//#endif")) {
				int endLine = lineReader.getLineNumber();
				LocationInfo popped = infoStack.pop();

				featureLocations.add(
						new FeatureLocation(javaSourceFile, stackedExpressions, popped.startLine + 1, endLine - 1));
				stackedExpressions.pop();
			} else if (line.contains("//#elif")) {
				
				int endLine = lineReader.getLineNumber();
				LocationInfo popped = infoStack.pop();
				featureLocations.add(
						new FeatureLocation(javaSourceFile, stackedExpressions, popped.startLine + 1, endLine - 1));
				stackedExpressions.pop();
				
				String featureExpression;
				line = removeSpace(line);
				featureExpression = line.substring(7);

				LocationInfo locationInfo = new LocationInfo(featureExpression, endLine);
				infoStack.add(locationInfo);
				stackedExpressions.push(featureExpression);
			} else if (line.contains("//#else")) {
				int endLine = lineReader.getLineNumber();
				LocationInfo popped = infoStack.pop();
				featureLocations.add(new FeatureLocation(javaSourceFile, stackedExpressions, popped.startLine + 1, endLine - 1));
				stackedExpressions.pop();

				String elseExpression = "!(" + popped.featureExpression + ")";
				LocationInfo locationInfo = new LocationInfo(elseExpression, endLine);
				infoStack.add(locationInfo);
				stackedExpressions.push(elseExpression);
			}
		}

		return featureLocations;
	}

	private String removeSpace(String line) {
		line = line.replace(" ", "");
		line = line.replace("\t", "");
		return line;
	}

	public static void main(String[] args) throws IOException {
		Collection<FeatureLocation> featureLocations = new FeatureLocator().analyze(
				"D:\\workspace-featureide\\Elevator-Antenna-v1.2\\src\\de\\ovgu\\featureide\\examples\\elevator\\core\\controller\\Request.java");

		for (FeatureLocation featureLocation : featureLocations) {
			for(String feature : featureLocation.getFeatureExpressions()) {
				System.out.println(feature);
			}
			System.out.println("line: " + featureLocation.getLineStart() + " ~ " + featureLocation.getLineEnd());
		}
	}

	protected class LocationInfo {
		protected final int startLine;
		protected final String featureExpression;

		private LocationInfo(String featureExpression, int startLine) {
			this.featureExpression = featureExpression;
			this.startLine = startLine;
		}
	}

	private String getStackedExpression(Stack<LocationInfo> stack) {
		String toReturn = "";

		Stack<LocationInfo> reversed = new Stack<LocationInfo>();
		Iterator<LocationInfo> iter = stack.iterator();

		while (iter.hasNext()) {
			LocationInfo info = iter.next();

			reversed.push(info);
		}

		while (!reversed.isEmpty()) {
			LocationInfo info = reversed.pop();
			if (toReturn.isBlank())
				toReturn = info.featureExpression;
			else
				toReturn = "(" + info.featureExpression + ")&(" + toReturn + ")";
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
