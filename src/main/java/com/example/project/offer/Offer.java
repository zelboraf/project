package com.example.project.offer;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "offers")
@Data
public class Offer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String offerId;
	private double price;
	private double area;
	@Column(name = "price_per_m2")
	private double pricePerM2;
	private String description;
	private String seller;

	public Offer() {}

	public Offer(String offerId, double price, double area, double pricePerM2, String description, String seller) {
		this.offerId = offerId;
		this.price = price;
		this.area = area;
		this.pricePerM2 = pricePerM2;
		this.description = description;
		this.seller = seller;
	}
}
