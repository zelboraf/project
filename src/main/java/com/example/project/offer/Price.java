package com.example.project.offer;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prices")
@Data
public class Price {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private double value;
	private LocalDateTime localDateTime;
	@ManyToMany
	private Offer offer;

}
