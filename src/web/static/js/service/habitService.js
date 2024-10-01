export function resetHabit(habitId) {
    return fetch(`/habits?id=${habitId}&update=reset`, {
        method: 'PUT'
    }).then(response => {
        if (!response.ok) throw new Error('Ошибка при сбросе задачи');
        console.log(`Задача с ID ${habitId} обновлена`);
    });
}