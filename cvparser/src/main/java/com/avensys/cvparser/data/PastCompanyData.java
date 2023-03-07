package com.avensys.cvparser.data;

public class PastCompanyData {
	private String companyName;
	private String dateJoined;
	private String dateLeave;
	private String reasonForLeaving;
	
	public PastCompanyData(String companyName, String dateJoined, String dateLeave, String reasonForLeaving) {
		super();
		this.companyName = companyName;
		this.dateJoined = dateJoined;
		this.dateLeave = dateLeave;
		this.reasonForLeaving = reasonForLeaving;
	}
	
	@Override
	public String toString() {
		return "Company: "+this.companyName+" Date Join: "+this.dateJoined+" Date Leave: "+this.dateLeave+" Reason For Leave: " +this.reasonForLeaving;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDateJoined() {
		return dateJoined;
	}

	public void setDateJoined(String dateJoined) {
		this.dateJoined = dateJoined;
	}

	public String getDateLeave() {
		return dateLeave;
	}

	public void setDateLeave(String dateLeave) {
		this.dateLeave = dateLeave;
	}

	public String getReasonForLeaving() {
		return reasonForLeaving;
	}

	public void setReasonForLeaving(String reasonForLeaving) {
		this.reasonForLeaving = reasonForLeaving;
	}
	
	
	
}
