package Interfaccia;


import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class DCTGraph extends JFrame{

  public static void DCTGraph(long durationLowPerf, long durationJTransform) {

	  // 	USARE DATA SERIES
    // Prepare the data set
    DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
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
  
}