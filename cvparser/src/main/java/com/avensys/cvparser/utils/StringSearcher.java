package com.avensys.cvparser.utils;

public class StringSearcher {
	/**
	 * Method for extracting Day, Month, and Year from strings.
	 * @param text
	 * @return a String array in order of Date, Month, Year
	 */
	public String[] searchDate(String text) {
		String[] candDates = text.split("\\W");
		for (String s : candDates) {
			System.out.println(s);
		}
		return null;
	}
}
