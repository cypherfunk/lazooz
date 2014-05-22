/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.i2click.ml.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Nazmul
 */
public class RESTfulUtils {

    public static String getCurrentDateTime(){
        String datetime = "0000-00-00 00:00:00";
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            datetime = dateFormat.format(date);
            System.out.println(dateFormat.format(date));
        } catch (Exception ex) {
            System.out.println("Exception :: "+ex.getMessage());
        }
        return datetime;
    }
    
    // it's for public call for set sccess result with details
    public static HashMap restSuccessResultWithDetails(String string, HashMap details) {
        return successResult(string, details);
    }

    // it's for public call for set sccess result
    public static HashMap restSuccessResult(String string) {
        return successResult(string, null);
    }

    // it's for public call for set error result
    public static HashMap restErrorResult(Integer code, String string) {
        return errorResult(code, string);
    }

    // check is valid json command or not
    public static boolean isValidCommand(String command) {
        return isJSONObject(command) || isJSONArray(command);
    }
    
    // check is valid json paramteters or not
    public static HashMap validParameters(String command, String[] parametersList) {
        if(parametersList != null && parametersList.length > 0){
            for(int i=0; i<parametersList.length; i++){
                JSONObject cmdJson = new JSONObject(command);
                if(!cmdJson.has(parametersList[i])){
                    return errorResult(Constans.INVALID_PARAMETERS_CODE, Constans.INVALID_PARAMETERS_MESSAGE + ", " + parametersList[i] + " Missing.");
                }
            }
        }
        return null;
    }

    public static String getMD5(String md5) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            return "";
        }
    }
    // ########### call private method below here ############################ 
    // common or genarel error message with details result set
    private static HashMap errorResult(Integer code, String message) {
        HashMap result = new HashMap();
        try {
            result.put(Constans.CODE, code);
            result.put(Constans.STATUS, false);
            result.put(Constans.MESSAGE, message);
        } catch (Exception ex) {
            serverError(ex);
        }
        return result;
    }

    // common or genarel success message with details result set
    private static HashMap successResult(String message, HashMap details) {
        HashMap result = new HashMap();
        try {
            result.put(Constans.CODE, 200);
            result.put(Constans.STATUS, true);
            result.put(Constans.MESSAGE, message);
            if (details != null && !details.isEmpty()) {
                result.put(Constans.DETAILS, details);
            }
        } catch (Exception ex) {
            serverError(ex);
        }
        return result;
    }

    // when serve rest api and any error create by server then result with error message
    private static HashMap serverError(Exception exception) {
        HashMap result = new HashMap();
        result.put(Constans.CODE, 500);
        result.put(Constans.STATUS, false);
        result.put(Constans.MESSAGE, "Internal Server Error");
        result.put(Constans.ERROR_DESCRIPTION, exception.getMessage());
        return result;
    }

    // check is valid json object or not
    private static boolean isJSONObject(String possibleJson) {
        try {
            new JSONObject(possibleJson);
            return true;
        } catch (JSONException ex) {
            return false;
        }
    }

    // check is valid json array or not
    private static boolean isJSONArray(String possibleJson) {
        try {
            new JSONArray(possibleJson);
            return true;
        } catch (JSONException ex) {
            return false;
        }
    }
}
