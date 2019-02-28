package com.example.project;

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
	private final PriceInterface priceInterface;
	private final TypeInterface typeInterface;
	private final CityInterface cityInterface;
	private final DistrictInterface districtInterface;

	public OfferController(OfferInterface offerInterface, OfferService offerService, TypeInterface typeInterface,
	                       DistrictInterface districtInterface, CityInterface cityInterface, PriceInterface priceInterface) {
		this.offerInterface = offerInterface;
		this.offerService = offerService;
		this.priceInterface = priceInterface;
		this.typeInterface = typeInterface;
		this.cityInterface = cityInterface;
		this.districtInterface = districtInterface;
	}

	private double parseNumber(String s) {
		return Double.parseDouble(s.replaceAll("[^\\d,]", "").replace(",", "."));
	}

	@GetMapping("/update")
	public String updateOffers(
			@RequestParam String typeName,
			@RequestParam String cityName,
			@RequestParam String districtName,
			Model model){
		try {
			String URL = "https://www.otodom.pl/sprzedaz/" + typeName + "/" + cityName + "/" + districtName;
			String CSS_QUERY = "div.col-md-content article.offer-item[data-featured-name='listing_no_promo']";

			Document document = Jsoup.connect(URL).get();
			Elements offerHtml = document.select(CSS_QUERY);
			List<Offer> offers = new ArrayList<>();
			Type type = typeInterface.getOneByName(typeName);
			City city = cityInterface.getOneByName(cityName);
			District district = districtInterface.getOneByName(districtName);
			for (Element e : offerHtml) {
				Offer offer = new Offer(
						e.attr("data-tracking-id"),
						Math.round(parseNumber(e.select(".offer-item-price").text()) / 100.0) / 10.0,
						parseNumber(e.select(".offer-item-area").text()),
						e.select(".offer-item-title").text(),
						e.select(".pull-right").text(),
						type,
						city,
						district
				);
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
		model.addAttribute("types", typeInterface.findAll());
		model.addAttribute("cities", cityInterface.findAll());
		model.addAttribute("districts", districtInterface.findAll());
		Offer averageOffer = offerService.getAverageOffer();
		model.addAttribute("averageOffer", averageOffer);
		List<Offer> offers = offerService.markCheapOffers(offerInterface.findAll(), averageOffer);
		model.addAttribute("offers", offers);
		return "home";
	}

	@GetMapping("/details/{id}")
	public String priceHistory(@PathVariable long id, Model model) {
		List<Price> prices = priceInterface.getAllByOfferId(id);
		Offer offer = offerInterface.getOne(id);
		prices.add(new Price(offer.getPrice(), offer.getPricePerM2(), offer.getLocalDateTime()));
		model.addAttribute("prices", prices);
		model.addAttribute("offer", offer);
		return "details";
	}

}
