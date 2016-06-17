package com.cts.gto.techngage.smartride.backend.dao.vo;

import java.util.Date;

public class Booking {
	int booking_id;
	String booking_num;
	int psngr_id;
	int route_id;
	char booking_type;
	int onboard_stop;
	int offboard_stop;
	Date pickup_dt_tm;
	public int getBooking_id() {
		return booking_id;
	}
	public void setBooking_id(int booking_id) {
		this.booking_id = booking_id;
	}
	public String getBooking_num() {
		return booking_num;
	}
	public void setBooking_num(String booking_num) {
		this.booking_num = booking_num;
	}
	public int getPsngr_id() {
		return psngr_id;
	}
	public void setPsngr_id(int psngr_id) {
		this.psngr_id = psngr_id;
	}
	public int getRoute_id() {
		return route_id;
	}
	public void setRoute_id(int route_id) {
		this.route_id = route_id;
	}
	public char getBooking_type() {
		return booking_type;
	}
	public void setBooking_type(char booking_type) {
		this.booking_type = booking_type;
	}
	public int getOnboard_stop() {
		return onboard_stop;
	}
	public void setOnboard_stop(int onboard_stop) {
		this.onboard_stop = onboard_stop;
	}
	public int getOffboard_stop() {
		return offboard_stop;
	}
	public void setOffboard_stop(int offboard_stop) {
		this.offboard_stop = offboard_stop;
	}
	public Date getPickup_dt_tm() {
		return pickup_dt_tm;
	}
	public void setPickup_dt_tm(Date pickup_dt_tm) {
		this.pickup_dt_tm = pickup_dt_tm;
	}
	public int getFare_blocked() {
		return fare_blocked;
	}
	public void setFare_blocked(int fare_blocked) {
		this.fare_blocked = fare_blocked;
	}
	public int getFare_charged() {
		return fare_charged;
	}
	public void setFare_charged(int fare_charged) {
		this.fare_charged = fare_charged;
	}
	public int getFare_refund() {
		return fare_refund;
	}
	public void setFare_refund(int fare_refund) {
		this.fare_refund = fare_refund;
	}
	public char getBooking_status() {
		return booking_status;
	}
	public void setBooking_status(char booking_status) {
		this.booking_status = booking_status;
	}
	int fare_blocked;
	int fare_charged;
	int fare_refund;
	char booking_status;

}
