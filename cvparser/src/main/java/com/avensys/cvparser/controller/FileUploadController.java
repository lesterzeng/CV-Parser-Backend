package com.avensys.cvparser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.avensys.cvparser.dto.UploadErrorDTO;
import com.avensys.cvparser.dto.UploadErrorListDTO;
import com.avensys.cvparser.service.FileUploadService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/upload")
public class FileUploadController {

	@Autowired
	private FileUploadService fus;

	@GetMapping("/cancel")
	public ResponseEntity<?> handleFileUpload() {
		fus.cancelUpload();
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value="/up", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> handleFileUpload(@RequestParam("files") List<MultipartFile> files) {
		UploadErrorListDTO errors = new UploadErrorListDTO();
		try {
			errors = fus.extractText(files);
		}
//		catch (FileSizeLimitExceededException ex) {
//			// Handle the exception here
//			ex.printStackTrace();
//			System.out.println("I CAUGHT IT!");
//		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		// Process the uploaded files

		// DEBUG
		if (errors.getErrors().size() > 0) {
			System.out.println("Inside error list");
			System.out.println("Success: " + errors.getSuccessCount());
			System.out.println("Failed: " + errors.getFailCount());
			for (UploadErrorDTO error : errors.getErrors()) {
				System.out.println(error.getFileName());
				System.out.println(error.getErrorMessage());
			}
		}

		if (errors.getErrors().size() > 0) {
			// Return a Bad Request status code and the errors DTO in the response body
			return ResponseEntity.badRequest().body(errors);
		} else {
			// Return an OK status code with no response body
			return ResponseEntity.ok().build();
		}

//		return ResponseEntity.ok().build();
	}
}