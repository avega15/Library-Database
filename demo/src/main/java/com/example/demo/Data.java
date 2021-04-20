package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Data {
	
	@JsonProperty("bib_data")
	Bib_Data bib_data;
	@JsonProperty("holding_data")
	Holding_Data holding_data;
	
	
	
	public Data() {
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
	
	public Data combine() {
		
		return null;
	}

	@SuppressWarnings("null")
	public Listing toListing(Data output) {
		Listing list = null;
		list.setTitle(output.bib_data.getTitle());
		list.setAuthor(output.bib_data.getAuthor());
		list.setIsbn(output.bib_data.getIsbn());
		list.setDate_of_publication(output.bib_data.getDate_of_publication());
		list.setCall_number(output.holding_data.getCall_number());
		
		return list;
	}
	
}
