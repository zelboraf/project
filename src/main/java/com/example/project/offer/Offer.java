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
	@ManyToMany
	private Price price;
	@Column(name = "area", nullable = false)
	private double area;
//	@Column(name = "old_price")
//	private double oldPrice;
	@Column(name = "price_per_m2")
	private double pricePerM2;
	@Column(name = "descripiton")
	private String description;
	@Column(name = "seller")
	private String seller;
	@JoinColumn(name = "residential_type_id")
	@ManyToOne
	private ResidentialType residentialType;
	@JoinColumn(name = "district_id")
	@ManyToOne
	private District district;


	public Offer() {}

}
