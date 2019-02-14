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
	@Column(name = "offer_id", nullable = false)
	private String offerId;
	@Column(name = "price", nullable = false)
	private double price;
	@Column(name = "area", nullable = false)
	private double area;
	@Column(name = "old_price")
	private double oldPrice;
	@Column(name = "price_per_m2")
	private double pricePerM2;
	@Column(name = "descripiton")
	private String description;
	@Column(name = "seller")
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
