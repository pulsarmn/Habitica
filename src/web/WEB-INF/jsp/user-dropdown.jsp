<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="bundles.jsp"%>

<div class="user-dropdown-menu" id="userDropdownMenu">
    <div class="dropdown-item" id="language-item">
        <span class="dropdown-icon-item">
            <span class="text"><fmt:message key="home.user.language.item" bundle="${labels}"/></span>
        </span>
    </div>
    <div class="dropdown-item delete-task-item" id="logout-item">
        <span class="dropdown-icon-item">
            <span class="text"><fmt:message key="home.user.logout.item" bundle="${labels}"/></span>
        </span>
    </div>
</div>