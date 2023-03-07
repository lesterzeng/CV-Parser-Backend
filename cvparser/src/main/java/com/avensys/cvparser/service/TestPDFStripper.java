package com.avensys.cvparser.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.springframework.beans.factory.annotation.Autowired;

import com.avensys.cvparser.data.CVKeywords;
import com.avensys.cvparser.data.DocTextBox;

/**
 * Experimental Class. Do not use!<br>
 * Extends PDFBox's PDFTextStripper and overwrites the writeString() method to determine the text box's x and y-coordinates. This functionality can be used
 * to further improve the CV Parsing mechanics.
 * @author User
 *
 */
public class TestPDFStripper extends PDFTextStripper {

	public TestPDFStripper() throws IOException {
		super();
	}

	private HashMap<String, List<DocTextBox>> headerGroup = new HashMap<>();
	private String activeHeader = "Filler";

	private void checkAndAssignKeyword(String text, float xStart, float yStart, float xEnd, float yEnd) {

		if (activeHeader.equals("Filler") && this.headerGroup.get(activeHeader) == null)
			this.headerGroup.put(activeHeader, new ArrayList<DocTextBox>());

		for (CVKeywords keyword : CVKeywords.values()) {
			if (text.split(" ").length < 6 && Pattern.matches(keyword.getRegex(), text)) {
				activeHeader = keyword.label;
				System.out.println("Matches " + keyword.label);
				if (this.headerGroup.get(activeHeader) == null)
					this.headerGroup.put(activeHeader, new ArrayList<DocTextBox>());
				break;
			}
		}
		if (!text.strip().equals("")) {
			this.headerGroup.get(activeHeader).add(new DocTextBox(text, xStart, xEnd, yStart, yEnd));
		}
	}

	@Override
	protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
		StringBuilder sb = new StringBuilder();
		int counter = 0;
		final float limits = 30.0f;
		float xStart = 0;
		float yStart = 0;
		float xpos = 0;
		float ypos = 0;
		for (TextPosition text : textPositions) {
			if (sb.isEmpty()) {
				xStart = text.getXDirAdj();
				yStart = text.getYDirAdj();
			}
			if ((text.getXDirAdj() - xpos > limits || text.getYDirAdj() > ypos) && !sb.isEmpty()) {

				checkAndAssignKeyword(sb.toString(), xStart, yStart, xpos, ypos);

				sb.delete(0, sb.length());
				xStart = text.getXDirAdj();
				yStart = text.getYDirAdj();
				counter++;
			}
			xpos = text.getXDirAdj();
			ypos = text.getYDirAdj();
			sb.append(text.getUnicode());

//			System.out.println("String[" + text.getXDirAdj() + "," +
//					text.getYDirAdj() + " fs=" +text.getFontSize() + " xscale=" +
//					text.getXScale() + " height="+ text.getHeightDir() + " space="+text.getWidthOfSpace() + " width="+text.getWidthDirAdj() +
//					"]" + text.getUnicode());

		}

//		System.out.println("[%f,%f] to [%f,%f] ".formatted(xStart, yStart, xpos, ypos) + counter + ":" + sb.toString());
		checkAndAssignKeyword(sb.toString(), xStart, yStart, xpos, ypos);

	}

	public HashMap<String, List<DocTextBox>> getHeaderGroup() {
		return headerGroup;
	}
	
}
