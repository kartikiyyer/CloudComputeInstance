package service;


import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/")
public class RequestServer {	
	
	@Path("/request")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainText() {		
		return "hello";
	}
	
	@Path("/request")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String processRequestParameter(@FormParam("server") String server, @FormParam("location") int location, @FormParam("request") int request, @FormParam("cpu") double cpu, @FormParam("storage") double storage, @FormParam("ram") double ram, @FormParam("time") double time) {
		
		System.out.println("server: "+server);
		System.out.println("location: "+location);
		System.out.println("request: "+request);
		System.out.println("cpu: "+cpu);
		System.out.println("storage: "+storage);
		System.out.println("ram: "+ram);
		System.out.println("Time to serve the request:" + (time * 1000));
		
		try {
			Thread.sleep((long)(time * 1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		generateResponse(String.valueOf(server), String.valueOf(location), String.valueOf(request), String.valueOf(cpu), String.valueOf(storage), String.valueOf(ram));
		
		return "";
	}
	
	
	public int generateResponse(String server, String location, String request, String cpu, String storage, String ram) {
		String url = "http://"+server+":8080/LoadBalancer/response";
		String charset = "UTF-8";
		int status = 0;
		
		try {
			String query = String.format("location=%s&request=%s&cpu=%s&storage=%s&ram=%s", 
				URLEncoder.encode(location, charset),
				URLEncoder.encode(request, charset), 
				URLEncoder.encode(cpu, charset), 
			    URLEncoder.encode(storage, charset),
			    URLEncoder.encode(ram, charset));
			
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true); // Triggers POST.
			connection.setRequestProperty("Accept-Charset", charset);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
			//connection.connect();
			try (OutputStream output = connection.getOutputStream()) {
			    output.write(query.getBytes(charset));
			    output.flush();
			    output.close();
			}
			
			status = connection.getResponseCode();
			
			System.out.println("This is the status from server: "+ status);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}	
}
