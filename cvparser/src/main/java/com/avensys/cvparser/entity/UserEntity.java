package com.avensys.cvparser.entity;

import java.util.ArrayList;
import java.util.List;

import com.avensys.cvparser.audit.Auditable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "user")
public class UserEntity extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "username", unique = true)
	private String username;

	private String password;

	@Column(name = "salt")
	private String salt;

	private String user_type;

	//EMPLOYEE NOT USED
//	@OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL)
//	@JsonIgnore
//	private Employee employee;

	/*
	 * In Hibernate, the CascadeType is an enum that specifies the type of cascading
	 * behavior to be applied to a parent-child relationship in a JPA entity
	 * mapping. The available options are:
	 * 
	 * CascadeType.PERSIST: Persist operations are cascaded from parent to child
	 * entities.
	 * 
	 * CascadeType.MERGE: Merge operations are cascaded from parent to child
	 * entities.
	 * 
	 * CascadeType.REFRESH: Refresh operations are cascaded from parent to child
	 * entities.
	 * 
	 * CascadeType.REMOVE: Remove operations are cascaded from parent to child
	 * entities.
	 * 
	 * CascadeType.DETACH: Detach operations are cascaded from parent to child
	 * entities.
	 * 
	 * CascadeType.ALL: All operations (persist, merge, refresh, remove, and detach)
	 * are cascaded from parent to child entities.
	 */

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST })
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<Role> roles = new ArrayList<>();

	@Transient
	private String errorMessage;

	public UserEntity(String username, String password, String user_type) {
		this.username = username;
		this.password = password;
		this.user_type = user_type;
	}

//	public UserEntity(String username, String password, String salt, String user_type, Employee employee) {
//		this.username = username;
//		this.password = password;
//		this.salt = salt;
//		this.user_type = user_type;
//		this.employee = employee;
//	}

	public UserEntity(Long id) {
		this.id = id;
	}

	// getters and setters
}