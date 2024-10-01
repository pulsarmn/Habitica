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