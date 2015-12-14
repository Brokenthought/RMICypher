package ie.gmit.sw;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CrackerHandler extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String remoteHost = null;
	private static long jobNumber = 0;
	private static VigenereRequestManager vrm = new VigenereRequestManager();
	String result;


	public void init() throws ServletException {
		ServletContext ctx = getServletContext();
		remoteHost = ctx.getInitParameter("RMI_SERVER");

		//starts endlessly running thread to watch the queue
		EncryptionConsumer consumer = new EncryptionConsumer(vrm, remoteHost);
		Thread consumerThread = new Thread(consumer);
		consumerThread.start();
	}


	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		int maxKeyLength = Integer.parseInt(req.getParameter("frmMaxKeyLength"));
		String cypherText = req.getParameter("frmCypherText");
		String taskNumber = req.getParameter("frmStatus");

		out.print("<html><head><title>Distributed Systems Assignment</title>");	

		out.print("<link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\">");
		out.print("</head>");		
		out.print("<body>");

		if (taskNumber == null){
			jobNumber++;			
			Request r = new Request(cypherText,maxKeyLength,jobNumber);		
			taskNumber = new String("T" + jobNumber);				
			vrm.add(r);	
			out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");	
		}else{
			String[] jobResult = vrm.checkJobNumber(jobNumber);
			if(jobResult[0] != null)
			{				
				//little bit of bootstrap
				out.print(" <div class=\"form-group\"> <label for=\"comment\">Best match is:"
						+ "</label><textarea class=\"form-control\" rows=\"5\"  > " + jobResult[0] +  "</textarea></div>");
				out.print("Using the key " +  jobResult[1]);
				out.print("<P>Maximum Key Length: " + maxKeyLength);	
				out.print(" <div class=\"form-group\"> <label for=\"comment\">CypherText:"
						+ "</label><textarea class=\"form-control\" rows=\"5\" > " + cypherText +  "</textarea></div>");
				out.print("RMI Server is located at " + remoteHost);

			}

		}



		out.print("<form name=\"frmCracker\">");
		out.print("<input name=\"frmMaxKeyLength\" type=\"hidden\" value=\"" + maxKeyLength + "\">");
		out.print("<input name=\"frmCypherText\" type=\"hidden\" value=\"" + cypherText + "\">");
		out.print("<input name=\"frmStatus\" type=\"hidden\" value=\"" + taskNumber + "\">");
		out.print("</form>");								
		out.print("</body>");	
		out.print("</html>");	

		out.print("<script>");
		out.print("var wait=setTimeout(\"document.frmCracker.submit();\", 3000);");
		out.print("</script>");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
