package lab.square.antennafeaturelocator.model;

public class OrNode extends ExpressionNode {

	public OrNode(ExpressionNode leftExp, ExpressionNode rightExp) {
		this.left = leftExp;
		this.right = rightExp;
		this.value = "|";
	}

}
