package com.example.project;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "types")
@Data
public class Type {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;

}
