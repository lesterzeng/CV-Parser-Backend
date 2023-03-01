package com.avensys.cvparser.dto;

import java.util.List;

import lombok.Data;

@Data
public class UploadErrorListDTO {
	private List<UploadErrorDTO> errors;
	private int successCount;
	private int failCount;

	public UploadErrorListDTO(List<UploadErrorDTO> errors, int successCount, int failCount) {
		this.errors = errors;
		this.successCount = successCount;
		this.failCount = failCount;
	}

	public UploadErrorListDTO() {
	}

}
