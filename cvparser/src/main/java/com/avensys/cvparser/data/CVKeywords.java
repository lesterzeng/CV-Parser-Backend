package com.avensys.cvparser.data;

import java.util.HashMap;
import java.util.Map;

public enum CVKeywords {
	EXPERIENCE("experience","(?i).*((work\\s)?)(experience|employment)(s?).*"),
	SKILL("skill","(?i).*skill(s?).*"),
	WORK("work","(?i).*((work\\s)?)(experience|employment)(s?).*"),
	EDUCATION("education","(?i).*education.*"),
	OBJECTIVE("objective","(?i).*education.*"),
	PERSONAL("personalinfo","(?i).*personal.*")
	;
	
	public final String label;
	public final String regex;
	private static final Map<String, CVKeywords> BY_LABEL = new HashMap<>();
	private static final Map<String, CVKeywords> BY_REGEX = new HashMap<>();
	
	static {
        for (CVKeywords e: values()) {
            BY_LABEL.put(e.label, e);
            BY_REGEX.put(e.regex, e);
        }
    }
	
	private CVKeywords(String label, String regex) {
		this.label = label;
		this.regex = regex;
	}
	
	public static CVKeywords valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
	
	/**
	 * Returns Pattern Matcher compatible String Regex associated with the enum.
	 * @return the String value of the associated regex.
	 */
	public String getRegex() {
		return this.regex;
	}
}
