package ie.gmit.sw;

import java.rmi.Naming;

public class EncryptionConsumer implements Runnable{
	private VigenereRequestManager vrm;
	private String remoteHost;
	
	public EncryptionConsumer(VigenereRequestManager vrm, String remoteHost) {
		this.vrm = vrm;
		this.remoteHost = remoteHost;
	}
	
	public void run() {
		while(true){	
			
			try {
				Request req = vrm.queueTake();
				String[] result;
				VigenereBreaker vb = (VigenereBreaker) Naming.lookup(remoteHost + "/cypher-service");								
			    result = vb.decrypt(req.getCypherText(), req.getMaxKeySize());
				vrm.outPut(req.getJobNumber(),result);
				
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}

	}

}