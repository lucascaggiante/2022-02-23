package it.polito.tdp.yelp.db;

import it.polito.tdp.yelp.model.Business;

public class TestDao {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		YelpDao dao = new YelpDao();
		System.out.println(String.format("Users: %d\nBusiness: %d\nReviews: %d\n", 
				dao.getAllUsers().size(), dao.getAllBusiness().size(), dao.getAllReviews().size()));
		
		YelpDao dao1 = new YelpDao();
		
		
	}

}
