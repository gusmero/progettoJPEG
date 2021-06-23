package Interfaccia;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jtransforms.dct.DoubleDCT_2D;

import Controller.DCT2;

import java.awt.Color;
import javax.swing.JSplitPane;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.event.ActionListener;

public class Interfaccia extends JFrame {

	private JPanel contentPane;
	private JTextField txtInteroF;
	private JTextField txtInteroD;
	public File selectedFile = null;
	private JTextField txtN;
	private JTextField txtNmax;
	private JTextField txtStep;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interfaccia frame = new Interfaccia();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Interfaccia() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JTextPane txtpnRisultati = new JTextPane();
		txtpnRisultati.setText("Risultati Questa \u00E8 una prova dei risultati possibili ");
		panel.add(txtpnRisultati);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GREEN);
		contentPane.add(panel_1, BorderLayout.NORTH);
		

		Panel panel_3 = new Panel();
		panel_3.setBackground(Color.RED);
		contentPane.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblN = new JLabel("N:");
		panel_1.add(lblN);
		
		txtN = new JTextField();
		panel_1.add(txtN);
		txtN.setColumns(5);
		
		JLabel lblNmax = new JLabel("Nmax");
		panel_1.add(lblNmax);
		
		txtNmax = new JTextField();
		panel_1.add(txtNmax);
		txtNmax.setColumns(5);
		
		JLabel lblStep = new JLabel("Step");
		panel_1.add(lblStep);
		
		txtStep = new JTextField();
		panel_1.add(txtStep);
		txtStep.setColumns(5);
		
		JButton btnConfronta = new JButton("Confronta");
		panel_1.add(btnConfronta);
		btnConfronta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DCT2 dct2=new DCT2();
				
				int n=Integer.parseInt(txtN.getText());
				int nMax=Integer.parseInt(txtNmax.getText());
				int step=Integer.parseInt(txtStep.getText());
				long [][] result = new long[2][(nMax-n)/step];
				result=dct2.confronta2( n ,nMax,step);
				ComparisonChart comparison=new ComparisonChart();
				comparison.DCTGraph(result, n, nMax, step);
			}
		});
		
		
		JButton btnNewButton = new JButton("Carica immagine");
		panel_1.add(btnNewButton);
		

		JLabel lblImage = new JLabel() ;
		panel_3.add(lblImage);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				selectedFile = selectFile();
				displayFile(selectedFile , lblImage);
			}
		});
		
		txtInteroD = new JTextField();
		txtInteroD.setText("intero d");
		panel_1.add(txtInteroD);
		txtInteroD.setColumns(10);
		
		txtInteroF = new JTextField();
		txtInteroF.setText("intero F");
		panel_1.add(txtInteroF);
		txtInteroF.setColumns(10);
		
		JButton btnComprimiImmagine = new JButton("Comprimi ");
		panel_1.add(btnComprimiImmagine);
		btnComprimiImmagine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedImage bufferimage = null;
				try {
					bufferimage = ImageIO.read(selectedFile);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int [][] pixels =convertTo2DWithoutUsingGetRGB(bufferimage);
				DCT2 dct2=new DCT2();
				dct2.applyDCT2(pixels);
				DoubleDCT_2D dct2D=new DoubleDCT_2D(pixels.length,pixels[0].length);
				double [][] newmatrix =new double [10][10];
				dct2D.forward(newmatrix, true);
				dct2D.inverse(newmatrix, true);
			}
		});
		
		
		
		
		
	}
	
	public double[][] confronta (int n) {
		DCT2 dct2=new DCT2();
		
		
		double matrix[][] = new double[n][n];
		double matrixJtransform [][] = new double[n][n];
		DoubleDCT_2D dct2dtest = new DoubleDCT_2D(n, n);
		long seed = 1;
		Random r = new Random(seed);
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				double randomValue = r.nextInt(255);
				matrix[i][j] = randomValue;
				matrixJtransform[i][j] = randomValue;
			}
		}
		
		
		long startTimeLowperf = System.nanoTime();
		matrix=dct2.applyDCT2(matrix);
		long endTimeLowperf = System.nanoTime();
		long durationLowperf = (endTimeLowperf - startTimeLowperf)/1000;
		System.out.println("Il tempo di esecuzione della dctLowPerf è: " + durationLowperf);
		long startTimeJtransform = System.nanoTime();
		dct2dtest.forward(matrixJtransform, true);
		long endTimeJtransform = System.nanoTime();
		long durationJtransform = (endTimeJtransform - startTimeJtransform)/1000;
		System.out.println("Il tempo di esecuzione della dctJtransform è: " + durationJtransform);
		DCTGraph.DCTGraph(durationLowperf, durationJtransform);
		return matrix;
		}
	
	
	

		
	
	
	

	
	public void displayFile(File selectedFile,JLabel lblImage) {
		Image image = null;
		if(selectedFile!=null) {
		try {
			image = ImageIO.read(selectedFile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ImageIcon icon = new ImageIcon(image);
		lblImage.setIcon(icon);
		}
	}
	
	
	public File selectFile() {
		File selectedFile=null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
		    selectedFile = fileChooser.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		    
		}
		return selectedFile;
		
	}
	
	 private static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {
	      final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	      final int width = image.getWidth();
	      final int height = image.getHeight();
	      final boolean hasAlphaChannel = image.getAlphaRaster() != null;
	      int[][] result = new int[height][width];
	      if (hasAlphaChannel) {
	         final int pixelLength = 4;
	         for (int pixel = 0, row = 0, col = 0; pixel + 3 < pixels.length; pixel += pixelLength) {
	            int argb = 0;
	            argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
	            argb += ((int) pixels[pixel + 1] & 0xff); // blue
	            argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
	            argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
	            result[row][col] = argb;
	            col++;
	            if (col == width) {
	               col = 0;
	               row++;
	            }
	         }
	      } else {
	         final int pixelLength = 3;
	         for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
	            int argb = 0;
	            argb += -16777216; // 255 alpha
	            argb += ((int) pixels[pixel] & 0xff); // blue
	            argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
	            argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
	            result[row][col] = argb;
	            col++;
	            if (col == width) {
	               col = 0;
	               row++;
	            }
	         }
	      }
	      return result;
	   }
	
}
