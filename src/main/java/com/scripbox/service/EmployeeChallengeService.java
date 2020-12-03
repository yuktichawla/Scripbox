package com.scripbox.service;

import java.util.List;

import com.scripbox.model.Challenge;
import com.scripbox.model.Employee;
import com.scripbox.model.GenericResponse;

public interface EmployeeChallengeService {

	GenericResponse saveChallenges(List<Challenge> challenges, String userName);

	GenericResponse upVoteChallenge(String id, String userName);

	GenericResponse collaborateChallenge(String id, String userName);

	List<Employee> getChallengeColaborations(String id, String userName);

	List<Challenge> getChallenges(boolean creationDateSort, boolean voteCountSort);

}
