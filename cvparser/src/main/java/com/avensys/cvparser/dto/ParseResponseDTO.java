package com.avensys.cvparser.dto;

import java.util.List;

import com.avensys.cvparser.entity.CandidateEntity;

import lombok.Data;

/**
 * Data class containing information to decode a file from client side
 * @author Yan Hong
 *
 */
@Data
public class ParseResponseDTO {
	private List<CandidateEntity> encodedData;

	public ParseResponseDTO(List<CandidateEntity> encodedData) {
		this.encodedData = encodedData;
	}
	
}
