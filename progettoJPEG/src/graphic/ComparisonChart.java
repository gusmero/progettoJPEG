package graphic;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;	
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

public class ComparisonChart extends JFrame{
	
	private static final long serialVersionUID = 1L;
	JPanel contentPane;
	JSplitPane splitPane;
	JPanel panelLeft;
	JPanel panelRight;
	
	
	public ComparisonChart() {
		setBounds(100, 100, 1200, 1000);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);


		splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setResizeWeight(0.5);

		getContentPane().add(splitPane, BorderLayout.CENTER);

	}

	

	public void DCTGraph(long [][]result, int n , int max , int step){
		setVisible(true);
		setSize(new Dimension(1000, 800));

		JFreeChart charthomemade = createChart(result[0], n, max, step);
		JFreeChart chartlibrary = createChart(result[1], n, max, step);

		charthomemade.setTitle(new TextTitle("Performance DCT2 homemade", new Font(null, Font.PLAIN, 15)));
		chartlibrary.setTitle(new TextTitle("Performance DCT2 JTransform", new Font(null, Font.PLAIN, 15)));

		panelLeft = new JPanel();
		splitPane.setLeftComponent(panelLeft);
		ChartPanel plotPanelHomemade = new ChartPanel(charthomemade);
		panelLeft.add(plotPanelHomemade,BorderLayout.NORTH);
		splitPane.add(panelLeft, JSplitPane.LEFT);

		panelRight = new JPanel();
		splitPane.setRightComponent(panelRight);
		ChartPanel plotPanelLibrary = new ChartPanel(chartlibrary);
		panelRight.add(plotPanelLibrary,BorderLayout.CENTER);
		splitPane.add(panelRight, JSplitPane.RIGHT);
		pack();
		
	}

	private JFreeChart createChart(long []result , int n, int max , int step) {
		//dataset creation
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

		//chart creation
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
		range = new LogarithmicAxis("Time (ms)");
		domain = new NumberAxis("Block dimension (N x N)");
		range.setAutoRange(true);
		plot.setDomainAxis(domain);
		plot.setRangeAxis(range);
		return chart;
	}
}


