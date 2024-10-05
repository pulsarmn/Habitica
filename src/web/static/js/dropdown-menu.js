import {hideModal, deleteModal, showModal} from "./service/modalService.js";
import {
    getJsonEntity, handleEntityClick,
    reloadEntities, resetHabit,
    updateEntity
} from "./service/generalService.js";

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

document.addEventListener('click', function(event) {
    const userItem = event.target.closest('#userItem');
    const dropdownMenu = document.querySelector('#userDropdownMenu');

    if (userItem) {
        document.querySelectorAll('.dropdown-menu').forEach(menu => {
            menu.classList.remove('show-dropdown');
        });
        dropdownMenu.classList.add('show-dropdown');
    } else {
        dropdownMenu.classList.remove('show-dropdown');
    }

    if (dropdownMenu.classList.contains('show-dropdown') && dropdownMenu.contains(event.target)) {
        dropdownMenu.classList.remove('show-dropdown');
    }
});

document.querySelector(`#userItem`).addEventListener(`click`, function(event) {
    if (event.target.closest(`#logout-item`)) {
        fetch(`/logout`, {
            method: `POST`
        }).then(response => {
            if (response.ok) {
                console.log(`Success`);
                location.reload();
            }
        }).catch(error => {
            console.log(`error`, error);
        });
    }else if (event.target.closest(`#language-item`)) {
        fetch(`/change-language`, {
            method: `GET`
        }).then(response => {
            if (response.ok) {
                return response.text();
            }
        }).then(html => {
            const modalWindowWrapper = document.querySelector(`#modal-window-wrapper`);
            modalWindowWrapper.innerHTML = html;
            const modalWindow = modalWindowWrapper.querySelector(`#choose-language-modal`);
            fillLangModalWindow(modalWindowWrapper);
            showModal(modalWindow);
            handleSaveLang(modalWindowWrapper);
            document.querySelector('#close-modal-btn').addEventListener('click', function() {
                hideModal(modalWindow);
                deleteModal(modalWindowWrapper);
            });
        }).catch(error => {
            console.log(`error`, error);
        });
    }else if (event.target.closest(`#profile-item`)) {
        fetch(`/profile`, {
            method: `GET`
        }).then(response => {
            if (response.ok) {
                return response.text();
            }
        }).then(html => {
            const modalWindowWrapper = document.querySelector(`#modal-window-wrapper`);
            modalWindowWrapper.innerHTML = html;
            const modalWindow = modalWindowWrapper.querySelector(`#edit-entity-modal`);
            fillProfileWindow(modalWindowWrapper);
            showModal(modalWindow);

        })
    }
});

function fillLangModalWindow(modalWindowWrapper) {
    const language = modalWindowWrapper.querySelector(`#user-language`);
    const langData = getJsonLang(modalWindowWrapper);

    language.value = langData.lang;
}

function getJsonLang(modalWindowWrapper) {
    const langDataElement = modalWindowWrapper.querySelector(`#langData`);
    return JSON.parse(langDataElement.textContent);
}

function handleSaveLang(modalWindowWrapper) {
    const saveButton = modalWindowWrapper.querySelector(`.save-entity`);
    saveButton.addEventListener(`click`, function(event) {
        const language = modalWindowWrapper.querySelector(`#user-language`);
        const langData = {
            lang: language.value
        }
        fetch(`/change-language`, {
            method: `PUT`,
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(langData)
        }).then(response => {
            if (response.ok) {
                console.log(`Success`);
                hideModal(modalWindowWrapper.querySelector(`#choose-language-modal`));
                deleteModal(modalWindowWrapper);
                location.reload();
            }
        }).catch(error => {
            console.log(`error`, error);
        })
    });
}

function fillTaskModalWindow(modalWindowWrapper) {
    const taskTitle = modalWindowWrapper.querySelector(`#entity-title`);
    const taskDescription = modalWindowWrapper.querySelector(`#entity-notes`);
    const taskComplexity = modalWindowWrapper.querySelector(`#entity-difficulty`);
    const taskDeadline = modalWindowWrapper.querySelector(`#entity-deadline`);

    const taskData = getJsonEntity(`task-data`);

    taskTitle.value = taskData.heading;
    taskDescription.innerHTML = (taskData.description === undefined) ? `` : taskData.description;
    taskComplexity.value = taskData.complexity;
    if (taskDeadline != null) {
        taskDeadline.value = taskData.deadline;
    }
}

function handleSaveTask(modalWindowWrapper, taskId) {
    const saveButton = modalWindowWrapper.querySelector(`.save-entity`);
    saveButton.addEventListener(`click`, function() {
        const taskTitle = modalWindowWrapper.querySelector(`#entity-title`);
        const taskDescription = modalWindowWrapper.querySelector(`#entity-notes`);
        const taskComplexity = modalWindowWrapper.querySelector(`#entity-difficulty`);
        const taskDeadline = modalWindowWrapper.querySelector(`#entity-deadline`);

        const taskData = {
            id: taskId,
            heading: taskTitle.value,
            description: taskDescription.value,
            complexity: taskComplexity.value,
            deadline: taskDeadline.value
        };

        updateEntity(taskId, `tasks`, taskData).then(() => {
            reloadEntities(`tasks`, `tasks-container`)
            hideModal(modalWindowWrapper.querySelector(`#edit-entity-modal`));
            deleteModal(modalWindowWrapper);
        });
    });
}

function handleSaveDailyTask(modalWindowWrapper, dailyTaskId) {
    const saveButton = modalWindowWrapper.querySelector(`.save-entity`);
    saveButton.addEventListener(`click`, function() {
        const taskTitle = modalWindowWrapper.querySelector(`#entity-title`);
        const taskDescription = modalWindowWrapper.querySelector(`#entity-notes`);
        const taskComplexity = modalWindowWrapper.querySelector(`#entity-difficulty`);
        const taskDeadline = modalWindowWrapper.querySelector(`#entity-deadline`);

        const taskData = {
            id: dailyTaskId,
            heading: taskTitle.value,
            description: taskDescription.value,
            complexity: taskComplexity.value,
            deadline: taskDeadline.value
        };

        updateEntity(dailyTaskId, `daily-tasks`, taskData).then(() => {
            reloadEntities(`daily-tasks`, `daily-tasks-container`);
            hideModal(modalWindowWrapper.querySelector(`#edit-entity-modal`));
            deleteModal(modalWindowWrapper);
        });
    });
}

function handleSaveHabit(modalWindowWrapper, habitId) {
    const saveButton = modalWindowWrapper.querySelector(`.save-entity`);
    saveButton.addEventListener(`click`, function() {
        const taskTitle = modalWindowWrapper.querySelector(`#entity-title`);
        const taskDescription = modalWindowWrapper.querySelector(`#entity-notes`);
        const taskComplexity = modalWindowWrapper.querySelector(`#entity-difficulty`);

        const taskData = {
            id: habitId,
            heading: taskTitle.value,
            description: taskDescription.value,
            complexity: taskComplexity.value,
        };

        updateEntity(habitId, `habits`, taskData).then(() => {
            reloadEntities(`habits`, `habits-container`);
            hideModal(modalWindowWrapper.querySelector(`#edit-entity-modal`));
            deleteModal(modalWindowWrapper);
        });
    });
}

function handleResetHabit(modalWindowWrapper, habitId) {
    const modalWindow = modalWindowWrapper.querySelector(`#edit-entity-modal`);
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

function fillRewardModalWindow(modalWindowWrapper) {
    const rewardTitle = modalWindowWrapper.querySelector(`#entity-title`);
    const rewardDescription = modalWindowWrapper.querySelector(`#entity-notes`);
    const rewardCost = modalWindowWrapper.querySelector(`#entity-cost`);

    const rewardData = getJsonEntity(`reward-data`);

    rewardTitle.value = rewardData.heading;
    rewardDescription.innerHTML = (rewardData.description === undefined) ? `` : rewardData.description;
    rewardCost.value = rewardData.cost;
}

function handleSaveReward(modalWindowWrapper, rewardId) {
    const saveButton = modalWindowWrapper.querySelector(`.save-entity`);
    saveButton.addEventListener(`click`, function() {
        const rewardTitle = modalWindowWrapper.querySelector(`#entity-title`);
        const rewardDescription = modalWindowWrapper.querySelector(`#entity-notes`);
        const rewardCost = modalWindowWrapper.querySelector(`#entity-cost`);

        const rewardData = {
            id: rewardId,
            heading: rewardTitle.value,
            description: rewardDescription.value,
            cost: rewardCost.value
        };

        updateEntity(rewardId, `rewards`, rewardData).then(() => {
            reloadEntities(`rewards`, `rewards-container`);
            hideModal(modalWindowWrapper.querySelector(`#edit-entity-modal`));
            deleteModal(modalWindowWrapper);
        });
    });
}

handleEntityClick(
    `tasks-container`,
    `.task-wrapper`,
    `.task-id`,
    `tasks`,
    fillTaskModalWindow,
    handleSaveTask
);

handleEntityClick(
    `daily-tasks-container`,
    `.daily-task-wrapper`,
    `.daily-task-id`,
    `daily-tasks`,
    fillTaskModalWindow,
    handleSaveDailyTask
);

handleEntityClick(
    'rewards-container',
    '.reward-wrapper',
    '.reward-id',
    'rewards',
    fillRewardModalWindow,
    handleSaveReward
);

handleEntityClick(
    'habits-container',
    '.habit-wrapper',
    '.habit-id',
    'habits',
    fillTaskModalWindow,
    handleSaveHabit,
    handleResetHabit
);