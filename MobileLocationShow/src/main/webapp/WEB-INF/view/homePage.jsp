<%-- 
    Document   : homePage
    Created on : Nov 30, 2013, 12:02:30 AM
    Author     : Nazmul
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="Namzul">
        <title>Mobile Location Show</title>

        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/assets/css/bootstrap.css" rel="stylesheet">

        <style type="text/css">
            body {
                padding-top: 50px;
            }
            .starter-template {
                padding: 40px 15px;
                text-align: center;
            }
        </style>

        <!-- Just for debugging purposes. Don't actually copy this line! -->
        <!--[if lt IE 9]><script src="assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->
        
        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
        <script src="http://maps.google.com/maps/api/js?sensor=true"/></script>
        <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
        <script type="text/javascript">
            var usersLocation = ${AllLocationWithProfilesList};
            var loggedInLocation = ${LoggedInLocationWithProfilesList};
            $(document).ready(function (){
                initialize();
            });
        </script>
        <script src="${pageContext.request.contextPath}/assets/js/maps.js"></script>
        <style type="text/css">
            #map_canvas{
                border: 2px solid #CCCCCC;
                border-radius: 5px;
                height: 500px;
                width: 100%;
            }
        </style>
    </head>

    <body>
        <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="javascript:void(0);">ZooZ Users Map</a>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="javascript:void(0);">Home</a></li>
                        <li><a href="javascript:void(0);">About</a></li>
                        <li><a href="javascript:void(0);">Contact</a></li>
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </div>
        <div class="container">
            <div class="starter-template">
                <div class="row pull-left" style="margin-bottom: 20px; margin-left: 0px;">
                    <a id="registerd" class="btn btn-primary" onclick="javascript: registeredLocation();">Registered Users</a>
                    <a id="loggedin" class="btn btn-default" onclick="javascript: loggedLocation();">Currently Online</a>
                </div>
                <div class="row">
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                        <div id="map_canvas"></div>
                    </div>
                </div>
            </div>
        </div><!-- /.container -->
    </body>
</html>
