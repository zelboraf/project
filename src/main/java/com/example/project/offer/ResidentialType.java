package com.example.project.offer;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "residential_type")
@Data
public class ResidentialType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;

}
