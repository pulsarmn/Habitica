// tracking a click on a specific button

updateHabits();

document.addEventListener('DOMContentLoaded', function () {
    const taskInput = document.getElementById('habitInput');
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

document.getElementById('habits-container').addEventListener('click', function(event) {
    const habitWrapper = event.target.closest('.habit-wrapper');
    const habitId = habitWrapper.querySelector('.habit-id').textContent.trim();

    if (event.target.closest('.left-control')) {
        fetch(`/habits?habitId=${habitId}`, {
            method: 'PUT',
            body: JSON.stringify({ habitId: habitId, action: 'increment' }),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => {
            if (response.ok) {
                updateHabits();
                console.log(`Привычка с ID ${habitId} обновлена (увеличение)`);
            } else {
                console.error('Ошибка при увеличении привычки');
            }
        }).catch(error => {
            console.error('Ошибка сети:', error);
        });

    }else if (event.target.closest('.right-control')) {
        fetch(`/habits?habitId=${habitId}`, {
            method: 'PUT',
            body: JSON.stringify({habitId: habitId, action: 'decrement'}),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => {
            if (response.ok) {
                updateHabits();
                console.log(`Привычка с ID ${habitId} обновлена (уменьшение)`);
            } else {
                console.error('Ошибка при уменьшении привычки');
            }
        }).catch(error => {
            console.error('Ошибка сети:', error);
        });
    }
})

// document.querySelectorAll('.habit-wrapper').forEach(function (habitWrapper) {
//     const habitId = habitWrapper.querySelector('.habit-id').textContent.trim();
//     const leftControl = habitWrapper.querySelector('.left-control');
//     const rightControl = habitWrapper.querySelector('.right-control');
//
//     leftControl.addEventListener('click', function () {
//         fetch(`/habits?habitId=${habitId}`, {
//             method: 'PUT',
//             body: JSON.stringify({ habitId: habitId, action: 'increment' }),
//             headers: {
//                 'Content-Type': 'application/json'
//             }
//         }).then(response => {
//             if (response.ok) {
//                 console.log(`Привычка с ID ${habitId} обновлена (увеличение)`);
//             } else {
//                 console.error('Ошибка при увеличении привычки');
//             }
//         }).catch(error => {
//             console.error('Ошибка сети:', error);
//         });
//     });
//
//     rightControl.addEventListener('click', function () {
//         fetch(`/habits?habitId=${habitId}`, {
//             method: 'PUT',
//             body: JSON.stringify({ habitId: habitId, action: 'decrement' }),
//             headers: {
//                 'Content-Type': 'application/json'
//             }
//         }).then(response => {
//             if (response.ok) {
//                 console.log(`Привычка с ID ${habitId} обновлена (уменьшение)`);
//             } else {
//                 console.error('Ошибка при уменьшении привычки');
//             }
//         }).catch(error => {
//             console.error('Ошибка сети:', error);
//         });
//     });
// });

function updateHabits() {
    fetch(`/habits`, {
        method: `GET`,
        headers: {
            'Content-Type': 'text/html'
        }
    }).then(response => {
        if (response.ok) {
            return response.text();
        }else {
            throw new Error('Ошибка при получении списка привычек!');
        }
    }).then(html => {
        const habitList = document.getElementById('habits-container');
        habitList.innerHTML = html;
    }).catch(error => {
        console.log("Error: ", error);
    });
}