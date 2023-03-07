package com.avensys.cvparser.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avensys.cvparser.data.CVKeywords;
import com.avensys.cvparser.entity.CandidateEntity;
import com.avensys.cvparser.entity.Skill;
import com.avensys.cvparser.data.DocTextBox;

/**
 * Service class that parses CV Content and generates {@link CandidateEntity}
 * 
 * @author User
 *
 */
@Service
public class ParseService {

	@Autowired
	Map<String,String> fileData;
	/**
	 * Reads all files stored in {@link FileData}
	 * @return
	 */
	public List<CandidateEntity> parseMultipleFiles(){
		System.out.println(fileData.values().size());
		System.out.println("Method Called Successfully");
		return null;
	}
	/**
	 * Reads a single file's contents as a single string separated by line breaks.
	 * Parsing is done on the assumption that all lines are in visual order. i.e.
	 * lines at the top of the document are at the start of the string and lines at
	 * the bottom are at the end of the string.
	 * 
	 * @param contents contents is a String representation of the entire document
	 * @return a {@link CandidateEntity} object containing all the compiled
	 *         information.
	 */
	public CandidateEntity parseFile(String contents) {
		return parseFile(contents.split("\n"));
	}

	/**
	 * Reads a single file's contents as a String array where each entry is a line
	 * in the document. Parsing is done on the assumption that all lines are in
	 * visual order. i.e. lines at the top of the document are at the start of the
	 * array and lines at the bottom are at the end of the array.
	 * 
	 * @param contents contents is a String Array representation of the entire
	 *                 document line-by-line
	 * @return A {@link CandidateEntity} object containing all the compiled
	 *         information.
	 */
	public CandidateEntity parseFile(String[] contents) {
		String activeHeader = CVKeywords.PERSONAL.label;
		HashMap<String, List<DocTextBox>> headerGroup = new HashMap<>();

		for (String line : contents) {
			activeHeader = checkAndAssignKeyword(line, activeHeader, headerGroup);
		}
		return generateProfileData(headerGroup);
	}

	/**
	 * Sorting Helper Method that searches for keywords to find Header sections and
	 * assigns the relevant text boxes/lines to the Headers. This method deals in
	 * Header search and textbox assignments only.
	 * 
	 * @param line         String representation of current line in document to sort
	 *                     and assign.
	 * @param activeHeader String representation of current header to assign found
	 *                     textboxes
	 * @param headerGroup  HashMap with key of String representation of Header
	 *                     Keyword, and value of a List of {@link DocTextBox}
	 *                     representing the textboxes assigned to the header
	 * @return String representation of current or new activeHeader value to be used
	 *         in future calls of this method.
	 */
	private String checkAndAssignKeyword(String line, String activeHeader,
			HashMap<String, List<DocTextBox>> headerGroup) {
		String outputHeader = activeHeader;
		String subLine = line.strip();
		System.out.println("Test: " + subLine + " Header:" + activeHeader);
		if (outputHeader.equals(CVKeywords.PERSONAL.label) && !headerGroup.containsKey(outputHeader))
			headerGroup.put(outputHeader, new ArrayList<DocTextBox>());

		for (CVKeywords keyword : CVKeywords.values()) {
			if (subLine.split(" ").length < 6 && Pattern.matches(keyword.getRegex(), subLine)) {
				outputHeader = keyword.label;
//				System.out.println("Matches " + keyword.label + "\nSource: " + subLine);
//				System.out.println(outputHeader);
				if (!headerGroup.containsKey(outputHeader)) {
//					System.out.println("Header added");
					headerGroup.put(outputHeader, new ArrayList<DocTextBox>());
				}
				break;
			}
		}
		if (!subLine.strip().equals("")) {
			headerGroup.get(activeHeader).add(new DocTextBox(subLine));
		}

		return outputHeader;
	}

//	private void checkAndAssignKeyword(String text) {
//
//		if (activeHeader.equals("Filler") && this.headerGroup.get(activeHeader) == null)
//			this.headerGroup.put(activeHeader, new ArrayList<DocTextBox>());
//
//		for (CVKeywords keyword : CVKeywords.values()) {
//			if (text.split(" ").length < 6 && Pattern.matches(keyword.getRegex(), text)) {
//				activeHeader = keyword.label;
//				System.out.println("Matches " + keyword.label);
//				if (this.headerGroup.get(activeHeader) == null)
//					this.headerGroup.put(activeHeader, new ArrayList<DocTextBox>());
//				break;
//			}
//		}
//		if (!text.strip().equals("")) {
//			this.headerGroup.get(activeHeader).add(new DocTextBox(text));
//		}
//	}

	/**
	 * Method used to generate {@link CandidateEntity} attribute values by searching
	 * sorted Header Sections in a document. Used after running the document through
	 * {@link #checkAndAssignKeyword(String, String, HashMap)}. See
	 * {@link #parseFile(String[])} for implementation.
	 * 
	 * @param headerGroup HashMap with key of String representation of Header
	 *                    Keyword, and value of a List of {@link DocTextBox}
	 *                    representing the textboxes assigned to the header
	 * @return A {@link CandidateEntity} object containing all the compiled
	 *         information.
	 */
	private CandidateEntity generateProfileData(HashMap<String, List<DocTextBox>> headerGroup) {

		CandidateEntity output = new CandidateEntity();
		// TODO: Remove
		for (String lister : headerGroup.keySet()) {
			System.out.println("Key:" + lister + headerGroup.get(lister).toString());
		}
		if (headerGroup.get(CVKeywords.WORK.label) != null) {
			System.out.println("Work Process called");
			this.processWork(headerGroup.get(CVKeywords.WORK.label), output);
		}
		if (headerGroup.get(CVKeywords.EXPERIENCE.label) != null) {
			System.out.println("Experience Process Called");
			this.processExperience(headerGroup.get(CVKeywords.EXPERIENCE.label), output);
		}
		if (headerGroup.get(CVKeywords.SKILL.label) != null) {
			System.out.println("Skill Process Called");
			this.processSkill(headerGroup.get(CVKeywords.SKILL.label), output);
		}
		if (headerGroup.get(CVKeywords.PERSONAL.label) != null) {
			System.out.println("Personal Process Called");
			processPersonal(headerGroup.get(CVKeywords.PERSONAL.label), output);

		}
//		processPersonal(headerGroup.get("Filler"), output);
		output.build();
		return output;
	}

	/**
	 * Seaches for years of experience.
	 * 
	 * @param experiences
	 * @param ce
	 */
	private void processExperience(List<DocTextBox> experiences, CandidateEntity ce) {
		List<String> allDates = new ArrayList<>();
		for (int i = 0; i < experiences.size(); i++) {
			String str = experiences.get(i).getText().strip();
//			System.out.println(i + " " + str);
			List<String> dates = this.findDate(str);
			if (dates != null) {
				allDates.addAll(dates);
			}

		}
		System.out.println("Size of Experience:" + allDates.size());
		for (int i = 0; i < allDates.size(); i++) {
			if (i == 0)
				continue;

			int[] prevDate = this.strToNumDate(allDates.get(i - 1));
			int[] currDate = this.strToNumDate(allDates.get(i));
			System.out.println("PrevDate" + Arrays.toString(prevDate));
			System.out.println("CurrDate" + Arrays.toString(currDate));
			int dateCheck = currDate[0] - prevDate[0] + (currDate[1] - prevDate[1]) * 12;
			if (dateCheck < 0) {
				System.out.println("Negative Date Calculation");
				continue;
			} else {
				System.out.println(dateCheck);
				if (dateCheck == 0)
					dateCheck = 1;
				ce.setWorkExp(ce.getWorkExp() + Math.round(dateCheck / 12f));
				System.out.println(ce.getWorkExp());
			}

		}
	}

	/**
	 * Searches for Previous 3 Companies
	 * 
	 * @param works
	 * @param ce
	 */
	private void processWork(List<DocTextBox> works, CandidateEntity ce) {
		for (int i = 0; i < works.size(); i++) {
			String str = works.get(i).getText().strip();
			System.out.println(i + " " + str);
		}
	}

	/**
	 * Searches for personal information such as Name, Email, and Mobile.
	 * 
	 * @param personals
	 * @param ce
	 */
	private void processPersonal(List<DocTextBox> personals, CandidateEntity ce) {
		System.out.println("ProcessPersonal Called");
		for (int i = 0; i < personals.size(); i++) {
			String str = personals.get(i).getText().strip();
			System.out.println("Personal: " + i + " " + str);
			System.out.println(str.split(" ").length);

			// Process Name
			if ((ce.getFirstName() == null && ce.getLastName() == null)
					&& ((str.split(" ").length >= 2 && str.split(" ").length < 6)
							|| !str.matches("^([\\s\\w,/.-]+)$"))) {
				String[] nameSplit = str.split(" ");

				if (nameSplit.length > 2) {
					StringBuilder sb = new StringBuilder();
					for (int j = 1; j < nameSplit.length - 1; j++) {
						sb.append(nameSplit[j] + " ");
					}
					ce.setMidName(sb.toString().trim());
				}
				ce.setFirstName(nameSplit[0]);
				ce.setLastName(nameSplit[nameSplit.length - 1]);
//				System.out.println("Name Candidate: " + str);
			}

			// Process Mobile
			if (ce.getPhoneNumber() == null && (str.toLowerCase().contains("mobile") || str.toLowerCase().contains("phone")
					|| str.contains("+65") || str.contains("+ 65"))) {
				String mobileStr = str.replaceAll("[^\\d\\s+-]", "");

				ce.setPhoneNumber(mobileStr);
				System.out.println("Mobile candidate: " + str);
				System.out.println(mobileStr);
			}

			// Process Email
			if (ce.getEmail() == null && (str.toLowerCase().contains("email") || str.matches(".*@.+[.]com.*"))) {
				Matcher regexMatch = Pattern.compile(".*@.+[.]com.*").matcher(str);
				String emailStr = null;
				if (regexMatch.find()) {
					emailStr = regexMatch.group();
					System.out.println("Cropped" + emailStr);
				}
				emailStr = str.replaceAll("(?i)e(-)?mail[\\W]*", "");

				ce.setEmail(emailStr);
				System.out.println("Email candidate: " + str);
				System.out.println(emailStr);
			}
		}
	}

	/**
	 * Searches for list of skills and appends to an array
	 * 
	 * @param skills
	 * @param ce
	 */
	private void processSkill(List<DocTextBox> skills, CandidateEntity ce) {
		for (DocTextBox line : skills) {
			String subLine = line.getText().strip();
			if (subLine.contains(":")) {
				subLine = subLine.replaceAll(".*:\s*", "");

			}
//			System.out.println("Preprocess:" + subLine);
			String[] splitText = subLine.split("(?i)[^\\w\\s]|(and)");
//			System.out.println("Skill Test:");
			for (String s : splitText) {
//				System.out.println("Skill:" + s.strip());
				Skill skillItem = new Skill();
				skillItem.setSkill_description(s.strip());
				if (ce.getSkills()==null) {
					ce.setSkills(new ArrayList<Skill>());
				}
				ce.getSkills().add(skillItem);
			}
		}
	}

	/**
	 * Helper method to search for and extract date formats in CV Documents
	 * 
	 * @param line String representation of a line to examine
	 * @return
	 */
	private List<String> findDate(String line) {
//		System.out.println("Date Called: "+line);
		Matcher checker = Pattern.compile("(?i)\\b(\\d{2}[ -/])?([a-z]{3,9}|\\d{2})[ -/](\\d{4}|\\d{2})\\b")
				.matcher(line.trim());
		List<String> output = new ArrayList<>();
		while (checker.find()) {
			System.out.println("Checker Group:" + checker.group());
			output.add(checker.group());
		}
		if (output.size() == 0) {
//			System.out.println("Line Failed");
			return null;
		} else {
			if (line.toLowerCase().contains("current") || line.toLowerCase().contains("present")) {
				String[] currDate = LocalDate.now().toString().split("-");
				output.add(currDate[1] + "-" + currDate[0]);
			}

			return output;
		}

	}

	/**
	 * Helper method to convert Dates from String format to integer array values for
	 * calculations.
	 * 
	 * @param date
	 * @return int[] representing { month, year } in integer value.
	 */
	private int[] strToNumDate(String date) {
		System.out.println("InputDate: " + date);
		String[] allMonthFormat = new String[] { "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct",
				"nov", "dec", "january", "february", "march", "april", "may", "june", "july", "august", "september",
				"october", "november", "december" };

		int[] output = new int[2];
		String[] dateFormat = date.split("[ -/]");
		// TODO: Remove this section:
		System.out.println("DateFormat: " + Arrays.toString(dateFormat));

		int formatLength = dateFormat.length;
		if (formatLength > 2) {
			dateFormat = new String[] { dateFormat[1], dateFormat[2] };
		}
		output[1] = Integer.parseInt(dateFormat[1]);
		if (dateFormat[0].matches("\\d{2}")) {
			output[0] = Integer.parseInt(dateFormat[0]);
		} else {
			for (int i = 0; i < allMonthFormat.length; i++) {
				if (allMonthFormat[i].equals(dateFormat[0].toLowerCase().trim())) {
					System.out.println("Match month:" + allMonthFormat[i]);
					output[0] = i < 12 ? i + 1 : i - 11;

					break;
				}
			}
			if (output[0] == 0) {
				output[0] = 1;
			}
		}
		return output;
	}
}
