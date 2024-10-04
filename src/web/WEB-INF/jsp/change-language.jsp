<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="modal-overlay" id="choose-language-modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2>Изменить язык</h2>
            <div class="modal-header-buttons">
                <button class="close-modal" id="close-modal-btn">Отмена</button>
                <button class="save-entity">Сохранить</button>
            </div>
        </div>
        <div class="modal-body">
            <label for="user-language">Язык</label>
            <select id="user-language">
                <option value="ru_RU">Русский</option>
                <option value="en_US">Английский</option>
                <option value="de_DE">Немецкий</option>
                <option value="fr_FR">Французский</option>
            </select>
        </div>
    </div>
</div>
<script type="application/json" id="langData">${requestScope.lang}</script>