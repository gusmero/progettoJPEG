package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.jtransforms.dct.DoubleDCT_2D;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controller.DCT2;

class Test1Confronta2 {

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

	@Test
	void test() {
		DCT2 dct2=new DCT2();
	     DoubleDCT_2D dct2D=new DoubleDCT_2D(10,10);
		
	     
	     long seed = 1;
	     Random r = new Random(seed);
	     double n = r.nextInt(100);
	     
	     long [] performance=dct2.confronta2((int)n);
	     
	     assertNotNull(performance);
	}

}
