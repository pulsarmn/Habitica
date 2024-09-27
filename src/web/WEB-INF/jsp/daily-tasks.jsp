<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach var="dailyTask" items="${requestScope.dailyTasks}">
    <%@ include file="daily-task.jsp"%>
</c:forEach>