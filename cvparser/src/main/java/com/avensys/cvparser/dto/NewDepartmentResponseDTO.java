package com.avensys.cvparser.dto;

import lombok.Data;

@Data
public class NewDepartmentResponseDTO {
	private String status;
	private String msg;
	private String role;
	private Long id;
	
	public NewDepartmentResponseDTO(String status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public NewDepartmentResponseDTO(String status, String msg, String role) {
		this.status = status;
		this.msg = msg;
		this.role = role;
	}

	public NewDepartmentResponseDTO(String status, String msg, Long id, String role) {
		this.status = status;
		this.msg = msg;
		this.id = id;
		this.role = role;
		
	}
}
