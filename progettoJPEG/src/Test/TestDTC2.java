/**
 * 
 */
package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jtransforms.dct.DoubleDCT_2D;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import progettoJPEG.DCT2;




/**
 * @author andre
 *
 */
class TestDTC2 {
	
	

	@Test
	void test() {
        DCT2 dct2=new DCT2();
		
        DoubleDCT_2D dct2D=new DoubleDCT_2D(10,10);
        
        
		//double [][] matrix =new double [10][10];
		double [][] newmatrix =new double [10][10];
		double [][] NEWmatrix =new double [10][10];
		double [][] dct2matrix=new double [10][10];
		int [][] dct2x=new int [10][10];
		int [][] pippo=new int [10][10];
	/*	for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j]=Math.random()*255;
				NEWmatrix[i][j]=matrix[i][j];
			}
		} */
		double [][] matrix = new double [][] {
			{231,32, 233, 161, 24, 71, 140, 245},
			{247, 40, 248, 245, 124, 204, 36, 107},
			{234, 202, 245, 167, 9, 217, 239, 173},
			{193, 190, 100, 167, 43, 180, 8, 70},
			{11, 24, 210, 177, 81, 243, 8, 112},
			{97, 195, 203, 47, 125, 114, 165, 181},
			{193, 70, 174, 167, 41, 30, 127, 245},
			{87, 149, 57, 192, 65, 129, 178, 228}
			};
		
		// library
	/*	System.out.println(matrix[0][0]);
		System.out.println((int)matrix[0][0]);
		dct2D.forward(matrix,true);
		dct2x=conversione(matrix);
		
		
		System.out.println(matrix[0][0]);
		System.out.println((int)matrix[0][0]);
		dct2D.inverse(matrix, true);
		pippo=conversione(matrix);
		
		*/
		System.out.println(matrix[0][0]);
		System.out.println((int)matrix[0][0]);
		
		//home made
		System.out.println("Trasformazione dct2");
		dct2matrix=dct2.applyDCT2(matrix);
		System.out.println(dct2matrix[0][0]);
		System.out.println(dct2matrix[0][1]);
		System.out.println(dct2matrix[0][2]);
		System.out.println(dct2matrix[0][3]);
		
		System.out.println("Trasformazione inversa");
		newmatrix=dct2.applyIDCT2(dct2matrix); 
		System.out.println(newmatrix[0][0]);
		
		assertTrue ( dct2.MatrixEqualOrNot( matrix, newmatrix));
		
	}
	
	public int [][] conversione (double [][] matrix){
		int [][] pippo=new int [10][10];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				pippo[i][j]=(int)matrix[i][j];
			}
		}
		return pippo;
	}
	
	public double [][] conversione(int[][] newmatrix){
		double [][] pippo=new double [10][10];
		for (int i = 0; i < newmatrix.length; i++) {
			for (int j = 0; j < newmatrix[0].length; j++) {
				pippo[i][j]=(int)newmatrix[i][j];
			}
		}
		return pippo;
	}
	
	
	
	
	
	
	
	
	

}
