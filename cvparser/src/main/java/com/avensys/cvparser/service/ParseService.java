package com.avensys.cvparser.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import com.avensys.cvparser.dto.ParseRequestDTO;

@Service
public class ParseService {
	public void parseFiles(ParseRequestDTO prd) {
		System.out.println("Method Called");
		if (prd.getFileType() == "application/pdf")
			System.out.println("pdf found");
			try {
				parsePdfFile(prd.getEncodedData());
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public String parsePdfFile(String fileData) throws IOException {
		String[] lineBreak = fileData.split("\n");
		int counter = 0;
		for (String line: lineBreak) {
//			System.out.println("%d: %s".formatted(counter,line));
			counter++;
		}
		return null;
	}
}
