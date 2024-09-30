import {updateHabits, updateHabitSeries} from "../service/habitService.js";

updateHabits();

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
                            updateHabits();
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
        updateHabitSeries(habitId, `increment`);
    }else if (event.target.closest('.right-control')) {
        updateHabitSeries(habitId, `decrement`);
    }
})