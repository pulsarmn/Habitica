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

export function awardReward(elementId, type) {
    return fetch(`/purchase-reward?type=${type}&id=${elementId}`, {
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