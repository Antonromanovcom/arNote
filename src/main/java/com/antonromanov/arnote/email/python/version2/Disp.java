package com.antonromanov.arnote.email.python.version2;

public class Disp {

	private static void print(BuildingType building) {
		System.out.println("Building Info: " +
				building.getBuildingId() + " " +
				building.getBuildingName() + " " +
				building.getBuildingAddress());
	}

	/**
	 * Create three building objects by calling the create() method of
	 * the factory.
	 */

	public void $$$doit() {
		BuildingFactory factory = new BuildingFactory();
		print(factory.create("BUILDING-A", "100 WEST MAIN", "1"));
		print(factory.create("BUILDING-B", "110 WEST MAIN", "2"));
		print(factory.create("BUILDING-C", "120 WEST MAIN", "3"));

	}


}
