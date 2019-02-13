package com.example.project.offer;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class OfferService {

	private final OfferInterface offerInterface;

	private List<Offer> newOffers = new ArrayList<>();
	private List<Offer> updatedOffers = new ArrayList<>();
	private List<Offer> oldOffers = new ArrayList<>();
	private int offerCount;


	public void offerUpdate(List<Offer> offers) {
		offerCount = 0;
		for (Offer o : offers) {
			String offerId = o.getOfferId();
			if (offerInterface.existsByOfferId(offerId)) {
				Offer oldOffer = offerInterface.findByOfferId(offerId);
				if (oldOffer.getPrice() != o.getPrice()) {
					o.setOldPrice(oldOffer.getPrice());
					updatedOffers.add(o);
					offerInterface.save(o);
				} else {
				    // offer already in db, no changes --> do nothing
				}
			} else {
				newOffers.add(o);
				offerInterface.save(o);
			}
			offerCount++;
		}
	}

}
