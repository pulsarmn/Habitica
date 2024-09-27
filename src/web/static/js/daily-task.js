// tracking daily task events

updateDailyTasks();

document.addEventListener('DOMContentLoaded', function () {
    const taskInput = document.getElementById('dailyTaskInput');

    taskInput.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            e.preventDefault();

            const taskValue = taskInput.value.trim();
            if (taskValue) {
                fetch('/daily-tasks', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: `dailyTaskHeading=${encodeURIComponent(taskValue)}`
                })
                    .then(response => {
                        if (response.ok) {
                            taskInput.value = '';
                            updateDailyTasks();
                            console.assert('Задача отправлена успешно!');
                        } else {
                            console.assert('Ошибка при отправке задачи');
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

document.getElementById('daily-tasks-container').addEventListener('click', function(event) {
    if (event.target.closest('.daily-task-control')) {
        const taskWrapper = event.target.closest('.daily-task-wrapper');
        const leftControl = taskWrapper.querySelector('.left-control');
        const svgCheck = taskWrapper.querySelector('.check');
        const displayCheck = taskWrapper.querySelector('.display-check-icon');
        const dailyTaskId = taskWrapper.querySelector('.daily-task-id').textContent;
        const isTaskCompleted = leftControl.classList.contains('task-disabled');

        if (isTaskCompleted) {
            leftControl.classList.remove('task-disabled');
            leftControl.classList.add('task-neutral-bg-color');
            if (displayCheck) {
                displayCheck.classList.add('check');
                displayCheck.classList.remove('display-check-icon');
            }

            fetch(`/daily-tasks?dailyTaskId=${dailyTaskId}`, {
                method: 'PUT',
                body: JSON.stringify({ action: 'decrement' }),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                if (response.ok) {
                    updateDailyTasks();
                    console.log(`Задача с ID ${dailyTaskId} обновлена (уменьшение)`);
                } else {
                    console.error('Ошибка при уменьшении счётчика');
                }
            }).catch(error => {
                console.error('Ошибка сети:', error);
            });
        } else {
            leftControl.classList.remove('task-neutral-bg-color');
            leftControl.classList.add('task-disabled');
            if (svgCheck) {
                svgCheck.classList.add('display-check-icon');
                svgCheck.classList.remove('check');
            }

            fetch(`/daily-tasks?dailyTaskId=${dailyTaskId}`, {
                method: 'PUT',
                body: JSON.stringify({ action: 'increment' }),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                if (response.ok) {
                    updateDailyTasks();
                    console.log(`Задача с ID ${dailyTaskId} обновлена (увеличение)`);
                } else {
                    console.error('Ошибка при увеличении счётчика');
                }
            }).catch(error => {
                console.error('Ошибка сети:', error);
            });
        }
    }
})

function updateDailyTasks() {
    fetch(`/daily-tasks`, {
        method: `GET`,
        headers: {
            'Content-Type': 'text/html'
        }
    }).then(response => {
        if (response.ok) {
            return response.text();
        }else {
            throw new Error('Ошибка при получении списка ежедневных задач!');
        }
    }).then(html => {
        const dailyTaskList = document.getElementById('daily-tasks-container');
        dailyTaskList.innerHTML = html;
    }).catch(error => {
        console.error('Error', error);
    });
}