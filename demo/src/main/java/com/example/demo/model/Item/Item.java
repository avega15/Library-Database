package com.example.demo.model.Item;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

import com.example.demo.model.Bibliography.Bibliography;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Item {
	
	@JsonProperty("bib_data")
	Bib_Data bib_data;
	@JsonProperty("holding_data")
	Holding_Data holding_data;
	
	public Item() {
	}
	
	public Bib_Data getBib_Data() {
		return bib_data;
	}
	
	public void setBibValue(Bib_Data bib_data) {
		this.bib_data = bib_data;
	}
	
	public Holding_Data getHolding_Data() {
		return holding_data;
	}
	
	public void setHolding_Data(Holding_Data holding_data) {
		this.holding_data= holding_data;
	}
	
	public String toString() {
		return bib_data.toString() + "///" + holding_data;
	}

	public ArrayList<String> toArrayList() {
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(bib_data.toArrayList());
		list.add(holding_data.toString());
		
		return list;
	}
	
	public Item combine() {
		
		return null;
	}

	public Bibliography toBib() {
		Bibliography list = new Bibliography();
		list.setTitle(bib_data.getTitle());
		list.setAuthor(bib_data.getAuthor());
		list.setIsbn(bib_data.getIsbn());
		list.setDate_of_publication(bib_data.getDate_of_publication());
		list.setCall_number(holding_data.getCall_number());
		
		return list;
	}
	
}
