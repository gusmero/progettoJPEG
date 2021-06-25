package Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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

		long seed = 1;
		long [][] performance=null;
		performance= dct2.compare(10 , 50 ,5);
		System.out.println(performance[0][0]);

		for (int i = 0; i < performance[0].length; i++) {
			System.out.println(performance[0][i] +" "+performance[1][i]   );
		}
		assertNotNull(performance);
	}

}
