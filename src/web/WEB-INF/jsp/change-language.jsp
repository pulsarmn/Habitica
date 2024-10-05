<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="bundles.jsp"%>

<div class="modal-overlay" id="choose-language-modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2><fmt:message key="home.change.language.header" bundle="${labels}"/></h2>
            <div class="modal-header-buttons">
                <button class="close-modal" id="close-modal-btn"><fmt:message key="home.edit.cancel.link" bundle="${labels}"/></button>
                <button class="save-entity"><fmt:message key="home.edit.save.button" bundle="${labels}"/></button>
            </div>
        </div>
        <div class="modal-body">
            <label for="user-language"><fmt:message key="home.change.language.label" bundle="${labels}"/></label>
            <select id="user-language">
                <option value="ru_RU">Русский</option>
                <option value="en_US">English</option>
                <option value="de_DE">Deutsch</option>
                <option value="fr_FR">Français</option>
            </select>
        </div>
    </div>
</div>
<script type="application/json" id="langData">${requestScope.lang}</script>