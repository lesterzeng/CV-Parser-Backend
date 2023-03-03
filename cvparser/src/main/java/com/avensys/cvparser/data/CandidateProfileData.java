package com.avensys.cvparser.data;

import java.util.ArrayList;
import java.util.List;

public class CandidateProfileData {
	private String name;
	private String email;
	private String mobile;
	private List<String> skills = new ArrayList<>();
	private List<String> pastCompanies = new ArrayList<>();
	private int experienceYears;
	
	public CandidateProfileData() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public List<String> getPastCompanies() {
		return pastCompanies;
	}

	public void setPastCompanies(List<String> pastCompanies) {
		this.pastCompanies = pastCompanies;
	}

	public int getExperienceYears() {
		return experienceYears;
	}

	public void setExperienceYears(int experienceYears) {
		this.experienceYears = experienceYears;
	}
	
}
