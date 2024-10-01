import {
    createEntity,
    reloadEntities,
    updateBalance,
    updateEntitySeries,
    withdrawReward
} from "../service/generalService.js";

reloadEntities(`habits`, `habits-container`);

document.addEventListener('DOMContentLoaded', function () {
    const taskInput = document.querySelector('#habitInput');
    taskInput.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            const taskValue = taskInput.value.trim();
            if (taskValue) {
                createEntity(`habits`, taskValue, taskInput);
            }
        }
    });

    taskInput.addEventListener('input', function () {
        if (this.value.includes('\n')) {
            this.value = this.value.replace(/\n/g, '');
        }
    });
});

document.querySelector('#habits-container').addEventListener('click', function(event) {
    const habitWrapper = event.target.closest('.habit-wrapper');
    const habitId = habitWrapper.querySelector('.habit-id').textContent.trim();

    if (event.target.closest('.left-control')) {
        updateEntitySeries(habitId, `habits`, `increment`);
        withdrawReward(habitId, `habit`, `increment`).then(() => {
            updateBalance();
            reloadEntities(`habits`, `habits-container`);
        });
    }else if (event.target.closest('.right-control')) {
        updateEntitySeries(habitId, `habits`, `decrement`);
        withdrawReward(habitId, `habit`, `decrement`).then(() => {
            updateBalance();
            reloadEntities(`habits`, `habits-container`);
        });
    }
});