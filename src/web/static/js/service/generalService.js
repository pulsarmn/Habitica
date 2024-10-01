import {deleteModal, hideModal} from "./modalService.js";

export function getEntityDataToEdit(entityId, endpoint) {
    return fetch(`/${endpoint}?id=${entityId}`, {
        method: `GET`,
        headers: { 'Content-Type': 'text/html' }
    }).then(response => {
        if (response.ok) return response.text();
        throw new Error(`Error receiving entity data: ${response.statusText}`);
    });
}

export function reloadEntities(endpoint, container) {
    fetch(`/${endpoint}`, {
        method: `GET`,
        headers: {
            'Content-Type': 'text/html'
        }
    }).then(response => {
        if (response.ok) {
            return response.text();
        }else {
            throw new Error(`Error when getting the list of entities: ${response.statusText}`);
        }
    }).then(html => {
        const habitList = document.querySelector(`#${container}`);
        habitList.innerHTML = html;
    }).catch(error => {
        console.log(`Error: `, error);
    });
}

export function updateEntity(entityId, endpoint, entityData) {
    const baseQuery = `/${endpoint}`;
    const params = new URLSearchParams({ id: entityId });

    if (endpoint === 'daily-tasks') {
        params.append('update', 'true');
    } else if (endpoint === 'habits') {
        params.append('update', 'update');
    }

    const query = `${baseQuery}?${params.toString()}`;

    return fetch(query, {
        method: `PUT`,
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(entityData)
    }).then(response => {
        if (!response.ok) throw new Error(`Error saving an entity: ${response.statusText}`);
        console.log(`Entity with id ${entityId} has been updated!`);
    });
}

export function deleteEntity(itemId, endpoint, container) {
    return fetch(`/${endpoint}?id=${itemId}`, {
        method: `DELETE`
    }).then(response => {
        if (response.ok) {
            console.log(`Element with ID ${itemId} has been deleted`);
            reloadEntities(endpoint, container);
        }else {
            console.error(`Error deleting ${endpoint} element`);
        }
    }).catch(error => {
        console.error(`Error`, error);
    });
}

export function awardReward(entityId, type) {
    return fetch(`/purchase-reward?type=${type}&id=${entityId}`, {
        method: `PUT`
    }).then(response => {
        if (response.ok) {
            console.log(`The reward is accrued!`);
        }else {
            throw new Error(`Error when calculating remuneration!`)
        }
    }).catch(error => {
        console.error(`Network error!`, error);
    });
}

export function updateEntitySeries(entityId, endpoint, action) {
    fetch(`/${endpoint}?id=${entityId}`, {
        method: 'PUT',
        body: JSON.stringify({ action: `${action}` }),
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => {
        if (response.ok) {
            reloadEntities(endpoint, `${endpoint}-container`);
            console.log(`Entity with id ${entityId} has been updated!`);
        } else {
            console.error(`Error when changing the counter: ${response.statusText}`);
        }
    }).catch(error => {
        console.error('Network error:', error);
    });
}

export function updateBalance() {
    fetch(`/balance`, {
        method: `GET`,
    })
        .then(response => response.json())
        .then(balance => {
            animateBalanceChange(balance.balance, 1000);
        }).catch(error => console.error(`Balance update error: `, error));
}

function animateBalanceChange(targetBalance, duration) {
    const balanceElement = document.querySelector(`#userBalance`);
    const startTime = performance.now();
    const initialBalance = Number(balanceElement.textContent);

    function updateBalanceAnimation(currentTime) {
        const elapsedTime = currentTime - startTime;
        const progress = Math.min(elapsedTime / duration, 1);

        const currentBalance = initialBalance + (targetBalance - initialBalance) * progress;

        balanceElement.textContent = currentBalance.toFixed(2);

        if (progress < 1) {
            requestAnimationFrame(updateBalanceAnimation);
        }
    }
    requestAnimationFrame(updateBalanceAnimation);
}

export function withdrawReward(elementId, type, action) {
    return fetch(`/purchase-reward?type=${type}&id=${elementId}&action=${action}`, {
        method: `PUT`
    }).then(response => {
        if (response.ok) {
            console.log(`The reward is accrued!`);
        }else {
            throw new Error(`An error occurred when calculating the reward: ${response.statusText}`)
        }
    }).catch(error => {
        console.error(`Network error:`, error);
    });
}

export function test() {
    fetch('/tasks', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `taskHeading=${encodeURIComponent(taskValue)}`
    }).then(response => {
        if (response.ok) {
            taskInput.value = '';
            console.log('Задача отправлена успешно!');
            reloadEntities(`tasks`, `tasks-container`);
        } else {
            console.log('Ошибка при отправке задачи');
        }
    }).catch(error => {console.assert('Ошибка сети: ' + error.message);});
}

export function test1() {
    fetch('/rewards', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `rewardHeading=${encodeURIComponent(taskValue)}`
    }).then(response => {
        if (response.ok) {
            taskInput.value = '';
            reloadEntities(`rewards`, `rewards-container`);
            console.log('Награда отправлена успешно!');
        } else {
            console.log('Ошибка при отправке награды!');
        }
    }).catch(error => {console.log('Ошибка сети: ' + error.message);});
}

export function test2() {
    fetch('/habits', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `habitHeading=${encodeURIComponent(taskValue)}`
    }).then(response => {
        if (response.ok) {
            taskInput.value = '';
            reloadEntities(`habits`, `habits-container`);
            console.log('Привычка отправлена успешно!');
        } else {
            console.log('Ошибка при отправке привычки!');
        }
    }).catch(error => {console.log('Ошибка сети: ' + error.message);});
}

export function test3() {
    fetch('/daily-tasks', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `dailyTaskHeading=${encodeURIComponent(taskValue)}`
    }).then(response => {
        if (response.ok) {
            taskInput.value = '';
            reloadEntities(`daily-tasks`, `daily-tasks-container`);
            console.log('Задача отправлена успешно!');
        } else {
            console.log('Ошибка при отправке задачи');
        }
    }).catch(error => {console.log('Ошибка сети: ' + error.message);});
}

export function createEntity(endpoint, entityValue, entityInput) {
    fetch(`/${endpoint}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `entityHeading=${encodeURIComponent(entityValue)}`
    }).then(response => {
        if (response.ok) {
            entityInput.value = '';
            reloadEntities(endpoint, `${endpoint}-container`);
            console.log('The entity was sent successfully!');
        } else {
            throw new Error(`Error sending the entity: ${response.statusText}`)
        }
    }).catch(error => {console.log('Network error:', error);});
}

export function setupEntityInput(inputId, endpoint) {
    const taskInput = document.querySelector(`#${inputId}`);

    taskInput.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            e.preventDefault();

            const taskValue = taskInput.value.trim();
            if (taskValue) {
                createEntity(endpoint, taskValue, taskInput);
            }
        }
    });

    taskInput.addEventListener('input', function () {
        if (this.value.includes('\n')) {
            this.value = this.value.replace(/\n/g, '');
        }
    });
}

export function getJsonEntity(entityDataId) {
    const modalWindowWrapper = document.querySelector(`#modal-window-wrapper`);
    const taskDataElement = modalWindowWrapper.querySelector(`#${entityDataId}`);
    return JSON.parse(taskDataElement.textContent);
}

function handleDeleteItem(modalWindowWrapper, dailyTaskId) {
    const modalWindow = modalWindowWrapper.querySelector(`#edit-task-modal`);
    const deleteButton = modalWindowWrapper.querySelector(`.delete-task`);

    deleteButton.addEventListener(`click`, function() {
        deleteEntity(dailyTaskId, `tasks`, `tasks-container`).then(() => {
            if (modalWindow != null) {
                hideModal(modalWindow);
                deleteModal(modalWindowWrapper);
            }
        });
    });
}

function handleDeleteDailyTask(modalWindowWrapper, dailyTaskId) {
    const modalWindow = modalWindowWrapper.querySelector(`#edit-daily-task-modal`);
    const deleteButton = modalWindowWrapper.querySelector(`.delete-task`);

    deleteButton.addEventListener(`click`, function() {
        deleteEntity(dailyTaskId, `daily-tasks`, `daily-tasks-container`).then(() => {
            if (modalWindow != null) {
                hideModal(modalWindow);
                deleteModal(modalWindowWrapper);
            }
        });
    });
}

function handleDeleteHabit(modalWindowWrapper, habitId) {
    const modalWindow = modalWindowWrapper.querySelector(`#edit-habit-modal`);
    const deleteButton = modalWindowWrapper.querySelector(`.delete-task`);

    deleteButton.addEventListener(`click`, function() {
        deleteEntity(habitId, `habits`, `habits-container`).then(() => {
            if (modalWindow != null) {
                hideModal(modalWindow);
                deleteModal(modalWindowWrapper);
            }
        });
    });
}

function handleDeleteReward(modalWindowWrapper, rewardId) {
    const modalWindow = modalWindowWrapper.querySelector(`#edit-reward-modal`);
    const deleteButton = modalWindowWrapper.querySelector(`.delete-reward`);

    deleteButton.addEventListener(`click`, function() {
        deleteEntity(rewardId, `rewards`, `rewards-container`).then(() => {
            if (modalWindow != null) {
                hideModal(modalWindow);
                deleteModal(modalWindowWrapper);
            }
        });
    });
}

export function handleDeleteEntity(modalWindowWrapper, entityId, endpoint) {
    const modalWindow = modalWindowWrapper.querySelector(`#edit-entity-modal`);
    const deleteButton = modalWindowWrapper.querySelector(`.delete-entity`);

    deleteButton.addEventListener(`click`, function() {
        deleteEntity(entityId, endpoint, `${endpoint}-container`).then(() => {
            if (modalWindow != null) {
                hideModal(modalWindow);
                deleteModal(modalWindowWrapper);
            }
        });
    });
}