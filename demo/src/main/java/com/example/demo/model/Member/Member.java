package com.example.demo.model.Member;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Member {
	public List<Link> member;
	
	public Member() {
		
	}
	
	public void setMember(List<Link> member) {
		this.member = member;
	}
	
	@Override
	public String toString() {
		return member.toString();
		
	}
}