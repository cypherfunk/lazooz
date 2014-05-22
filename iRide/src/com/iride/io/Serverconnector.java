package com.iride.io;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;

/**
 * 
 * @author Ashiq
 *
 */
public class Serverconnector implements Runnable
{
	public static final int HTTP_STRING = 0;
	public static final int HTTP_IMAGE = 1;
	
	public static final String HTTP_GET_METHOD = "GET";
	public static final String HTTP_POST_METHOD = "POST";
	
	private ServerResponseListener serverResponseListener;
	private URL URLString;
	private String httpMethod = HTTP_GET_METHOD;
	private int requestType = -1;
	private int returnDataType = -1;
	
	private boolean isZipped = false;
	
	
	DispatchHandler dispatchHandler;
	
	public Serverconnector(ServerResponseListener serverResponseListener, Activity activity, String serverAddress, 
			Hashtable<String, String> params, int requestType, int returnDataType) 
	{
		
		this.serverResponseListener = serverResponseListener;
		
		StringBuffer url = new StringBuffer();
		try 
		{
			url.append(serverAddress);
			
			if (params != null)
			{
				Enumeration <String>elements = params.keys();
				
				int i = 0;
				while (elements.hasMoreElements()) 
				{
					String key = (String) elements.nextElement();
					String value = (String) params.get(key);

					if (i > 0) 
					{
						url.append("&");
					}
					url.append(key);
					url.append("=");
					url.append(URLEncoder.encode(value, "UTF-8"));
					i++;
				}
				
			}
			
			System.out.println("Before URL: " + url.toString());
			this.URLString = new URL(url.toString());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		this.httpMethod = HTTP_GET_METHOD;
		this.requestType = requestType;
		this.returnDataType = returnDataType;
	}
	
	
	public Serverconnector(ServerResponseListener serverResponseListener, Activity activity, String serverAddress,
			Hashtable<String, String> params, String httpMethod, int requestType, int returnDataType)
	{
		this(serverResponseListener, activity, serverAddress, params, requestType, returnDataType);
		this.httpMethod = httpMethod;
	}
	
	public void start()
    {
		dispatchHandler = new DispatchHandler(serverResponseListener);
		(new Thread(this)).start();
    }

	

	
	public void run()
	{
		// create handler
		HttpURLConnection connection = null;
		InputStream inputStream = null;
		
		try
		{
			System.out.println("URL:"+URLString.toString());
			
			
			try
			{
				System.out.println("Trying to connect with internet");
				connection = (HttpURLConnection) URLString.openConnection();
				
			}
			catch(Exception e)
			{
				throw new Exception("Server down");
			}
			
			connection.setRequestMethod((this.httpMethod == null || this.httpMethod.equals(HTTP_POST_METHOD)) ? HTTP_POST_METHOD : HTTP_GET_METHOD);
			//connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Accept", "*/*");
            
            try
            {
            	inputStream = connection.getInputStream();
            }
            catch(Exception e)
			{
				throw new Exception("No internet is available");
			}
            
			int resCode = connection.getResponseCode();
			
			if (resCode != HttpURLConnection.HTTP_OK)
			{
				if(resCode == HttpURLConnection.HTTP_MOVED_PERM ||
                        resCode == HttpURLConnection.HTTP_MOVED_TEMP || resCode == HttpURLConnection.HTTP_SEE_OTHER)
                {
                      this.URLString = new URL(connection.getHeaderField("Location")) ;
                      System.out.println("Redirected url :" + this.URLString.toString());
                      
                      run();                     
                }
                else
                {
                	throw new Exception("Server down");
                }
			}
			else 
			{
				if (isZipped)
				{
					
					inputStream = new GZIPInputStream(inputStream);	
//					Logger.consolePrint("gzipped data");
				}
				
				if(this.returnDataType == HTTP_STRING)
				{
					byte b[] = new byte[2048];
					StringBuffer stb = new StringBuffer();
					int readed = -1;
					while((readed = inputStream.read(b)) != -1)
					{
						stb.append(new String(b, 0, readed));
					}
					b = null;
//					Logger.consolePrint("Data223:"+new String(stb));
					if (this.dispatchHandler != null)
					{
						this.dispatchHandler.setData(new Response(new String(stb), this.requestType, returnDataType, true));
						this.dispatchHandler.sendMessage(new Message());
					}
				}
				else if(this.returnDataType == HTTP_IMAGE)
				{
					Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
					if (this.dispatchHandler != null)
					{
						this.dispatchHandler.setData(new Response(bitmap, this.requestType, returnDataType, true));
						this.dispatchHandler.sendMessage(new Message());
					}
				}
			}
		} 
		catch (Exception e) 
		{
			if (this.dispatchHandler != null)
			{
				this.dispatchHandler.setData(new Response(null, requestType, HTTP_STRING, false, e, e.getMessage()));
				this.dispatchHandler.sendMessage(new Message());
			}
			System.out.println("Sever Connector: Exception in Server connetion");
			e.printStackTrace();
			
		}
		finally
		{
			try
			{
				if (connection != null)
				{
					connection.disconnect();
				}
				if (inputStream != null)
				{
					inputStream.close();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	public void setGZipValue(boolean isGzipped)
	{
		this.isZipped = isGzipped;
	}
}
