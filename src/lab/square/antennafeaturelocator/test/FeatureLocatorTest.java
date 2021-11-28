package lab.square.antennafeaturelocator.test;

import java.io.IOException;
import java.util.Collection;

import org.junit.Test;

import lab.square.antennafeaturelocator.core.FeatureLocation;
import lab.square.antennafeaturelocator.core.FeatureLocator;

public class FeatureLocatorTest {
	
	@Test
	public void testLocator() throws IOException {
		Collection<FeatureLocation> featureLocations = new FeatureLocator().analyze(
				"D:\\workspace-featureide\\Elevator-Antenna-v1.2\\src\\de\\ovgu\\featureide\\examples\\elevator\\core\\controller\\Request.java");

		for (FeatureLocation featureLocation : featureLocations) {
			for (String feature : featureLocation.getFeatureExpressions()) {
				System.out.println(feature);
			}
			System.out.println(featureLocation.expressionToString());
			System.out.println("line: " + featureLocation.getLineStart() + " ~ " + featureLocation.getLineEnd());
		}
	}
}
