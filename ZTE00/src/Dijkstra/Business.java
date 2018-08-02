package Dijkstra;

import java.util.List;

public class Business {

	private int businessId;
	private int requestBandwidth;
	private  List<int[]> route;
	private  List<StringBuilder> routeS;
	
	public Business(int businessId,int requestBandwidth,List<int[]> route,List<StringBuilder> routeS) {
		this.businessId = businessId;
		this.requestBandwidth = requestBandwidth;
		this.route = route;
		this.routeS = routeS;
	}
     
	public int getBusinessId() {
		return businessId;
	}

	public int getRequestBandwidth() {
		return requestBandwidth;
	}
	
	public List<int[]> getRoute() {
		return route;
	}

	public  List<StringBuilder> getRouteS() {
		return routeS;
	}

	public void setRouteS(List<StringBuilder> routeS) {
		this.routeS = routeS;
	}
	
	

}
