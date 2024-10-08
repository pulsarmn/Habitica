export function showModal(modalWindow) {
    modalWindow.classList.add('active');
}

export function hideModal(modalWindow) {
    modalWindow.classList.remove('active');
}

export function putModal(modalWindowWrapper, html) {
    modalWindowWrapper.innerHTML = html;
}

export function deleteModal(modalWindowWrapper) {
    modalWindowWrapper.innerHTML = '';
}

export function toggleSaveButton(taskTitleInput, saveButton) {
    if (taskTitleInput.value.trim()) {
        saveButton.classList.remove('disable');
    } else {
        saveButton.classList.add('disable');
    }
}

export function disableSaveButton(saveButton) {
    saveButton.classList.add(`disable`);
}

export function enableSaveButton(saveButton) {
    saveButton.classList.remove(`disable`);
}