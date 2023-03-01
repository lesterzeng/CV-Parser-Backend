package com.avensys.cvparser.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.avensys.cvparser.dto.UploadErrorDTO;
import com.avensys.cvparser.dto.UploadErrorListDTO;

@Service
@Component
public class FileUploadService {

	public UploadErrorListDTO extractText(List<MultipartFile> files) throws IOException, TikaException, SAXException {
//		Tika tika = new Tika();
		List<UploadErrorDTO> errorList = new ArrayList<UploadErrorDTO>();
		int successCount = 0, failCount = 0;
		System.out.println("List: " + files);
		for (MultipartFile file : files) {
//			    System.out.println(tika.parseToString(file));
			String fileName = file.getOriginalFilename();
			String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
			System.out.println("File Name: " + fileName);
			System.out.println("File Extension: " + extension);

//			byte[] fileContent = file.getBytes();
//			System.out.println(new String(fileContent, StandardCharsets.UTF_8));

			String mimeType = new Tika().detect(file.getBytes());
			System.out.println("File type: " + mimeType);
			String text = "";
			switch (mimeType) {
			case "application/pdf":
				text = processPdf(file);
				successCount++;
				break;
//	            case "application/msword":
			case "application/x-tika-msoffice":
//				System.out.println("This is .doc file.");
//				text = processDoc(file);
//				break;
			case "application/x-tika-ooxml":
//				System.out.println("This is .docx file.");
				if (file.getOriginalFilename().endsWith(".docx")||file.getOriginalFilename().endsWith(".doc")) {
					text = processDoc(file);
					successCount++;
				} else {
					failCount++;
					UploadErrorDTO error = new UploadErrorDTO(fileName, "Unsupported file type: " + mimeType);
//					errorList.add("File "+fileName+" is of unsupported file type: "+mimeType);
					errorList.add(error);
				}
				break;
//	            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
//	                processDocx(fileBytes);
//	                break;
			default:
				System.out.println("Unsupported file type: " + mimeType);
				failCount++;
				UploadErrorDTO error = new UploadErrorDTO(fileName, "Unsupported file type: " + mimeType);
//				errorList.add("File "+fileName+" is of unsupported file type: "+mimeType);
				errorList.add(error);
			}
			System.out.println(text);

//			InputStream stream = file.getInputStream();
//			AutoDetectParser parser = new AutoDetectParser();
//			BodyContentHandler handler = new BodyContentHandler();
//			Metadata metadata = new Metadata();
//			System.out.println("Metadata: " + metadata);
//			ParseContext context = new ParseContext();
//			parser.parse(stream, handler, metadata, context);
//			String text = handler.toString();
//			System.out.println("Extracted Text: " + text);
		}

//		  return tika.parseToString(file);
		return new UploadErrorListDTO(errorList, successCount, failCount);
	}

	public String processPdf(MultipartFile file) {
		String text = "";
		try (final PDDocument document = PDDocument.load(file.getInputStream())) {
			final PDFTextStripper pdfStripper = new PDFTextStripper();
			text = pdfStripper.getText(document);
		} catch (final Exception ex) {
			ex.printStackTrace();
			text = "Error parsing PDF";
		}
		return text;
	}

	public String processDoc(MultipartFile file) {
		String text = "";
		try (InputStream is = file.getInputStream()) {
			String filename = file.getOriginalFilename();

			if (filename.endsWith(".doc")) {
				HWPFDocument doc = new HWPFDocument(is);
				WordExtractor extractor = new WordExtractor(doc);
				text = extractor.getText();
			} else if (filename.endsWith(".docx")) {
				XWPFDocument doc = new XWPFDocument(is);
//                WordExtractor extractor = new WordExtractor(doc);
				XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
				text = extractor.getText();
			} else {
				throw new IllegalArgumentException("Unsupported file type");
			}
		} catch (IOException e) {
			// Handle exception
		}
		return text;
	}

}
