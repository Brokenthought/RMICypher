package ie.gmit.sw;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class VigenereRequestManager 
{
	private BlockingQueue<Request> queue = new ArrayBlockingQueue<Request>(10);

	//changed string to a string array so it can send the key as well
	private Map<Long, String[]> out = new ConcurrentHashMap<Long, String[]>();
	

	public Request queueTake() throws InterruptedException
	{
		return queue.take();
	}
	
	public void outPut(Long jobNo, String[] result)
	{
		out.put(jobNo, result);
	}
		
	public void add(Request r)
	{
		try
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					try
					{
						queue.put(r);						
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}).start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public String[] checkJobNumber(long jobnumber)
	{
		if(out.containsKey(jobnumber))
		{
			return out.get(jobnumber);
		}
		return null;
	}


}



