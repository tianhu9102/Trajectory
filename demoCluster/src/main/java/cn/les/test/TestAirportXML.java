package cn.les.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 解析airport.xml文件，写成SQL语句的文件
 * @author 14206
 *
 */
public class TestAirportXML {

    public static void main(String[] args) {
    	String input = "D:/airport.xml";
    	String output = "D:/airport.txt";
    	
    	File file = new File(input);//读取数据
    	Map<Integer,String> map = pat(txt2String(file));
    	writeout(map,output); //写出数据，该方法中有写出文件路径
    	System.out.println("write finished!");
    }
    
    public static Map<Integer,String> txt2String(File file){
        StringBuilder result = new StringBuilder();
        Map<Integer,String> map = new HashMap<Integer, String>();
        try{
        	InputStreamReader read = new InputStreamReader(new FileInputStream(file),"gbk");   
            BufferedReader br = new BufferedReader(read);//构造一个BufferedReader类来读取文件
            String s = null;
            int i=0;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            	i++;
                map.put(i,s);
                //System.out.println(map.get(i));
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }
    
    public static Map<Integer,String> pat(Map<Integer,String> map){
    	
    	Map<Integer,String> map1 = new HashMap<Integer,String>();
    	int index=0;
    	for(int i=4;i<=849;i++){
    		//System.out.println(i+" "+map.get(i));

    		String am[]= map.get(i).split("><"); // 0 1
    	    String intstst = am[0];//“><”的左侧部分
    	    String bm[]=intstst.split(" "); // 0 1 ... 10 11
    	    
    	    StringBuffer sn = new StringBuffer();
    	    for(int j=1;j<bm.length;j++){
    	    	String cm[] = bm[j].split("=");
    	    	String valuem = cm[1];
    	    	String value;
    	    	if(valuem.length()-1<=1){
    	    		value="null";
    	    	}else{
    	    		value = valuem.substring(1, valuem.length()-1);
    	    	}
    	    	
    	    	
    	    	if(j<bm.length-1){
    	    		sn = sn.append(value+" ");
    	    	}else{
    	    		sn = sn.append(value);
    	    	}
    	    	
    	    }
    	    map1.put(index, sn.toString());
    	    index++; 		
    	}
    	return map1;
    }
    
    
    public static void writeout(Map<Integer,String> map,String output){
    	if (map.size() > 0) {
			String spt = System.getProperty("line.separator");
			try {
				StringBuffer filename = new StringBuffer();
				filename = filename.append(output);
				FileWriter fWriter = new FileWriter(filename.toString());
				
				Iterator<Integer> iterator = map.keySet().iterator();
				
				/*
				fWriter.write("create table t_airport ("
						+ " name varchar(15),id3 varchar(15),longitude varchar(15),latitude varchar(15),"
						+ "longi_xpos varchar(15),lati_ypos varchar(15),altitude varchar(15),local varchar(15),"
						+ "ew varchar(15),sn varchar(15),fullname varchar(15) ); "+"\n");
				
				*/
				for(int i =0;i<map.size();i++){
					Integer tmp = iterator.next();
					fWriter.write("insert into t_airport values(");
					
					for(int j =0;j<map.get(i).split(" ").length;j++){
			    		fWriter.write("'"+map.get(i).split(" ")[j]+"'");
			    		if(j<map.get(i).split(" ").length-1){
			    			fWriter.write(",");
			    		}
			    	}
					fWriter.write(");");
					fWriter.write(spt);
				}
				fWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
       	System.out.println(map.size());
    }
    
}