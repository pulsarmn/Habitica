<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="modal-overlay" id="edit-entity-modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2>Изменить награду</h2>
            <div class="modal-header-buttons">
                <button class="close-modal" id="close-modal-btn">Отмена</button>
                <button class="save-reward">Сохранить</button>
            </div>
        </div>
        <div class="modal-body">
            <label for="reward-title">Заголовок*</label>
            <input type="text" id="reward-title" placeholder="Добавить название" required />

            <label for="reward-notes">Заметки</label>
            <textarea id="reward-notes" placeholder="Добавить заметку"></textarea>


            <label for="reward-cost">Цена</label>
            <input type="number" id="reward-cost" min="0" step="0.01" />
        </div>
        <div class="modal-footer">
            <button class="delete-entity">Удалить награду</button>
        </div>
    </div>
</div>
<script type="application/json" id="reward-data">${requestScope.rewardData}</script>