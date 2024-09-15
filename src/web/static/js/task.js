// tracking task events

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

document.querySelectorAll('.task-control').forEach(function (taskControl) {
    taskControl.addEventListener('click', function () {
        const taskWrapper = this.closest('.task-wrapper');
        const leftControl = taskWrapper.querySelector('.left-control');
        const svgCheck = taskWrapper.querySelector('.check');

        const taskId = taskWrapper.querySelector('.task-id').textContent;

        if (svgCheck) {
            leftControl.classList.remove('task-neutral-bg-color');
            leftControl.classList.add('task-disabled');

            svgCheck.classList.add('display-check-icon');
            svgCheck.classList.remove('check');

            fetch(`/tasks?taskId=${taskId}`, {
                method: 'DELETE'
            }).then(response => {
                if (response.ok) {
                    console.log(`Задача с ID ${taskId} удалена`);
                } else {
                    console.error('Ошибка при удалении задачи');
                }
            }).catch(error => {
                console.error('Ошибка сети:', error);
            });
        } else {
            leftControl.classList.remove('task-disabled');
            leftControl.classList.add('task-neutral-bg-color');

            const displayCheck = taskWrapper.querySelector('.display-check-icon');
            displayCheck.classList.add('check');
            displayCheck.classList.remove('display-check-icon');
        }
    });
});