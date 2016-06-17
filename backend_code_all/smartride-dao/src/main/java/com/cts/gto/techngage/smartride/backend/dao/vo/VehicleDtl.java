package com.cts.gto.techngage.smartride.backend.dao.vo;

public class VehicleDtl {
	int vehicle_id;
	String reg_num;
	String vehicle_name;
	int seat_count;
	int cost_per_km;

	public int getVehicle_id() {
		return vehicle_id;
	}

	public void setVehicle_id(int vehicle_id) {
		this.vehicle_id = vehicle_id;
	}

	public String getReg_num() {
		return reg_num;
	}

	public void setReg_num(String reg_num) {
		this.reg_num = reg_num;
	}

	public String getVehicle_name() {
		return vehicle_name;
	}

	public void setVehicle_name(String vehicle_name) {
		this.vehicle_name = vehicle_name;
	}

	public int getSeat_count() {
		return seat_count;
	}

	public void setSeat_count(int seat_count) {
		this.seat_count = seat_count;
	}

	public int getCost_per_km() {
		return cost_per_km;
	}

	public void setCost_per_km(int cost_per_km) {
		this.cost_per_km = cost_per_km;
	}

	

}
