package graphic;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jtransforms.dct.DoubleDCT_2D;

import Controller.DCT2;

public class GraphicInterface extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtInteroF;
	private JTextField txtInteroD;
	public File selectedFile = null;
	public File outputFile = null;
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
					GraphicInterface frame = new GraphicInterface();
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
	public GraphicInterface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, BorderLayout.SOUTH);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		contentPane.add(panel_1, BorderLayout.NORTH);


		Panel panel_3 = new Panel();
		panel_3.setBackground(Color.WHITE);
		contentPane.add(panel_3, BorderLayout.CENTER);

		JLabel lblN = new JLabel("N:");
		panel_1.add(lblN);

		txtN = new JTextField();
		panel_1.add(txtN);
		txtN.setColumns(5);

		JLabel lblNmax = new JLabel("Nmax:");
		panel_1.add(lblNmax);

		txtNmax = new JTextField();
		panel_1.add(txtNmax);
		txtNmax.setColumns(5);

		JLabel lblStep = new JLabel("Step:");
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
				result=dct2.compare(n, nMax, step);
				ComparisonChart comparison=new ComparisonChart();
				comparison.DCTGraph(result, n, nMax, step);
			}
		});


		JButton btnNewButton = new JButton("Carica immagine");
		panel_1.add(btnNewButton);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		panel_3.setLayout(new BorderLayout(0, 0));



		panel_3.add(splitPane);


		JLabel lblImage1 = new JLabel();
		//splitPane.setLeftComponent(lblImage1);
		JPanel panelLeft = new JPanel();
		panelLeft.add(lblImage1,BorderLayout.CENTER);
		splitPane.add(panelLeft, JSplitPane.LEFT);


		JLabel lblImage2 = new JLabel();
		//splitPane.setRightComponent(lblImage2);
		JPanel panelRight = new JPanel();
		panelRight.add(lblImage2,BorderLayout.CENTER);
		splitPane.add(panelRight, JSplitPane.RIGHT);

		// CARICA FILE
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedFile = selectFile();
				displayFile(selectedFile, lblImage1);
			}
		});

		JLabel lblF = new JLabel("F:");
		panel_1.add(lblF);

		txtInteroF = new JTextField();
		panel_1.add(txtInteroF);
		txtInteroF.setColumns(5);

		JLabel lblD = new JLabel("d:");
		panel_1.add(lblD);

		txtInteroD = new JTextField();
		panel_1.add(txtInteroD);
		txtInteroD.setColumns(5);

		JButton btnComprimiImmagine = new JButton("Comprimi");
		panel_1.add(btnComprimiImmagine);
		btnComprimiImmagine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int f = Integer.parseInt(txtInteroF.getText());
				int d = Integer.parseInt(txtInteroD.getText());
				int[][] pixels = convertiFile2Pixel();


				int[][] result = suddividi(pixels, f, d);

				convertPixelsToFile(result);
				displayFile(outputFile, lblImage2);
			}
		});	
	}


	public void convertPixelsToFile(int[][] result) {

		BufferedImage image=new BufferedImage(result.length, result[0].length,BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster wr = image.getRaster();
		int[] pixel = new int[1];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				pixel[0] = result[i][j];
				wr.setPixel(i, j, pixel);
			}
		}
		outputFile = new File(System.getProperty("user.dir")+"/img/compressed/COMPRESSED_"+selectedFile.getName());
		try {
			ImageIO.write(image, "BMP", outputFile);
			System.out.println("File: '"+outputFile.getName()+"' Saved To: " + outputFile.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public int[][] convertiFile2Pixel(){

		BufferedImage bufferimage = null;
		try {
			bufferimage = ImageIO.read(selectedFile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int width = bufferimage.getWidth();
		int height = bufferimage.getHeight();
		int pixels[][] = new int[width][height];
		Raster raster = bufferimage.getData();
		for (int i = 0; i < raster.getWidth(); i++) {
			for (int j = 0; j < raster.getHeight(); j++) {
				pixels[i][j] = raster.getSample(i, j, 0);
			}
		}
		return pixels;
	}

	public int[][] suddividi(int[][]matrix, int f , int d){
		int[][] block=new int [f][f];
		int[][] result = new int[matrix.length][matrix[0].length];
		double [][] blockDouble=new double [f][f];
		for (int i = 0; i < matrix.length; i=i+f) {
			for (int j = 0; j < matrix[0].length; j=j+f) {
				if(i + f <= matrix.length && j + f <= matrix[0].length) {
					for (int k = 0; k < f; k++) {
						for (int l = 0; l < f; l++) {
							block[k][l]=matrix[k+i][l+j];

						}
					}
					//DCTlibrary
					DoubleDCT_2D dct2D=new DoubleDCT_2D(f,f);
					blockDouble=conversione(block);

					dct2D.forward(blockDouble, true);
					//filtraggio

					filtraggio(blockDouble,d);
					//DCT inversa
					dct2D.inverse(blockDouble, true);

					// reinserirlo nella matrix
					for (int k = 0; k < f; k++) {
						for (int l = 0; l < f; l++) {
								if(blockDouble[k][l] < 0)
									result[i+k][j+l] = 0;
								else
									if(blockDouble[k][l] > 255)
										result[i+k][j+l] = 255;
									else
										result[i+k][j+l]=(int)blockDouble[k][l];							
							}
					}
				}
			}
		}
		return result;
	}

	public double[][] filtraggio (double [][] blockDouble ,int d){
		for (int i = 0; i < blockDouble.length; i++) {
			for (int j = 0; j < blockDouble[0].length; j++) {
				if(i + j >= d) {
					blockDouble[i][j] = 0;
				}
			}
		}
		return blockDouble;

	}


	public double [][] conversione(int[][] newmatrix){
		double [][] converted=new double[newmatrix.length][newmatrix[0].length];
		for (int i = 0; i < newmatrix.length; i++) {
			for (int j = 0; j < newmatrix[0].length; j++) {
				converted[i][j]=(int)newmatrix[i][j];
			}
		}
		return converted;
	}

	public void displayFile(File selectedFile,JLabel lblImage) {
		Image image = null;
		int width;
		int height;
		if(selectedFile!=null) {
			try {
				image = ImageIO.read(selectedFile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			double ratio;
			if(image.getWidth(getParent()) < image.getHeight(getParent())){
				ratio = 1.0 * image.getWidth(getParent()) / image.getHeight(getParent());
				width = (int)(image.getWidth(getParent()) * Math.pow(ratio, image.getHeight(getParent())/1000));
				height = (int)(image.getHeight(getParent()) * Math.pow(ratio, image.getHeight(getParent())/1000));
			}else {
				ratio = 1.0 * image.getHeight(getParent()) / image.getWidth(getParent());
				width = (int)(image.getWidth(getParent()) * Math.pow(ratio, image.getWidth(getParent())/1000));
				height = (int)(image.getHeight(getParent()) * Math.pow(ratio, image.getWidth(getParent())/1000));
			}


			Image scaled = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(scaled);
			lblImage.setIcon(icon);
		}
	}

	public File selectFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
			System.out.println("Selected file: " + selectedFile.getAbsolutePath());

		}
		return selectedFile;

	}


}
