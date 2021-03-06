package com.example.demo.model.Item;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Bib_Data {

	String title;
	String author;
	String isbn;
	String date_of_publication;
	
	public Bib_Data() {
	}
	
	
	public String getAuthor() {
		return cleanAuthor(author);
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return cleanISBN(isbn);
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getDate_of_publication() {
		return cleanDate(date_of_publication);
	}

	public void setDate_of_publication(String date_of_publication) {
		this.date_of_publication = date_of_publication;
	}
	
	public String getTitle() {
		return cleanTitle(title);
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String toString() {
		if(isbn == null) {
			isbn = "N/A";
		}
		return title + "///" + author + "///" + isbn + "///" + date_of_publication;
	}
	public ArrayList<String> toArrayList() {
		ArrayList<String> arr = new ArrayList<String>();		
		
		arr.add(cleanTitle(title));
		arr.add(cleanAuthor(author));
		arr.add(cleanISBN(isbn));
		arr.add(cleanDate(date_of_publication));
		return arr;
	}
	
	//Cleaning data
	public String cleanTitle(String title) {
		title = title.substring(0, title.length() - 1);
		boolean isSpace = Character.isWhitespace(title.charAt(title.length() - 1));
		if(isSpace) {
			title = title.trim();
		}
		return title;
	}
	
	public String cleanAuthor(String author) {
		if(author.charAt(author.length() - 1) == ',' ||
				author.charAt(author.length() - 1) == '.') {
			author = author.substring(0, author.length() - 1);
			
		}
		
		if(author.contains("(") || author.contains(")")) {
			author = author.replaceAll("\\(.*?\\)", "");
			return author;
		}
		
		String[] name = author.split(", ");
		return name[0] + " " + name[1];
	}
	
	public String cleanISBN (String isbn) {
		if(isbn != null) {
			isbn= isbn.replaceAll("[^0-9]","");
			return isbn;
		}else {
			return "N/A";
		}
		
	}
	
	public String cleanDate(String date_of_publication) {
		date_of_publication= date_of_publication.replaceAll("[^0-9]","");
		return date_of_publication;
	}
	
	
}
