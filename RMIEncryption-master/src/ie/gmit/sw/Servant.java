package ie.gmit.sw;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;

import java.rmi.registry.LocateRegistry;

public class Servant {
 public static void main(String[] args) throws MalformedURLException, AlreadyBoundException, Exception 
 {
		System.out.println("starting remote service...");
		
		LocateRegistry.createRegistry(1099);
		
		Naming.bind("cypher-service", new VigenereBreakerImpl());
		
		System.out.println("service started...");
}
}
