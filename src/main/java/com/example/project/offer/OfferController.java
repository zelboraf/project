package com.example.project.offer;

import com.example.project.offer.Offer;
import com.example.project.offer.OfferInterface;
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
public class OfferController {

	private final OfferInterface offerInterface;

	public OfferController(OfferInterface offerInterface) {
		this.offerInterface = offerInterface;
	}

	private double parseNumber(String s) {
		return Double.parseDouble(s.replaceAll("[^\\d,]", "").replace(",", "."));
	}

	@RequestMapping("/get")
	public String getOffers(Model model) {
		try {
			String url = "https://www.otodom.pl/sprzedaz/mieszkanie/gdansk/srodmiescie/?search%5Bdist%5D=0&search%5Bdistrict_id%5D=28&search%5Bsubregion_id%5D=439&search%5Bcity_id%5D=40&search%5Border%5D=created_at_first%3Adesc&nrAdsPerPage=72";
			Document document = Jsoup.connect(url).get();

			int offerCounter = 0;
			List<Offer> newOffers = new ArrayList<>();
			List<Offer> updatedOffers = new ArrayList<>();
			Elements offerHtml = document.select("article");
			for (Element e : offerHtml) {
				Offer offer = new Offer();
				offer.setOfferId(e.attr("data-tracking-id"));
				offer.setArea(parseNumber(e.select(".offer-item-area").text()));
				offer.setPrice(parseNumber(e.select(".offer-item-price").text()) / 1000);
				DecimalFormat df = new DecimalFormat("#.#");
				offer.setPricePerM2(Double.valueOf(df.format(offer.getPrice() / offer.getArea())));
				offer.setDescription(e.select(".offer-item-title").text());
				offer.setSeller(e.select(".pull-right").text());

				if (offerInterface.existsByOfferId(offer.getOfferId())) {
					updatedOffers.add(offer);
				} else {
					newOffers.add(offer);
					offerInterface.save(offer);
				}
				offerCounter++;
			}
			model.addAttribute("offerCounter", offerCounter);
			model.addAttribute("updatedOffers", updatedOffers);
			model.addAttribute("newOffers", newOffers);
			return "get";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/")
	public String showOffers(Model model) {
		Offer avgOffer = new Offer();
		avgOffer.setArea(offerInterface.getAvgArea());
		avgOffer.setPrice(offerInterface.getAvgPrice());
		avgOffer.setPricePerM2(offerInterface.getAvgPricePerM2());
		model.addAttribute("avgOffer", avgOffer);

		List<Offer> offers = offerInterface.findAll();
		model.addAttribute("offers", offers);
		return "home";
	}

}
