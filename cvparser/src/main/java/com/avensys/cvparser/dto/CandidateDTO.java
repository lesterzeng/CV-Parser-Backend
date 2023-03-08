package com.avensys.cvparser.dto;

import java.util.ArrayList;
import java.util.List;

import com.avensys.cvparser.entity.CandidateEntity;

import lombok.Data;

@Data
public class CandidateDTO {

	private List<CandidateEntity> successList;
	private List<CandidateEntity> duplicateList;
	
	public CandidateDTO() {
		super();
		successList = new ArrayList<>();
		duplicateList = new ArrayList<>();
	}


	
	
}
