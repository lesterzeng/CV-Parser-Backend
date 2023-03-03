package com.avensys.cvparser.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.avensys.cvparser.entity.CandidateEntity;

public interface CandidateRepo extends CrudRepository<CandidateEntity, Long> {
	
	public Optional<CandidateEntity> findById(int id);
	
	public Optional<CandidateEntity> findByEmail(String email);
	
	public Optional<CandidateEntity> findByPhoneNumber(String phoneNumber);
	
	public List<CandidateEntity> findByFirstName(String firstName);

	public List<CandidateEntity> findByLastName(String lastName);
	
	public List<CandidateEntity> findByWorkExp(int workExp);
	
	
//	public Optional<CandidateEntity> findByFirstName(String firstName);
//	
//	public Optional<CandidateEntity> findByLastName(String lastName);
//	
//	public Optional<CandidateEntity> findByWorkExperience(String workExp);

}