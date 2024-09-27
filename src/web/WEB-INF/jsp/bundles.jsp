<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang != null ? sessionScope.lang : locale}"/>
<fmt:setBundle basename="messages" var="messages"/>
<fmt:setBundle basename="labels" var="labels"/>
<fmt:setBundle basename="validation" var="validation"/>
<fmt:setBundle basename="errors" var="errors"/>
