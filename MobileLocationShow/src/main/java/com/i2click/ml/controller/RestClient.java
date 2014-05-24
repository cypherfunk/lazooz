
package com.i2click.ml.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class RestClient {
	 RestTemplate restTemplate;
	 static DefaultExecutor executor = new DefaultExecutor();
	 
	 	static void execHelper(String command) throws ExecuteException, IOException {
	 		System.out.println("Executing: "+ command);
/*
	 		CommandLine commandLine = new CommandLine(command);
    		DefaultExecutor executor = new DefaultExecutor();
    		executor.execute(commandLine);
    		*/
    		String s = null;

    		        try {
    		            
    	 // run the Unix "ps -ef" command
    		            // using the Runtime exec method:
    		        	System.setProperty("user.dir", "/home/rideshare/");
    		        	File workdir = new File("/home/rideshare/");
    		            Process p = Runtime.getRuntime().exec(command, null, workdir);
    		            
    		            BufferedReader stdInput = new BufferedReader(new 
    		                 InputStreamReader(p.getInputStream()));

    		            BufferedReader stdError = new BufferedReader(new 
    		                 InputStreamReader(p.getErrorStream()));

    		            // read the output from the command
    		            System.out.println("Here is the standard output of the command:\n");
    		            while ((s = stdInput.readLine()) != null) {
    		                System.out.println(s);
    		            }
    		            
    		            // read any errors from the attempted command
    		            System.out.println("Here is the standard error of the command (if any):\n");
    		            while ((s = stdError.readLine()) != null) {
    		                System.out.println(s);
    		            }
    		            
    		         
    		        }
    		        catch (IOException e) {
    		            System.out.println("exception happened - here's what I know: ");
    		            e.printStackTrace();
    		         
    		        }
    		    }
    		
	 	
	 	
	    public static void SendRest(String cmd) {
	    	try {
	    		 
	    		URL url = new URL("https://test.omniwallet.org/v1/transaction/send/");
	    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    		conn.setRequestMethod("POST");
	    		conn.setRequestProperty("Accept", "application/json");
	    		conn.setDoOutput(true);
	    		OutputStream os = conn.getOutputStream();
	    		System.out.println("To Server ...."+cmd);
	    		os.write(cmd.getBytes());
	    		os.flush();
	    		if (conn.getResponseCode() != 200) {
	    			throw new RuntimeException("Failed : HTTP error code : "
	    					+ conn.getResponseCode());
	    		}
	     
	    		BufferedReader br = new BufferedReader(new InputStreamReader(
	    			(conn.getInputStream())));
	     
	    		String output;
	    		System.out.println("Output from Server .... \n");
	    		
	    		String json = "";
	    		while ((output = br.readLine()) != null) {
	    			json += output;
	    			System.out.println(output);
	    		}
	    		JSONObject res = new JSONObject(json);
	    		
	    		String hex = res.getString("transaction");
	    		execHelper("rm -rf /home/rideshare/txfile.tx /home/rideshare/txfile.tx.signed");
	    		File tx = new File("/home/rideshare/txfile.tx");
	    		FileOutputStream fos= new FileOutputStream(tx);
	    		fos.write(hex.getBytes());
	    		fos.flush();
	    		fos.close();
	    		//execHelper("echo " + hex + " > /home/rideshare/txfile.tx");
	    		execHelper("/home/rideshare/mktk.sh");
	    		execHelper("sx sendtx-obelisk /home/rideshare/txfile.tx.signed");
	    		
	    		conn.disconnect();
	     
	    	  } catch (MalformedURLException e) {
	     
	    		e.printStackTrace();
	     
	    	  } catch (IOException e) {
	     
	    		e.printStackTrace();
	     
	    	  }
	     
	    	}
	     
	       
	    }



