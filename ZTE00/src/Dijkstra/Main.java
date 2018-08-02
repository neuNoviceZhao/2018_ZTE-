package Dijkstra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {
	private static List<Business> business = new LinkedList<Business>();
	private static int alternateRouteNum;
	private static int routeNum = 955;
	private static int businessNum = 1000;
	private static Map <Key,Edge> edge1 = new HashMap<Key,Edge>();
	private static Map <Key,Edge> edge2 = new HashMap<Key,Edge>();//
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		long t1 = System.currentTimeMillis();
		String [] graAndReq = read("C:/Users/zyf/Desktop/grditopoAndRequest.txt", null);
                dataProgress(graAndReq);
                long t2 = System.currentTimeMillis();
		System.out.println("dataP:"+(t2-t1));//处理读入数据的计时程序
		GeneticAlgorithm ga = new GeneticAlgorithm(edge2, business);
		ga.calculate();
		String result [] = ga.printResult();
		String select [] = ga.printSelect();
		write("C:/Users/zyf/Desktop/result.txt", result, false);
		write("C:/Users/zyf/Desktop/select.txt", select, false);
       		long t3 = System.currentTimeMillis();
                System.out.println("Time:"+(t3-t2)/1000+"s");//整个求解计时程序
		
	}
	
	
        public static void dataProgress(String[] graAndReq){//处理读入的文件
	      
		List<String[]> data = new LinkedList<String[]>();//存放数据集
		String sp[];
		for (int i = 0; i < graAndReq.length; i++) {
			sp = graAndReq[i].split(" ");
			data.add(sp);
		}
		
		
		for (int i = 0; i < routeNum; i++) {//存入边信息
			int start = Integer.parseInt(data.get(i+1)[0]);
			int end = Integer.parseInt(data.get(i+1)[1]);
			Key k1 = new Key(start, end);
			Key k2 = new Key(end, start);
			Edge e1 = new Edge(start,end,Integer.parseInt(data.get(i+1)[2]));
			Edge e2 = new Edge(end,start,Integer.parseInt(data.get(i+1)[2]));
			
			edge1.put(k1, e1);
			edge1.put(k2, e2);
		}
		
		alternateRouteNum = Integer.parseInt(data.get(routeNum+1)[1]);
                int idNum = 0;
	      
		for (int i = routeNum+2; i < data.size(); i = i+alternateRouteNum+1) {
			List<int[]> route = new ArrayList<int[]>();
			List<StringBuilder> routeS = new ArrayList<StringBuilder>();
			for (int j = 0; j < alternateRouteNum; j++) {
				StringBuilder s = new StringBuilder(); 
				int r [] = new int[data.get(i+j+1).length];
				for (int k = 0; k < data.get(i+j+1).length; k++) {
					r[k] = Integer.parseInt(data.get(i+j+1)[k]);
					if(k < data.get(i+j+1).length - 1){
						s.append(r[k]).append(" ");
					}else{
						s.append(r[k]);
					}
				}
				routeS.add(s);
				route.add(r);
			}
			business.add(new Business(idNum++, Integer.parseInt(data.get(i)[1]), route,routeS));
		}
		
		for (int i = 0; i < business.size(); i++) {
			for (int j = 0; j < business.get(i).getRoute().size(); j++) {
				int r[] = business.get(i).getRoute().get(j);
				for (int k = 0; k < r.length-1; k++) {
					Key key = new Key(r[k], r[k+1]);
					Edge e = edge1.get(key);
					edge2.put(key, e);
				}
			}
		}
		
	}
	
	public static List<Double> getScore(int [] selectRoute){
		
		for (int i = 0; i < businessNum; i++) {
			int selcetR[] = business.get(i).getRoute().get(selectRoute[i]);
			int requestBandWidth = business.get(i).getRequestBandwidth();
			for (int j = 0; j < selcetR.length-1; j++) {
				Key k = new Key(selcetR[j], selcetR[j+1]);
				Edge e = edge1.get(k);
				e.setBandWidthRest(e.getBandWidthRest() - requestBandWidth);
				edge1.put(k, e);
		    }
		
	   }
		List <Double> maxRateRoute = new LinkedList<Double>(); 
		maxRateRoute.add((double)0);
		maxRateRoute.add((double)0);
		maxRateRoute.add((double)0);
		double maxR = 0;
		for(Key key:edge1.keySet()){  
		    Edge e = edge1.get(key);
			double rate = 1-(double)e.getBandWidthRest()/e.getBandwidth(); 
			if(rate > maxR && rate!= 1.0){
				maxRateRoute.set(0,(double)key.start);
				maxRateRoute.set(1,(double)key.end);
				maxRateRoute.set(2,rate);
				maxR = rate;
			}
	
		}  
		return maxRateRoute;
		
	}
	/**
	 * 读数据
	 * @param filePath
	 * @param spec
	 * @return
	 */
	public static String[] read(final String filePath, final Integer spec)  {   
		File file = new File(filePath);
		List<String> lines = new LinkedList<String>();
		BufferedReader br = null;
		FileReader fb = null;
		try
		{
		    fb = new FileReader(file);
		    br = new BufferedReader(fb);

		    String str = null;
		    int index = 0;
		    while (((spec == null) || index++ < spec) && (str = br.readLine()) != null)
		    {
			lines.add(str);
		    }
		}
		catch (IOException e)
		{
		    e.printStackTrace();
		}
		finally
		{
				try {
					if(br != null)br.close();
					if(fb != null)fb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

		}
		return lines.toArray(new String[lines.size()]);
        }
	
	 public static int write(final String filePath, final String[] contents, final boolean append){
	        File file = new File(filePath);
	        if (contents == null)
	        {
	            System.out.println("file [" + filePath + "] invalid!!!");
	            return 0;
	        }


	        FileWriter fw = null;
	        BufferedWriter bw = null;
	        try
	        {
	           
	            fw = new FileWriter(file, append);
	            bw = new BufferedWriter(fw);
	            for (String content : contents)
	            {
	                if (content == null)
	                {
	                    continue;
	                }
	                bw.write(content);
	                bw.newLine();
	            }
	        }
	        catch (IOException e)
	        {
	            e.printStackTrace();
	            return 0;
	        }
	        finally
	        {
	        	try {
					if(bw != null)bw.close();
					if(fw != null)fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	           
	        }

	        return 1;
	}
}
