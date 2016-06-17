package com.cts.gto.techngage.smartride.backend.dao.vo;

public class RouteStopMap {
	int map_id;
	int route_id;
	int stop_id;
	int estm_arv_tm;
	int next_stop_dist;
	int fare_next_stop;
	public int getMap_id() {
		return map_id;
	}
	public void setMap_id(int map_id) {
		this.map_id = map_id;
	}
	public int getRoute_id() {
		return route_id;
	}
	public void setRoute_id(int route_id) {
		this.route_id = route_id;
	}
	public int getStop_id() {
		return stop_id;
	}
	public void setStop_id(int stop_id) {
		this.stop_id = stop_id;
	}
	public int getEstm_arv_tm() {
		return estm_arv_tm;
	}
	public void setEstm_arv_tm(int estm_arv_tm) {
		this.estm_arv_tm = estm_arv_tm;
	}
	public int getNext_stop_dist() {
		return next_stop_dist;
	}
	public void setNext_stop_dist(int next_stop_dist) {
		this.next_stop_dist = next_stop_dist;
	}
	public int getFare_next_stop() {
		return fare_next_stop;
	}
	public void setFare_next_stop(int fare_next_stop) {
		this.fare_next_stop = fare_next_stop;
	}

}
