package com.example.project;

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
	private double pricePerM2;
	private LocalDateTime localDateTime;

	public Price() {}

	public Price(double value, double pricePerM2, LocalDateTime localDateTime) {
		this.value = value;
		this.pricePerM2 = pricePerM2;
		this.localDateTime = localDateTime;
	}

}
