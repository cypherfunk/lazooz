<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
boolean anonUser = false;
String remoteUser = request.getRemoteUser();
if (remoteUser == null || remoteUser.equals("anonymousUser") ) {
	anonUser = true;
}%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

	
<!--  Insert header from tiles -->
	<tiles:insertAttribute name="header" />
	
	<!--  Insert body from tiles -->
	<tiles:insertAttribute name="body" />
	
	<!--  Insert header from tiles -->
	<tiles:insertAttribute name="footer" />
</html>
