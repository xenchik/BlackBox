package dk.danskebank.markets.trading.client.java;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import dk.danskebank.markets.trading.client.ClientSample;

public class ClientTester {

	
	private static ClientSample sample;

	@BeforeClass
    public static void init() {
      sample = new ClientSample();
      System.out.println("Init");
    }
	
	@Test
	public void testNameNotNull() {
		String name = sample.getStrategyName();
		assertNotNull(name);
	}
	
	@Test
	public void testNameNotToShort() {
		String name = sample.getStrategyName();
		if(name.length() < 4)
			fail("Strategyname must be at least 4 chars long");
	}
	
	@Test
	public void testStrategy() {
		try {
			Random rnd = new Random(123);
			sample.tick(0.001);
			for(int i=0 ; i < 10000; i++) {
				sample.tick(rnd.nextDouble()*50);			
			}
		} catch(Exception e) {
			fail("Exception happen while streaming prices:"+e.getMessage());
		}
	}
	
	@Test
	public void testSlowDecisions() {
		
		try {
			Random rnd = new Random(123);
			sample.tick(0.001);
			long start = System.nanoTime();
			for(int i=0 ; i < 10000; i++) {
				sample.tick(rnd.nextDouble()*100000);			
			}
			long end = System.nanoTime();
			
			if(end-start > 10_000_000_000L) {
				fail("You are spending to much time analysing the market!");
			}
			
		} catch(Exception e) {
			fail("Exception happen while streaming prices:"+e.getMessage());
		}
	}
}
