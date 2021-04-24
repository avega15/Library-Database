package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Data;
import com.example.demo.model.Listing;
import com.example.demo.model.Member;

@RestController
public class APIcontroller {

	@Autowired
	private RestTemplate restTemplate;
	
	private static String url = "https://api-na.hosted.exlibrisgroup.com/almaws/v1/conf/sets/15528919710002976/members";
	private static String key = "l7xx552b0dcc0d0f47e9a9f5da308caaa6de";

	//ArrayList<ArrayList<String>> bib = new ArrayList<ArrayList<String>>();
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
		
		//Sorts an ArrayList of Objects alphabetically based on getTitle()
		Collections.sort(listings, new Comparator<Listing>() {
			public int compare(Listing v1, Listing v2) {
				return v1.getTitle().compareTo(v2.getTitle());
			}
		});

		return listings;
	}

	public ArrayList<Listing> sortByDate(ArrayList<Listing> listings) {

		////Sorts an ArrayList of Objects in ascending order by getDate...()
		Collections.sort(listings, new Comparator<Listing>() {
			public int compare(Listing v1, Listing v2) {
				return v1.getDate_of_publication().compareTo(v2.getDate_of_publication());
			}
		});

		return listings;
	}
	
	//Gets all links from members
	private ArrayList<String> getLinks() {
		//Uses restTemplate to put JSON into a Member Object.
		//Member Object is a List of Link Object
		//Link object only contains link field, ignores other JSON
		Member books = restTemplate.getForObject(
				url + "?apikey=" + key + "&amp;format=json", Member.class);
		ArrayList<String> link = new ArrayList<String>();
		
		//Creates array of links
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
		
		//Gets JSON link of every book
		ArrayList<String> link = getLinks();
		
		String linkURL;
		for (int i = 0; i < link.size(); i++) {
			linkURL = link.get(i);

			//restTemplate puts JSON into a Data Object which 
			//is made up of a Bib_Data and Holding_Data object
			Data output = restTemplate.getForObject(
					linkURL + "?apikey=" + key + "&amp;format=json", Data.class);
			
			//Converts Data object into Listing Object.
			//Listing Object has all the JSON data we 
			//need in a single, convenient place.
			info.add(output.toListing());


		}
		return info;
	}

}