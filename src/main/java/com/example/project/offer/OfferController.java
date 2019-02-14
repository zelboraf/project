package com.example.project.offer;

import lombok.extern.java.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@Log
public class OfferController {

	private final OfferInterface offerInterface;
	private final OfferService offerService;

	public OfferController(OfferInterface offerInterface, OfferService offerService) {
		this.offerInterface = offerInterface;
		this.offerService = offerService;
	}

	private double parseNumber(String s) {
		return Double.parseDouble(s.replaceAll("[^\\d,]", "").replace(",", "."));
	}

	@RequestMapping("/get")
	public String getOffers(Model model) {

		try {
			String URL = "https://www.otodom.pl/sprzedaz/mieszkanie/gdansk/srodmiescie/?search%5Bdist%5D=0&search%5Bdistrict_id%5D=28&search%5Bsubregion_id%5D=439&search%5Bcity_id%5D=40&search%5Border%5D=created_at_first%3Adesc&nrAdsPerPage=24";
			String CSS_QUERY = "div.col-md-content article.offer-item[data-featured-name='listing_no_promo']";

			Document document = Jsoup.connect(URL).get();
			Elements offerHtml = document.select(CSS_QUERY);
			List<Offer> offers = new ArrayList<>();
			for (Element e : offerHtml) {
					Offer offer = new Offer();
					offer.setOfferId(e.attr("data-tracking-id"));
					offer.setArea(parseNumber(e.select(".offer-item-area").text()));
					offer.setPrice(parseNumber(e.select(".offer-item-price").text()) / 1000);
					DecimalFormat df = new DecimalFormat("#.#");
					offer.setPricePerM2(Double.valueOf(df.format(offer.getPrice() / offer.getArea())));
					offer.setDescription(e.select(".offer-item-title").text());
					offer.setSeller(e.select(".pull-right").text());
					offers.add(offer);
					log.info(e.text());
			}
			offerService.offerUpdate(offers);
			model.addAttribute("countProcessed", offerService.getCountProcessed());
			model.addAttribute("countNew", offerService.getCountNew());
			model.addAttribute("countUpdated", offerService.getCountUpdated());
			model.addAttribute("countAll", offerInterface.countAll());
			model.addAttribute("updatedOffers", offerService.getUpdatedOffers());
			model.addAttribute("newOffers", offerService.getNewOffers());
			return "get";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

//	@RequestMapping("/")
//	public String showOffers(Model model) {
//		Offer avgOffer = new Offer();
//		avgOffer.setArea(offerInterface.getAvgArea());
//		avgOffer.setPrice(offerInterface.getAvgPrice());
//		avgOffer.setPricePerM2(offerInterface.getAvgPricePerM2());
//		model.addAttribute("avgOffer", avgOffer);
//		List<Offer> offers = offerInterface.findAll();
//		model.addAttribute("offers", offers);
//		return "home";
//	}

}
