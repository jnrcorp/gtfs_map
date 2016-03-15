<%@ page language="java" isErrorPage="true" session="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%if(exception!=null && exception.getMessage()!=null && exception.getMessage().contains("Too many active sessions")){%>
	<jsp:forward page="exceedMaxSession.jsp"/>
<%}%>
<%if(exception!=null){request.setAttribute("exception", exception);}%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
	<title><fmt:message key="errorPage.title"/></title>
	<meta name="googlebot" content="nofollow" />
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/styles/ovcalendar.css"/>" />
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/styles/styles_v2.css"/>" />
</head>
<body>
<div id="screen">
    <div id="content">
    <h1><fmt:message key="errorPage.heading"/></h1>
    <div><fmt:message key="errorPage.title" /></div>
	<div>${exception}</div>
	<pre><fmt:message key="errors.general"/></pre>
    </div>
</div>
</body>
</html>
