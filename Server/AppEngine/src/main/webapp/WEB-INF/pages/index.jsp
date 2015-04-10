<%@ page import="ar.com.ironsoft.marroccl.web.core.constants.SharedConstants" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<!doctype html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title ng-bind="'MarrocCL - ' + $root.title"> MarrocCL </title>
    <!-- CSS
   ================================================== -->

    <!-- Bootstrap
    ================================================== -->
    <link href="/vendor/bootstrap/bootstrap.css" rel="stylesheet"
          type="text/css">
    <link href="/vendor/bootstrap/fonts/font-awesome.min.css" rel="stylesheet"
          type="text/css">
    <!-- SB Admin 2 Tempalte
    ================================================== -->
    <link href="/vendor/sb-admin/sb-admin-2.css" rel="stylesheet"
          type="text/css">
    <!-- Metis Menu
    ================================================== -->
    <link href="/vendor/metisMenu/metisMenu.min.css" rel="stylesheet"
          type="text/css">
    <!-- Notifications
   ================================================== -->
    <link href="/vendor/angular/notifications/notifications.css"
          rel="stylesheet" type="text/css">
    <!-- APP
    ================================================== -->
    <link href="app/css/app.css" rel="stylesheet" type="text/css">
    <!-- Favicons
    ================================================== -->
    <link rel="shortcut icon" href="favicon.ico">

</head>
<body>
<div id="wrapper">
    <navigation></navigation>
    <div id="page-wrapper">
        <div ng-view></div>
    </div>

    <% String appVersion = SharedConstants.APP_VERSION; %>

    <app-footer app-version="<%=appVersion%>"></app-footer>

</div>

<div notifications="top right"></div>

<%
    List<String> sl = new ArrayList<String>();
    if (SharedConstants.isDevelopmentServer()) {
        // JQUERY
        sl.add("/vendor/jquery/jquery-1.11.0.min.js");
        sl.add("/vendor/jquery/jquery.validate.min.js");
        // BOOTSTRAP
        sl.add("/vendor/bootstrap/bootstrap.min.js");
        // METIS MENU
        sl.add("/vendor/metisMenu/metisMenu.min.js");
        // ANGULAR
        sl.add("/vendor/angular/angular.min.js");
        sl.add("/vendor/angular/angular.route.min.js");
        sl.add("/vendor/angular/angular.resource.min.js");
        sl.add("/vendor/angular/angular.animate.min.js");
        // ANGULAR - BOOTSTRAP
        sl.add("/vendor/angular/bootstrap/angularui-bootstrap-0.10.0.min.js");
        // ANGULAR - NOTIFICATIONS
        sl.add("/vendor/angular/notifications/notifications.js");
        // ANGULAR - TRANSLATE
        sl.add("/vendor/angular/translate/angular-translate.min.js");
        sl.add("/vendor/angular/translate/angular-translate-loader-static-files.min.js");
        // START APP
        sl.add("/app/app.js");
        sl.add("/app/modules/modules.js");
        // CORE MODULE
        sl.add("/app/modules/core/coreModule.js");
        // FOOTER MODULE
        sl.add("/app/modules/core/footer/footerModule.js");
        // INPUTS MODULE
        sl.add("/app/modules/core/input/inputModule.js");
        // MODAL MODULE
        sl.add("/app/modules/core/modal/modalModule.js");
        // NAVIGATION MODULE
        sl.add("/app/modules/core/navigation/navigationModule.js");
        // PAGINATION MODULE
        sl.add("/app/modules/core/pagination/paginationModule.js");
        // NOTIFICATION MODULE
        sl.add("/app/modules/core/notification/growlModule.js");
        // DASHBOARD MODULE
        sl.add("/app/modules/dashboard/dashboardModule.js");
        sl.add("/app/modules/dashboard/controller/dashboardController.js");
        // DEVICES MODULE
        sl.add("/app/modules/devices/devicesModule.js");
        sl.add("/app/modules/devices/controller/deviceController.js");
        sl.add("/app/modules/devices/controller/deviceModalController.js");
        sl.add("/app/modules/devices/controller/videoMessageModalController.js");
        // GAME MODULE
        sl.add("/app/modules/game/gameModule.js");
        sl.add("/app/modules/game/controller/gameController.js");
        //
    } else if (SharedConstants.isProductionServer()) {
        sl.add("/app/all.js");
    }
    for (String s : sl) {
        String script =
                "<script type=\"text/javascript\" src=\"" + s + "\"></script>";
        out.println(script);
    }

%>

<script src="https://apis.google.com/js/client.js?onload=init"></script>

</body>
</html>
