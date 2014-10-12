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
		System.out.println("Time to serve the request:" + time);
		
		RequestThread requestThread = new RequestThread();
		requestThread.setCpu(cpu);
		requestThread.setHd(storage);
		requestThread.setLocation(location);
		requestThread.setRam(ram);
		requestThread.setTime(time);
		requestThread.setServer(server);
		requestThread.setRequest(request);
		
		requestThread.start();
				
		
		return "";
	}
	
	
	
}
