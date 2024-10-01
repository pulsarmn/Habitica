import {
    awardReward,
    deleteEntity,
    reloadEntities,
    setupEntityInput,
    updateBalance
} from "../service/generalService.js";

reloadEntities(`tasks`, `tasks-container`);

document.addEventListener('DOMContentLoaded', function () {
    setupEntityInput(`taskInput`, `tasks`);
});

document.getElementById('tasks-container').addEventListener('click', function(event) {
    if (event.target.closest('.task-control')) {
        const taskControl = event.target.closest('.task-control');
        const taskWrapper = taskControl.closest('.task-wrapper');
        const leftControl = taskWrapper.querySelector('.left-control');
        const svgCheck = taskWrapper.querySelector('.check');
        const taskId = taskWrapper.querySelector('.task-id').textContent;

        if (svgCheck) {
            leftControl.classList.remove('task-neutral-bg-color');
            leftControl.classList.add('task-disabled');

            svgCheck.classList.add('display-check-icon');
            svgCheck.classList.remove('check');

            awardReward(taskId, `task`).then(() => {
                updateBalance();
                deleteEntity(taskId, `tasks`, `tasks-container`);
            })
        } else {
            leftControl.classList.remove('task-disabled');
            leftControl.classList.add('task-neutral-bg-color');

            const displayCheck = taskWrapper.querySelector('.display-check-icon');
            displayCheck.classList.add('check');
            displayCheck.classList.remove('display-check-icon');
        }
    }
});