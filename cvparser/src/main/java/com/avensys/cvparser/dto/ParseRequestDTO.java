package com.avensys.cvparser.dto;

import lombok.Data;

/**
 * Data class containing information to decode a file from client side
 * @author Yan Hong
 *
 */
@Data
public class ParseRequestDTO {
	private String fileType;
	private String encodedData;
}
