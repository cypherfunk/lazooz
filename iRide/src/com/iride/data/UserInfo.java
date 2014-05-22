/**
 * 
 */
package com.iride.data;

/**
 * @author Ashiq
 *
 */
public class UserInfo {
	private static UserInfo instance = null;
	
	private String userName = null;
	private String password = null;
	private String fullName = null;
	private String homeTown = null;
    private String MobileNumber = null;
	private int age = 0;
	private String sessionkey = null;
    private String PubKey = null;
    private String ActivationCode = null;
    private boolean Active = false;


	
	public static UserInfo getInstance()
	{
		if(instance == null){
			instance = new UserInfo();
		}
		return instance;
	}
    /**
     * @return the MobileNumber
     */
    public String getMobileNumber() {
        return MobileNumber;
    }
    /**
     * @param MobileNumber the MobileNumber to set
     */
    public void setMobileNumber(String MobileNumber) {
        this.MobileNumber = MobileNumber;
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return the sessionkey
	 */
	public String getSessionkey() {
		return sessionkey;
	}
	/**
	 * @param sessionkey the sessionkey to set
	 */
	public void setSessionkey(String sessionkey) {
		this.sessionkey = sessionkey;
	}

    /**
     * @return the PubKey
     */
    public String getPubKey() {
        return PubKey;
    }
    /**
     * @param PubKey the PubKey to set
     */
    public void setPubKey(String PubKey) {
        this.PubKey = PubKey;
    }

    /**
     * @return the ActivationCode
     */
    public String getActivationCode() {
        return ActivationCode;
    }
    /**
     * @param ActivationCode the ActivationCode to set
     */
    public void setActivationCode(String ActivationCode) {
        this.ActivationCode = ActivationCode;
    }

    /**
     * @return the Active
     */
    public boolean getActive() {
        return Active;
    }
    /**
     * @param Active the Active to set
     */
    public void setActive(boolean Active) {
        this.Active = Active;
    }
	
}
