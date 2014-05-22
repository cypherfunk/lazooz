package com.iride.data;

public class Config {
	public static final int REQ_LOGIN = 1;
	public static final int REQ_REGISTRATION = 2;
	public static final int REQ_CREATE_ACCOUNT = 3;
	public static final int REQ_CHANGE_PASSWORD = 4;
	public static final int REQ_FORGOT_PASSWORD = 5;
	public static final int REQ_LOGOUT = 6;
	public static final int REQ_FB_LOGIN = 7;
	public static final int REQ_GPS = 8;
    public static final int REQ_ACTIVATION = 9;
	
	// 	test server
//	public static final String IRIDE_SERVER_URL = "http://192.168.0.101:8080/MobileLocationShow/rest/api/";
	//live server
	//Mike`s server http://iride.agorahost.com:8080/MobileLocationShow/
	//public static final String IRIDE_SERVER_URL = "http://iride.agorahost.com:8080/MobileLocationShow/rest/api/";
   // public static final String IRIDE_SERVER_URL = "http://188.226.187.87:8080/MobileLocationShow/rest/api/";
    public static final String IRIDE_SERVER_URL = "http://188.226.187.87:8080/LaZooZ/rest/api/";
	public static final String COMMAND = "command";
//	http://mobilelocation.jelastic.servint.net/mls/rest/api/login?command={%E2%80%98username%E2%80%99:%E2%80%99nazmul%E2%80%99,%E2%80%99password%E2%80%99:%E2%80%99123456%E2%80%99}
}
