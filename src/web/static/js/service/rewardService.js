export function saveReward(rewardId, rewardData) {
    return fetch(`/rewards?id=${rewardId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(rewardData)
    }).then(response => {
        if (!response.ok) throw new Error('Ошибка при сохранении задачи');
        console.log(`Задача с ID ${rewardId} обновлена`);
    });
}