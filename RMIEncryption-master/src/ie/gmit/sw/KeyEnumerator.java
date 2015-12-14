package ie.gmit.sw;

public class KeyEnumerator {

	private QuadgramMap map = null;
	private float bestScore;
	private String bestKey;
	
	public KeyEnumerator() throws Exception{
		map = new QuadgramMap("./WarAndPeace.txt");
	}
	
	private char[] getNextKey(char[] key){
		for (int i = key.length - 1; i >=0; i--){
			if (key[i] =='Z'){
				if (i == 0) return null;
				key[i] = 'A';
			}else{ 
				key[i]++;
				break;
			}
		}
		return key;
	}
	
	
	public String[] crackCypher(String cypherText, int maxKeyLength){
		char[] key = null;
		
	//int counter = 0;
		for (int j = 3; j <= maxKeyLength; j++){
			key = new char[j];
			for (int k = 0; k < key.length; k++){ 
				key[k] = 'A';
			}	
			do{
				//counter++;
				String result = new Vigenere(new String(key)).doCypher(cypherText, false);
				float score = map.getScore(result);
				if (score > bestScore) {
					bestScore = score;
					bestKey = new String(key);
				}			
			}while ((key = getNextKey(key)) != null);
		}
//		System.out.println("Enumerated " + counter + " keys.");
//		System.out.println("Best key is:  " + bestKey);
		
		String bestResult[] = {new Vigenere(bestKey).doCypher(cypherText, false),bestKey};

		
		return bestResult;
	}
	
//	public static void main(String[] args) throws Exception {
//		new KeyEnumerator().crackCypher(cypherText, 3);
//		
//	}
}