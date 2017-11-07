package cn.les.kghj;

import java.awt.Color;
import java.util.Iterator;
import java.util.Map;
import java.awt.BasicStroke; 
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
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
public class PlotTraLL extends ApplicationFrame 
{
	private static final long serialVersionUID = 1L;

   public PlotTraLL( String applicationTitle, String chartTitle,Map<Integer, Track> map )
   {
      super(applicationTitle);
	  JFreeChart xylineChart = ChartFactory.createXYLineChart(
         chartTitle ,
         "Longitude" ,
         "Latitude" ,
         createDataset(map) ,
         PlotOrientation.VERTICAL ,
         true , true , false);
	 
      ChartPanel chartPanel = new ChartPanel( xylineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 860 , 567 ) );
      
      final XYPlot plot = xylineChart.getXYPlot( );
      ValueAxis axis = plot.getRangeAxis();
      axis.setRange(28,48);
      plot.setRangeAxis(axis);
      
      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
      renderer.setSeriesPaint( 0 , Color.BLUE );
      renderer.setSeriesStroke( 0 , new BasicStroke( 0.5f ) );
      plot.setRenderer( renderer ); 
      setContentPane( chartPanel ); 
   }
   
   private XYDataset createDataset(Map<Integer, Track> map )
   {
      final XYSeries flight_Longitude = new XYSeries( "2D-Curve" );        

      Iterator<Integer> iterator = map.keySet().iterator();
	  
      while (iterator.hasNext()) { 
		  Integer  gg= iterator.next();
		  flight_Longitude.add( Double.valueOf(map.get(gg).getX()) , Double.valueOf(map.get(gg).getY()) ); 
	  }
		//Double.valueOf(iterator.next())  Double.valueOf(map.get(iterator.next()).getTimestamp())
      final XYSeriesCollection dataset = new XYSeriesCollection( );          
      dataset.addSeries( flight_Longitude );   
      return dataset;
   }
}
