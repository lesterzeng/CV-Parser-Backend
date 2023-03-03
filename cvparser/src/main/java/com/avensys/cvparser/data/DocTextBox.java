package com.avensys.cvparser.data;

public class DocTextBox {
	private String text;
	private float xStart;
	private float xEnd;
	private float yStart;
	private float yEnd;

	private float xMid;
	private float yMid;

	public DocTextBox(String text, float xStart, float xEnd, float yStart, float yEnd) {
		super();
		this.text = text;
		this.xStart = xStart;
		this.xEnd = xEnd;
		this.yStart = yStart;
		this.yEnd = yEnd;
		this.xMid = (xStart + xEnd) / 2f;
		this.yMid = (yStart + yEnd) / 2f;
	}

}
