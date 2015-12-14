package ie.gmit.sw;

import java.rmi.RemoteException;

import java.rmi.server.UnicastRemoteObject;

// RMI
public class VigenereBreakerImpl extends UnicastRemoteObject implements VigenereBreaker {

	private static final long serialVersionUID = 1L;
	private KeyEnumerator breaker;
	
	public VigenereBreakerImpl() throws Exception {
		breaker = new KeyEnumerator();	
	}
	
	@Override
	public String[] decrypt(String cypherText, int maxKeyLength) throws RemoteException {	
		String[] result = breaker.crackCypher(cypherText, maxKeyLength);
		return result;
	}
	
}
