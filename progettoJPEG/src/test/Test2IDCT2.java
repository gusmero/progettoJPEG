package test;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utils.DCT2;
import utils.MatrixOperations;

class Test2IDCT2 {

	double [][] originalMatrix;
	double [][] originalResult;
	int [][] originalIntMatrix;
	int [][] originalIntResult;


	public Test2IDCT2() {
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

		originalIntMatrix = MatrixOperations.doubleMatrixToInt(originalMatrix);
		originalIntResult = MatrixOperations.doubleMatrixToInt(originalResult);
	}

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}


	@SuppressWarnings("deprecation")
	@Test
	void test() {
	

		double [][] matrixA = new double [originalMatrix.length][originalMatrix[0].length];
		double [][] matrixB = new double [originalMatrix.length][originalMatrix[0].length];
		int [][] matrixInt = new int [matrixA.length][matrixA[0].length];

		matrixA=DCT2.applyDCT2(originalMatrix);
		matrixB=DCT2.applyIDCT2(matrixA);
		matrixInt = MatrixOperations.doubleMatrixToInt(matrixB);
		for (int i = 0; i < matrixInt.length; i++) {
			for (int j = 0; j < matrixInt[0].length; j++) {
				//System.out.println(matrixInt[i][j]);
			}
		}

		assertEquals (originalIntMatrix, matrixInt);

	}

}
