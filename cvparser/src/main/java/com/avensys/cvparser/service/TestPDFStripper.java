package com.avensys.cvparser.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import com.avensys.cvparser.utils.CVKeywords;

public class TestPDFStripper extends PDFTextStripper {

	public TestPDFStripper() throws IOException {
		super();
	}

	private HashMap<String, List<String>> headerGroup = new HashMap<>();
	private String activeHeader = "Filler";

	private void checkAndAssignKeyword(String text, float xPos, float yPos) {
//		System.out.println(CVKeywords.EXPERIENCE.getRegex());
//		System.out.println(CVKeywords.EXPERIENCE.label);
		if (activeHeader.equals("Filler") && this.headerGroup.get(activeHeader) == null)
			this.headerGroup.put(activeHeader, new ArrayList<String>());

		for (CVKeywords keyword : CVKeywords.values()) {
			if (text.split(" ").length < 6 && Pattern.matches(keyword.getRegex(), text)) {
				activeHeader = keyword.label;
				System.out.println("Matches " + keyword.label);
				if (this.headerGroup.get(activeHeader) == null)
					this.headerGroup.put(activeHeader, new ArrayList<String>());
				break;
			}
		}
		if (!text.strip().equals("")) {
			this.headerGroup.get(activeHeader).add(text);
		}
	}

	public void parseInformation() {
		// TODO: Remove
		for (String lister : this.headerGroup.keySet()) {
			System.out.println("Key:" + lister + this.headerGroup.get(lister).toString());
		}
		if (this.headerGroup.get(CVKeywords.EXPERIENCE.label) != null) {
			this.processExperience(this.headerGroup.get(CVKeywords.EXPERIENCE.label));
		}
		if (this.headerGroup.get(CVKeywords.SKILL.label) != null) {
			this.processSkill(this.headerGroup.get(CVKeywords.SKILL.label));
		}
		if (this.headerGroup.get(CVKeywords.PERSONAL.label) != null) {
			processPersonal(this.headerGroup.get(CVKeywords.PERSONAL.label));
		} else {
			processPersonal(this.headerGroup.get("Filler"));
		}

	}

	private void processExperience(List<String> experiences) {

	}

	/**
	 * Searches for personal information such as Name, Email, and Mobile. Name searches run on the assumption that majority of names are First Line
	 * @param personals
	 */
	private void processPersonal(List<String> personals) {
		for (int i = 0; i< personals.size(); i++) {
			String str = personals.get(i).strip();
			System.out.println(i+" "+str);
			System.out.println(str.split(" ").length);
			if((str.split(" ").length >=2 && str.split(" ").length <6)&&!str.matches("^([\\s\\w,/.-]+)$")) {
				System.out.println("Name Candidate: "+str);
			}
			if (str.toLowerCase().contains("mobile")) {
				System.out.println("Mobile candidate: "+str);
			}
			if (str.toLowerCase().contains("email")) {
				System.out.println("Email candidate: "+str);
			}
		}
	}
	
	private void processSkill(List<String> skills) {
		
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
		float fontSize = 0;
		String[] keywords = new String[] { "experience", "" };
		for (TextPosition text : textPositions) {
			if (sb.isEmpty()) {
				xStart = text.getXDirAdj();
				yStart = text.getYDirAdj();
				fontSize = text.getFontSize();
			}
			if ((text.getXDirAdj() - xpos > limits || text.getYDirAdj() > ypos) && !sb.isEmpty()) {
//				System.out.println(
//						"[%f,%f] to [%f,%f] ".formatted(xStart, yStart, xpos, ypos) + counter + ":" + sb.toString());

				checkAndAssignKeyword(sb.toString(), xStart, yStart);

				sb.delete(0, sb.length());
				xStart = text.getXDirAdj();
				yStart = text.getYDirAdj();
				fontSize = text.getFontSize();
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
		checkAndAssignKeyword(sb.toString(), xStart, yStart);

	}

	/**
	 * Test Method: TODO: To be removed
	 */
	public void finalOutput() {
		for (String lister : this.headerGroup.keySet()) {
			System.out.println("Key:" + lister + this.headerGroup.get(lister).toString());
		}
	}
}
