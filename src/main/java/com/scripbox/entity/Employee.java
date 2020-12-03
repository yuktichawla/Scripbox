package com.scripbox.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String designation;
	
	@OneToMany(mappedBy = "employee",fetch=FetchType.EAGER)
	private List<ChallengeEntity> challenges;
	
	@ManyToMany
	@JoinColumn(name="challenge_voted_id",nullable=false)
	private Set<ChallengeEntity> votedChallenges;
	
	@ManyToMany
	@JoinColumn(name="challenge_collaboration_id",nullable=false)
	private Set<ChallengeEntity> collaboratedChallenges;
	
	@OneToOne(mappedBy = "employee",fetch = FetchType.LAZY)
	private UserEntity user;
}
