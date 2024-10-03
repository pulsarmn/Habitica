import {deleteModal, hideModal, putModal, showModal, toggleSaveButton} from "./modalService.js";

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

export function handleEntityClick(containerId, entityWrapperClass, entityIdClass, endpoint, modalFiller, saveHandler, resetHandler = null) {
    document.querySelector(`#${containerId}`).addEventListener('click', function(event) {
        if (event.target.closest('.habitica-menu-dropdown-toggle')) {
            const dropdownToggle = event.target.closest('.habitica-menu-dropdown-toggle');
            const entityWrapper = dropdownToggle.closest(entityWrapperClass);
            const dropdownMenu = entityWrapper.querySelector('.dropdown-menu');
            const entityId = entityWrapper.querySelector(entityIdClass).textContent;

            dropdownMenu.addEventListener('click', function(event) {
                if (event.target.closest('.delete-task-item')) {
                    deleteEntity(entityId, endpoint, containerId);
                } else if (event.target.closest('.edit-task-item')) {
                    editEntity(entityId, endpoint, modalFiller, saveHandler, resetHandler);
                }
            });
        }
    });
}

function editEntity(entityId, endpoint, modalFiller, saveHandler, resetHandler) {
    getEntityDataToEdit(entityId, endpoint).then(html => {
        const modalWindowWrapper = document.querySelector(`#modal-window-wrapper`);
        putModal(modalWindowWrapper, html);

        const modalWindow = modalWindowWrapper.querySelector(`#edit-entity-modal`);
        const saveButton = modalWindowWrapper.querySelector(`.save-entity`);
        const titleInput = modalWindowWrapper.querySelector(`#entity-title`);

        modalFiller(modalWindowWrapper);
        showModal(modalWindow);
        toggleSaveButton(titleInput, saveButton);

        titleInput.addEventListener(`input`, function() {
            toggleSaveButton(titleInput, saveButton);
        });

        saveHandler(modalWindowWrapper, entityId);

        if (resetHandler) {
            resetHandler(modalWindowWrapper, entityId);
        }

        handleDeleteEntity(modalWindowWrapper, entityId, endpoint);

        document.querySelector('#close-modal-btn').addEventListener('click', function() {
            hideModal(modalWindow);
            deleteModal(modalWindowWrapper);
        });
    }).catch(error => {
        console.log(`Error while receiving entity data`, error);
    });
}

export function resetHabit(habitId) {
    return fetch(`/habits?id=${habitId}&update=reset`, {
        method: 'PUT'
    }).then(response => {
        if (!response.ok) throw new Error('Error when resetting an entity!');
        console.log(`Entity with ID ${habitId} has been reset!`);
    });
}