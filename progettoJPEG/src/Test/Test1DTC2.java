/**
 * 
 */
package Test;

import static org.junit.Assert.assertEquals;
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

import Controller.DCT2;




/**
 * @author andre
 *
 */
class Test1DTC2 {
	
	public double [][] originalMatrix;
	double [][] originalResult;
	int [][] originalIntMatrix;
	int [][] originalIntResult;
	
	public Test1DTC2() {
		originalMatrix = new double [][] {
			{231,32, 233, 161, 24, 71, 140, 245},
			{247, 40, 248, 245, 124, 204, 36, 107},
			{234, 202, 245, 167, 9, 217, 239, 173},
			{193, 190, 100, 167, 43, 180, 8, 70},
			{11, 24, 210, 177, 81, 243, 8, 112},
			{97, 195, 203, 47, 125, 114, 165, 181},
			{193, 70, 174, 167, 41, 30, 127, 245},
			{87, 149, 57, 192, 65, 129, 178, 228}
			};
			
			originalResult = new double [][] {
				{1118 , 44 ,75.9 ,-138, 3.50 ,122 ,195 ,-101},
				{77.1 ,114 ,-21.8 ,41.3 ,8.77 ,99 ,138, 10.9},
				{44.8 ,-62.7, 111, -76.3, 124, 95.5, -39.8, 58.51},
				{-69.9 ,-40.2 ,-23.4 ,-76.7 ,26.6 ,-36.8 ,66.1 ,125},
				{-109 ,-43.3, -55.5 ,8.17, 30.2, -28.6 ,2.44, -94.1},
				{-5.38 ,56.6 ,173 ,-35.4 ,32.3 , 33.4 ,-58.1 , 19.0},
				{78.8 ,-64.5, 118, -15.0, -137, -30.6, -105 ,39.8},
				{19.7, -78.1, 0.972, -72.3 ,-21.5 ,81.3 ,63.7 ,5.90}
				};
				
				//originalIntMatrix =conversione(originalMatrix);
				//originalIntResult =conversione(originalResult);
	}

	@Test
	void test() {
		
        DCT2 dct2=new DCT2();
        DoubleDCT_2D dct2D=new DoubleDCT_2D(10,10);

		//double [][] matrix =new double [10][10];
		double [][] matrix =new double [originalMatrix.length][originalMatrix[0].length];
		int [][] dct2matrix =new int [matrix.length][matrix[0].length];
		int [][] matrix2 =new int [originalMatrix.length][originalMatrix[0].length];
		matrix2 = suddividi(conversionei(originalMatrix),4,2);
		System.out.println(matrix2.length);
		System.out.println(matrix2[0].length);
		for(int i=0;i<matrix2.length;i++)
			for(int j=0;j<matrix2[0].length;j++)
				System.out.println(matrix2[i][j]);
		//matrix=dct2.applyDCT2(originalMatrix);
		//dct2matrix=conversione(matrix);

		assertEquals (  originalIntResult, dct2matrix);
	}
	

	
	
	
	
//	public int [][] conversione (double [][] matrix){
//		int [][] pippo=new int [10][10];
//		for (int i = 0; i < matrix.length; i++) {
//			for (int j = 0; j < matrix[0].length; j++) {
//				pippo[i][j]=(int)matrix[i][j];
//			}
//		}
//		return pippo;
//	}
	public int [][] conversionei(double[][] newmatrix){
		int [][] converted=new int[newmatrix.length][newmatrix[0].length];
		for (int i = 0; i < newmatrix.length; i++) {
			for (int j = 0; j < newmatrix[0].length; j++) {
				converted[i][j]=(int)newmatrix[i][j];
			}
		}
		return converted;
	}
	
	public double [][] conversioned(int[][] newmatrix){
		double [][] converted=new double[newmatrix.length][newmatrix[0].length];
		for (int i = 0; i < newmatrix.length; i++) {
			for (int j = 0; j < newmatrix[0].length; j++) {
				converted[i][j]=newmatrix[i][j];
			}
		}
		return converted;
	}
	
	public double [][] troncaDouble(double [][] matrix){
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				matrix[i][j]=Math.floor(matrix[i][j]*1000)/1000;
			}
		}
		return matrix;
	}
	
	public double[][] filtraggio (double [][] blockDouble ,int d){
		for (int i = 0; i < blockDouble.length; i++) {
			for (int j = 0; j < blockDouble[0].length; j++) {
				if(i + j >= d) {
					blockDouble[i][j]=0;
				}
			}
		}
		return blockDouble;

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
					blockDouble=conversioned(block);
					dct2D.forward(blockDouble, true);
					//filtraggio
					blockDouble=filtraggio(blockDouble,d);
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
	
	
	
	

}
