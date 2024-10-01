export function getHabitDataToEdit(habitId){
    return fetch(`/habits?id=${habitId}`, {
        method: 'GET',
        headers: { 'Content-Type': 'text/html' }
    }).then(response => {
        if (response.ok) return response.text();
        throw new Error('Ошибка при получении данных задачи!');
    });
}

export function updateHabit(habitId, taskData) {
    return fetch(`/habits?id=${habitId}&update=update`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(taskData)
    }).then(response => {
        if (!response.ok) throw new Error('Ошибка при сохранении задачи');
        console.log(`Задача с ID ${habitId} обновлена`);
    });
}

export function updateHabitSeries(habitId, action) {
    fetch(`/habits?id=${habitId}`, {
        method: 'PUT',
        body: JSON.stringify({habitId: habitId, action: `${action}`}),
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
        if (response.ok) {
            updateHabits();
            console.log(`Привычка с ID ${habitId} обновлена (уменьшение)`);
        } else {
            console.error('Ошибка при уменьшении привычки');
        }
    }).catch(error => {
        console.error('Ошибка сети:', error);
    });
}

export function updateHabits() {
    fetch(`/habits`, {
        method: `GET`,
        headers: {
            'Content-Type': 'text/html'
        }
    }).then(response => {
        if (response.ok) {
            return response.text();
        }else {
            throw new Error('Ошибка при получении списка привычек!');
        }
    }).then(html => {
        const habitList = document.querySelector('#habits-container');
        habitList.innerHTML = html;
    }).catch(error => {
        console.log("Error: ", error);
    });
}

export function resetHabit(habitId) {
    return fetch(`/habits?id=${habitId}&update=reset`, {
        method: 'PUT'
    }).then(response => {
        if (!response.ok) throw new Error('Ошибка при сбросе задачи');
        console.log(`Задача с ID ${habitId} обновлена`);
    });
}

export function withdrawReward(elementId, type, action) {
    return fetch(`/purchase-reward?type=${type}&id=${elementId}&action=${action}`, {
        method: `PUT`
    }).then(response => {
        if (response.ok) {
            console.log(`Вознаграждение начислено!`);
        }else {
            throw new Error(`Ошибка при начислении вознаграждения!`)
        }
    }).catch(error => {
        console.error(`Ошибка сети`);
    });
}