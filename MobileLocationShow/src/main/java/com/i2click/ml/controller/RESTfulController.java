/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.i2click.ml.controller;

import com.google.gson.Gson;
import com.i2click.ml.entity.FacebookEntity;
import com.i2click.ml.entity.LocationEntity;
import com.i2click.ml.entity.LocationLogEntity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.i2click.ml.entity.ProfilesEntity;
import com.i2click.ml.service.FacebookManager;
import com.i2click.ml.service.LocationManager;
import com.i2click.ml.service.LocationLogManager;
import com.i2click.ml.service.ProfilesManager;
import com.i2click.ml.utils.Constans;
import com.i2click.ml.utils.RESTfulUtils;
import java.util.HashMap;
import java.util.UUID;

import javax.annotation.Resource;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author Nazmul
 */
@Controller
@RequestMapping("/rest/api")
public class RESTfulController {

	@Autowired
	private ProfilesManager profilesManager;
	@Resource(name = "facebookManager")
	private FacebookManager facebookManager;
	@Resource(name = "locationManager")
	private LocationManager locationManager;
	@Resource(name = "locationLogManager")
	private LocationLogManager locationLogManager;

	// login call is here
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public @ResponseBody
	HashMap getLoginInJSON(HttpServletRequest request,
			HttpServletResponse response) {
		HashMap hashMap = new HashMap();
		String command = request.getParameter("command");
		if (RESTfulUtils.isValidCommand(command)) {
			String[] parametersList = { "username", "password" };
			hashMap = RESTfulUtils.validParameters(command, parametersList);
			if (hashMap == null) {
				JSONObject cmdJson = new JSONObject(command);
				String username = cmdJson.getString("username");
				String password = RESTfulUtils.getMD5(cmdJson
						.getString("password"));

				if (username == null || username.isEmpty()) {
					hashMap = RESTfulUtils.restErrorResult(
							Constans.INVALID_REQUIERD_CODE, "USERNAME "
									+ Constans.INVALID_REQUIERD_MESSAGE);
				} else if (password == null || password.isEmpty()) {
					hashMap = RESTfulUtils.restErrorResult(
							Constans.INVALID_REQUIERD_CODE, "PASSWORD "
									+ Constans.INVALID_REQUIERD_MESSAGE);
				} else {
					ProfilesEntity profile = profilesManager.login(username,
							password);
					if (profile != null) {
						HashMap userinfo = new HashMap();
						userinfo.put("fullname", profile.getFullname());
						userinfo.put("hometown", profile.getHometown());
						userinfo.put("age", profile.getAge());
						userinfo.put("sessionkey", profile.getSessionkey());
						hashMap = RESTfulUtils.restSuccessResultWithDetails(
								"Successfully logedin.", userinfo);
					} else {
						hashMap = RESTfulUtils.restErrorResult(
								Constans.INVALID_AUTHORITATIVE_CODE,
								Constans.INVALID_AUTHORITATIVE_MESSAGE);
					}
				}
			}
		} else {
			hashMap = RESTfulUtils.restErrorResult(
					Constans.INVALID_COMMAND_CODE,
					Constans.INVALID_COMMAND_MESSAGE);
		}
		return hashMap;
	}

	// registration call is here
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public @ResponseBody
	HashMap getRegistrationInJSON(HttpServletRequest request,
			HttpServletResponse response) {
		HashMap hashMap = new HashMap();

		String command = request.getParameter("command");
		if (RESTfulUtils.isValidCommand(command)) {
			// String[] parametersList = {"username", "password", "fullname",
			// "hometown", "age"};
			String[] parametersList = { "mobilenumber", "publickey" };
			hashMap = RESTfulUtils.validParameters(command, parametersList);
			if (hashMap == null) {
				ProfilesEntity profiles = new ProfilesEntity();
				JSONObject cmdJson = new JSONObject(command);
			
				if (cmdJson.getString("mobilenumber").isEmpty()) {
					hashMap = RESTfulUtils.restErrorResult(
							Constans.INVALID_REQUIERD_CODE, "MOBILE NUMBER "
									+ Constans.INVALID_REQUIERD_MESSAGE);
				} else {
					if (!profilesManager.isAlreadyExist(cmdJson
							.getString("mobilenumber"))) {
						profiles.setUsername("Joe");
						profiles.setPassword("0");
						profiles.setFullname("Dow");
						profiles.setHometown("0");
						profiles.setAge("99");
						profiles.setActivationCode("Z00Z");
						profiles.setActive(0);
						profiles.setZooz(0);

						profiles.setCreated_datetime(RESTfulUtils
								.getCurrentDateTime());
						profiles.setMobileNumber(cmdJson
								.getString("mobilenumber"));
						profiles.setPubKey(cmdJson.getString("publickey"));
						
						/* Set session id temporary here */
						String sessionKey = UUID.randomUUID().toString();
						profiles.setSessionkey(sessionKey);
						profilesManager.addProfiles(profiles);
						HashMap userinfo = new HashMap();
						userinfo.put("mobilenumber", profiles.getMobileNumber());
						userinfo.put("sessionkey", profiles.getSessionkey());
						// hashMap =
						// RESTfulUtils.restSuccessResult("Profile successfully created.");
						hashMap = RESTfulUtils.restSuccessResultWithDetails(
								"Profile successfully created.", userinfo);
					} else {
						hashMap = RESTfulUtils
								.restErrorResult(
										Constans.INVALID_REQUIERD_CODE,
										"Mobile Number already exist, Please try another number.");
					}
				}
			}
		} else {
			hashMap = RESTfulUtils.restErrorResult(
					Constans.INVALID_COMMAND_CODE,
					Constans.INVALID_COMMAND_MESSAGE);
		}
		return hashMap;
	}

	// activation call is here
	@RequestMapping(value = "/activation", method = RequestMethod.GET)
	public @ResponseBody
	HashMap getActivationInJSON(HttpServletRequest request,
			HttpServletResponse response) {
		HashMap hashMap = new HashMap();
		String command = request.getParameter("command");
		if (RESTfulUtils.isValidCommand(command)) {
			// String[] parametersList = {"username", "password", "fullname",
			// "hometown", "age"};
			String[] parametersList = { "sessionkey", "mobilenumber",
					"activationcode" };
			hashMap = RESTfulUtils.validParameters(command, parametersList);
			if (hashMap == null) {
				ProfilesEntity profiles = new ProfilesEntity();
				JSONObject cmdJson = new JSONObject(command);

				if (cmdJson.getString("mobilenumber").isEmpty()) {
					hashMap = RESTfulUtils.restErrorResult(
							Constans.INVALID_REQUIERD_CODE, "MOBILE NUMBER "
									+ Constans.INVALID_REQUIERD_MESSAGE);
				} else {
					if (profilesManager.isAlreadyExist(cmdJson
							.getString("mobilenumber"))) {
						ProfilesEntity user_profile = profilesManager
								.findBySessionKey(cmdJson
										.getString("sessionkey"));
						if (user_profile.getActivationCode().equals(
								cmdJson.getString("activationcode"))) {
							user_profile.setActive(1);
							/* Pay one ZooZ */
							user_profile.setZooz(user_profile.getZooz() + 1);
							issueTranaction(user_profile.getPubKey(),1);
							profilesManager.addProfiles(user_profile);
							HashMap userinfo = new HashMap();
							userinfo.put("mobilenumber",
									user_profile.getMobileNumber());
							userinfo.put("sessionkey",
									user_profile.getSessionkey());
							userinfo.put("active", user_profile.getActive());
							
							hashMap = RESTfulUtils
									.restSuccessResultWithDetails(
											"Activated succesfully.", userinfo);
						} else {
							hashMap = RESTfulUtils.restErrorResult(
									Constans.INVALID_REQUIERD_CODE,
									"Wrong Activation Code,Please try again");
						}
					} else {
						hashMap = RESTfulUtils
								.restErrorResult(
										Constans.INVALID_REQUIERD_CODE,
										"Mobile Number not exist, Please try another number.");
					}
				}
			} else {
				hashMap = RESTfulUtils.restErrorResult(
						Constans.INVALID_COMMAND_CODE,
						Constans.INVALID_COMMAND_MESSAGE);
			}
		}
		return hashMap;
	}

	// logout call is here
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public @ResponseBody
	HashMap getLogoutInJSON(HttpServletRequest request,
			HttpServletResponse response) {
		HashMap hashMap = new HashMap();
		String command = request.getParameter("command");
		if (RESTfulUtils.isValidCommand(command)) {
			String[] parametersList = { "sessionkey" };
			hashMap = RESTfulUtils.validParameters(command, parametersList);
			if (hashMap == null) {
				JSONObject cmdJson = new JSONObject(command);
				if (cmdJson.getString("sessionkey").isEmpty()) {
					hashMap = RESTfulUtils.restErrorResult(
							Constans.INVALID_REQUIERD_CODE, "SESSIONKEY "
									+ Constans.INVALID_REQUIERD_MESSAGE);
				} else {
					ProfilesEntity profiles = profilesManager
							.findBySessionKey(cmdJson.getString("sessionkey"));
					if (profiles != null && profiles.getPkid() != null
							&& profiles.getPkid() > 0) {
						if (profilesManager.logout(profiles.getPkid())) {
							hashMap = RESTfulUtils
									.restSuccessResult(Constans.SUCCESS_MESSAGE);
						} else {
							hashMap = RESTfulUtils.restErrorResult(
									Constans.INVALID_CODE,
									Constans.INVALID_MESSAGE);
						}
					} else {
						hashMap = RESTfulUtils.restErrorResult(
								Constans.INVALID_SESSIONKEY_CODE,
								Constans.INVALID_SESSIONKEY_MESSAGE);
					}
				}
			}
		} else {
			hashMap = RESTfulUtils.restErrorResult(
					Constans.INVALID_COMMAND_CODE,
					Constans.INVALID_COMMAND_MESSAGE);
		}
		return hashMap;
	}

	// gpsinfo call is here
	@RequestMapping(value = "/gpsinfo", method = RequestMethod.GET)
	public @ResponseBody
	HashMap getGPSInfoInJSON(HttpServletRequest request,
			HttpServletResponse response) {
		HashMap hashMap = new HashMap();
		String command = request.getParameter("command");
		if (RESTfulUtils.isValidCommand(command)) {
			String[] parametersList = { "sessionkey", "latitude", "longitude" };
			hashMap = RESTfulUtils.validParameters(command, parametersList);
			if (hashMap == null) {
				JSONObject cmdJson = new JSONObject(command);
				if (cmdJson.getString("sessionkey").isEmpty()) {
					hashMap = RESTfulUtils.restErrorResult(
							Constans.INVALID_REQUIERD_CODE, "SESSIONKEY "
									+ Constans.INVALID_REQUIERD_MESSAGE);
				} else if (cmdJson.getString("latitude").isEmpty()) {
					hashMap = RESTfulUtils.restErrorResult(
							Constans.INVALID_REQUIERD_CODE, "LATITUDE "
									+ Constans.INVALID_REQUIERD_MESSAGE);
				} else if (cmdJson.getString("longitude").isEmpty()) {
					hashMap = RESTfulUtils.restErrorResult(
							Constans.INVALID_REQUIERD_CODE, "LONGITUDE "
									+ Constans.INVALID_REQUIERD_MESSAGE);
				} else {
					ProfilesEntity profiles = profilesManager
							.findBySessionKey(cmdJson.getString("sessionkey"));
					if (profiles != null && profiles.getPkid() != null
							&& profiles.getPkid() > 0
							&& profiles.getActive() == 1) {
						LocationEntity locationEntity = locationManager
								.getLEObject(profiles.getPkid());
						if (locationEntity == null) {
							locationEntity = new LocationEntity();
						}

						LocationLogEntity locationLogEntity = locationLogManager
								.getLEObject(profiles.getPkid());
						if (locationLogEntity == null) {
							locationLogEntity = new LocationLogEntity();
						}

						locationEntity.setLatitude(cmdJson
								.getString("latitude"));
						locationEntity.setLongitude(cmdJson
								.getString("longitude"));
						locationEntity.setLast_datetime(RESTfulUtils
								.getCurrentDateTime());
						locationEntity.setProfileid(profiles.getPkid() + "");
						if (!locationManager.isExist(profiles.getPkid())) {
							// System.out.println("1)locationEntity "+locationEntity.getPkid()+" ");
							locationManager.save(locationEntity);
						} else {
							/* locationManager.update(locationEntity); */
							// System.out.println("locationEntity "+locationEntity.getPkid()+" ");
							locationManager.update(locationEntity);
						}

						locationLogEntity.setLatitude(cmdJson
								.getString("latitude"));
						locationLogEntity.setLongitude(cmdJson
								.getString("longitude"));
						locationLogEntity.setLast_datetime(RESTfulUtils
								.getCurrentDateTime());
						locationLogEntity.setProfileid(profiles.getPkid() + "");

						locationLogManager.save(locationLogEntity);
						/* Pay one ZooZ */
						profiles.setZooz(profiles.getZooz() + 1);
						
						profilesManager.addProfiles(profiles);
						issueTranaction(profiles.getPubKey(),1);
						HashMap userinfo = new HashMap();
						userinfo.put("ZOOZ AMOUNT", profiles.getZooz());
						hashMap = RESTfulUtils.restSuccessResultWithDetails(
								Constans.SUCCESS_MESSAGE, userinfo);
						// hashMap =
						// RESTfulUtils.restSuccessResult(Constans.SUCCESS_MESSAGE);
					} else {

						hashMap = RESTfulUtils
								.restErrorResult(
										Constans.INVALID_SESSIONKEY_CODE,
										Constans.INVALID_SESSIONKEY_MESSAGE
												+ " ->Active = "
												+ profiles.getActive());
					}
				}
			}
		} else {
			hashMap = RESTfulUtils.restErrorResult(
					Constans.INVALID_COMMAND_CODE,
					Constans.INVALID_COMMAND_MESSAGE);
		}
		return hashMap;
	}

	// facebookinfo call is here
	@RequestMapping(value = "/facebookinfo", method = RequestMethod.GET)
	public @ResponseBody
	HashMap getFacebookInfoInJSON(HttpServletRequest request,
			HttpServletResponse response) {
		HashMap hashMap = new HashMap();
		String command = request.getParameter("command");
		if (RESTfulUtils.isValidCommand(command)) {
			String[] parametersList = { "access_token", "fbuid", "data" };
			hashMap = RESTfulUtils.validParameters(command, parametersList);
			if (hashMap == null) {
				JSONObject cmdJson = new JSONObject(command);
				if (cmdJson.getString("access_token").isEmpty()) {
					hashMap = RESTfulUtils.restErrorResult(
							Constans.INVALID_REQUIERD_CODE, "ACCESS_TOKEN "
									+ Constans.INVALID_REQUIERD_MESSAGE);
				} else if (cmdJson.getString("fbuid").isEmpty()) {
					hashMap = RESTfulUtils.restErrorResult(
							Constans.INVALID_REQUIERD_CODE, "FACEBOOK ID "
									+ Constans.INVALID_REQUIERD_MESSAGE);
				} else if (cmdJson.getJSONObject("data").toString().isEmpty()) {
					hashMap = RESTfulUtils.restErrorResult(
							Constans.INVALID_REQUIERD_CODE, "DATA "
									+ Constans.INVALID_REQUIERD_MESSAGE);
				} else {
					JSONObject data = cmdJson.getJSONObject("data");
					String username = "";
					if (data.has("username")) {
						username = data.getString("username");
					}
					if (username.isEmpty() || username.equalsIgnoreCase("null")) {
						username = cmdJson.getString("fbuid");
					}
					if (!username.isEmpty()) {
						ProfilesEntity profileInfo = profilesManager
								.findByUsername(username);
						if (profileInfo == null) {
							profileInfo = new ProfilesEntity();
						}
						profileInfo.setUsername(username);
						profileInfo.setCreated_datetime(RESTfulUtils
								.getCurrentDateTime());
						profileInfo.setSessionkey(cmdJson.getString("fbuid"));
						profileInfo.setPassword("");
						profileInfo.setIsfbagree("1");
						if (data.has("fullname")) {
							profileInfo.setFullname(data.getString("fullname"));
						}
						if (data.has("hometown")) {
							profileInfo.setHometown(data.getString("hometown"));
						}
						if (data.has("age")) {
							profileInfo.setAge(data.getString("age"));
						}
						profilesManager.addProfiles(profileInfo);

						ProfilesEntity profile = profilesManager
								.findByUsername(username);
						if (profile != null && profile.getPkid() != null
								&& profile.getPkid() > 0) {
							FacebookEntity facebookEntity = facebookManager
									.getFBObject(profile.getPkid());
							if (facebookEntity == null) {
								facebookEntity = new FacebookEntity();
							}
							facebookEntity.setFbuid(cmdJson.getString("fbuid"));
							facebookEntity.setData(cmdJson
									.getJSONObject("data").toString());
							facebookEntity.setAccess_token(cmdJson
									.getString("access_token"));
							facebookEntity.setProfileid(profile.getPkid() + "");
							if (!facebookManager.isExist(profile.getPkid())) {
								facebookManager.save(facebookEntity);
							} else {
								System.out.println("GSON :: "
										+ new Gson().toJson(facebookEntity));
								facebookManager.update(facebookEntity);
							}
							hashMap = RESTfulUtils
									.restSuccessResult(Constans.SUCCESS_MESSAGE);
						} else {
							hashMap = RESTfulUtils.restErrorResult(
									Constans.INVALID_CODE,
									Constans.INVALID_MESSAGE);
						}
					} else {
						hashMap = RESTfulUtils.restErrorResult(
								Constans.INVALID_REQUIERD_CODE,
								"USERNAME OR FBUID "
										+ Constans.INVALID_REQUIERD_MESSAGE);
					}
				}
			}
		} else {
			hashMap = RESTfulUtils.restErrorResult(
					Constans.INVALID_COMMAND_CODE,
					Constans.INVALID_COMMAND_MESSAGE);
		}
		return hashMap;
	}

	private boolean issueTranaction(String address,Integer Amount) {

		// JSONObject cmdJson = new JSONObject();
        /*
		String cmd = new String();

		cmd = "from_address=1Htqiy3JBG9wYyFJ6utJxzcj6BneKhHZ2M&pubKey=0482f6ed50547048a94a5d9208b14afcf6bfcb898eab44eb7eb67ed6c585e6740111a8e8da704d819a635f22512932a58621b2de871ec97c8e0f79d51657237bf1&to_address="
				+ address
				+ "&amount="+Amount+"&currency=SP2147483670&fee=10000&marker=false";

        */
		//RestClient.SendRest(cmd);
		System.out.println("Dummy...the zooz mininig is done on a seperate procees");

		return true;
	}

	/*
	 * // logged in users call is here
	 * 
	 * @RequestMapping(value = "/loggedinusers", method = RequestMethod.GET)
	 * public @ResponseBody HashMap getLoggedUsersInfoInJSON(HttpServletRequest
	 * request, HttpServletResponse response) { HashMap hashMap = new HashMap();
	 * String command = request.getParameter("command"); if
	 * (RESTfulUtils.isValidCommand(command)) { String[] parametersList =
	 * {"sessionkey"}; hashMap = RESTfulUtils.validParameters(command,
	 * parametersList); if (hashMap == null) { JSONObject cmdJson = new
	 * JSONObject(command); if (cmdJson.getString("sessionkey").isEmpty()) {
	 * hashMap = RESTfulUtils.restErrorResult(Constans.INVALID_REQUIERD_CODE,
	 * "SESSIONKEY " + Constans.INVALID_REQUIERD_MESSAGE); } else {
	 * List<ProfilesEntity> profiles = profilesManager.getAllProfiless(); if
	 * (profiles != null && profiles.size() > 0) { HashMap profilesmap = new
	 * HashMap(); profilesmap.put("list", profiles); hashMap =
	 * RESTfulUtils.restSuccessResultWithDetails(Constans.SUCCESS_MESSAGE,
	 * profilesmap); } } } } else { hashMap =
	 * RESTfulUtils.restErrorResult(Constans.INVALID_COMMAND_CODE,
	 * Constans.INVALID_COMMAND_MESSAGE); } return hashMap; // }
	 * 
	 * // logout call is here
	 * 
	 * @RequestMapping(value = "/test", method = RequestMethod.GET) public
	 * @ResponseBody HashMap getTestInJSON(HttpServletRequest request,
	 * HttpServletResponse response) { HashMap hashMap = new HashMap(); String
	 * command = request.getParameter("command");
	 * if(RESTfulUtils.isValidCommand(command)){ String[] parametersList =
	 * {"sessionkey"}; hashMap = RESTfulUtils.validParameters(command,
	 * parametersList); if(hashMap == null){ JSONObject cmdJson = new
	 * JSONObject(command); if (cmdJson.getString("sessionkey").isEmpty()) {
	 * hashMap = RESTfulUtils.restErrorResult(Constans.INVALID_REQUIERD_CODE,
	 * "SESSIONKEY " + Constans.INVALID_REQUIERD_MESSAGE); }else{ ProfilesEntity
	 * profiles =
	 * profilesManager.findBySessionKey(cmdJson.getString("sessionkey"));
	 * if(profiles != null && profiles.getPkid() != null && profiles.getPkid() >
	 * 0){ ArrayList<Object> obj = profilesManager.locationList(); if(obj !=
	 * null){ HashMap result = new HashMap(); result.put("list", obj); hashMap =
	 * RESTfulUtils.restSuccessResultWithDetails(Constans.SUCCESS_MESSAGE,
	 * result); } //if(profilesManager.logout(profiles.getPkid())) // hashMap =
	 * RESTfulUtils.restSuccessResult(Constans.SUCCESS_MESSAGE); //else //
	 * hashMap = RESTfulUtils.restErrorResult(203,
	 * "We are unable to logout, please try again."); }else{ hashMap =
	 * RESTfulUtils.restErrorResult(Constans.INVALID_SESSIONKEY_CODE,
	 * Constans.INVALID_SESSIONKEY_MESSAGE); } } } }else{ hashMap =
	 * RESTfulUtils.restErrorResult(Constans.INVALID_COMMAND_CODE,
	 * Constans.INVALID_COMMAND_MESSAGE); } return hashMap; }
	 */

}
