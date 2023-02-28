package com.avensys.cvparser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avensys.cvparser.dto.ParseRequestDTO;
import com.avensys.cvparser.service.ParseService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/parse")
public class ParseController {
	@Autowired
	ParseService parser;
	/**
	 * API Call to handle parsing of files in the back end.
	 * @param encodedData RequestBody of the HTTP Request mapped to {@link ParseRequestDTO}
	 */
	@PostMapping("upload")
	public void parseFile(@RequestBody ParseRequestDTO encodedData) {
		System.out.println(encodedData.getFileType());
		parser.parseFiles(encodedData);
	}
	
	@PostMapping("filter")
	public void filterFile(@RequestBody ParseRequestDTO encodedData) {
		
	}
}
