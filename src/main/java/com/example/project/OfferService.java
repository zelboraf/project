package com.example.project;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class OfferService {

	private final OfferInterface offerInterface;
	private final PriceInterface priceInterface;

	public OfferService(OfferInterface offerInterface, PriceInterface priceInterface) {
		this.offerInterface = offerInterface;
		this.priceInterface = priceInterface;
	}

	private List<Offer> createdOffers = new ArrayList<>();
	private List<Offer> updatedOffers = new ArrayList<>();
	private List<Counter> counters = new ArrayList<>();

	public List<List> updateNewOffers(List<Offer> offers) {
		Counter counter = new Counter();
		for (Offer newOffer : offers) {
			String offerId = newOffer.getOfferId();
			Offer offer = offerInterface.findOneByOfferId(offerId);
			if (offer == null) {
				offerInterface.save(newOffer);
				createdOffers.add(newOffer);
				counter.incCreated();
			} else {
				if (newOffer.getPrice() != offer.getPrice()) {
					Price price = new Price(offer.getPrice(), offer.getPricePerM2(), offer.getLocalDateTime());
					offer.getPriceHistory().add(price);
					priceInterface.save(price);
					offer.setPrice(newOffer.getPrice());
					offer.setPricePerM2(newOffer.getPricePerM2());
					offer.setLocalDateTime(newOffer.getLocalDateTime());
					updatedOffers.add(offer);
					offerInterface.save(offer);
					counter.incUpdated();
				}
				counter.incProcessed();
			}
		}
		counter.setAll(offerInterface.countAll());
		counters.add(counter);
		return new ArrayList<>(Arrays.asList(updatedOffers, createdOffers, counters));
	}

	public Offer getAverageOffer() {
		if (offerInterface.countAll() == 0) {
			return new Offer(0, 0, 0);
		}
		return new Offer(
				offerInterface.getAveragePrice(),
				offerInterface.getAverageArea(),
				offerInterface.getAveragePricePerM2()
		);
	}

	public List<Offer> markCheapOffers(List<Offer> offers, Offer averageOffer) {
		List<Offer> markedOffers = new ArrayList<>();
		for (Offer offer : offers) {
			if (offer.getPricePerM2() < averageOffer.getPricePerM2() * 8 / 10d) {
				offer.setCheap(true);
			}
			markedOffers.add(offer);
		}
		return markedOffers;
	}
}

