import {saveTask} from "./service/taskService.js";
import {showModal, hideModal, toggleSaveButton, putModal, deleteModal} from "./service/modalService.js";
import {saveReward} from "./service/rewardService.js";
import {updateDailyTask} from "./service/dailyTaskService.js";
import {resetHabit, updateHabit} from "./service/habitService.js";
import {getEntityDataToEdit, reloadEntities} from "./service/generalService.js";

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

document.querySelector(`#tasks-container`).addEventListener(`click`, function(event) {
    if (event.target.closest('.habitica-menu-dropdown-toggle')) {
        const dropdownToggle = event.target.closest('.habitica-menu-dropdown-toggle');
        const taskWrapper = dropdownToggle.closest('.task-wrapper');
        const dropdownMenu = taskWrapper.querySelector('.dropdown-menu');
        const taskId = taskWrapper.querySelector('.task-id').textContent;

        dropdownMenu.addEventListener('click', function(event) {
            if (event.target.closest('.delete-task-item')) {
                deleteItem(taskId, `tasks`, reloadEntities, `tasks-container`);
            }else if (event.target.closest('.edit-task-item')) {
                getEntityDataToEdit(taskId, `tasks`).then(html => {
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
    if (taskDeadline != null) {
        taskDeadline.value = taskData.deadline;
    }
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
            reloadEntities(`tasks`, `tasks-container`)
            hideModal(modalWindowWrapper.querySelector(`#edit-task-modal`));
            deleteModal(modalWindowWrapper);
        });
    });
}

function handleDeleteItem(modalWindowWrapper, dailyTaskId) {
    const modalWindow = modalWindowWrapper.querySelector(`#edit-task-modal`);
    const deleteButton = modalWindowWrapper.querySelector(`.delete-task`);

    deleteButton.addEventListener(`click`, function() {
        deleteItem(dailyTaskId, `tasks`, reloadEntities, `tasks-container`).then(() => {
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
                deleteItem(dailyTaskId, `daily-tasks`, reloadEntities, `daily-tasks-container`);
            }else if (event.target.closest('.edit-task-item')) {
                getEntityDataToEdit(dailyTaskId, `daily-tasks`).then(html => {
                    const modalWindowWrapper = document.getElementById(`modal-window-wrapper`);
                    putModal(modalWindowWrapper, html);
                    const modalWindow = modalWindowWrapper.querySelector(`#edit-daily-task-modal`);
                    const saveButton = modalWindowWrapper.querySelector(`.save-task`);
                    const taskTitleInput = modalWindowWrapper.querySelector(`#task-title`);

                    fillTaskModalWindow(modalWindowWrapper);
                    showModal(modalWindow);
                    toggleSaveButton(taskTitleInput, saveButton);

                    taskTitleInput.addEventListener(`input`, function(event) {
                        toggleSaveButton(taskTitleInput, saveButton);
                    });

                    handleSaveDailyTask(modalWindowWrapper, dailyTaskId);
                    handleDeleteDailyTask(modalWindowWrapper, dailyTaskId);

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

function handleSaveDailyTask(modalWindowWrapper, dailyTaskId) {
    const saveButton = modalWindowWrapper.querySelector(`.save-task`);
    saveButton.addEventListener(`click`, function() {
        const taskTitle = modalWindowWrapper.querySelector(`#task-title`);
        const taskDescription = modalWindowWrapper.querySelector(`#task-notes`);
        const taskComplexity = modalWindowWrapper.querySelector(`#task-difficulty`);
        const taskDeadline = modalWindowWrapper.querySelector(`#task-deadline`);

        const taskData = {
            id: dailyTaskId,
            heading: taskTitle.value,
            description: taskDescription.value,
            complexity: taskComplexity.value,
            deadline: taskDeadline.value
        };

        updateDailyTask(dailyTaskId, taskData).then(() => {
            reloadEntities(`daily-tasks`, `daily-tasks-container`);
            hideModal(modalWindowWrapper.querySelector(`#edit-daily-task-modal`));
            deleteModal(modalWindowWrapper);
        });
    });
}

function handleDeleteDailyTask(modalWindowWrapper, dailyTaskId) {
    const modalWindow = modalWindowWrapper.querySelector(`#edit-daily-task-modal`);
    const deleteButton = modalWindowWrapper.querySelector(`.delete-task`);

    deleteButton.addEventListener(`click`, function() {
        deleteItem(dailyTaskId, `daily-tasks`, reloadEntities, `daily-tasks-container`).then(() => {
            if (modalWindow != null) {
                hideModal(modalWindow);
                deleteModal(modalWindowWrapper);
            }
        });
    });
}

document.getElementById('habits-container').addEventListener('click', function(event) {
    if (event.target.closest('.habitica-menu-dropdown-toggle')) {
        const dropdownToggle = event.target.closest('.habitica-menu-dropdown-toggle');
        const habitWrapper = dropdownToggle.closest('.habit-wrapper');
        const dropdownMenu = habitWrapper.querySelector('.dropdown-menu');
        const habitId = habitWrapper.querySelector('.habit-id').textContent;

        dropdownMenu.addEventListener('click', function(event) {
            if (event.target.closest('.delete-task-item')) {
                deleteItem(habitId, `habits`, reloadEntities, `habits-container`);
            }else if (event.target.closest('.edit-task-item')) {
                getEntityDataToEdit(habitId, `habits`).then(html => {
                    const modalWindowWrapper = document.getElementById(`modal-window-wrapper`);
                    putModal(modalWindowWrapper, html);
                    const modalWindow = modalWindowWrapper.querySelector(`#edit-habit-modal`);
                    const saveButton = modalWindowWrapper.querySelector(`.save-task`);
                    const taskTitleInput = modalWindowWrapper.querySelector(`#task-title`);

                    fillTaskModalWindow(modalWindowWrapper);
                    showModal(modalWindow);
                    toggleSaveButton(taskTitleInput, saveButton);

                    taskTitleInput.addEventListener(`input`, function(event) {
                        toggleSaveButton(taskTitleInput, saveButton);
                    });

                    handleSaveHabit(modalWindowWrapper, habitId);
                    handleDeleteHabit(modalWindowWrapper, habitId);
                    handleResetHabit(modalWindowWrapper, habitId);

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

function handleSaveHabit(modalWindowWrapper, habitId) {
    const saveButton = modalWindowWrapper.querySelector(`.save-task`);
    saveButton.addEventListener(`click`, function() {
        const taskTitle = modalWindowWrapper.querySelector(`#task-title`);
        const taskDescription = modalWindowWrapper.querySelector(`#task-notes`);
        const taskComplexity = modalWindowWrapper.querySelector(`#task-difficulty`);

        const taskData = {
            id: habitId,
            heading: taskTitle.value,
            description: taskDescription.value,
            complexity: taskComplexity.value,
        };

        updateHabit(habitId, taskData).then(() => {
            reloadEntities(`habits`, `habits-container`);
            hideModal(modalWindowWrapper.querySelector(`#edit-habit-modal`));
            deleteModal(modalWindowWrapper);
        });
    });
}

function handleDeleteHabit(modalWindowWrapper, habitId) {
    const modalWindow = modalWindowWrapper.querySelector(`#edit-habit-modal`);
    const deleteButton = modalWindowWrapper.querySelector(`.delete-task`);

    deleteButton.addEventListener(`click`, function() {
        deleteItem(habitId, `habits`, reloadEntities, `habits-container`).then(() => {
            if (modalWindow != null) {
                hideModal(modalWindow);
                deleteModal(modalWindowWrapper);
            }
        });
    });
}

function handleResetHabit(modalWindowWrapper, habitId) {
    const modalWindow = modalWindowWrapper.querySelector(`#edit-habit-modal`);
    const resetButton = modalWindowWrapper.querySelector(`.reset-habit`);

    resetButton.addEventListener(`click`, function() {
        resetHabit(habitId).then(() => {
            reloadEntities(`habits`, `habits-container`);
            if (modalWindow != null) {
                hideModal(modalWindow);
                deleteModal(modalWindowWrapper);
            }
        });
    });
}

document.getElementById(`rewards-container`).addEventListener(`click`, function(event) {
    if (event.target.closest(`.habitica-menu-dropdown-toggle`)) {
        const dropdownToggle = event.target.closest('.habitica-menu-dropdown-toggle');
        const rewardWrapper = dropdownToggle.closest('.reward-wrapper');
        const dropdownMenu = rewardWrapper.querySelector('.dropdown-menu');
        const rewardId = rewardWrapper.querySelector('.reward-id').textContent;

        dropdownMenu.addEventListener(`click`, function(event) {
            if (event.target.closest(`.delete-task-item`)) {
                deleteItem(rewardId, `rewards`, reloadEntities, `rewards-container`);
            }else if (event.target.closest(`.edit-task-item`)) {
                getEntityDataToEdit(rewardId, `rewards`).then(html => {
                    const modalWindowWrapper = document.querySelector(`#modal-window-wrapper`);
                    putModal(modalWindowWrapper, html);
                    const modalWindow = modalWindowWrapper.querySelector(`#edit-reward-modal`);
                    const saveButton = modalWindowWrapper.querySelector(`.save-reward`);
                    const rewardTitleInput = modalWindowWrapper.querySelector(`#reward-title`);

                    fillRewardModalWindow(modalWindowWrapper);
                    showModal(modalWindow);
                    toggleSaveButton(rewardTitleInput, saveButton);

                    rewardTitleInput.addEventListener(`input`, function(event) {
                        toggleSaveButton(rewardTitleInput, saveButton);
                    });

                    handleSaveReward(modalWindowWrapper, rewardId);
                    handleDeleteReward(modalWindowWrapper, rewardId);

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

function fillRewardModalWindow(modalWindowWrapper) {
    const rewardTitle = modalWindowWrapper.querySelector(`#reward-title`);
    const rewardDescription = modalWindowWrapper.querySelector(`#reward-notes`);
    const rewardCost = modalWindowWrapper.querySelector(`#reward-cost`);

    const rewardData = getJsonReward(modalWindowWrapper);

    rewardTitle.value = rewardData.heading;
    rewardDescription.innerHTML = (rewardData.description === undefined) ? `` : rewardData.description;
    rewardCost.value = rewardData.cost;
}

function getJsonReward() {
    const modalWindowWrapper = document.querySelector(`#modal-window-wrapper`);
    const taskDataElement = modalWindowWrapper.querySelector(`#reward-data`);
    return JSON.parse(taskDataElement.textContent);
}


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
            reloadEntities(`rewards`, `rewards-container`);
            hideModal(modalWindowWrapper.querySelector(`#edit-reward-modal`));
            deleteModal(modalWindowWrapper);
        });
    });
}

function handleDeleteReward(modalWindowWrapper, rewardId) {
    const modalWindow = modalWindowWrapper.querySelector(`#edit-reward-modal`);
    const deleteButton = modalWindowWrapper.querySelector(`.delete-reward`);

    deleteButton.addEventListener(`click`, function() {
        deleteItem(rewardId, `rewards`, reloadEntities, `rewards-container`).then(() => {
            if (modalWindow != null) {
                hideModal(modalWindow);
                deleteModal(modalWindowWrapper);
            }
        });
    });
}

function deleteItem(itemId, endpoint, updateFunction, container) {
    return fetch(`/${endpoint}?id=${itemId}`, {
        method: `DELETE`
    }).then(response => {
        if (response.ok) {
            console.log(`Element with ID ${itemId} has been deleted`);
            updateFunction(endpoint, container);
        }else {
            console.error(`Error deleting ${endpoint} element`);
        }
    }).catch(error => {
        console.error(`Error`, error);
    });
}