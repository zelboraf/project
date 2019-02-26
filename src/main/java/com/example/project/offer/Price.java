package com.example.project.offer;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "prices")
@Data
public class Price {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private double value;
	private LocalDateTime localDateTime;

	public Price(double value, LocalDateTime localDateTime) {
		this.value = value;
		this.localDateTime = localDateTime;
	}

}
