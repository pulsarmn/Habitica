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

export function updateRewards() {
    fetch(`/rewards`, {
        method: `GET`,
        headers: {
            'Content-Type': 'text/html'
        }
    }).then(response => {
        if (response.ok) {
            return response.text();
        }else {
            throw new Error('Ошибка при получении списка наград!');
        }
    }).then(html => {
        const rewardList = document.querySelector('#rewards-container');
        rewardList.innerHTML = html;
    }).catch(error => {
        console.error('Error: ', error);
    })
}