package com.example.demo.model.Item;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Holding_Data {
	@JsonProperty("call_number")
	String call_number;

	public Holding_Data() {
	}
	
	public String getCall_number() {
		return call_number;
	}

	public void setCall_number(String call_number) {
		this.call_number = call_number;
	}
	
	public String toString() {
		return call_number;
	}
	
}
