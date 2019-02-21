package com.example.project.offer;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
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

	private List<Offer> newOffers = new ArrayList<>();
	private List<Offer> updatedOffers = new ArrayList<>();
	private List<Offer> oldOffers = new ArrayList<>();
	private List<Price> newPrices = new ArrayList<>();
	private List<Price> oldPrices = new ArrayList<>();
	private int countProcessed;
	private int countNew;
	private int countUpdated;


	public void offerUpdate(List<Offer> offers) {
		countProcessed = countNew = countUpdated = 0;
		for (Offer newOffer : offers) {
			String offerId = newOffer.getOfferId();
			if (offerInterface.existsByOfferId(offerId)) {
				Offer offer = offerInterface.findByOfferId(offerId);
				Price price = priceInterface.getCurrentPrice();
				if (offer.getPrice() != newOffer.getPrice()) {
					offer.setPrice(newOffer.getPrice());
					updatedOffers.add(offer);
					offerInterface.save(offer);
					countUpdated++;
				}
			} else {
				newOffers.add(newOffer);
				offerInterface.save(newOffer);
				countNew++;
			}
			countProcessed++;
		}
	}

	public Offer getAverageOffer() {
		Offer averageOffer = new Offer();
		if (offerInterface.countAll() > 0) {
			averageOffer.setArea(offerInterface.getAvgArea());
			averageOffer.setPrice(offerInterface.getAvgPrice());
			averageOffer.setPricePerM2(offerInterface.getAvgPricePerM2());
		} else {
			averageOffer.setArea(0);
			averageOffer.setPrice(0);
			averageOffer.setPricePerM2(0);
		}
		return averageOffer;
	}

}
