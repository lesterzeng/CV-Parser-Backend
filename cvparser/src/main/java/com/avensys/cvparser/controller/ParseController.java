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
	 * API Call to handle parsing of singular file in the back end.
	 * @param stringData is String representation of File Contents in UTF-8
	 */
	@PostMapping("docstring")
	public void parseFile(@RequestBody String stringData) {
		System.out.println(parser.parseFile(stringData));
	}
	
	/**
	 * API Call to handle parsing of multiple files in the back end.
	 * @param stringData is String Array representation of all File Contents in UTF-8
	 */
	@PostMapping("docstrings")
	public void parseMultiFile(@RequestBody String stringData[]) {
		for (String fileData: stringData) {
			parser.parseFile(fileData);
		}
	}
	
}
