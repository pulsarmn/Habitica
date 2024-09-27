<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="modal-overlay" id="edit-task-modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2>Изменить задачу</h2>
            <div class="modal-header-buttons">
                <button class="close-modal" id="close-modal-btn">Отмена</button>
                <button class="save-task">Сохранить</button>
            </div>
        </div>
        <div class="modal-body">
            <label for="task-title">Заголовок*</label>
            <input type="text" id="task-title" placeholder="Добавить название" required />

            <label for="task-notes">Заметки</label>
            <textarea id="task-notes" placeholder="Добавить заметку"></textarea>

            <label for="task-difficulty">Сложность</label>
            <select id="task-difficulty">
                <option value="TRIFLE">Пустяк</option>
                <option value="EASY">Легко</option>
                <option value="NORMAL">Нормально</option>
                <option value="DIFFICULT">Сложно</option>
            </select>

            <label for="task-deadline">Выполнить до</label>
            <input type="date" id="task-deadline" />
        </div>
        <div class="modal-footer">
            <button class="delete-task">Удалить задачу</button>
        </div>
    </div>
</div>
<script type="application/json" id="task-data">${requestScope.taskData}</script>