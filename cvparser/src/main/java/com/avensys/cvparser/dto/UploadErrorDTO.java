package com.avensys.cvparser.dto;

import lombok.Data;

@Data
public class UploadErrorDTO {
	public UploadErrorDTO(String fileName, String errorMessage) {
		this.fileName = fileName;
		this.errorMessage = errorMessage;
	}
	private String fileName;
	private String errorMessage;
}
