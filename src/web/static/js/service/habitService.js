import {reloadEntities} from "./generalService.js";

export function updateHabitSeries(habitId, action) {
    fetch(`/habits?id=${habitId}`, {
        method: 'PUT',
        body: JSON.stringify({habitId: habitId, action: `${action}`}),
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
        if (response.ok) {
            reloadEntities(`habits`, `habits-container`);
            console.log(`Привычка с ID ${habitId} обновлена (уменьшение)`);
        } else {
            console.error('Ошибка при уменьшении привычки');
        }
    }).catch(error => {
        console.error('Ошибка сети:', error);
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