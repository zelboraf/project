package com.example.project;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "offers")
@Data
@ToString(exclude = {"priceHistory"})
public class Offer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "offer_id", nullable = false)
	private String offerId;
	@Column(name = "descripiton")
	private String description;
	@Column(name = "seller")
	private String seller;
	@Column(name = "area", nullable = false)
	private double area;
	@Column(name = "price", nullable = false)
	private double price;
	@Column(name = "price_per_m2")
	private double pricePerM2;
	@Column(name = "is_cheap")
	private boolean isCheap;
	@JoinColumn(name = "type_id")
	@ManyToOne
	private Type type;
	@JoinColumn(name = "city_id")
	@ManyToOne
	private City city;
	@JoinColumn(name = "district_id")
	@ManyToOne
	private District district;
	@Column(name = "local_date_time")
	private LocalDateTime localDateTime;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "offers_prices",
			joinColumns = {@JoinColumn(name = "offer_id")},
			inverseJoinColumns = {@JoinColumn(name = "price_id")}
		)
	private List<Price> priceHistory = new ArrayList<>();

	public Offer() {}

	public Offer(
			String offerId,
			double price,
			double area,
			String description,
			String seller,
			Type type,
			City city,
			District district
	) {
		this.offerId = offerId;
		this.price = price;
		this.area = area;
		this.description = description;
		this.seller = seller;
		this.type = type;
		this.city = city;
		this.district = district;

		this.localDateTime = LocalDateTime.now();
		this.pricePerM2 = Math.round((price / area) * 10) / 10.0;
	}

	public Offer(double price, double area, double pricePerM2) {
		this.price = price;
		this.area = area;
		this.pricePerM2 = pricePerM2;
	}

}
