<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="bundles.jsp"%>
<div class="modal-overlay" id="edit-entity-modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2><fmt:message key="home.edit.reward.header" bundle="${labels}"/></h2>
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


            <label for="entity-cost"><fmt:message key="home.edit.entity.cost" bundle="${labels}"/></label>
            <input type="number" id="entity-cost" min="0" step="0.01"/>
        </div>
        <div class="modal-footer">
            <button class="delete-entity"><fmt:message key="home.reward.delete.button" bundle="${labels}"/></button>
        </div>
    </div>
</div>
<script type="application/json" id="reward-data">${requestScope.rewardData}</script>