package ie.gmit.sw;

import java.util.*;
import java.util.Map.Entry;
import java.io.*;

public class QuadgramMap {
	private Map<String, Integer> map = new HashMap<String, Integer>();
	// maps an object
	
	public QuadgramMap(String filename) throws Exception {
		parse(filename);
	}
	
	public float getScore(String text) {
		float score = 0.00f;
		for (int i = 0; i < text.length(); i++) {
			if (i+4 > text.length()) break;
			
			String next = text.substring(i, i+4);
			
			if (map.get(next) != null) {
				float frequency = (float)map.get(next);
				float total = (float)map.size();
				
				score += Math.log10(total/frequency);
			}
		}
		
		return score;
	}
	
	private void parse(String filename) throws Exception{
		// don't need bufferedreader here
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		StringBuffer sb = new StringBuffer();
		
		// need to be able to parse any file
		int j;
		while ((j = br.read()) != -1) {
			char next = (char) j;
			
			if (next >= 'A' && next <= 'Z' || next >= 'a' && next <= 'z') {
				sb.append(next);
			}
//			else
//			{
//				sb = new StringBuffer();
//			}
			if (sb.length() == 4) {
				String qGram = sb.toString().toUpperCase();
				sb = new StringBuffer();
				// this should all be in different methods
				
				int frequency = 0;
				if (map.containsKey(qGram)) {
					frequency = map.get(qGram);
				}
				
				frequency++;
				
				map.put(qGram, frequency);
			}
			
		}
		br.close();
		
		//System.out.println(map);
	}
	
	public static void main(String[] args) throws Exception {
		QuadgramMap war = new QuadgramMap("./WarAndPeace.txt");
		int largest =0;
		String key = "";
		for (Entry<String, Integer> entry : war.map.entrySet())
		{
		    if (largest <  entry.getValue()) {
		    	largest =  entry.getValue();
		    	key = entry.getKey();
		    }
		    
		}
		System.out.println(key + "   "+war.map.get(key));
	}
}
