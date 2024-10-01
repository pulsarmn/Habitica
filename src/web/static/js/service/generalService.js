export function getEntityDataToEdit(entityId, endpoint) {
    return fetch(`/${endpoint}?id=${entityId}`, {
        method: 'GET',
        headers: { 'Content-Type': 'text/html' }
    }).then(response => {
        if (response.ok) return response.text();
        throw new Error('Ошибка при получении данных сущности!');
    });
}