<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach var="task" items="${requestScope.tasks}">
    <%@ include file="task.jsp"%>
</c:forEach>