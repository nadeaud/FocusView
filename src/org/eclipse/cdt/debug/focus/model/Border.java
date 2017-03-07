package org.eclipse.cdt.debug.focus.model;

public class Border {

	private String axis;
	private int min;
	private int max;
	
	public Border(String axis, int min, int max) {
		this.axis = axis;
		this.max = max;
		this.min = min;
	}
	
	public String getAxis() {
		return axis;
	}

	public int getMax() {
		return max;
	}
	
	public int getMin() {
		return min;
	}

	public void setMax(int max) {
		this.max = max;
	}
	
	public void setMin(int min) {
		this.min = min;
	}
}
