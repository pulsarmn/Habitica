// tracking a click on a specific button

document.addEventListener('DOMContentLoaded', function () {
    const taskInput = document.getElementById('habitInput');
    taskInput.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            const taskValue = taskInput.value.trim();
            if (taskValue) {
                fetch('/habit', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: `habitHeading=${encodeURIComponent(taskValue)}`
                })
                    .then(response => {
                        if (response.ok) {
                            taskInput.value = '';
                            console.assert('Привычка отправлена успешно!');
                        } else {
                            console.assert('Ошибка при отправке привычки!');
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

document.querySelectorAll('.habit-wrapper').forEach(function (habitWrapper) {
    const habitId = habitWrapper.querySelector('.habit-id').textContent.trim();
    const leftControl = habitWrapper.querySelector('.left-control');
    const rightControl = habitWrapper.querySelector('.right-control');

    leftControl.addEventListener('click', function () {
        fetch(`/habits/update`, {
            method: 'PUT',
            body: JSON.stringify({ habitId: habitId, action: 'increase' }),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => {
            if (response.ok) {
                console.log(`Привычка с ID ${habitId} обновлена (увеличение)`);
            } else {
                console.error('Ошибка при увеличении привычки');
            }
        }).catch(error => {
            console.error('Ошибка сети:', error);
        });
    });

    rightControl.addEventListener('click', function () {
        fetch(`/habits/update`, {
            method: 'PUT',
            body: JSON.stringify({ habitId: habitId, action: 'decrease' }),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => {
            if (response.ok) {
                console.log(`Привычка с ID ${habitId} обновлена (уменьшение)`);
            } else {
                console.error('Ошибка при уменьшении привычки');
            }
        }).catch(error => {
            console.error('Ошибка сети:', error);
        });
    });
});