<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach var="reward" items="${requestScope.rewards}">
    <%@ include file="reward.jsp"%>
</c:forEach>