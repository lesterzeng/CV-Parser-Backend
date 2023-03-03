package com.avensys.cvparser.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import com.avensys.cvparser.data.CVKeywords;
import com.avensys.cvparser.data.CandidateProfileData;
import com.avensys.cvparser.data.DocTextBox;
import com.avensys.cvparser.dto.ParseRequestDTO;

@Service
public class ParseService {
	public void parseFiles(ParseRequestDTO prd) {
		System.out.println("Method Called");
		if (prd.getFileType() == "application/pdf")
			System.out.println("pdf found");
		try {
			parsePdfFile(prd.getEncodedData());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String parsePdfFile(String fileData) throws IOException {
		String[] lineBreak = fileData.split("\n");
		int counter = 0;
		for (String line : lineBreak) {
//			System.out.println("%d: %s".formatted(counter,line));
			counter++;
		}
		return null;
	}

	public CandidateProfileData generateProfileData(HashMap<String, List<DocTextBox>> headerGroup) {
		// TODO: Remove
		for (String lister : headerGroup.keySet()) {
			System.out.println("Key:" + lister + headerGroup.get(lister).toString());
		}
		if (headerGroup.get(CVKeywords.WORK.label) != null) {
			this.processWork(headerGroup.get(CVKeywords.WORK.label));
		}
		if (headerGroup.get(CVKeywords.EXPERIENCE.label) != null) {
			this.processExperience(headerGroup.get(CVKeywords.EXPERIENCE.label));
		}
		if (headerGroup.get(CVKeywords.SKILL.label) != null) {
			this.processSkill(headerGroup.get(CVKeywords.SKILL.label));
		}
		if (headerGroup.get(CVKeywords.PERSONAL.label) != null) {
			processPersonal(headerGroup.get(CVKeywords.PERSONAL.label));

		}
		processPersonal(headerGroup.get("Filler"));

		return new CandidateProfileData();
	}

	

	private void processExperience(List<DocTextBox> experiences) {
		for (int i = 0; i < experiences.size(); i++) {
			String str = experiences.get(i).getText().strip();
			System.out.println(i + " " + str);

		}
	}

	private void processWork(List<DocTextBox> works) {

	}

	/**
	 * Searches for personal information such as Name, Email, and Mobile. Name
	 * searches run on the assumption that majority of names are First Line
	 * 
	 * @param personals
	 */
	private void processPersonal(List<DocTextBox> personals) {
		for (int i = 0; i < personals.size(); i++) {
			String str = personals.get(i).getText().strip();
			System.out.println(i + " " + str);
			System.out.println(str.split(" ").length);
			if ((str.split(" ").length >= 2 && str.split(" ").length < 6) || !str.matches("^([\\s\\w,/.-]+)$")) {
				System.out.println("Name Candidate: " + str);
			}
			if (str.toLowerCase().contains("mobile") || str.contains("+65") || str.contains("+ 65")) {
				System.out.println("Mobile candidate: " + str);
			}
			if (str.toLowerCase().contains("email") || str.matches(".*@.+[.]com.*")) {
				System.out.println("Email candidate: " + str);
			}
		}
	}

	private void processSkill(List<DocTextBox> skills) {
		for (DocTextBox line : skills) {
			String subLine = line.getText().strip();
			if (subLine.contains(":")) {
				subLine = subLine.replaceAll(".*:", "");

			}
			System.out.println(subLine.split("[\\W]*"));
		}
	}
}
