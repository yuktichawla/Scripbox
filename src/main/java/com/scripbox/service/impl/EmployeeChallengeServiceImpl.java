package com.scripbox.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scripbox.entity.ChallengeEntity;
import com.scripbox.entity.TagEntity;
import com.scripbox.entity.UserEntity;
import com.scripbox.exception.ApplicationException;
import com.scripbox.model.Challenge;
import com.scripbox.model.Employee;
import com.scripbox.model.GenericResponse;
import com.scripbox.model.Tag;
import com.scripbox.repository.ChallengeRepository;
import com.scripbox.repository.LoginRepository;
import com.scripbox.repository.TagRepository;
import com.scripbox.service.EmployeeChallengeService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeChallengeServiceImpl<Employees> implements EmployeeChallengeService {
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private ChallengeRepository challengeRepository;
	
	@Autowired
	private TagRepository tagRepository;

	@Override
	public GenericResponse saveChallenges(List<com.scripbox.model.Challenge> challenges,
			String userName) {
		UserEntity user = loginRepository.findByUserName(userName);
		if(user != null) {
			List<ChallengeEntity> challengeEntities = challenges.stream().map(c -> {
				try {
					TagEntity tag = tagRepository.findById(c.getTag().getTagId());
					if(tag==null) {
						return null;
					}
					return new ChallengeEntity(user.getEmployee(), c.getTitle(), c.getDescription(), tag,
							System.currentTimeMillis());
				} catch (Exception e) {
					log.error("challenge with title could not be added - {}", c.getTitle());
				}
				return null;
			}).filter(Objects::nonNull).collect(Collectors.toList());
			challengeRepository.save((Iterable<ChallengeEntity>)challengeEntities);
		} else {
			throw new ApplicationException("UNAUTHORIZED_USER", "user not authorized");
		}
		return new GenericResponse("SUCCESS");
	}

	@Override
	public GenericResponse upVoteChallenge(String id, String userName) {
		return updateChallenge(id, userName, true , false);
	}

	@Override
	public GenericResponse collaborateChallenge(String id, String userName) {
		return updateChallenge(id, userName, false , true);
	}

	private GenericResponse updateChallenge(String id, String userName, boolean upVote, boolean collaborate) {
		UserEntity user = loginRepository.findByUserName(userName);
		if(user != null) {
			ChallengeEntity challenge = challengeRepository.findById(id);
			if(challenge != null) {
				if(challenge.getEmployee().getId() != user.getEmployee().getId()) {
					if(upVote) {
						challenge.getEmpVote().add(user.getEmployee());
					} else if(collaborate) {
						challenge.getEmpCollaboration().add(user.getEmployee());
					}
					challengeRepository.save(challenge);
				} else {
					throw new ApplicationException("UNAUTHORIZED_OPERATION", "not authorized to vote your own challenges");
				}
			} else {
				throw new ApplicationException("CHALLENGE_NOT_FOUND", "challenge not found");
			}
		} else {
			throw new ApplicationException("UNAUTHORIZED_USER", "user not authorized");
		}
		return new GenericResponse("SUCCESS");
	}

	@Override
	public List<Employee> getChallengeColaborations(String id, String userName) {
		ChallengeEntity challenge = challengeRepository.findById(id);
		if(challenge != null) {
			return challenge.getEmpCollaboration().stream().map(c -> {
				return new Employee(c.getId(), c.getName(), c.getDesignation());
			}).collect(Collectors.toList());
		} else {
			throw new ApplicationException("CHALLENGE_NOT_FOUND", "challenge not found");
		}
	}

	@Override
	public List<com.scripbox.model.Challenge> getChallenges(boolean creationDateSort, boolean voteCountSort) {
		List<ChallengeEntity> challengesEntities = new ArrayList<>();
		if(creationDateSort) {
			challengesEntities = challengeRepository.findAllByOrderByCreationDateDesc();
		} else {
			Iterable<ChallengeEntity> challengeEntities = challengeRepository.findAll();
			challengeEntities.forEach(challengesEntities::add);
		} 
		if(voteCountSort) {
			challengesEntities.sort((ChallengeEntity c1, ChallengeEntity c2)->c1.getEmpVote().size() - c2.getEmpVote().size());
		}
		
		return getChallengesModel(challengesEntities);
		
	}

	private List<Challenge> getChallengesModel(List<ChallengeEntity> challengesEntities) {
		return challengesEntities.stream().map(c -> {
			Tag tag = new Tag(c.getTag().getId(), c.getTag().getName());
			return new Challenge(c.getId(), c.getTitle(), c.getDescription(), tag);
		}).collect(Collectors.toList());
	}

	

}
