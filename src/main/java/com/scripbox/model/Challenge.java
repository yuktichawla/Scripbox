package com.scripbox.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Challenge {

	private int id;
	private String title;
	private String description;
	private Tag tag;
}
