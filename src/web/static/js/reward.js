document.addEventListener('DOMContentLoaded', function () {
    const taskInput = document.getElementById('rewardInput');
    taskInput.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            const taskValue = taskInput.value.trim();
            if (taskValue) {
                fetch('/reward', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: `rewardHeading=${encodeURIComponent(taskValue)}`
                })
                    .then(response => {
                        if (response.ok) {
                            taskInput.value = '';
                            console.assert('Награда отправлена успешно!');
                        } else {
                            console.assert('Ошибка при отправке награды!');
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

function updateBalance() {
    fetch('/balance', {
        method: 'GET',
    })
        .then(response => response.json())
        .then(balance => {
            document.getElementById('userBalance').textContent = balance;
        })
        .catch(error => console.error('Ошибка обновления баланса: ', error));
}