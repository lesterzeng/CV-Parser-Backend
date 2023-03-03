package com.avensys.cvparser.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "jobs_list")
public class JobsList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@OneToMany(mappedBy = "jobsList", cascade = CascadeType.ALL, orphanRemoval = true)
	// @JsonBackReference(value="EmployeeDepartments")
	@JsonIgnore
//	@JsonManagedReference
	private List<CandidateEntity> candidates;
	
	@Column(name = "job_title")
	private String jobTitle;
	
	@Column(name = "start_title")
	private Integer start_date;
	
	@Column(name = "end_title")
	private Integer end_date;
	
}
