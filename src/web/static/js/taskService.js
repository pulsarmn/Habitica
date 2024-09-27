export function getTaskDataToEdit(taskId) {
    return fetch(`/tasks?id=${taskId}`, {
        method: 'GET',
        headers: { 'Content-Type': 'text/html' }
    }).then(response => {
        if (response.ok) return response.text();
        throw new Error('Ошибка при получении данных задачи!');
    });
}

export function saveTask(taskId, taskData) {
    return fetch(`/tasks?id=${taskId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(taskData)
    }).then(response => {
        if (!response.ok) throw new Error('Ошибка при сохранении задачи');
        console.log(`Задача с ID ${taskId} обновлена`);
    });
}

// Удаление задачи
export function deleteTask(taskId) {
    return fetch(`/tasks?id=${taskId}`, {
        method: 'DELETE'
    }).then(response => {
        if (response.ok) {
            console.log(`Задача с ID ${taskId} удалена`);
        } else {
            throw new Error('Ошибка при удалении задачи');
        }
    }).catch(error => {
        console.error('Ошибка сети:', error);
    });
}

export function updateTasks() {
    return fetch(`/tasks`, {
        method: `GET`,
        headers: {
            'Content-Type': 'text/html'
        }
    }).then(response => {
        if (response.ok) {
            return response.text();
        }else {
            throw new Error('Ошибка при получении списка задач!');
        }
    }).then(html => {
        const taskList = document.getElementById('tasks-container');
        taskList.innerHTML = html;
    }).catch(error => {
        console.error("Error: ", error);
    });
}