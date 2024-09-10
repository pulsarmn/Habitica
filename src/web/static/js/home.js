document.addEventListener('DOMContentLoaded', function () {
    const taskInput = document.getElementById('taskInput');

    taskInput.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            e.preventDefault(); // Отменяем добавление новой строки

            const taskValue = taskInput.value.trim(); // Удаляем лишние пробелы
            if (taskValue) { // Проверяем, что поле не пустое
                // Отправляем данные на сервер
                fetch('/tasks', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: `taskHeading=${encodeURIComponent(taskValue)}` // Кодируем данные для отправки
                })
                    .then(response => {
                        if (response.ok) {
                            // Обработка успешного ответа
                            taskInput.value = ''; // Очищаем поле после отправки
                            console.assert('Задача отправлена успешно!');
                        } else {
                            // Обработка ошибки
                            console.assert('Ошибка при отправке задачи');
                        }
                    })
                    .catch(error => {
                        // Обработка сетевых ошибок
                        console.assert('Ошибка сети: ' + error.message);
                    });
            }
        }
    });

    // Ограничение на ввод только одной строки
    taskInput.addEventListener('input', function () {
        if (this.value.includes('\n')) {
            this.value = this.value.replace(/\n/g, ''); // Удаляем перенос строки, если он был вставлен
        }
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const taskInput = document.getElementById('dailyTaskInput');

    taskInput.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            e.preventDefault();

            const taskValue = taskInput.value.trim();
            if (taskValue) {
                // Отправляем данные на сервер
                fetch('/daily-tasks', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: `dailyTaskHeading=${encodeURIComponent(taskValue)}` // Кодируем данные для отправки
                })
                    .then(response => {
                        if (response.ok) {
                            // Обработка успешного ответа
                            taskInput.value = ''; // Очищаем поле после отправки
                            console.assert('Задача отправлена успешно!');
                        } else {
                            // Обработка ошибки
                            console.assert('Ошибка при отправке задачи');
                        }
                    })
                    .catch(error => {
                        // Обработка сетевых ошибок
                        console.assert('Ошибка сети: ' + error.message);
                    });
            }
        }
    });

    // Ограничение на ввод только одной строки
    taskInput.addEventListener('input', function () {
        if (this.value.includes('\n')) {
            this.value = this.value.replace(/\n/g, ''); // Удаляем перенос строки, если он был вставлен
        }
    });
});

document.getElementById('avatar').addEventListener('click', function() {
    document.getElementById('fileInput').click();
});

document.getElementById('fileInput').addEventListener('change', function() {
    const file = this.files[0];

    if (file) {
        // Проверка типа файла
        const validImageTypes = ['image/jpeg', 'image/png', 'image/gif'];
        if (!validImageTypes.includes(file.type)) {
            alert('Выберите изображение формата JPEG, PNG или GIF.');
            return;
        }

        // Проверка размера файла (например, не более 5MB)
        const maxSizeInBytes = 5 * 1024 * 1024;
        if (file.size > maxSizeInBytes) {
            alert('Файл слишком большой. Максимальный размер 5MB.');
            return;
        }

        const formData = new FormData();
        formData.append('profileAvatar', file);

        // Отправка AJAX запроса
        const xhr = new XMLHttpRequest();
        xhr.open('POST', '/upload-avatar', true);
        xhr.onload = function () {
            if (xhr.status === 200) {
                alert('Фотография успешно обновлена!');
                document.getElementById('avatar').src = URL.createObjectURL(file);
            } else {
                alert('Произошла ошибка при загрузке файла.');
            }
        };
        xhr.send(formData);
    }
});

document.querySelectorAll('.task-control').forEach(function (taskControl) {
    taskControl.addEventListener('click', function () {
        const taskWrapper = this.closest('.task-wrapper'); // Найдем родительский элемент с классом task-wrapper
        const leftControl = taskWrapper.querySelector('.left-control'); // Найдем элемент с классом left-control внутри task-wrapper
        const svgCheck = taskWrapper.querySelector('.check'); // Найдем иконку checkbox внутри текущей задачи

        const taskId = taskWrapper.querySelector('.task-id').textContent;

        if (svgCheck) { // Если checkbox не установлен
            leftControl.classList.remove('task-neutral-bg-color'); // Убираем нейтральный цвет фона у left-control
            leftControl.classList.add('task-disabled'); // Добавляем стиль для отключённой задачи на left-control

            svgCheck.classList.add('display-check-icon'); // Отображаем галочку
            svgCheck.classList.remove('check'); // Убираем старый класс

            // Отправляем запрос на удаление задачи

            fetch(`/tasks?taskId=${taskId}`, {
                method: 'DELETE'
            }).then(response => {
                if (response.ok) {
                    console.log(`Задача с ID ${taskId} удалена`);
                } else {
                    console.error('Ошибка при удалении задачи');
                }
            }).catch(error => {
                console.error('Ошибка сети:', error);
            });
        } else { // Если checkbox уже установлен
            leftControl.classList.remove('task-disabled'); // Убираем стиль для отключённой задачи
            leftControl.classList.add('task-neutral-bg-color'); // Возвращаем нейтральный цвет фона

            const displayCheck = taskWrapper.querySelector('.display-check-icon');
            displayCheck.classList.add('check'); // Возвращаем старый класс checkbox
            displayCheck.classList.remove('display-check-icon'); // Убираем класс отображаемой галочки
        }
    });
});

document.querySelectorAll('.daily-task-control').forEach(function (taskControl) {
    taskControl.addEventListener('click', function () {
        const taskWrapper = this.closest('.daily-task-wrapper'); // Найдем родительский элемент с классом task-wrapper
        const leftControl = taskWrapper.querySelector('.left-control'); // Найдем элемент с классом left-control внутри task-wrapper
        const svgCheck = taskWrapper.querySelector('.check'); // Найдем иконку checkbox внутри текущей задачи
        const displayCheck = taskWrapper.querySelector('.display-check-icon'); // Найдем отображаемую галочку

        const dailyTaskId = taskWrapper.querySelector('.daily-task-id').textContent;

        if (svgCheck) { // Если checkbox не установлен (задача еще не выполнена)
            leftControl.classList.remove('task-neutral-bg-color'); // Убираем нейтральный цвет фона у left-control
            leftControl.classList.add('task-disabled'); // Добавляем стиль для отключённой задачи на left-control

            svgCheck.classList.add('display-check-icon'); // Отображаем галочку
            svgCheck.classList.remove('check'); // Убираем старый класс

            fetch(`/daily-tasks?dailyTaskId=${dailyTaskId}`, {
                method: 'PUT',
                body: JSON.stringify({ action: 'increment' }), // Увеличиваем счётчик
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                if (response.ok) {
                    console.log(`Задача с ID ${dailyTaskId} обновлена (увеличение)`);
                } else {
                    console.error('Ошибка при увеличении счётчика');
                }
            }).catch(error => {
                console.error('Ошибка сети:', error);
            });
        } else if (displayCheck) { // Если checkbox установлен (задача выполнена)
            leftControl.classList.remove('task-disabled'); // Убираем стиль для отключённой задачи
            leftControl.classList.add('task-neutral-bg-color'); // Возвращаем нейтральный цвет фона

            displayCheck.classList.add('check'); // Возвращаем старый класс checkbox
            displayCheck.classList.remove('display-check-icon'); // Убираем класс отображаемой галочки

            fetch(`/daily-tasks?dailyTaskId=${dailyTaskId}`, {
                method: 'PUT',
                body: JSON.stringify({ action: 'decrement' }), // Уменьшаем счётчик
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                if (response.ok) {
                    console.log(`Задача с ID ${dailyTaskId} обновлена (уменьшение)`);
                } else {
                    console.error('Ошибка при уменьшении счётчика');
                }
            }).catch(error => {
                console.error('Ошибка сети:', error);
            });
        }
    });
});