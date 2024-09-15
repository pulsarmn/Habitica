// change user avatar
document.getElementById('avatar').addEventListener('click', function() {
    document.getElementById('fileInput').click();
});

document.getElementById('fileInput').addEventListener('change', function() {
    const file = this.files[0];

    if (file) {
        const validImageTypes = ['image/jpeg', 'image/png', 'image/gif'];
        if (!validImageTypes.includes(file.type)) {
            alert('Выберите изображение формата JPEG, PNG или GIF.');
            return;
        }

        const maxSizeInBytes = 5 * 1024 * 1024;
        if (file.size > maxSizeInBytes) {
            alert('Файл слишком большой. Максимальный размер 5MB.');
            return;
        }

        const formData = new FormData();
        formData.append('profileAvatar', file);

        const xhr = new XMLHttpRequest();
        xhr.open('POST', '/upload-avatar', true);
        xhr.onload = function () {
            if (xhr.status === 200) {
                alert('Фотография успешно обновлена!');
                document.getElementById('avatar').src = URL.createObjectURL(file);
            } else {
                alert('Произошла ошибка при загрузке файла.');
            }
        };
        xhr.send(formData);
    }
});