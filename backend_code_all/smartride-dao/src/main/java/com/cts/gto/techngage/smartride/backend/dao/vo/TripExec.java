package com.cts.gto.techngage.smartride.backend.dao.vo;

import java.util.Date;

public class TripExec {

	int trip_id;
	int route_id;
	int vehicle_id;
	Date trip_date;
	int next_stop;
	char trip_status;
	public void setTrip_date(Date trip_date) {
		this.trip_date = trip_date;
	}
	public void setTrip_status(char trip_status) {
		this.trip_status = trip_status;
	}
	public int getTrip_id() {
		return trip_id;
	}
	public void setTrip_id(int trip_id) {
		this.trip_id = trip_id;
	}
	public int getRoute_id() {
		return route_id;
	}
	public void setRoute_id(int route_id) {
		this.route_id = route_id;
	}
	public int getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(int vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	
	public int getNext_stop() {
		return next_stop;
	}
	public void setNext_stop(int next_stop) {
		this.next_stop = next_stop;
	}

	
}
