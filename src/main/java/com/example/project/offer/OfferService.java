package com.example.project.offer;

import lombok.Data;
import lombok.extern.java.Log;
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
@Log
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
			try {
				Offer offer = offerInterface.findOneByOfferId(offerId);
				if (newOffer.getPrice() != offer.getPrice()) {
					log.info("new offer");
					offer.setPrice(newOffer.getPrice());
					offer.setLocalDateTime(newOffer.getLocalDateTime());
					updatedOffers.add(offer);
					offerInterface.save(offer);
					Price price = new Price(offer.getPrice(), offer.getLocalDateTime());
					offer.getPriceHistory().add(price);
					priceInterface.save(price);
					counter.incUpdated();
				}
			} catch (Exception ex){
				log.info("new offer");
				createdOffers.add(newOffer);
				offerInterface.save(newOffer);
				counter.incCreated();
			}
			counter.incProcessed();
		}
		counter.setAll(offerInterface.countAll());
		counters.add(counter);
		return new ArrayList<>(Arrays.asList(updatedOffers, createdOffers, counters));
	}

	public Offer getAverageOffer() {
		if (offerInterface.countAll() > 0) {
			return new Offer(
					offerInterface.getAvgPrice(),
					offerInterface.getAvgArea(),
					offerInterface.getAvgPricePerM2()
			);
		}
		return new Offer(0, 0, 0);
	}

}
