import {getTaskDataToEdit, saveTask, updateTasks} from "./taskService.js";
import {showModal, hideModal, toggleSaveButton, putModal, deleteModal} from "./modalService.js";
import {getRewardDataToEdit, saveReward, updateRewards} from "./rewardService.js";

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
                deleteItem(taskId, `tasks`, updateTasks);
            }else if (event.target.closest('.edit-task-item')) {
                getTaskDataToEdit(taskId).then(html => {
                    const modalWindowWrapper = document.getElementById(`modal-window-wrapper`);
                    putModal(modalWindowWrapper, html);
                    const modalWindow = modalWindowWrapper.querySelector(`#edit-task-modal`);
                    const saveButton = modalWindowWrapper.querySelector(`.save-task`);
                    const taskTitleInput = modalWindowWrapper.querySelector(`#task-title`);

                    fillTaskModalWindow(modalWindowWrapper);
                    showModal(modalWindow);
                    toggleSaveButton(taskTitleInput, saveButton);

                    taskTitleInput.addEventListener(`input`, function(event) {
                        toggleSaveButton(taskTitleInput, saveButton);
                    });

                    handleSaveTask(modalWindowWrapper, taskId);
                    handleDeleteItem(modalWindowWrapper, taskId);

                    document.getElementById('close-modal-btn').addEventListener('click', function() {
                        hideModal(modalWindow);
                        deleteModal(modalWindowWrapper);
                    });
                }).catch(error => {
                    console.log(`Error while receiving task data`, error);
                });
            }
        });
    }
});

function fillTaskModalWindow(modalWindowWrapper) {
    const taskTitle = modalWindowWrapper.querySelector(`#task-title`);
    const taskDescription = modalWindowWrapper.querySelector(`#task-notes`);
    const taskComplexity = modalWindowWrapper.querySelector(`#task-difficulty`);
    const taskDeadline = modalWindowWrapper.querySelector(`#task-deadline`);

    const taskData = getJsonTask(modalWindowWrapper);

    taskTitle.value = taskData.heading;
    taskDescription.innerHTML = (taskData.description === undefined) ? `` : taskData.description;
    taskComplexity.value = taskData.complexity;
    taskDeadline.value = taskData.deadline;
}

function getJsonTask() {
    const modalWindowWrapper = document.getElementById(`modal-window-wrapper`);
    const taskDataElement = modalWindowWrapper.querySelector(`#task-data`);
    return JSON.parse(taskDataElement.textContent);
}

function handleSaveTask(modalWindowWrapper, taskId) {
    const saveButton = modalWindowWrapper.querySelector(`.save-task`);
    saveButton.addEventListener(`click`, function() {
        const taskTitle = modalWindowWrapper.querySelector(`#task-title`);
        const taskDescription = modalWindowWrapper.querySelector(`#task-notes`);
        const taskComplexity = modalWindowWrapper.querySelector(`#task-difficulty`);
        const taskDeadline = modalWindowWrapper.querySelector(`#task-deadline`);

        const taskData = {
            id: taskId,
            heading: taskTitle.value,
            description: taskDescription.value,
            complexity: taskComplexity.value,
            deadline: taskDeadline.value
        };

        saveTask(taskId, taskData).then(() => {
            updateTasks();
            hideModal(modalWindowWrapper.querySelector(`#edit-task-modal`));
            deleteModal(modalWindowWrapper);
        });
    });
}

function handleDeleteItem(modalWindowWrapper, taskId) {
    const modalWindow = modalWindowWrapper.querySelector(`#edit-task-modal`);
    const deleteButton = modalWindowWrapper.querySelector(`.delete-task`);

    deleteButton.addEventListener(`click`, function() {
        deleteItem(taskId, `tasks`, updateTasks).then(() => {
            if (modalWindow != null) {
                hideModal(modalWindow);
                deleteModal(modalWindowWrapper);
            }
        });
    });
}

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

function handleSaveReward(modalWindowWrapper, rewardId) {
    const saveButton = modalWindowWrapper.querySelector(`.save-reward`);
    saveButton.addEventListener(`click`, function() {
        const rewardTitle = modalWindowWrapper.querySelector(`#reward-title`);
        const rewardDescription = modalWindowWrapper.querySelector(`#reward-notes`);
        const rewardCost = modalWindowWrapper.querySelector(`#reward-cost`);

        const rewardData = {
            id: rewardId,
            heading: rewardTitle.value,
            description: rewardDescription.value,
            cost: rewardCost.value
        };

        saveReward(rewardId, rewardData).then(() => {
            updateRewards();
            hideModal(modalWindowWrapper.querySelector(`#edit-reward-modal`));
            deleteModal(modalWindowWrapper);
        });
    });
}

function handleDeleteReward(modalWindowWrapper, rewardId) {
    const modalWindow = modalWindowWrapper.querySelector(`#edit-reward-modal`);
    const deleteButton = modalWindowWrapper.querySelector(`.delete-reward`);

    deleteButton.addEventListener(`click`, function() {
        deleteItem(rewardId, `rewards`, updateRewards).then(() => {
            if (modalWindow != null) {
                hideModal(modalWindow);
                deleteModal(modalWindowWrapper);
            }
        });
    });
}

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