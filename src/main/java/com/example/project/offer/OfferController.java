package com.example.project.offer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OfferController {

	private final OfferInterface offerInterface;
	private final OfferService offerService;
	private final ResidentialTypeInterface residentialTypeInterface;
	private final DistrictInterface districtInterface;

	public OfferController(OfferInterface offerInterface, OfferService offerService, ResidentialTypeInterface residentialTypeInterface, DistrictInterface districtInterface) {
		this.offerInterface = offerInterface;
		this.offerService = offerService;
		this.residentialTypeInterface = residentialTypeInterface;
		this.districtInterface = districtInterface;
	}

	private double parseNumber(String s) {
		return Double.parseDouble(s.replaceAll("[^\\d,]", "").replace(",", "."));
	}

	@GetMapping("/update")
	public String updateOffers(@RequestParam String residentialType, @RequestParam String district, Model model) {
		try {
			String URL = "https://www.otodom.pl/sprzedaz/" + residentialType + "/gdansk/" + district +
					"?search[filter_float_m%3Afrom]=70&search[filter_float_m%3Ato]=80";
			String CSS_QUERY = "div.col-md-content article.offer-item[data-featured-name='listing_no_promo']";

			Document document = Jsoup.connect(URL).get();
			Elements offerHtml = document.select(CSS_QUERY);
			List<Offer> offers = new ArrayList<>();
			List<Price> prices = new ArrayList<>();
			for (Element e : offerHtml) {
				Offer offer = new Offer();
				Price price = new Price();
				offer.setOfferId(e.attr("data-tracking-id"));
				offer.setArea(parseNumber(e.select(".offer-item-area").text()));
				price.setValue(parseNumber(e.select(".offer-item-price").text()) / 1000);
				DecimalFormat df = new DecimalFormat("#.#");
				offer.setPricePerM2(Double.valueOf(df.format(price.getValue() / offer.getArea())));
				offer.setDescription(e.select(".offer-item-title").text());
				offer.setSeller(e.select(".pull-right").text());
				offer.setDistrict(districtInterface.getOneByName(district));
				offer.setResidentialType(residentialTypeInterface.getOneByName(residentialType));
				offers.add(offer);
				prices.add(price);
			}
			offerService.offerUpdate(offers);
			model.addAttribute("countProcessed", offerService.getCountProcessed());
			model.addAttribute("countNew", offerService.getCountNew());
			model.addAttribute("countUpdated", offerService.getCountUpdated());
			model.addAttribute("countAll", offerInterface.countAll());
			model.addAttribute("updatedOffers", offerService.getUpdatedOffers());
			model.addAttribute("newOffers", offerService.getNewOffers());
			return "update";
		} catch (Exception e) {
			e.printStackTrace();
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

}
