<%-- 
    Document   : layout-public
    Created on : Nov 28, 2013, 1:37:48 AM
    Author     : Nazmul
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!--  Insert header from tiles -->
<tiles:insertAttribute name="header" />

<!--  Insert body from tiles -->
<tiles:insertAttribute name="body" />

<!--  Insert header from tiles -->
<tiles:insertAttribute name="footer" />

