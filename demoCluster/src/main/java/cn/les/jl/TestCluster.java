package cn.les.jl;

import java.util.Map;
import cn.les.kghj.Track;

public class TestCluster {
	public static void main(String[] args) {

		ReadData rData = new ReadData();
		//获取所有的飞行计划
		Map<Integer, String> map =  rData.getAcidTrackno();
		System.out.println("总飞行计划次数："+map.size());
        
		//写出所有的数据集[执行完一次，注释掉]
		
		for(int i=1;i<map.size()+1;i++){
			Map<Integer, Track> map1 = rData.getSingleTra(map.get(i));
			rData.writeDataSet(map1);//写出数据
		}
		System.out.println("数据写出完毕！");
		
		rData.plotSHLL(rData.getSingleTra(map.get(3)));//画图

	}

}
