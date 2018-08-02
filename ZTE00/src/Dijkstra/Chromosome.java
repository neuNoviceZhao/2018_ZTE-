package Dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Chromosome implements Cloneable{
	private  List<Business> business = new LinkedList<Business>();
	private static int businessNum = 1000;
	private  Map <Key,Edge> edges = new HashMap<Key,Edge>();
	private  int gene[] ;
	private  List<Double> routeAndBestUseage;//链路及其利用率
	private static List<Integer> mutationIndex = new ArrayList<Integer>();//变异位置
	
        public Chromosome(Map <Key,Edge> edges,List<Business> business) {
		this.edges = edges;
		this.gene = creatGene(businessNum);
		this.business = business;
		this.routeAndBestUseage = getScore();
	}
   
        private int [] creatGene(int size){
    	    int createGene[] = new int[size];
    	    for (int i = 0; i < size; i++) {
	 		createGene[i] = (int)(Math.random()*3);
            }
	    return createGene;
	}
    
    
        public  Chromosome clone(){
	        if(this == null || this.gene == null){
	        	return null;
	        }
	        Chromosome copy = new Chromosome(edges,business);

	        for (int i = 0; i < this.gene.length; i++) {
	        	copy.gene[i] = this.gene[i];
	        }
	        copy.routeAndBestUseage.clear();
	        copy.routeAndBestUseage.addAll(this.routeAndBestUseage);
	        return copy;
        }
	
	public  List<Chromosome> crossover(Chromosome chro1){
		if(chro1 == null || this == null){
			return null;
		}
		if(chro1.gene == null || this.gene == null){
			return null;
		}
		if(chro1.gene.length != this.gene.length){
			return null;
		}
		Chromosome c1 = chro1.clone();
		Chromosome c2 = this.clone();
	
		int a = (int)(Math.random()*businessNum);
		int b = (int)(Math.random()*businessNum);
		int start = a > b ? b : a;
		int end = a > b ? a : b;

		for (int i = start;i <= end;i++){
			int temp = c2.gene[i];
			c2.gene[i] = c1.gene[i];
			c1.gene[i] = temp;
		}
		c1.setRouteAndBestUseage();
		c2.setRouteAndBestUseage();
	
		List<Chromosome> list =  new LinkedList<Chromosome>();
		list.add(c1);
		list.add(c2);
		return list;
	}

	public void mutation(int num){
		int range = 1000 / num;
		//每次与上次变异位置不同
		for (int i = 0; i < num; i++) {
			int index = (int)( Math.random() * range) + range * i;//将1000个位置分成十份，每一份上一个变异点
			//int index = (int)( Math.random() * 1000 );//每一百个点
			if(mutationIndex.size() < num){
				mutationIndex.add(index);
			}else{
				if(mutationIndex.contains(index)){
					i = i - 1;
					continue;
				}else{
					mutationIndex.add(i+num, index);
				}
			}
			int number = (int)((3*Math.random()));
			//int number = 2;
			gene[index] = number;

		} 
		if(mutationIndex.size() == 2*num){
			mutationIndex = new ArrayList<Integer>(mutationIndex.subList(num, 2*num));
		}
		this.setRouteAndBestUseage();//重置分数
	}
	
	//算链路最大带宽占用率
	public List<Double> getScore(){
       
		for (int i = 0; i < businessNum; i++) {
			int selcetR[] = business.get(i).getRoute().get(gene[i]);
			int requestBandWidth = business.get(i).getRequestBandwidth();
			for (int j = 0; j < selcetR.length-1; j++) {
				Key k = new Key(selcetR[j], selcetR[j+1]);
				Edge e = edges.get(k);
				e.setBandWidthRest(e.getBandWidthRest() - requestBandWidth);
				edges.put(k, e);
		    }
		
	        }
		
		List <Double> maxRateRoute = new LinkedList<Double>(); 
		maxRateRoute.add((double)0);
		maxRateRoute.add((double)0);
		maxRateRoute.add((double)0);
		double maxR = 0;
		for(Key key:edges.keySet()){  
		    Edge e = edges.get(key);
			double rate = 1-(double)e.getBandWidthRest()/e.getBandwidth(); 
			e.reset();//重置
			edges.put(key, e);
			if(rate > maxR && rate!= 1.0){
				maxRateRoute.set(0,(double)key.start);
				maxRateRoute.set(1,(double)key.end);
				maxRateRoute.set(2,rate);
				maxR = rate;
			}
		}  
	
		return maxRateRoute;	
		
	}
	
	public int[] getGene() {
		return gene;
	}

	public void setRouteAndBestUseage() {
		this.routeAndBestUseage = getScore();
	}

	public List<Double> getRouteAndBestUseage() {
		return routeAndBestUseage;
	}

	@Override
	public boolean equals(Object obj) {
		Chromosome chro = (Chromosome)obj;
		if(this.routeAndBestUseage.get(2) == chro.getRouteAndBestUseage().get(2)){
			return true;
		}else{
			return false;
		}
	}
	

}
