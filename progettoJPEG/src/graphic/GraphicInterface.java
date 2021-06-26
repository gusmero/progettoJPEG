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

import utils.DCT2;
import utils.MatrixOperations;

public class GraphicInterface extends JFrame {

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
	 * Lancio applicazione
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
	 * Creazione Frame
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
				int n = Integer.parseInt(txtN.getText());
				int nMax = Integer.parseInt(txtNmax.getText());
				int step = Integer.parseInt(txtStep.getText());
				long [][] result = new long[2][(nMax-n)/step];
				result = DCT2.compare(n, nMax, step);
				ComparisonChart comparison = new ComparisonChart();
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
		JPanel panelLeft = new JPanel();
		panelLeft.add(lblImage1,BorderLayout.CENTER);
		splitPane.add(panelLeft, JSplitPane.LEFT);

		JLabel lblImage2 = new JLabel();
		JPanel panelRight = new JPanel();
		panelRight.add(lblImage2,BorderLayout.CENTER);
		splitPane.add(panelRight, JSplitPane.RIGHT);

		// Carica file
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
				int[][] pixels = convertFileToPixels();
				int[][] result = MatrixOperations.subdivide(pixels, f, d);
				convertPixelsToFile(result);
				displayFile(outputFile, lblImage2);
			}
		});	
	}

	/**
	 * Selezione file da cartella di progetto
	 */
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
	
	/**
	 * Conversione dell'immagine in una matrice di pixels
	 */
	public int[][] convertFileToPixels(){

		BufferedImage bufferimage = null;
		try {
			bufferimage = ImageIO.read(selectedFile);
		} catch (IOException e1) {
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

	/**
	 * Conversione della matrice di pixels in immagine
	 */
	public void convertPixelsToFile(int[][] result) {
		//BufferedImage inizializzata con parametro TYPE_BYTE_GRAY per immagini in bianco e nero		
		BufferedImage image = new BufferedImage(result.length, result[0].length,BufferedImage.TYPE_BYTE_GRAY);
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
			e.printStackTrace();
		}
	}

	public void displayFile(File selectedFile, JLabel lblImage) {
		Image image = null;
		int width;
		int height;
		double ratio;
		if(selectedFile != null) {
			try {
				image = ImageIO.read(selectedFile);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			/**
			 * Selezione della dimensione massima(Height o Width) e creazione di un ratio compreso tra 0 e 1,
			 * Quest'ultimo viene utilizzato per scalare l'immagine mantenedo le proporzioni.
			 */
			if(image.getWidth(getParent()) < image.getHeight(getParent())){
				ratio = 1.0 * image.getWidth(getParent()) / image.getHeight(getParent());
				width = (int)(image.getWidth(getParent()) * Math.pow(ratio, image.getHeight(getParent())/1000));
				height = (int)(image.getHeight(getParent()) * Math.pow(ratio, image.getHeight(getParent())/1000));
			}
			else {
				ratio = 1.0 * image.getHeight(getParent()) / image.getWidth(getParent());
				width = (int)(image.getWidth(getParent()) * Math.pow(ratio, image.getWidth(getParent())/1000));
				height = (int)(image.getHeight(getParent()) * Math.pow(ratio, image.getWidth(getParent())/1000));
			}
			Image scaled = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(scaled);
			lblImage.setIcon(icon);
		}
	}
}
