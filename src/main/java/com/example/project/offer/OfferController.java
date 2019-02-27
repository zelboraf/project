package com.example.project.offer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OfferController {

	private final OfferInterface offerInterface;
	private final OfferService offerService;
	private final ResidentialTypeInterface residentialTypeInterface;
	private final DistrictInterface districtInterface;
	private final PriceInterface priceInterface;

	public OfferController(OfferInterface offerInterface, OfferService offerService, ResidentialTypeInterface residentialTypeInterface, DistrictInterface districtInterface, PriceInterface priceInterface) {
		this.offerInterface = offerInterface;
		this.offerService = offerService;
		this.residentialTypeInterface = residentialTypeInterface;
		this.districtInterface = districtInterface;
		this.priceInterface = priceInterface;
	}

	private double parseNumber(String s) {
		return Double.parseDouble(s.replaceAll("[^\\d,]", "").replace(",", "."));
	}

	@GetMapping("/update")
	public String updateOffers(@RequestParam String residentialTypeName, @RequestParam String districtName, Model model) {
		try {
			String URL = "https://www.otodom.pl/sprzedaz/" + residentialTypeName + "/gdansk/" + districtName;
			String CSS_QUERY = "div.col-md-content article.offer-item[data-featured-name='listing_no_promo']";

			Document document = Jsoup.connect(URL).get();
			Elements offerHtml = document.select(CSS_QUERY);
			List<Offer> offers = new ArrayList<>();
			District district = districtInterface.getOneByName(districtName);
			ResidentialType residentialType = residentialTypeInterface.getOneByName(residentialTypeName);
			for (Element e : offerHtml) {
				Offer offer = new Offer(
						e.attr("data-tracking-id"),
						Math.round(parseNumber(e.select(".offer-item-price").text()) / 100.0) / 10.0,
						parseNumber(e.select(".offer-item-area").text()),
						e.select(".offer-item-title").text(),
						e.select(".pull-right").text(),
						residentialType,
						district
				);
//				Price price = new Price();
				offers.add(offer);
			}
			List<List> updatedNewOffers = offerService.updateNewOffers(offers);
			model.addAttribute("updatedOffers", updatedNewOffers.get(0));
			model.addAttribute("createdOffers", updatedNewOffers.get(1));
			model.addAttribute("counters", updatedNewOffers.get(2));
			return "update";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@GetMapping("/")
	public String showOffers(Model model) {
		model.addAttribute("averageOffer", offerService.getAverageOffer());
		model.addAttribute("residentialTypes", residentialTypeInterface.findAll());
		model.addAttribute("districts", districtInterface.findAll());
		model.addAttribute("offers", offerInterface.findAll());
		return "home";
	}

	@GetMapping("/price_history/{id}")
	public String priceHistory(@PathVariable long id, Model model) {
		List<Price> prices = priceInterface.getAllByOfferId(id);
		model.addAttribute(prices);
		return "price_history";
	}


}
