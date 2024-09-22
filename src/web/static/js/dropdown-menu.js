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