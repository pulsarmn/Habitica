import {createEntity, reloadEntities, updateBalance} from "../service/generalService.js";

reloadEntities(`rewards`, `rewards-container`);

document.addEventListener('DOMContentLoaded', function () {
    const taskInput = document.getElementById('rewardInput');
    taskInput.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            const taskValue = taskInput.value.trim();
            if (taskValue) {
                createEntity(`rewards`, taskValue, taskInput);
            }
        }
    });

    taskInput.addEventListener('input', function () {
        if (this.value.includes('\n')) {
            this.value = this.value.replace(/\n/g, '');
        }
    });
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