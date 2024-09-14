<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="daily-task-wrapper" data-task-status="${dailyTask.status}">
    <span class="daily-task-id" style="display: none">${dailyTask.id}</span>
    <div class="daily-task transition">
        <div class="d-flex">
            <div class="left-control d-flex justify-content-center ${dailyTask.status ? 'task-disabled' : 'task-neutral-bg-color'}">
                <div class="daily-task-control daily-todo-control task-neutral-control-inner-daily-todo" role="checkbox" tabindex="0">
                    <div class="svg-icon ${dailyTask.status ? 'display-check-icon' : 'check'}">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16">
                            <path d="M6.54,13c-.3,0-.59-.13-.81-.35l-3.73-3.9,1.62-1.69,2.86,2.98L12.26,3l1.74,1.56L7.41,12.58c-.21,.25-.51,.4-.83,.42-.01,0-.03,0-.04,0Z" fill-rule="evenodd"></path>
                        </svg>
                    </div>
                </div>
            </div>
            <div class="daily-task-content">
                <div class="d-flex justify-content-between pl-75">
                    <h3 class="daily-task-title markdown">
                        <p>${dailyTask.heading}</p>
                    </h3>
                    <div role="button" class="habitica-menu-drop-down dropdown">
                        <div class="habitica-menu-dropdown-toggle">
                            <div class="dropdown-icon d-flex">
                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 4 16">
                                    <path fill-rule="evenodd" d="M2 4a2 2 0 1 1 0-4 2 2 0 0 1 0 4zm0 6a2 2 0 1 1 0-4 2 2 0 0 1 0 4zm0 6a2 2 0 1 1 0-4 2 2 0 0 1 0 4z"></path>
                                </svg>
                            </div>
                        </div>
                        <div class="dropdown-menu">
                            <div class="dropdown-item edit-task-item">
                                <span class="dropdown-icon-item">
                                    <span class="edit-svg-icon">
                                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16">
                                            <path fill-rule="evenodd" d="M2.723 11.859l1.418 1.419-2.219.788.8-2.207zm2.762.686l-2.03-2.03 7.386-7.385 2.03 2.03-7.386 7.385zm8.704-10.731c.56.56.56 1.468 0 2.03l-.285.284-2.03-2.03.286-.284a1.438 1.438 0 0 1 2.027 0h.002zM11.125.782l-.8.8-8.417 8.415a.731.731 0 0 0-.098.122s-.012.024-.02.036a.713.713 0 0 0-.048.1v.012L.044 15.022a.73.73 0 0 0 .934.935l4.755-1.704a.728.728 0 0 0 .102-.05l.034-.018a.731.731 0 0 0 .122-.097l9.227-9.213A2.896 2.896 0 0 0 11.125.782z"></path>
                                        </svg>
                                    </span>
                                    <span class="text">Изменить</span>
                                </span>
                            </div>
                            <div class="dropdown-item up-task-item">
                                <span class="dropdown-icon-item">
                                    <span class="edit-svg-icon">
                                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 10 11">
                                            <g fill="none" fill-rule="evenodd" stroke="#686274" stroke-width="2">
                                                <path d="M5 3v8M9 6L5 2 1 6"></path>
                                            </g>
                                        </svg>
                                    </span>
                                    <span class="text">Наверх</span>
                                </span>
                            </div>
                            <div class="dropdown-item down-task-item">
                                <span class="dropdown-icon-item">
                                    <span class="edit-svg-icon">
                                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 10 11">
                                            <g fill="none" fill-rule="evenodd" stroke="#686274" stroke-width="2">
                                                <path d="M5,0v8 M1,5l4,4l4-4"></path>
                                            </g>
                                        </svg>
                                    </span>
                                    <span class="text">Вниз</span>
                                </span>
                            </div>
                            <div class="dropdown-item delete-task-item">
                                <span class="dropdown-icon-item">
                                    <span class="edit-svg-icon delete-svg-icon">
                                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 14 16">
                                            <path fill-rule="evenodd" d="M3 14h8V4H3v10zM14 4h-1v10a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V4H0V2h4V1a1 1 0 0 1 1-1h4a1 1 0 0 1 1 1v1h4v2zm-6 8h1V6H8v6zm-3 0h1V6H5v6z"></path>
                                        </svg>
                                    </span>
                                    <span class="text">Удалить</span>
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="daily-task-notes small-text markdown pl-75">
                    <p>${dailyTask.description}</p>
                </div>
                <div class="daily-task-counter">
                    <span>${dailyTask.series}</span>
                </div>
            </div>
        </div>
    </div>
</div>