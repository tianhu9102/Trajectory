package cn.les.kghj;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author DUYUE
 * Time:2017-09-29
 * 功能：基于分段的聚类算法
 * 参考：http://blog.csdn.net/jsc9410/article/details/51008444
 */
public class TraClus {

	//第一步：航迹分段：特征值选取
	public void partition() {
	
	}
	
	//第二步：航迹重组：密度聚类
	public void group(){
		
	}
	
	/**
	 * 根据输入的一条航迹，输出对应的特征点航迹 CharacterPoints
	 * @param TRi
	 * @return
	 */
	public Map<Integer,Track> getCP(Map<Integer,Track> TRi){
		//选取特征点的航迹
		Map<Integer,Track> CPi = new HashMap<Integer, Track>();
		CPi.put(0, TRi.get(0));
		int temp=0;
		
		//初始点坐标
		double x0 =TRi.get(0).getX();
		double y0=TRi.get(0).getY();
		
		//当前点坐标、L(H)和L(D\H)
		double x;
		double y;
		double Lh;
		double Ldh;
		
		for(int i=1;i<TRi.size()-1;i++){
			//当前点坐标
			x = TRi.get(i).getX();
			y = TRi.get(i).getY();
			
			//线段Li(初始点与当前点构成，随着i的变化而变化)
			Map<Integer,Track> Li = new HashMap<Integer, Track>();
			Li.put(0, TRi.get(0));
			Li.put(1, TRi.get(i));
			
			//线段Li的L(H)
			double xx0 = Math.sqrt( Math.pow(x-x0, 2)+Math.pow(y-y0, 2) );
			Lh = Logarithm.log(xx0 , 2);
			
			double dvert=0;
			double dtheta=0;
			//线段Lj集合(初始点与当前点之间由相邻点构成的线段集合)
			for(int j=0;j<i;j++){
				
				Map<Integer,Track> Lj = new HashMap<Integer, Track>();
				Lj.put(0, TRi.get(j));
				Lj.put(1, TRi.get(j+1));
				
				Map<Integer,Double> distance = dist(Li, Lj);
				dvert = dvert + distance.get(0);
				dtheta = dtheta + distance.get(2);
			}
			Ldh = Logarithm.log(dtheta , 2)+Logarithm.log(dvert , 2);
			
			if (Lh+Ldh<=xx0) {
				temp++;
				CPi.put(temp, TRi.get(i));
			}
		}
		return CPi;
	}
	
    /**
     * 线段  距离函数
     * @param Li
     * @param Lj
     */
	static Map<Integer,Double> dist(Map<Integer,Track> Li, Map<Integer,Track> Lj){
		
		//Li线段起始、结束点坐标
		double x0 = Li.get(0).getX();
		double y0 = Li.get(0).getY();
		double x1 = Li.get(Li.size()-1).getX();
		double y1 = Li.get(Li.size()-1).getY();
		
		double k0 = (y1-y0)/(x1-x0);
		double b0 = y0 - k0*x0;
		
		//Lj线段起始、结束点坐标
		double x2 = Lj.get(0).getX();
		double y2 = Lj.get(0).getY();
		double x3 = Lj.get(Lj.size()-1).getX();
		double y3 = Lj.get(Lj.size()-1).getY();
		
		double k1 = (y3-y2)/(x3-x2);
		double b1 = y2 - k1*x2;
		double tanTheta = Math.abs((k1-k0)/(1+k0*k1));
		
		//过Lj的起始点S且垂直于Li的线段参数
		double k2 = -1/k0;
		double b2 = y2 - k2*x2;
		
		//过Lj的结束点E且垂直于Li的线段参数
		double k3 = -1/k0;
		double b3 = y3 - k3*x3;
		
		//Se
		double x4 = (b2 - b0)/(k0-k2);
		double y4 = k0*x4+b0;
		//Ee
		double x5 = (b3 - b0)/(k0-k3);
		double y5 = k0*x5+b0;
		
		double lpara1 = Math.sqrt(Math.pow(x4-x0, 2)+Math.pow(y4-y0, 2));
		double lpara2 = Math.sqrt(Math.pow(x5-x1, 2)+Math.pow(y5-y1, 2));
	
		double lvert1 = Math.sqrt(Math.pow(x2-x4, 2)+Math.pow(y2-y4, 2));
		double lvert2 = Math.sqrt(Math.pow(x3-x5, 2)+Math.pow(y3-y5, 2));

		double dvert = (Math.pow(lvert1, 2)+Math.pow(lvert2, 2))/(lvert1+lvert2);
		double dpara = lpara1>lpara2?lpara2:lpara1;
		double dtheta = Math.sqrt(Math.pow(x4-x5, 2)+Math.pow(y4-y5, 2))*tanTheta;
		
		Map<Integer,Double> mapDist = new HashMap<Integer, Double>();
		mapDist.put(0, dvert);
		mapDist.put(1, dpara);
		mapDist.put(2, dtheta);
		
		return mapDist;
	}
}
