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

	public OfferService(OfferInterface offerInterface) {
		this.offerInterface = offerInterface;
	}

	private List<Offer> newOffers = new ArrayList<>();
	private List<Offer> updatedOffers = new ArrayList<>();
	private List<Offer> oldOffers = new ArrayList<>();
	private int countProcessed;
	private int countNew;
	private int countUpdated;


	public void offerUpdate(List<Offer> offers) {
		countProcessed = countNew = countUpdated = 0;
		for (Offer newOffer : offers) {
			String offerId = newOffer.getOfferId();
			if (offerInterface.existsByOfferId(offerId)) {
				Offer offer = offerInterface.findByOfferId(offerId);
				if (offer.getPrice() != newOffer.getPrice()) {
					offer.setOldPrice(offer.getPrice());
					offer.setPrice(newOffer.getPrice());
					updatedOffers.add(offer);
					offerInterface.save(offer);
					countUpdated++;
				} else {
				    // offer already in db, no changes
				}
			} else {
				newOffers.add(newOffer);
				offerInterface.save(newOffer);
				countNew++;
			}
			countProcessed++;
		}
	}

}
