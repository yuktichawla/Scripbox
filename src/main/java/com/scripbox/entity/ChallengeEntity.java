package com.scripbox.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="challenge")
public class ChallengeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="employee_id",nullable=false)
	private Employee employee;
	
	private String title;
	private String description;
	
	@ManyToOne
	@JoinColumn(name="tag_id",nullable=false)
	private TagEntity tag;
	
	private double creationDate;
	
	@OneToMany(mappedBy = "votedChallenges",fetch=FetchType.LAZY)
	private Set<Employee> empVote;
	
	@OneToMany(mappedBy = "collaboratedChallenges",fetch=FetchType.LAZY)
	private Set<Employee> empCollaboration;
	
	public ChallengeEntity(Employee employee, String title, String description, TagEntity tag, double creationDate) {
		super();
		this.employee = employee;
		this.title = title;
		this.description = description;
		this.tag = tag;
		this.creationDate = creationDate;
	}
	public Set<Employee> getEmpVote() {
		if(empVote == null) {
			return new HashSet<>();
		}
		return empVote;
	}
	public Set<Employee> getEmpCollaboration() {
		if(empCollaboration == null) {
			return new HashSet<>();
		}
		return empCollaboration;
	}
	
	
}
