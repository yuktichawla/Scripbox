package com.scripbox.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scripbox.exception.ApplicationException;
import com.scripbox.filter.JwtTokenUtil;
import com.scripbox.model.Challenge;
import com.scripbox.model.Employee;
import com.scripbox.model.GenericResponse;
import com.scripbox.service.EmployeeChallengeService;

@RestController
@RequestMapping("/employee")
public class ChallengesController {

	@Autowired
	private EmployeeChallengeService service;
	@Autowired
	JwtTokenUtil jwttokenUtil;
	
	@PostMapping(value = "/challenges", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getaccountStatement(@RequestBody List<Challenge> challenges , HttpServletRequest request)
			throws ApplicationException, NoSuchAlgorithmException {
		String userName = jwttokenUtil.getUsernameFromToken(request.getHeader("Authorization").substring(7)).toString();
		return new ResponseEntity<GenericResponse>(
				service.saveChallenges(challenges, userName),HttpStatus.CREATED);

	}
	
	@PostMapping(value = "/vote/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> upVoteChallenge(HttpServletRequest request, @PathParam(value = "id") String id)
			throws ApplicationException, NoSuchAlgorithmException {
		String userName = jwttokenUtil.getUsernameFromToken(request.getHeader("Authorization").substring(7)).toString();
		return new ResponseEntity<GenericResponse>(
				service.upVoteChallenge(id, userName),HttpStatus.ACCEPTED);

	}
	
	@PostMapping(value = "/collaborate/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> collaborateChallenge(HttpServletRequest request, @PathParam(value = "id") String id)
			throws ApplicationException, NoSuchAlgorithmException {
		String userName = jwttokenUtil.getUsernameFromToken(request.getHeader("Authorization").substring(7)).toString();
		return new ResponseEntity<GenericResponse>(
				service.collaborateChallenge(id, userName),HttpStatus.ACCEPTED);

	}
	
	@GetMapping(value = "/collaboration/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getChallengeColaborations(HttpServletRequest request, @PathParam(value = "id") String id)
			throws ApplicationException, NoSuchAlgorithmException {
		String userName = jwttokenUtil.getUsernameFromToken(request.getHeader("Authorization").substring(7)).toString();
		return new ResponseEntity<List<Employee>>(
				service.getChallengeColaborations(id, userName),HttpStatus.OK);

	}
	
	@GetMapping(value = "/challenges", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getChallenge(
			@RequestParam(required = false, value = "creationDate") boolean creationDateSort,
			@RequestParam(required = false, value = "voteCounts") boolean voteCountSort,
			HttpServletRequest request)
			throws ApplicationException, NoSuchAlgorithmException {
		return new ResponseEntity<List<Challenge>>(service.getChallenges(creationDateSort,voteCountSort),HttpStatus.OK);

	}
	
}
