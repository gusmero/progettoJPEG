package utils;

import java.util.Random;

import org.jtransforms.dct.DoubleDCT_2D;

public class MatrixOperations {

	public static int[][] subdivide(int[][] matrix, int f , int d){
		int[][] block = new int [f][f];
		int[][] result = new int[matrix.length][matrix[0].length];
		double [][] blockDouble = new double [f][f];
		for (int i = 0; i < matrix.length; i = i+f) {
			for (int j = 0; j < matrix[0].length; j = j+f) {
				if(i + f <= matrix.length && j + f <= matrix[0].length) {
					for (int k = 0; k < f; k++) {
						for (int l = 0; l < f; l++) {
							block[k][l] = matrix[k+i][l+j];
						}
					}

					/**
					 * Applichiamo la dct2 ad un blocco fxf
					 */
					DoubleDCT_2D dct2D=new DoubleDCT_2D(f,f);
					blockDouble=intMatrixToDouble(block);
					dct2D.forward(blockDouble, true);


					/**
					 * Eliminiamo le frequenze ckl con k + l >= d
					 */
					filtering(blockDouble,d);


					/**
					 * Applichiamo la dct2 inversa ad un blocco fxf 
					 */
					dct2D.inverse(blockDouble, true);


					/**
					 * Reinserimento del blocco fxf nella matrice di pixel originale
					 */
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


	/**
	 * Eliminiamo le frequenze ckl con k + l >= d (poniamo a 0 il valore del pixel)
	 */
	public static double[][] filtering (double [][] blockDouble, int d){
		for (int i = 0; i < blockDouble.length; i++) {
			for (int j = 0; j < blockDouble[0].length; j++) {
				if(i + j >= d) {
					blockDouble[i][j] = 0;
				}
			}
		}
		return blockDouble;

	}

	
	
	public static double[][] intMatrixToDouble(int[][] oldMatrix){
		double [][] converted = new double[oldMatrix.length][oldMatrix[0].length];
		for (int i = 0; i < oldMatrix.length; i++) {
			for (int j = 0; j < oldMatrix[0].length; j++) {
				converted[i][j] = oldMatrix[i][j];
			}
		}
		return converted;
	}

	
	/**
	 * Metodo per la conversione di matrici double a intere
	 */
	public static int [][] doubleMatrixToInt(double[][] oldMatrix){
		int [][] converted = new int[oldMatrix.length][oldMatrix[0].length];
		for (int i = 0; i < oldMatrix.length; i++) {
			for (int j = 0; j < oldMatrix[0].length; j++) {
				converted[i][j] = (int)(Math.ceil(oldMatrix[i][j] * Math.pow(10, 8)) / Math.pow(10, 8));
			}
		}
		return converted;
	}
	
	
	/**
	 * Metodo per la generazione di matrici random
	 */
	public static double[][] initRandMatrix (int n){
		double matrix[][] = new double[n][n];
		long seed = 1;
		Random r = new Random(seed);
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				double randomValue = r.nextInt(255);
				matrix[i][j] = randomValue;
			}
		}
		return matrix;
	}
	
	/**
	 * Metodo per la copia di matrice
	 */
	public static double[][] matrixCopy(double [][] matrix){
		double copy[][] = new double[matrix.length][matrix[0].length];
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				copy[i][j] = matrix[i][j];
			}
		}
		return copy;
	}
	
}
