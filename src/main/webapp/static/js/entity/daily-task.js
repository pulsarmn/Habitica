import {
    awardReward,
    reloadEntities,
    setupEntityInput,
    updateBalance,
    updateEntitySeries,
    withdrawReward
} from "../service/generalService.js";

reloadEntities(`daily-tasks`, `daily-tasks-container`);

document.addEventListener(`DOMContentLoaded`, function () {
    setupEntityInput(`dailyTaskInput`, `daily-tasks`);
});

document.querySelector(`#daily-tasks-container`).addEventListener('click', function(event) {
    if (event.target.closest('.daily-task-control')) {
        const taskWrapper = event.target.closest('.daily-task-wrapper');
        const leftControl = taskWrapper.querySelector('.left-control');
        const svgCheck = taskWrapper.querySelector('.check');
        const displayCheck = taskWrapper.querySelector('.display-check-icon');
        const dailyTaskId = taskWrapper.querySelector('.daily-task-id').textContent;
        const isTaskCompleted = leftControl.classList.contains('task-disabled');

        if (isTaskCompleted) {
            leftControl.classList.remove('task-disabled');
            leftControl.classList.add('task-neutral-bg-color');
            if (displayCheck) {
                displayCheck.classList.add('check');
                displayCheck.classList.remove('display-check-icon');
            }

            updateEntitySeries(dailyTaskId, `daily-tasks`, `decrement`);
            withdrawReward(dailyTaskId, `daily-task`, `decrement`).then(() => {
                updateBalance();
                reloadEntities(`daily-tasks`, `daily-tasks-container`);
            });
        } else {
            leftControl.classList.remove(`task-neutral-bg-color`);
            leftControl.classList.add(`task-disabled`);
            if (svgCheck) {
                svgCheck.classList.add(`display-check-icon`);
                svgCheck.classList.remove(`check`);
            }

            updateEntitySeries(dailyTaskId, `daily-tasks`, `increment`);
            awardReward(dailyTaskId, `daily-task`).then(() => {
                updateBalance();
                reloadEntities(`daily-tasks`, `daily-tasks-container`);
            });
        }
    }
});