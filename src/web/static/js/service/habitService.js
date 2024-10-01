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