import {reloadEntities, setupEntityInput, updateBalance} from "../service/generalService.js";

reloadEntities(`rewards`, `rewards-container`);

document.addEventListener('DOMContentLoaded', function () {
    setupEntityInput(`rewardInput`, `rewards`);
});

document.getElementById('rewards-container').addEventListener('click', function(event) {
    if (event.target.closest('.reward-control')) {
        const rewardControl = event.target.closest('.reward-control');
        const rewardWrapper = rewardControl.closest('.reward-wrapper');
        const rewardId = rewardWrapper.querySelector('.reward-id').textContent;

        fetch(`/purchase-reward`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: `rewardId=${encodeURIComponent(rewardId)}`
        }).then(response => {
            if (response.ok) {
                console.log(`Вознаграждение с ID ${rewardId} успешно куплено!`);
                updateBalance();
            }else if (response.status === 400) {
                console.error("Недостаточно средств!");
            }else {
                console.error("Ошибка при покупке вознаграждения!");
            }
        }).catch(error => {
            console.error('Ошибка сети: ', error);
        });
    }
});