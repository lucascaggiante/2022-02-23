package it.polito.tdp.yelp.model;

public class Adiacenze {

	private Review review1;
	private Review review2;
	private int peso;
	public Adiacenze(Review review1, Review review2, int peso) {
		super();
		this.review1 = review1;
		this.review2 = review2;
		this.peso = peso;
	}
	public Review getReview1() {
		return review1;
	}
	public void setReview1(Review review1) {
		this.review1 = review1;
	}
	public Review getReview2() {
		return review2;
	}
	public void setReview2(Review review2) {
		this.review2 = review2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	
}
