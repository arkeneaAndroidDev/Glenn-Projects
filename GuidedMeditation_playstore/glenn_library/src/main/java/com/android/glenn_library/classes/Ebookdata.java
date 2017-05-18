package com.android.glenn_library.classes;

public class Ebookdata {
String book_name;
String book_url;
boolean selected;
public Ebookdata(String book_name, String book_url , boolean selected) {
	super();
	this.book_name = book_name;
	this.book_url = book_url;
	this.selected = selected;
}
public boolean isSelected() {
	return selected;
}
public void setSelected(boolean selected) {
	this.selected = selected;
}
public String getBook_name() {
	return book_name;
}
public void setBook_name(String book_name) {
	this.book_name = book_name;
}
public String getBook_url() {
	return book_url;
}
public void setBook_url(String book_url) {
	this.book_url = book_url;
}

}
