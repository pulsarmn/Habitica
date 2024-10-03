import {reloadEntities, setupEntityInput, updateBalance} from "../service/generalService.js";

reloadEntities(`rewards`, `rewards-container`);

document.addEventListener('DOMContentLoaded', function () {
    setupEntityInput(`rewardInput`, `rewards`);
});

document.querySelector('#rewards-container').addEventListener('click', function(event) {
    if (event.target.closest('.reward-control')) {
        const rewardControl = event.target.closest('.reward-control');
        const rewardWrapper = rewardControl.closest('.reward-wrapper');
        const rewardId = rewardWrapper.querySelector('.reward-id').textContent;

        fetch(`/purchase-reward`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: `rewardId=${encodeURIComponent(rewardId)}`
        }).then(response => {
            if (response.ok) {
                console.log(`The reward with id 3 has been successfully purchased!`);
                updateBalance();
            }else if (response.status === 400) {
                console.error(`Insufficient funds: ${response.statusText}`);
            }else {
                throw new Error(`Error when buying a reward: ${response.statusText}`)
            }
        }).catch(error => {
            console.error(`Network error: `, error);
        });
    }
});