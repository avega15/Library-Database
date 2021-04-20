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
public class ApiController {

	@Autowired
	private RestTemplate restTemplate;
	private static String url = "https://api-na.hosted.exlibrisgroup.com/almaws/v1/conf/sets/1327214400000521/members";
	private static String key = "l8xxd676f2aa10c2482e95457d2f264bd8d6";

	ArrayList<String> link = new ArrayList<String>();
	ArrayList<ArrayList<String>> bib = new ArrayList<ArrayList<String>>();
	ArrayList<Data> fin = new ArrayList<Data>();

	@GetMapping("/main")
	public ModelAndView getBib(ModelAndView modelAndView) {
		modelAndView.addObject("bibs", bib);
		modelAndView.setViewName("main");

		return modelAndView;
	}
	
	@RequestMapping(value="/titleSort")
	public ModelAndView titleSort(ModelAndView modelAndView) {
		modelAndView.addObject("bibs", bib);
		modelAndView.setViewName("main");
		sortByTitle(bib);

		return modelAndView;
	}
	
	@RequestMapping(value="/dateSort")
	public ModelAndView dateSort(ModelAndView modelAndView) {
		modelAndView.addObject("bibs", bib);
		modelAndView.setViewName("main");
		sortByDate(bib);

		return modelAndView;
	}

	@PostConstruct
	public ArrayList<ArrayList<String>> getLinks() {
		Member books = restTemplate.getForObject(url + "?apikey=" + key + "&amp;format=json", Member.class);

		String book = books.toString();
		String[] links = book.split(", ", -1);

		for (int i = 0; i < links.length; i++) {
			links[i] = links[i].replace("[", "");
			links[i] = links[i].replace("]", "");
			link.add(links[i]);
		}

		boolean success;
		String linkURL;
		int count = link.size();
		for (int i = 0; i < link.size(); i++) {
			linkURL = link.get(i);
			success = false;

			while (!success) {
				try {
					Data output = restTemplate.getForObject(linkURL + "?apikey=" + key + "&amp;format=json",
							Data.class);

					// System.out.println(linkURL + "?apikey=" + key + "&amp;format=json");
					// System.out.println(output.getBib_Data());
					bib.add(output.toArrayList());
					// fin.add(output);

					success = true;
					System.out.println(count--);
				} catch (HttpClientErrorException e) {
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}

			}
		}

		return bib;
	}

	public ArrayList<ArrayList<String>> sortByTitle(ArrayList<ArrayList<String>> bib) {

		Collections.sort(bib, new Comparator<List<String>>() {
			@Override
			public int compare(List<String> a, List<String> b) {
				return a.get(0).compareTo(b.get(0));
			}
		});

		return bib;
	}

	public ArrayList<ArrayList<String>> sortByDate(ArrayList<ArrayList<String>> bib) {

		Collections.sort(bib, new Comparator<List<String>>() {
			@Override
			public int compare(List<String> a, List<String> b) {
				return a.get(3).compareTo(b.get(3));
			}
		});

		return bib;
	}

}
