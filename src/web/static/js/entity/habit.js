import {withdrawReward} from "../service/habitService.js";
import {reloadEntities, updateBalance, updateEntitySeries} from "../service/generalService.js";

reloadEntities(`habits`, `habits-container`);

document.addEventListener('DOMContentLoaded', function () {
    const taskInput = document.querySelector('#habitInput');
    taskInput.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            const taskValue = taskInput.value.trim();
            if (taskValue) {
                fetch('/habits', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: `habitHeading=${encodeURIComponent(taskValue)}`
                })
                    .then(response => {
                        if (response.ok) {
                            taskInput.value = '';
                            reloadEntities(`habits`, `habits-container`);
                            console.log('Привычка отправлена успешно!');
                        } else {
                            console.log('Ошибка при отправке привычки!');
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

document.querySelector('#habits-container').addEventListener('click', function(event) {
    const habitWrapper = event.target.closest('.habit-wrapper');
    const habitId = habitWrapper.querySelector('.habit-id').textContent.trim();

    if (event.target.closest('.left-control')) {
        updateEntitySeries(habitId, `habits`, `increment`);
        withdrawReward(habitId, `habit`, `increment`).then(() => {
            updateBalance();
            reloadEntities(`habits`, `habits-container`);
        });
    }else if (event.target.closest('.right-control')) {
        updateEntitySeries(habitId, `habits`, `decrement`);
        withdrawReward(habitId, `habit`, `decrement`).then(() => {
            updateBalance();
            reloadEntities(`habits`, `habits-container`);
        });
    }
});