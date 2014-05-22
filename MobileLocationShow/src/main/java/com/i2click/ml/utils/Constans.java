/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.i2click.ml.utils;

/**
 *
 * @author Nazmul
 */
public class Constans {
    // status code and message constan command
    // success result
    public final static Integer SUCCESS_CODE = 200;
    public final static String SUCCESS_MESSAGE = "Success";
    // invalid command
    public final static Integer INVALID_COMMAND_CODE = 400;
    public final static String INVALID_COMMAND_MESSAGE = "Bad Request";
    // invalid parameters
    public final static Integer INVALID_PARAMETERS_CODE = 449;
    public final static String INVALID_PARAMETERS_MESSAGE = "Retry with valid parameters";
    // requierd parameters
    public final static Integer INVALID_AUTHORITATIVE_CODE = 203;
    public final static String INVALID_AUTHORITATIVE_MESSAGE = "Non-Authoritative Information";
    public final static Integer INVALID_REQUIERD_CODE = 203;
    public final static String INVALID_REQUIERD_MESSAGE = "Information Missing.";
    // invalid login credential
    public final static Integer INVALID_CREDENTIAL_CODE = 203;
    public final static String INVALID_CREDENTIAL_MESSAGE = "Invalid username Or password.";
    public final static Integer INVALID_SESSIONKEY_CODE = 203;
    public final static String INVALID_SESSIONKEY_MESSAGE = "Invalid sessionkey";
    public final static Integer INVALID_CODE = 203;
    public final static String INVALID_MESSAGE = "we are unable to prcess, please try again.";
    
    
    // string constan
    public final static String CODE = "code";
    public final static String STATUS = "status";
    public final static String MESSAGE = "message";
    public final static String ERROR_DESCRIPTION = "error_description";
    public final static String DETAILS = "details";
}
