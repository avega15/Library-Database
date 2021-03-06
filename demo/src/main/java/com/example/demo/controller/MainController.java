package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Member.Member;
import com.example.demo.model.Bibliography.Bibliography;
import com.example.demo.model.Item.Item;

@RestController
public class MainController {

	@Autowired
	private RestTemplate restTemplate;

	// Gets API key value which is stored in application.properties
	@Value("${my.key}")
	private String key;
	
	//Allows toggle on title and date order
	private boolean titleOrder = false;
	private boolean dateOrder = false;

	private static String url = "https://api-na.hosted.exlibrisgroup.com/almaws/v1/conf/sets/15528919710002976/members";
	
	//Model
	ArrayList<Bibliography> bibs = new ArrayList<Bibliography>();

	@GetMapping("/main")
	public ModelAndView getBib(ModelAndView modelAndView) {
		//Model
		bibs = callAPI();

		modelAndView.addObject("bibs", bibs);
		modelAndView.setViewName("main");

		return modelAndView;
	}

	@RequestMapping(value = "/titleSort")
	public ModelAndView titleSort(ModelAndView modelAndView) {
		modelAndView.addObject("bibs", bibs);
		modelAndView.setViewName("main");
		
		sortByTitle(bibs);
		titleOrder = !titleOrder;
		dateOrder = false;
		
		return modelAndView;
	}

	@RequestMapping(value = "/dateSort")
	public ModelAndView dateSort(ModelAndView modelAndView) {
		modelAndView.addObject("bibs", bibs);
		modelAndView.setViewName("main");
		
		sortByDate(bibs);
		dateOrder = !dateOrder;
		titleOrder = false;
		
		return modelAndView;
	}

	public ArrayList<Bibliography> sortByTitle(ArrayList<Bibliography> listings) {
		// Sorts an ArrayList of Objects alphabetically based on getTitle()
		Collections.sort(listings, new Comparator<Bibliography>() {
			public int compare(Bibliography v1, Bibliography v2) {
				return v1.getTitle().compareTo(v2.getTitle());
			}
		});
		if(titleOrder) {
			Collections.reverse(listings);
		}
		
		
		return listings;
	}

	public ArrayList<Bibliography> sortByDate(ArrayList<Bibliography> listings) {
		// Sorts an ArrayList of Objects in ascending order by getDate...()
		Collections.sort(listings, new Comparator<Bibliography>() {
			public int compare(Bibliography v1, Bibliography v2) {
				return v1.getDate_of_publication().compareTo(v2.getDate_of_publication());
			}
		});
		if(dateOrder) {
			Collections.reverse(listings);
		}
		
		return listings;
	}

	private ArrayList<Bibliography> callAPI() {
		// Gets JSON link of every book
		ArrayList<String> link = getLinks();
		ArrayList<Bibliography> info = new ArrayList<Bibliography>();
		String linkURL;

		for (int i = 0; i < link.size(); i++) {
			linkURL = link.get(i);

			Item items = restTemplate.getForObject(
					linkURL + "?apikey=" + key + "&amp;format=json", Item.class);

			info.add(items.toBib());

		}

		return info;
	}

	// Gets all links from members JSON set
	private ArrayList<String> getLinks() {

		Member books = restTemplate.getForObject(
				url + "?apikey=" + key + "&amp;format=json", Member.class);
		
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
}
