<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="modal-overlay active" id="edit-entity-modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2>Изменить ежедневное дело</h2>
            <div class="modal-header-buttons">
                <button class="close-modal" id="close-modal-btn">Отмена</button>
                <button class="save-entity">Сохранить</button>
            </div>
        </div>
        <div class="modal-body">
            <label for="entity-title">Заголовок*</label>
            <input type="text" id="entity-title" placeholder="Добавить название" required />

            <label for="entity-notes">Заметки</label>
            <textarea id="entity-notes" placeholder="Добавить заметку"></textarea>

            <label for="entity-difficulty">Сложность</label>
            <select id="entity-difficulty">
                <option value="TRIFLE">Пустяк</option>
                <option value="EASY">Легко</option>
                <option value="NORMAL">Нормально</option>
                <option value="DIFFICULT">Сложно</option>
            </select>

            <label for="entity-deadline">Дата начала</label>
            <input type="date" id="entity-deadline" />
        </div>
        <div class="modal-footer">
            <button class="delete-entity">Удалить задачу</button>
        </div>
    </div>
</div>
<script type="application/json" id="task-data">${requestScope.dailyTaskData}</script>