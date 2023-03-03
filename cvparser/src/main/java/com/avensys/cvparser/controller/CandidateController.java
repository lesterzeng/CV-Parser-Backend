package com.avensys.cvparser.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.avensys.cvparser.entity.CandidateEntity;
import com.avensys.cvparser.entity.UserEntity;
import com.avensys.cvparser.service.CandidateService;
import com.avensys.cvparser.service.UserService;

@RestController
@CrossOrigin(origins = "*")
public class CandidateController {
	@Autowired
	private CandidateService cs;

	final static String CANDIDATE_API_PATH = "/cvparser/cand";

	@GetMapping(CANDIDATE_API_PATH)
	public List<CandidateEntity> getAllCandidate() {
		return cs.getAllCandidate();
	}

	@GetMapping(CANDIDATE_API_PATH + "/id/{id}")
	public Optional<CandidateEntity> getCandidateById(@PathVariable Long id) {
		return cs.getCandidateById(id);
	}
	
	@GetMapping(CANDIDATE_API_PATH + "/email/{email}")
	public Optional<CandidateEntity> getCandidateByEmail(@PathVariable String email) {
		return cs.getCandidateByEmail(email);
	}
	
	@GetMapping(CANDIDATE_API_PATH + "/phone/{phoneNumber}")
	public Optional<CandidateEntity> getCandidateByPhoneNumber(@PathVariable String phoneNumber) {
		return cs.getCandidateByPhoneNumber(phoneNumber);
	}
	
	@GetMapping(CANDIDATE_API_PATH + "/firstname/{firstName}")
	public List<CandidateEntity> getCandidateByFirstName(@PathVariable String firstName) {
		return cs.getCandidateByFirstName(firstName);
	}
	
	@GetMapping(CANDIDATE_API_PATH + "/lastname/{lastName}")
	public List<CandidateEntity> getCandidateByLastName(@PathVariable String lastName) {
		return cs.getCandidateByLastName(lastName);
	}
	
	@GetMapping(CANDIDATE_API_PATH + "/workexp/{workExp}")
	public List<CandidateEntity> getCandidateByWorkExp(@PathVariable int workExp) {
		return cs.getCandidateByWorkExp(workExp);
	}
	
	/*
	 * DUPLICATE OF REGISTER IN AuthController
	 */
//	@PostMapping(USER_API_PATH)
//	public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity u) {
////		System.out.println(u);
//		UserEntity user = us.add(u);
//
//		if (user.getErrorMessage() != null) {
//			return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
//		} else {
//			return new ResponseEntity<>(user, HttpStatus.OK);
//		}
//	}
	
	@PostMapping(CANDIDATE_API_PATH)
	public CandidateEntity addCandidate(@RequestBody CandidateEntity cand) {
		return cs.add(cand);
	}

	@PutMapping(CANDIDATE_API_PATH + "/{id}")
	public void updateCandidate(@PathVariable("id") Long id, @RequestBody CandidateEntity cand) {
		cs.editCandidateData(id, cand);
	}

	@DeleteMapping(CANDIDATE_API_PATH + "/{id}")
	public boolean deleteCandidate(@PathVariable("id") Long id) {
		return cs.deleteCandidate(id);
	}
	
}