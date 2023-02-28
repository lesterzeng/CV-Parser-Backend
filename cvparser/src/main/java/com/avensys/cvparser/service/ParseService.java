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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	private String parsePdfFile(String fileData) throws IOException {
		byte[] decoder = Base64.getDecoder().decode(fileData);
//		InputStream is = new ByteArrayInputStream(decoder);
		PDDocument pdfFile = PDDocument.load(decoder);
		PDFTextStripper stripper = new PDFTextStripper();
		// TODO: Check performance and toggle off if this takes too long.
		stripper.setSortByPosition(true);
		for (int p = 1; p <= pdfFile.getNumberOfPages(); ++p) {
			// Set the page interval to extract. If you don't, then all pages would be extracted.
			stripper.setStartPage(p);
			stripper.setEndPage(p);

			// let the magic happen
			String text = stripper.getText(pdfFile);
			// do some nice output with a header
			String pageStr = String.format("page %d:", p);
			System.out.println(pageStr);
			for (int i = 0; i < pageStr.length(); ++i) {
				System.out.print("-");
			}
			System.out.println();
			System.out.println(text.trim());
			System.out.println();

		}
//		InputStream decoded = java.util.Base64.getDecoder().wrap(inputStream);
//		PDDocument.load(decoded);
		pdfFile.close();
		return null;
	}
}
