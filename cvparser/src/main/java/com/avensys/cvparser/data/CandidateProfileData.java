package com.avensys.cvparser.data;

import java.util.ArrayList;
import java.util.List;

public class CandidateProfileData {
	private String name = null;
	private String email = null;
	private String mobile = null;
	private List<String> skills = new ArrayList<>();
	private List<PastCompanyData> pastCompanies = new ArrayList<>();
	private int experienceYears = 0;

	public CandidateProfileData() {

	}

	public void build() {
		if (this.name == null) {
			this.name = "No Name";
		}
		if (this.email == null) {
			this.email = "No Email";
		}
		if (this.mobile == null) {
			this.mobile = "No Mobile";
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Name: " + this.name + "\n").append("Email: " + this.email + "\n")
				.append("Mobile: " + this.mobile + "\n").append("Years of ExP: " + this.experienceYears + "\n");
		int counter = 1;
		for (String skill : this.skills) {
			sb.append("Skill " + counter + ": " + skill+"\n");
			counter++;
		}
		counter = 1;
		for (PastCompanyData pcd : this.pastCompanies) {
			sb.append(counter + ") " + pcd.toString()+"\n");
			counter++;
		}

		return "Name: " + this.name + " Email: " + this.email + " Mobile: " + this.mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (this.name == null)
			this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (this.email == null)
			this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		if (this.mobile == null)
			this.mobile = mobile;
	}

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public List<PastCompanyData> getPastCompanies() {
		return pastCompanies;
	}

	public void setPastCompanies(List<PastCompanyData> pastCompanies) {
		this.pastCompanies = pastCompanies;
	}

	public int getExperienceYears() {
		return experienceYears;
	}

	public void setExperienceYears(int experienceYears) {
		this.experienceYears = experienceYears;
	}

}
