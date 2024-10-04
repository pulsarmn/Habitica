<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="bundles.jsp"%>
<div class="modal-overlay active" id="edit-entity-modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2><fmt:message key="home.edit.daily.header" bundle="${labels}"/></h2>
            <div class="modal-header-buttons">
                <button class="close-modal" id="close-modal-btn"><fmt:message key="home.edit.cancel.link" bundle="${labels}"/></button>
                <button class="save-entity"><fmt:message key="home.edit.save.button" bundle="${labels}"/></button>
            </div>
        </div>
        <div class="modal-body">
            <label for="entity-title"><fmt:message key="home.edit.entity.header" bundle="${labels}"/></label>
            <input type="text" id="entity-title" placeholder="<fmt:message key="home.edit.title.input" bundle="${labels}"/>" required />

            <label for="entity-notes"><fmt:message key="home.edit.entity.notes" bundle="${labels}"/></label>
            <textarea id="entity-notes" placeholder="<fmt:message key="home.edit.notes.input" bundle="${labels}"/>"></textarea>

            <label for="entity-difficulty"><fmt:message key="home.edit.entity.difficulty" bundle="${labels}"/></label>
            <select id="entity-difficulty">
                <option value="TRIFLE"><fmt:message key="home.edit.difficulty.trifle" bundle="${labels}"/></option>
                <option value="EASY"><fmt:message key="home.edit.difficulty.easy" bundle="${labels}"/></option>
                <option value="NORMAL"><fmt:message key="home.edit.difficulty.medium" bundle="${labels}"/></option>
                <option value="DIFFICULT"><fmt:message key="home.edit.difficulty.hard" bundle="${labels}"/></option>
            </select>

            <label for="entity-deadline"><fmt:message key="home.edit.entity.start-date" bundle="${labels}"/></label>
            <input type="date" id="entity-deadline" />
        </div>
        <div class="modal-footer">
            <button class="delete-entity"><fmt:message key="home.daily.delete.button" bundle="${labels}"/></button>
        </div>
    </div>
</div>
<script type="application/json" id="task-data">${requestScope.dailyTaskData}</script>