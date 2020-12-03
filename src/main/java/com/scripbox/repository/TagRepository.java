package com.scripbox.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scripbox.entity.TagEntity;

@Repository
public interface TagRepository extends CrudRepository<TagEntity, Integer>{

	TagEntity findById(String id);

}
