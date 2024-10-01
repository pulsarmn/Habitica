import {reloadEntities} from "./generalService.js";

export function updateDailyTask(taskId, taskData) {
    return fetch(`/daily-tasks?id=${taskId}&update=true`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(taskData)
    }).then(response => {
        if (!response.ok) throw new Error('Ошибка при сохранении задачи');
        console.log(`Задача с ID ${taskId} обновлена`);
    });
}

export function updateDailyTaskSeries(dailyTaskId, action) {
    fetch(`/daily-tasks?id=${dailyTaskId}`, {
        method: 'PUT',
        body: JSON.stringify({ action: `${action}` }),
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
        if (response.ok) {
            reloadEntities(`daily-tasks`, `daily-tasks-container`);
            console.log(`Задача с ID ${dailyTaskId} обновлена (увеличение)`);
        } else {
            console.error('Ошибка при увеличении счётчика');
        }
    }).catch(error => {
        console.error('Ошибка сети:', error);
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