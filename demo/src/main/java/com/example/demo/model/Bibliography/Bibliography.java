package com.example.demo.model.Bibliography;

public class Bibliography {
	String title;
	String author;
	String isbn;
	String date_of_publication;
	String call_number;
	
	public Bibliography() {
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getDate_of_publication() {
		return date_of_publication;
	}

	public void setDate_of_publication(String date_of_publication) {
		this.date_of_publication = date_of_publication;
	}

	public String getCall_number() {
		return call_number;
	}

	public void setCall_number(String call_number) {
		this.call_number = call_number;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (obj instanceof Bibliography) {
            return ((Bibliography) obj).title == title;
        }
        return false;
    }

}
