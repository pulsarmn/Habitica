import {
    reloadEntities,
    setupEntityInput,
    updateBalance,
    updateEntitySeries,
    withdrawReward
} from "../service/generalService.js";

reloadEntities(`habits`, `habits-container`);

document.addEventListener(`DOMContentLoaded`, function () {
    setupEntityInput(`habitInput`, `habits`);
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