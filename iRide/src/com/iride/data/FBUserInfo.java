package com.iride.data;

import java.util.Calendar;

import com.facebook.model.GraphLocation;
import com.facebook.model.GraphUser;

public class FBUserInfo {
	
	private static FBUserInfo instance = null;
	
	private String userName = "";
	private String fullName = "";
	private String homeTown = "";
	private int age = 0;
	private String id = "";
	private String fbAccessToken = "";
	
	public FBUserInfo(){
		
	}
	
	public FBUserInfo(GraphUser user, String accessToken){
		userName = user.getUsername();
		fullName = user.getName();
		fbAccessToken = accessToken;
		id = user.getId();
		if(user.getLocation()!=null)
			homeTown = (String) user.getLocation().getProperty("name");
		else
			homeTown = "";
		//System.out.println(homeTown1.getProperty(propertyName)
		try{
			if(user.getBirthday() != null || !user.getBirthday().equalsIgnoreCase(""))
				age = calculateAge(user.getBirthday());
			else
				age = 0;
		}
		catch(Exception ex)
		{
			age = 0;		
		}
	}
	
	private int calculateAge(String birthday) {
		Calendar now=  Calendar.getInstance();
        Calendar birtdate = Calendar.getInstance();
        String[] strBirthDate = birthday.split("/"); 
        
        int mYear = Integer.parseInt(strBirthDate[2]);
        int mMonth = Integer.parseInt(strBirthDate[0]);
        int mDay = Integer.parseInt(strBirthDate[1]);
        
        
        birtdate.set(mYear, mMonth, mDay);

        int years = now.get(Calendar.YEAR) - birtdate.get(Calendar.YEAR);
        int months = now.get(Calendar.MONTH) - birtdate.get(Calendar.MONTH);
        int days = now.get(Calendar.DAY_OF_MONTH) - birtdate.get(Calendar.DAY_OF_MONTH);
        if (days < 0){
            months --;
        }
        if (months < 0){
            years --;
        }
		return years;
	}

	public static FBUserInfo getInstance()
	{
		if(instance == null){
			instance = new FBUserInfo();
		}
		return instance;
	}
	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * @return the homeTown
	 */
	public String getHomeTown() {
		return homeTown;
	}
	/**
	 * @param homeTown the homeTown to set
	 */
	public void setHomeTown(String homeTown) {
		this.homeTown = homeTown;
	}
	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}
	
	/**
	 * @return the fbAccessToken
	 */
	public String getFbAccessToken() {
		return fbAccessToken;
	}
	/**
	 * @param fbsessionkey the fbAccessToken to set
	 */
	public void setFbAccessToken(String strfbAccessToken) {
		this.fbAccessToken = strfbAccessToken;
	}

}
