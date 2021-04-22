package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class APIcontroller {

	@Autowired
	private RestTemplate restTemplate;
	private static String url = "https://api-na.hosted.exlibrisgroup.com/almaws/v1/conf/sets/15528919710002976/members";
	private static String key = "l7xx552b0dcc0d0f47e9a9f5da308caaa6de";

	ArrayList<ArrayList<String>> bib = new ArrayList<ArrayList<String>>();
	ArrayList<Listing> listings = new ArrayList<Listing>();
	
	@GetMapping("/main")
	public ModelAndView getBib(ModelAndView modelAndView) {
		listings = callAPI();
		modelAndView.addObject("bibs", listings);
		modelAndView.setViewName("main");

		return modelAndView;
	}

	@RequestMapping(value = "/titleSort")
	public ModelAndView titleSort(ModelAndView modelAndView) {
		modelAndView.addObject("bibs", listings);
		modelAndView.setViewName("main");
		sortByTitle(listings);

		return modelAndView;
	}

	@RequestMapping(value = "/dateSort")
	public ModelAndView dateSort(ModelAndView modelAndView) {
		modelAndView.addObject("bibs", listings);
		modelAndView.setViewName("main");
		sortByDate(listings);

		return modelAndView;
	}

	public ArrayList<Listing> sortByTitle(ArrayList<Listing> listings) {

		Collections.sort(listings, new Comparator<Listing>() {
			public int compare(Listing v1, Listing v2) {
				return v1.getTitle().compareTo(v2.getTitle());
			}
		});

		return listings;
	}

	public ArrayList<Listing> sortByDate(ArrayList<Listing> listings) {

		Collections.sort(listings, new Comparator<Listing>() {
			public int compare(Listing v1, Listing v2) {
				return v1.getDate_of_publication().compareTo(v2.getDate_of_publication());
			}
		});

		return listings;
	}
	private ArrayList<String> getLinks() {
		Member books = restTemplate.getForObject(url + "?apikey=" + key + "&amp;format=json", Member.class);
		ArrayList<String> link = new ArrayList<String>();
		
		String book = books.toString();
		String[] links = book.split(", ", -1);

		for (int i = 0; i < links.length; i++) {
			links[i] = links[i].replace("[", "");
			links[i] = links[i].replace("]", "");
			link.add(links[i]);
		}

		return link;
	}
	private ArrayList<Listing> callAPI() {
		ArrayList<Listing> info = new ArrayList<Listing>();
		
		ArrayList<String> link = getLinks();
		String linkURL;
		for (int i = 0; i < link.size(); i++) {
			linkURL = link.get(i);

			Data output = restTemplate.getForObject(
					linkURL + "?apikey=" + key + "&amp;format=json", Data.class);

			info.add(output.toListing());


		}
		return info;
	}

}
