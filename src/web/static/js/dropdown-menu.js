document.addEventListener('click', function(event) {
    const toggleButton = event.target.closest('.habitica-menu-dropdown-toggle');
    if (toggleButton) {
        const dropdownMenu = toggleButton.closest('.habit-wrapper, .daily-task-wrapper, .task-wrapper, .reward-wrapper').querySelector('.dropdown-menu');

        document.querySelectorAll('.dropdown-menu').forEach(menu => {
            menu.classList.remove('show-dropdown');
        });

        dropdownMenu.classList.add('show-dropdown');
    } else {
        document.querySelectorAll('.dropdown-menu').forEach(menu => {
            menu.classList.remove('show-dropdown');
        });
    }
});

document.getElementById('tasks-container').addEventListener('click', function(event) {
    if (event.target.closest('.habitica-menu-dropdown-toggle')) {
        const dropdownToggle = event.target.closest('.habitica-menu-dropdown-toggle');
        const taskWrapper = dropdownToggle.closest('.task-wrapper');
        const dropdownMenu = taskWrapper.querySelector('.dropdown-menu');
        const taskId = taskWrapper.querySelector('.task-id').textContent;

        dropdownMenu.addEventListener('click', function(event) {
            if (event.target.closest('.delete-task-item')) {
                fetch(`/tasks?taskId=${taskId}`, {
                    method: 'DELETE'
                }).then(response => {
                    if (response.ok) {
                        console.log(`Задача с ID ${taskId} удалена`);
                        updateTasks();
                    } else {
                        console.error('Ошибка при удалении задачи');
                    }
                }).catch(error => {
                    console.error('Ошибка сети:', error);
                });
            }else if (event.target.closest('.edit-task-item')) {

            }
        });
    }
});