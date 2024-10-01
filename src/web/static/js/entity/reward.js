import {reloadEntities} from "../service/generalService.js";

reloadEntities(`rewards`, `rewards-container`);

document.addEventListener('DOMContentLoaded', function () {
    const taskInput = document.getElementById('rewardInput');
    taskInput.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            const taskValue = taskInput.value.trim();
            if (taskValue) {
                fetch('/rewards', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: `rewardHeading=${encodeURIComponent(taskValue)}`
                })
                    .then(response => {
                        if (response.ok) {
                            taskInput.value = '';
                            reloadEntities(`rewards`, `rewards-container`);
                            console.log('Награда отправлена успешно!');
                        } else {
                            console.log('Ошибка при отправке награды!');
                        }
                    })
                    .catch(error => {
                        console.log('Ошибка сети: ' + error.message);
                    });
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
})

function updateBalance() {
    fetch('/balance', {
        method: 'GET',
    })
        .then(response => response.json())
        .then(balance => {
            animateBalanceChange(balance.balance, 1000);
        })
        .catch(error => console.error('Ошибка обновления баланса: ', error));
}

function animateBalanceChange(targetBalance, duration) {
    const balanceElement = document.getElementById('userBalance');
    const startTime = performance.now();
    const initialBalance = Number(balanceElement.textContent);

        function updateBalanceAnimation(currentTime) {
        const elapsedTime = currentTime - startTime;
        const progress = Math.min(elapsedTime / duration, 1);

        const currentBalance = initialBalance + (targetBalance - initialBalance) * progress;

        balanceElement.textContent = currentBalance.toFixed(2);

        if (progress < 1) {
            requestAnimationFrame(updateBalanceAnimation);
        }
    }

    requestAnimationFrame(updateBalanceAnimation);
}