package com.avensys.cvparser.entity;

import java.util.Date;
import java.util.List;

import com.avensys.cvparser.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "candidate")
public class CandidateEntity extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "mid_name")
	private String midName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "working_experience")
	private int workExp;

//	@ManyToOne
//    @JoinColumn(foreignKey = @ForeignKey(name = "jobs_list_id"), name = "jobs_list_id")
//    private JobsList jobList;

//	@Column(name = "jobs_list_id")
//	private String jobsListId;

//	@Column(name = "uploaded_date", nullable = false, updatable = false, insertable = false)
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date uploadedDate;

//	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST })
//	@JoinTable(name = "candidate_skills", joinColumns = @JoinColumn(name = "candidate_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
//	private List<Skill> skills = new ArrayList<>();
	
	@OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
	// @JsonBackReference(value="EmployeeDepartments")
//	@JsonIgnore
	@JsonManagedReference
	private List<Skill> skills;
	
	@OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
	// @JsonBackReference(value="EmployeeDepartments")
//	@JsonIgnore
	@JsonManagedReference
	private List<RecentCompanies> recentCompanies;

	@ManyToOne
//	@JsonManagedReference
//	@JsonIgnore
//	@JsonBackReference
//	@MapsId("employee_id") // departmentId's employee_id
	@JoinColumn(name = "jobs_list_id") // foreign key, cover over employee_id column
	private JobsList jobsList;
	
	public CandidateEntity(String email, String firstName, String midName, String lastName, String phoneNumber,
			int workExp) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.midName = midName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.workExp = workExp;
	}

	public CandidateEntity(Long id) {
		this.id = id;
	}

	public CandidateEntity(String email, String firstName, String midName, String lastName, String phoneNumber,
			int workExp, List<Skill> skills, List<RecentCompanies> recentCompanies, JobsList jobsList) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.midName = midName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.workExp = workExp;
		this.skills = skills;
		this.recentCompanies = recentCompanies;
		this.jobsList = jobsList;
	}
	
	

}