package com.cts.gto.techngage.smartride.backend.dao.vo;


public class Route {
	int route_id;
	String route_nm;
	int startStop;
	int endStop;
	int deprtTm;
	int estm_arv_tm;
	char is_return;
	
	public String toString1() {
		String str="{"
				+"\"route_id\":\""+route_id +"\""
				+", \"route_nm\":\""+route_nm +"\""
				+", \"deprtTm\":\""+deprtTm +"\""				
				+'}';
		
		return str;
	}
	
	public String toString() {
		String str="route_id="+route_id+'|'+"route_nm="+route_nm;
		
		return str;
	}
	
	
	public int getRoute_id() {
		return route_id;
	}
	public void setRoute_id(int route_id) {
		this.route_id = route_id;
	}
	public String getRoute_nm() {
		return route_nm;
	}
	public void setRoute_nm(String route_nm) {
		this.route_nm = route_nm;
	}
	public int getStartStop() {
		return startStop;
	}
	public void setStartStop(int startStop) {
		this.startStop = startStop;
	}
	public int getEndStop() {
		return endStop;
	}
	public void setEndStop(int endStop) {
		this.endStop = endStop;
	}
	public int getDeprtTm() {
		return deprtTm;
	}
	public void setDeprtTm(int deprtTm) {
		this.deprtTm = deprtTm;
	}
	public int getEstm_arv_tm() {
		return estm_arv_tm;
	}
	public void setEstm_arv_tm(int estm_arv_tm) {
		this.estm_arv_tm = estm_arv_tm;
	}
	public char getIs_return() {
		return is_return;
	}
	public void setIs_return(char is_return) {
		this.is_return = is_return;
	}
}