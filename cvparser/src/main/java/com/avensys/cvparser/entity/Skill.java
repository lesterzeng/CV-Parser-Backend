package com.avensys.cvparser.entity;

import com.avensys.cvparser.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "skills")
public class Skill extends Auditable<String>{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@ManyToOne
//	@JsonManagedReference
//	@JsonIgnore
	@JsonBackReference
//	@MapsId("employee_id") // departmentId's employee_id
	@JoinColumn(name = "candidate_id") // foreign key, cover over employee_id column
	private CandidateEntity candidate;
	
	private String skill_description;
}
