package utils;

import org.jtransforms.dct.DoubleDCT_2D;


public class DCT2 {
	
	public static double [][] applyDCT2(double[][] f) {
		double[][] dctMatrix = new double[f.length][f[0].length];
        for (int k = 0; k < f.length; k++) {
          for (int l = 0; l < f[0].length; l++) {
            double sum = 0.0;
            for (int i = 0; i < f.length; i++) {
              for (int j = 0; j < f[0].length; j++) {            	
            	sum = sum + ( f[i][j] * Math.cos((((2*i+1)/(2.0 * f.length)) * k * Math.PI)) * Math.cos((((2 * j+1)/(2.0 * f.length) * l * Math.PI))));
              }
            }
            if(k == 0 && l== 0)
                sum = sum / Math.sqrt(f.length*f[0].length);
            else if (k == 0 || l == 0)
                sum = sum * Math.sqrt(2.0/ (f.length *f[0].length));
            else {
                sum = sum * (2.0 / Math.sqrt(f.length*f[0].length));
            }
            dctMatrix[k][l]=sum;
           
          }
        }
        return dctMatrix;
    }
	
	
	
	public static double [][] applyIDCT2(double[][]c) {
		double[][] originalMatrix = new double[c.length][c[0].length];
		double a = 0 ;
        for (int i = 0; i < c.length; i++) {
          for (int j = 0; j < c[0].length; j++) {
            double sum = 0.0;
            for (int k = 0; k < c.length; k++) {
              for (int l = 0; l < c[0].length; l++) {   
            	if(k == 0 && l== 0)
                    a = 1 / Math.sqrt(c.length*c[0].length);
                else if (k == 0 || l == 0)
                     a =  Math.sqrt(2.0/ (c.length *c[0].length));
                 else {
                     a= (2.0 / Math.sqrt(c.length*c[0].length));
                }
            	sum += ( c[k][l]* a * Math.cos((((2 * i+1)/(2.0 * c.length) * k * Math.PI))) * Math.cos((((2 * j+1)/(2.0 * c[0].length) * l * Math.PI))));
            	
                 originalMatrix[i][j]=sum;
                
              }
            }
           
          }
        }
        return originalMatrix;
	}
	
	public static double [][] applyDCT2(int[][] f){
    	double[][] fd = new double[f.length][f[0].length];
    	for (int i = 0; i < f.length; i++) {
			for (int j = 0; j < f[0].length; j++) {
				fd[i][j] = f[i][j];
			}
		}
    	return applyDCT2(fd);
    }
	
	public static double [][] applyIDCT2(int[][] f){
    	double[][] fd = new double[f.length][f[0].length];
    	for (int i = 0; i < f.length; i++) {
			for (int j = 0; j < f[0].length; j++) {
				fd[i][j] = f[i][j];
			}
		}
    	return applyIDCT2(fd);
    }
	
	
	
	public static long[][] compare(int n, int max, int step) {
		
		DoubleDCT_2D dct2dtest;
		
		long[][] result = new long[2][(max-n)/step];
		double matrix[][];
		double matrixJtransform[][];
		
		
		for (int i = 0; i < result[0].length; i++) {
			dct2dtest = new DoubleDCT_2D(n+i*step, n+i*step);
			matrix  = MatrixOperations.initRandMatrix(n+i*step);
			matrixJtransform= MatrixOperations.matrixCopy(matrix);
			
			//dct2 home made performance calculation 
			long startTimeDCT2 = System.nanoTime();
			matrix=applyDCT2(matrix);
			long endTimeDCT2 = System.nanoTime();
			long durationDCT2 = (endTimeDCT2 - startTimeDCT2)/1000;
			result[0][i]=durationDCT2;
			
			//dct2 JTrasform library performance calculation 
			long startTimeJtransform = System.nanoTime();
			dct2dtest.forward(matrixJtransform, true);
			long endTimeJtransform = System.nanoTime();
			long durationJtransform = (endTimeJtransform - startTimeJtransform)/1000;
			result[1][i]=durationJtransform;
		}
		
		return result;
	}	
	


}
