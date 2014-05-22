<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Connect ! At Globalhood !</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="resources/styles/styles.css" rel="stylesheet" type="text/css" />
</head>
<body>

<div id="main">
<!-- header begins -->
	<tiles:insertAttribute name="header" />
<!-- header ends -->
    <div id="cont_top" ><span style="float: right">
    <img src="resources/images/ghood_home_logo1.png"/></span>
    <div id="cont_bot">
        <!-- content begins -->
        <div id="content">
          <div id="razd">
          <!--  Left column -->
            <div id="left">
                	<h2><spring:message code="label.homeContentLeftColumnHeader1"/></h2>
                    <div class="list"><br />
                    <c:forEach items="${companyList}" varStatus="status" var="item">
						<span><a href="viewCompany?c=${item.id}">${item.name}</a> from ${item.address.city}</span><br/>
				</c:forEach>
                   
                    </div>
                    <div id="left">
                    <h2><spring:message code="label.homeContentLeftColumnHeader2"/></h2>
                    <div class="list"><br />
                    <c:forEach items="${userList}" varStatus="status" var="item">
						<span><a href="viewUser?u=${item.id}">${item.name.firstName} ${item.name.lastName}</a> of ${item.company.name}</span><br/>
				</c:forEach>
				</div>
               <!--  <div class="read"><a href="#" class="button">Read More</a></div>  -->
                    </div>
              
            </div>
            <!--  Center Column -->
            <span style="float: right">
            <div id="center">
                  
                    <div class="text">

                        <tiles:insertAttribute name="body" />
                        
                    </div>
                    
            </div>
 			</span>
          
            <div style="clear: both"><img src="resources/images/spaser.gif" alt="" width="1" height="1" /></div>
          </div>
        </div>
    </div>
    </div>
    <!-- content ends -->
    <!-- footer begins -->
    <div id="footer"><tiles:insertAttribute name="footer" /></div>
  <!-- footer ends -->
</div>
</body>
</html>
