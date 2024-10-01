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