package Interfaccia;

 


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.Random;

 

import javax.swing.BorderFactory;
import javax.swing.JFrame;

 

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

 

import Controller.DCT2;

 

public class ComparisonChart extends JFrame{

 

 // public static void DCTGraph(long []dct2Perf, long []dct2LibPerf) {

 

      //     USARE DATA SERIES
    // Prepare the data set
  /*  DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
    barDataset.setValue(durationJTransform, "Libreria", "JTransform");
    barDataset.setValue(durationLowPerf, "Libreria", "home Made");

 

      
      
    //Create the chart
    JFreeChart chart = ChartFactory.createBarChart(
        "Tabella comparazione esecuzione DCT", "Libreria", "Tempo (microsecondi)", barDataset,
        PlotOrientation.HORIZONTAL, false, true, false);
    
    //Render the frame
    ChartFrame chartFrame = new ChartFrame("Tabella Tempo Esecuzione", chart);
    chartFrame.setVisible(true);
    chartFrame.setSize(560, 350);
  } */
  
    public void DCTGraph(long [][]result, int n , int max , int step){
        
        setVisible(true);
        setSize(new Dimension(800, 600));
        
        JFreeChart charthomemade = createChart(result[0], n, max, step);
        JFreeChart chartlibrary = createChart(result[1], n, max, step);
 
        charthomemade.setTitle(new TextTitle("Performance DCT2 homemade", new Font(null, Font.PLAIN, 9)));
        chartlibrary.setTitle(new TextTitle("Performance DCT2 JTransform", new Font(null, Font.PLAIN, 9)));
        
        ChartPanel plotPanelHomemade = new ChartPanel(charthomemade);
        add(plotPanelHomemade,BorderLayout.NORTH);
        
        ChartPanel plotPanelLibrary = new ChartPanel(chartlibrary);
        add(plotPanelLibrary,BorderLayout.CENTER);
        pack();
        
        
    }
    
    
    
  

 

      
      private JFreeChart createChart(long []result , int n, int max , int step) {
            
            XYSeriesCollection data = new XYSeriesCollection();
            XYLineAndShapeRenderer r = new XYLineAndShapeRenderer();

 

            XYSeries plotSeries = new XYSeries("Performance");
            for(int i = 0; i < result.length; i++) {
                plotSeries.add(n+i*step,result[i]);
            }
            
            
            data.addSeries(plotSeries);
            r.setSeriesStroke(0, new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            r.setSeriesShape(0, new Rectangle(new Dimension(0, 0)));
            r.setSeriesPaint(0, new Color(0.3f, 0.5f, 0.0f));
            JFreeChart chart = ChartFactory.createXYLineChart(" Comparison method", // title
                    " Block's dimension", // x axis label (will be set later)
                    " Time of execution", // y axis label (will be set later)
                    data, // data to display
                    PlotOrientation.VERTICAL, // plot orientation
                    false, // don't show legend
                    true, // show tooltips
                    false); // no urls
            

 

            XYPlot plot = chart.getXYPlot();
            plot.setRenderer(r);

 

            ValueAxis domain, range;
            range = new LogarithmicAxis(" Time ");
            domain = new NumberAxis(" Block dimension");
            range.setAutoRange(true);

 

            plot.setDomainAxis(domain);
            plot.setRangeAxis(range);
   

 

            return chart;
        }
  
}



















/*package Interfaccia;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import Controller.DCT2;

public class ComparisonChart extends JFrame{

 // public static void DCTGraph(long []dct2Perf, long []dct2LibPerf) {

	  // 	USARE DATA SERIES
    // Prepare the data set
  /*  DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
    barDataset.setValue(durationJTransform, "Libreria", "JTransform");
    barDataset.setValue(durationLowPerf, "Libreria", "home Made");

	  
	  
    //Create the chart
    JFreeChart chart = ChartFactory.createBarChart(
        "Tabella comparazione esecuzione DCT", "Libreria", "Tempo (microsecondi)", barDataset,
        PlotOrientation.HORIZONTAL, false, true, false);
    
    //Render the frame
    ChartFrame chartFrame = new ChartFrame("Tabella Tempo Esecuzione", chart);
    chartFrame.setVisible(true);
    chartFrame.setSize(560, 350);
  } 
  
	public void DCTGraph(long [][]result, int n , int max , int step){
		
		setVisible(true);
		setSize(new Dimension(400, 400));
		
		

		XYSeriesCollection data = new XYSeriesCollection();
		XYLineAndShapeRenderer r = new XYLineAndShapeRenderer();

		XYSeries plotSeries = new XYSeries("performance");
		for(int i = 0; i < result[0].length; i++) {
			plotSeries.add(n+i*step,result[0][i]);
		}
		
		
		data.addSeries(plotSeries);
		r.setSeriesStroke(0, new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
		r.setSeriesShape(0, new Rectangle(new Dimension(0, 0)));
		r.setSeriesPaint(0, new Color(0.3f, 0.5f, 0.0f));

		JFreeChart chart = ChartFactory.createXYLineChart(" Comparison method", // title
						" Block's dimension", // x axis label (will be set later)
						" Time of execution", // y axis label (will be set later)
						data, // data to display
						PlotOrientation.VERTICAL, // plot orientation
						false, // don't show legend
						true, // show tooltips
						false); // no urls
		chart.setTitle(new TextTitle("Performance DCT2 home-made", new Font(null, Font.PLAIN, 9)));

		XYPlot plot = chart.getXYPlot();
		plot.setRenderer(r);

		ValueAxis domain, range;
		range = new LogarithmicAxis(" Time ");
		domain = new NumberAxis(" Block dimension");
		range.setAutoRange(true);

		plot.setDomainAxis(domain);
		plot.setRangeAxis(range);

		ChartPanel phnPlotPanel = new ChartPanel(chart);
		add(phnPlotPanel, BorderLayout.CENTER);
	    pack();
	    
	    
	}
	
	
	
  

      
	  private XYDataset createDataset(long []result , int n, int max , int step) {
	        var series = new XYSeries("2016");
	        series.add(18, 567);
	        series.add(20, 612);
	        series.add(25, 800);
	        series.add(30, 980);
	        series.add(40, 1410);
	        series.add(50, 2350);
	        var dataset = new XYSeriesCollection();
	        dataset.addSeries(series);
	        return dataset;
	    }
  
}

*/

