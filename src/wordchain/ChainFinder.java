/*
 * Created on Nov 16, 2003
 */
package wordchain;

import java.util.*;
import java.io.*;

/**
 * @author Kyle
 */
public class ChainFinder {
	
	List words;

	private void readWordsOfLength(int wordLength) throws IOException {
		words = new ArrayList();
		BufferedReader r = new BufferedReader(new FileReader("wordlist.txt"));
		try {
			String line;
			while ((line = r.readLine()) != null) {
				if(line.length() == wordLength)
				words.add(line);
			}
		} finally {
			r.close();
		}
	}

	private void checkIsWord(String word) throws WordNotInDictionaryException {
		if (! words.contains(word)) {
			throw new WordNotInDictionaryException();
		}
	}

	List candidates;
	Map seen;
	int count;

	public String findChain(String start, String end)
		throws WordNotInDictionaryException, IOException {
		
		if (start.length() != end.length())
			throw new RuntimeException("words must be of the same length");

		if(start.equals(end)) 
			return start;
		
		readWordsOfLength(start.length());
		
		checkIsWord(start);
		checkIsWord(end);

		candidates = new LinkedList();
		seen = new HashMap();
		count = 0;
		
		candidates.add(start);
		seen.put(start, null);

		return searchForEndWord(end);
	}

	private String searchForEndWord(String end) {
		while (candidates.size() > 0) {
			if (count++ > 15000)
				throw new RuntimeException("exceeded search limit");

			String c = (String) candidates.remove(0);

			Iterator iter = words.iterator();
			while (iter.hasNext()) {
				String w = (String) iter.next();
				if (!seen.containsKey(w) && adjacent(c, w)) {
					seen.put(w, c);

					if (end.equals(w))
						return formatWordChain(w);

					candidates.add(w);
				}
			}
		}
		return null;
	}

	private String formatWordChain(String word) {
		String result = word;
		word = (String) seen.get(word);
		while (word != null) {
			result = word + "," + result;
			word = (String) seen.get(word);
		}
		return result;
	}

	public boolean adjacent(String a, String b) {
		int nDifferent = 0;
		int length = a.length();
		for (int i = 0; i < length; i++) {
			if (a.charAt(i) != b.charAt(i)) {
				nDifferent++;
			}
			if(nDifferent > 1)
				return false;
		}
		return true;
	}

	public static void main(String[] args) {
		ChainFinder cf = new ChainFinder();
		
		try {
			System.out.println(cf.findChain("cat", "dog"));
			System.out.println(cf.findChain("lead", "gold"));
			System.out.println(cf.findChain("ruby", "code"));
			System.out.println(cf.findChain("rogue", "Perth"));
			System.out.println(cf.findChain("crosser", "stouter"));
			System.out.println(cf.findChain("brushing", "cheating"));
			System.out.println(cf.findChain("sputtered", "blistered"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
