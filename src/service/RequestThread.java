package service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RequestThread extends Thread {

	private double time;
	private double cpu;
	private double hd;
	private double ram;
	private String server;
	private int location;
	private int request;
	
	
	public double getTime() {
		return time;
	}


	public void setTime(double time) {
		this.time = time;
	}


	public double getCpu() {
		return cpu;
	}


	public void setCpu(double cpu) {
		this.cpu = cpu;
	}


	public double getHd() {
		return hd;
	}


	public void setHd(double hd) {
		this.hd = hd;
	}


	public double getRam() {
		return ram;
	}


	public void setRam(double ram) {
		this.ram = ram;
	}


	public String getServer() {
		return server;
	}


	public void setServer(String server) {
		this.server = server;
	}


	public int getLocation() {
		return location;
	}


	public void setLocation(int location) {
		this.location = location;
	}


	public int getRequest() {
		return request;
	}


	public void setRequest(int request) {
		this.request = request;
	}


	public void run() {
		System.out.println("In thread");
		try {
			RequestThread.sleep((long) (time * 1000));
			generateResponse(String.valueOf(server), String.valueOf(location), String.valueOf(request), String.valueOf(cpu), String.valueOf(hd), String.valueOf(ram));
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
