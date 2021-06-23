package Interfaccia;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

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
  } */
  
	public static void DCTGraph(long []dct2Perf, long []dct2LibPerf){
		XYSeriesCollection data = new XYSeriesCollection();
		XYLineAndShapeRenderer r = new XYLineAndShapeRenderer();

		XYSeries plotSeries = new XYSeries("demoSeries");
		for(int i = 0; i < 500; i++) {
			plotSeries.add(Math.pow(10, i / 100.0 - 2), Math.sin(i / 30.0));
		}
		data.addSeries(plotSeries);
		r.setSeriesStroke(0, new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
		r.setSeriesShape(0, new Rectangle(new Dimension(0, 0)));
		r.setSeriesPaint(0, new Color(0.3f, 0.5f, 0.0f));

		JFreeChart chart = ChartFactory.createXYLineChart("", // title
						"", // x axis label (will be set later)
						"", // y axis label (will be set later)
						data, // data to display
						PlotOrientation.VERTICAL, // plot orientation
						false, // don't show legend
						true, // show tooltips
						false); // no urls
		chart.setTitle(new TextTitle("plotTitle", new Font(null, Font.PLAIN, 9)));

		XYPlot plot = chart.getXYPlot();
		plot.setRenderer(r);

		ValueAxis domain, range;
		domain = new LogarithmicAxis("Frequency [Hz]");
		range = new NumberAxis("SSB Phase Noise [dBc/Hz]");
		range.setAutoRange(true);

		plot.setDomainAxis(domain);
		plot.setRangeAxis(range);

		ChartPanel phnPlotPanel = new ChartPanel(chart);
		add(phnPlotPanel, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		PlotDemo main = new PlotDemo();
		main.setVisible(true);
		main.setSize(new Dimension(400, 400));
	}
  
  
  private XYDataset createDataset( long []dct2Perf) {
      final TimeSeries series = new TimeSeries( "DCT2 performance" );         
      Second current = new Second( );         
      double value = 100.0;         
      
      for (int i = 0; i < dct2Perf.length; i++) {
         
         try {
            value = value + Math.random( ) - 0.5;                 
            //series.add(i, dct2Perf[i]);                 
            current = ( Second ) current.next( ); 
         } catch ( SeriesException e ) {
            System.err.println("Error adding to series");
         }
      }

      return new TimeSeriesCollection(series);
   }
  
  
}

