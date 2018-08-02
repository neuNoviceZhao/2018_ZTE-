package Dijkstra;

public class Edge{
	
	private int startNode;
	private int endNode;
	private int bandWidth;
	private int bandWidthRest;
	
	public Edge(int startNode,int endNode,int bandWidth ) {
		this.startNode = startNode;
		this.endNode = endNode;
		this.bandWidth = bandWidth;
		this.bandWidthRest = bandWidth;
	}

	public void reset(){
		this.bandWidthRest = bandWidth;
	}
	
	public int getStartNode() {
		return startNode;
	}

	public void setStartNode(int startNode) {
		this.startNode = startNode;
	}

	public int getEndNode() {
		return endNode;
	}

	public void setEndNode(int endNode) {
		this.endNode = endNode;
	}

	public int getBandwidth() {
		return bandWidth;
	}

	public void setBandwidth(int bandwidth) {
		this.bandWidth = bandwidth;
	}

	public int getBandWidthRest() {
		return bandWidthRest;
	}

	public void setBandWidthRest(int bandWidthRest) {
		this.bandWidthRest = bandWidthRest;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.startNode+"->"+this.endNode+",bw"+bandWidth+",bwR"+bandWidthRest;
	}
	
	
	
	
}