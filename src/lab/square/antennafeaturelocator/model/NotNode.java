package lab.square.antennafeaturelocator.model;

public class NotNode extends ExpressionNode {
	public NotNode(ExpressionNode expression) {
		this.left = expression;
		this.value = "!";
	}
}
