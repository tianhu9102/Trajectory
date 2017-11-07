package cn.les.kghj;

import java.awt.Color;
import java.util.Iterator;
import java.util.Map;
import java.awt.BasicStroke; 
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.data.xy.XYDataset; 
import org.jfree.data.xy.XYSeries; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.chart.plot.XYPlot; 
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.xy.XYSeriesCollection; 
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

/**
 * 
 * @author DUYUE
 * 功能：绘制航迹速度、高度曲线图
 * 参考：http://www.yiibai.com/jfreechart/jfreechart_xy_chart.html
 */
public class PlotTraSH extends ApplicationFrame 
{
	private static final long serialVersionUID = 1L;

   public PlotTraSH( String applicationTitle, String chartTitle,Map<Integer, Track> map )
   {
      super(applicationTitle);

	JFreeChart xylineChart = ChartFactory.createXYLineChart(
         chartTitle ,
         "time(:min)" ,
         "value" ,
         createDataset(map) ,
         PlotOrientation.VERTICAL ,
         true , true , false);
         
      ChartPanel chartPanel = new ChartPanel( xylineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 860 , 567 ) );
      
      final XYPlot plot = xylineChart.getXYPlot( );
      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
      renderer.setSeriesPaint( 0 , Color.RED );
      renderer.setSeriesPaint( 1 , Color.GREEN );
      renderer.setSeriesStroke( 0 , new BasicStroke( 1.0f ) );
      renderer.setSeriesStroke( 1 , new BasicStroke( 1.0f ) );
      plot.setRenderer( renderer ); 
      setContentPane( chartPanel ); 
   }
   
   private XYDataset createDataset(Map<Integer, Track> map )
   {
      final XYSeries flight_speed = new XYSeries( "speed(km/h)  " );          
      final XYSeries flight_height = new XYSeries( " hegiht(*10)" );          

      Iterator<Integer> iterator = map.keySet().iterator();
		while (iterator.hasNext()) { 
			Integer gg = iterator.next();
			flight_speed.add( Double.valueOf(gg)/15 , Double.valueOf(map.get(gg).getSpeed()) ); 
			flight_height.add( Double.valueOf(gg)/15  , Double.valueOf(map.get(gg).getMcl()) ); 
	 }
 
      final XYSeriesCollection dataset = new XYSeriesCollection( );          
      dataset.addSeries( flight_speed );          
      dataset.addSeries( flight_height );    
      return dataset;
   }

 
}
