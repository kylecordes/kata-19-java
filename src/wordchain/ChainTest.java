package wordchain;

import java.io.IOException;

import junit.framework.TestCase;

/**
 * @author Kyle
 */
public class ChainTest extends TestCase {

	static ChainFinder cf = new ChainFinder();

	public void testAdjacent() {
		assertTrue(cf.adjacent("coo", "cox"));
		assertTrue(cf.adjacent("boo", "bpo"));
		assertTrue(cf.adjacent("angke", "angle"));
		assertFalse(cf.adjacent("foo", "angle"));
		assertFalse(cf.adjacent("fox", "mom"));
		assertFalse(cf.adjacent("berry", "larry"));
	}
	
	
	private void checkScenario(String start, String end, String chain)
		throws WordNotInDictionaryException, IOException {
		String actualChain = cf.findChain(start, end);
		if(! chain.equals(actualChain)) {
			System.out.println("E: " + chain);
			System.out.println("A: " + actualChain);
		}
		assertEquals(chain, actualChain);
	}

	public void testNoSteps() throws Exception {
		checkScenario("cat", "cat", "cat");
	}

	public void testDetectNotInDict() throws Exception {
		try {
			checkScenario("cat", "qat", "cat,qat");
			fail("should have objected to non dict word");
		} catch (WordNotInDictionaryException eexpected) {
			// ok
		}
	}

	public void testOneStep() throws Exception {
		checkScenario("cat", "cot", "cat,cot");
	}
	
	public void testTwoStep1() throws Exception {
		checkScenario("cat", "cog", "cat,cot,cog");
	}

	public void testTwoStep2() throws Exception {
		checkScenario("lead", "goad", "lead,load,goad");
	}

	public void testCatDog() throws Exception {
		checkScenario("cat", "dog", "cat,cot,cog,dog");
	}

	public void testLeadGold() throws Exception {
		checkScenario("lead", "gold", "lead,load,goad,gold");
	}

	public void testRubyCode() throws Exception {
		//checkScenario("ruby", "code", "ruby,rubs,robs,rods,rode,code");
		checkScenario("ruby", "code", "ruby,rubs,robs,robe,rode,code");
	}

	public void testCodeRuby() throws Exception {
		//checkScenario("code", "ruby", "code,rode,rods,robs,rubs,ruby");
		checkScenario("code", "ruby", "code,rode,robe,robs,rubs,ruby");
	}

}
