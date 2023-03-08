package com.avensys.cvparser.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.avensys.cvparser.dto.CandidateDTO;
import com.avensys.cvparser.entity.CandidateEntity;
import com.avensys.cvparser.repo.CandidateRepo;

@Service
@Component
public class CandidateService {
    @Autowired
    CandidateRepo cr;

    public List<CandidateEntity> getAllCandidate() {
        List<CandidateEntity> candidateList = (List<CandidateEntity>) cr.findAll();
        return candidateList;
    }

    public Optional<CandidateEntity> getCandidateById(Long id) {
        return cr.findById(id);
    }

    public Optional<CandidateEntity> getCandidateByEmail(String email) {
        return cr.findByEmail(email);
    }

    public Optional<CandidateEntity> getCandidateByPhoneNumber(String phoneNumber) {
        return cr.findByPhoneNumber(phoneNumber);
    }

    public List<CandidateEntity> getCandidateByFirstName(String firstName) {
        return cr.findByFirstName(firstName);
    }

    public List<CandidateEntity> getCandidateByLastName(String lastName) {
        return cr.findByLastName(lastName);
    }

    public List<CandidateEntity> getCandidateByWorkExp(int workExp) {
        return cr.findByWorkExp(workExp);
    }

    public CandidateDTO add(List<CandidateEntity> cand) {
    	CandidateDTO candDTO  = new CandidateDTO();
    	for(CandidateEntity candEntity: cand) {
    		if(cr.existsByFirstName(candEntity.getFirstName()) && cr.existsByLastName(candEntity.getLastName()) && cr.existsByEmail(candEntity.getEmail())) {

    
    			candDTO.getDuplicateList().add(candEntity);
    			
    		} else {
    			cr.save(candEntity);
    			candDTO.getSuccessList().add(candEntity);
    		}
    	} return candDTO;
    }

    public void editCandidateData(Long id, CandidateEntity cand) {
        cand.setId(id);

        cr.save(cand);
    }

    public boolean deleteCandidate(Long id) {
        if (cr.existsById(id)) {
            cr.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}