import {awardReward, deleteTask} from "../service/taskService.js";
import {reloadEntities} from "../service/generalService.js";

// updateTasks();
reloadEntities(`tasks`, `tasks-container`);

document.addEventListener('DOMContentLoaded', function () {
    const taskInput = document.getElementById('taskInput');

    taskInput.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            e.preventDefault();

            const taskValue = taskInput.value.trim();
            if (taskValue) {
                fetch('/tasks', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: `taskHeading=${encodeURIComponent(taskValue)}`
                })
                    .then(response => {
                        if (response.ok) {
                            taskInput.value = '';
                            console.log('Задача отправлена успешно!');
                            reloadEntities(`tasks`, `tasks-container`);
                        } else {
                            console.log('Ошибка при отправке задачи');
                        }
                    })
                    .catch(error => {
                        console.assert('Ошибка сети: ' + error.message);
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

document.getElementById('tasks-container').addEventListener('click', function(event) {
    if (event.target.closest('.task-control')) {
        const taskControl = event.target.closest('.task-control');
        const taskWrapper = taskControl.closest('.task-wrapper');
        const leftControl = taskWrapper.querySelector('.left-control');
        const svgCheck = taskWrapper.querySelector('.check');
        const taskId = taskWrapper.querySelector('.task-id').textContent;

        if (svgCheck) {
            leftControl.classList.remove('task-neutral-bg-color');
            leftControl.classList.add('task-disabled');

            svgCheck.classList.add('display-check-icon');
            svgCheck.classList.remove('check');

            awardReward(taskId, `task`).then(() => {
                updateBalance();
                deleteTask(taskId).then(() => reloadEntities(`tasks`, `tasks-container`));
            })
        } else {
            leftControl.classList.remove('task-disabled');
            leftControl.classList.add('task-neutral-bg-color');

            const displayCheck = taskWrapper.querySelector('.display-check-icon');
            displayCheck.classList.add('check');
            displayCheck.classList.remove('display-check-icon');
        }
    }
});

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
    const balanceElement = document.querySelector(`#userBalance`);
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
