package com.avensys.cvparser.data;

/**
 * Data class storing information regarding document text boxes. Currently used as a placeholder for future additions to CV Parsing algorithm.
 * @author User
 *
 */
public class DocTextBox {
	private String text;
	private float xStart;
	private float xEnd;
	private float yStart;
	private float yEnd;

	private float xMid;
	private float yMid;
	
	public DocTextBox(String text) {
		this.text = text;
		this.xStart = -1f;
		this.xEnd = -1f;
		this.yStart = -1f;
		this.yEnd = -1f;
		this.xMid = -1f;
		this.yMid = -1f;
	}

	public DocTextBox(String text, float xStart, float xEnd, float yStart, float yEnd) {
		this.text = text;
		this.xStart = xStart;
		this.xEnd = xEnd;
		this.yStart = yStart;
		this.yEnd = yEnd;
		this.xMid = (xStart + xEnd) / 2f;
		this.yMid = (yStart + yEnd) / 2f;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public float getxStart() {
		return xStart;
	}

	public void setxStart(float xStart) {
		this.xStart = xStart;
	}

	public float getxEnd() {
		return xEnd;
	}

	public void setxEnd(float xEnd) {
		this.xEnd = xEnd;
	}

	public float getyStart() {
		return yStart;
	}

	public void setyStart(float yStart) {
		this.yStart = yStart;
	}

	public float getyEnd() {
		return yEnd;
	}

	public void setyEnd(float yEnd) {
		this.yEnd = yEnd;
	}

	public float getxMid() {
		return xMid;
	}

	public void setxMid(float xMid) {
		this.xMid = xMid;
	}

	public float getyMid() {
		return yMid;
	}

	public void setyMid(float yMid) {
		this.yMid = yMid;
	}
	
	public boolean isUnderHeader(DocTextBox header) {
		return false;
	}
	
	public boolean isUnderHeader(float headerXStart, float headerYStart, float headerXEnd,float headerYEnd) {
		return false;
	}
	
	

}
