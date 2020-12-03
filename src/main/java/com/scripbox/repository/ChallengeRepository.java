package com.scripbox.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scripbox.entity.ChallengeEntity;

@Repository
public interface ChallengeRepository extends CrudRepository<ChallengeEntity, Integer>{

	ChallengeEntity findById(String id);

	List<ChallengeEntity> findAllByOrderByCreationDateDesc();

}
