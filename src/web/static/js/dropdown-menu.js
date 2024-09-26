import {getTaskDataToEdit, saveTask} from "./taskService.js";
import {showModal, hideModal, disableSaveButton, enableSaveButton, toggleSaveButton} from "./modalService.js";

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

document.getElementById('daily-tasks-container').addEventListener('click', function(event) {
    if (event.target.closest('.habitica-menu-dropdown-toggle')) {
        const dropdownToggle = event.target.closest('.habitica-menu-dropdown-toggle');
        const taskWrapper = dropdownToggle.closest('.daily-task-wrapper');
        const dropdownMenu = taskWrapper.querySelector('.dropdown-menu');
        const dailyTaskId = taskWrapper.querySelector('.daily-task-id').textContent;

        dropdownMenu.addEventListener('click', function(event) {
            if (event.target.closest('.delete-task-item')) {
                fetch(`/daily-tasks?dailyTaskId=${dailyTaskId}`, {
                    method: 'DELETE'
                }).then(response => {
                    if (response.ok) {
                        console.log(`Задача с ID ${dailyTaskId} удалена`);
                        updateDailyTasks();
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

document.getElementById('habits-container').addEventListener('click', function(event) {
    if (event.target.closest('.habitica-menu-dropdown-toggle')) {
        const dropdownToggle = event.target.closest('.habitica-menu-dropdown-toggle');
        const habitWrapper = dropdownToggle.closest('.habit-wrapper');
        const dropdownMenu = habitWrapper.querySelector('.dropdown-menu');
        const habitId = habitWrapper.querySelector('.habit-id').textContent;

        dropdownMenu.addEventListener('click', function(event) {
            if (event.target.closest('.delete-task-item')) {
                fetch(`/habits?habitId=${habitId}`, {
                    method: `DELETE`
                }).then(response => {
                    if (response.ok) {
                        console.log(`Привычка с ID ${habitId} удалена`);
                        updateHabits();
                    }else {
                        console.error(`Ошибка при удалении привычки`);
                    }
                }).catch(error => {
                    console.error(`Error: `, error);
                })
            }else if (event.target.closest('.edit-task-item')) {

            }
        });
    }
});

document.getElementById(`rewards-container`).addEventListener(`click`, function(event) {
    if (event.target.closest(`.habitica-menu-dropdown-toggle`)) {
        const dropdownToggle = event.target.closest('.habitica-menu-dropdown-toggle');
        const rewardWrapper = dropdownToggle.closest('.reward-wrapper');
        const dropdownMenu = rewardWrapper.querySelector('.dropdown-menu');
        const rewardId = rewardWrapper.querySelector('.reward-id').textContent;

        dropdownMenu.addEventListener(`click`, function(event) {
            if (event.target.closest(`.delete-task-item`)) {
                fetch(`/rewards?rewardId=${rewardId}`, {
                    method: `DELETE`
                }).then(response => {
                    if (response.ok) {
                        console.log(`Награда с ID ${rewardId} удалена`);
                        updateRewards();
                    }
                }).catch(error => {
                    console.error(`Error: `, error);
                });
            }
        });
    }
});

function deleteItem(itemId, endpoint, updateFunction) {
    return fetch(`/${endpoint}?id=${itemId}`, {
        method: `DELETE`
    }).then(response => {
        if (response.ok) {
            console.log(`Element with ID ${itemId} has been deleted`);
            updateFunction();
        }else {
            console.error(`Error deleting ${endpoint} element`);
        }
    }).catch(error => {
        console.error(`Error`, error);
    });
}