package com.example.demo.model.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Link {
	
	//String id;
	//String description
	String link;
	
	public Link() {	
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	@Override
	public String toString() {
		return link;
	}
	
}
