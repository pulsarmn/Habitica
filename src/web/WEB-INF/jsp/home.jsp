<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Задачи | Habitica</title>
    <link rel="stylesheet" href="<c:url value="/static/css/index.css"/>?v=1.1">
    <link rel="stylesheet" href="<c:url value="/static/css/home.css"/>?v=1.2">
</head>
<body>
<div id="app">
    <div class="navbar-wrapper">
        <nav class="navbar">
            <div class="app-logo">
                <svg width="32" height="32" viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg">
                    <path style="fill: white;" fill-rule="evenodd" clip-rule="evenodd" d="M31.62 28.86c-.32-.706-1.057-1.048-1.538-.706-.48.341-1.147.393-1.78.24-.633-.153-.753-1.604-.616-3.278.136-1.673.363-2.318.506-2.925.162-.61.877-.562.962-.084.086.479.582.479 1.307-.391.724-.87.617-3.409-.218-5.474-.836-2.065.326-1.865.664-1.66.337.205.544-.102.462-1.28-.082-1.178-1.166-2.098-2.039-2.663-.873-.564-1.936-1.186-1.911-2.697.025-1.511 2.08-1.464 2.358-1.439.279.025.815-.093.506-1.663-.31-1.57-1.43-1.869-2.133-1.826-.703.042-1.177.428-2.17.053-.995-.376-1.655-.23-2.58-.023-.926.206-2.138.776-3.646 1.183-.795.219-1.064.274-1.93.288-.532.008-.755.653-.043 1.444.563.643 1.839.814 2.606.707.494-.07.608.258.563.74a8.013 8.013 0 0 0-.01 1.795c.08.6.18 1.62-.103 2.286-.14.326-.545.677-.98.653-.565-.034-1.022-.7-1.414-1.49-.825-1.662-1.793-2.014-5.404-3.535-3.248-1.367-5.007-3.5-6.096-4.874-.969-1.217-1.939-.756-1.85.342.07.852.592 3.604 1.912 5.257 1.623 2.525 4.128 3.67 7.013 3.895.755.06 1.226.208 1.29.553.095.735-.622 1.244-1.959 1.09-1.336-.157-1.907.087-1.641.848.85 1.79 2.809 1.869 3.623 1.942.275.05 1.246 0 1.764.143.605.166.735 1.005-.14 1.459-1.558.76-2.237 1.391-3.025 2.83-.595 1.13-1.108 3.022-.574 5.745.513 2.648-3.337 2.733-5 2.357-.716-.151-1.47-1.512.287-2.65 1.421-.922 1.708-1.49 1.645-2.657-.074-1.36-.824-1.458-.822-2.64v-2.82a.435.435 0 0 0-.435-.435H7.698a.435.435 0 0 1-.435-.434v-1.7a.435.435 0 0 0-.435-.435H5.501a.435.435 0 0 1-.435-.435v-1.524a.435.435 0 0 0-.435-.435H3.015a.435.435 0 0 1-.435-.435v-1.603a.435.435 0 0 0-.435-.434H.435a.435.435 0 0 0-.435.434v1.705c0 .24.195.435.435.435h1.62c.24 0 .435.195.435.435v6.076c0 .241.195.435.435.435h1.71c.241 0 .436.196.436.435v1.988c0 .24.195.434.435.434h2.402c.734-.052.862.934.854 1.286-.016.803-.923 1.06-1.352 1.395-1.145.884-2.031 1.783-1.513 3.512l.013.036c.945 2.007 3.542 1.8 5.183 1.8h10.326c.584 0 1.184.135 1.046-.545-.136-.68-.425-1.61-1.265-1.61-.84 0-.703.467-1.524.228-.821-.238-.822-1.348.411-3.279 1.276-1.649 3.46-1.524 4.781-.358 1.32 1.166.93 3.191.653 4.354-.158.82.218 1.224.669 1.213h5.242c.806-.014.647-.556.185-1.614h.003z"></path>
                </svg>
            </div>
            <div class="collapse">
                <ul class="navbar-list">
                    <li class="navbar-list-item"><a href="/">Задачи</a></li>
                    <li class="navbar-list-item"><a href="">Скоро</a></li>
                    <li class="navbar-list-item"><a href="">Скоро</a></li>
                </ul>
                <div class="collapse-right">
                    <div class="navbar-balance">
                        <div class="item-with-icon">
                            <div class="top-menu-icon">
                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                                    <g fill="none" fill-rule="evenodd">
                                        <circle cx="12" cy="12" r="12" fill="#FFA623"></circle>
                                        <path fill="#FFF" d="M6.3 17.7c-3.1-3.1-3.1-8.2 0-11.3 3.1-3.1 8.2-3.1 11.3 0" opacity=".5"></path>
                                        <path fill="#FFF" d="M17.7 6.3c3.1 3.1 3.1 8.2 0 11.3-3.1 3.1-8.2 3.1-11.3 0" opacity=".25"></path>
                                        <path fill="#BF7D1A" d="M12 2C6.5 2 2 6.5 2 12s4.5 10 10 10 10-4.5 10-10S17.5 2 12 2zm0 18c-4.4 0-8-3.6-8-8s3.6-8 8-8 8 3.6 8 8-3.6 8-8 8z" opacity=".5"></path>
                                        <path fill="#BF7D1A" d="M13 9v2h-2V9H9v6h2v-2h2v2h2V9z" opacity=".75"></path>
                                    </g>
                                </svg>
                            </div>
                            <span>${sessionScope.userBalance.getBalance()}</span>
                        </div>
                    </div>
                    <div class="form-inline">
                        <div class="item-notifications">
                            <div class="top-menu-icon notifications pointer">
                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                                    <path fill-rule="evenodd" d="M21 0H3C1.3 0 0 1.3 0 3v14c0 1.7 1.3 3 3 3h4l2.9 3.1c1.2 1.2 3.1 1.2 4.2 0L17 20h4c1.7 0 3-1.3 3-3V3c0-1.7-1.3-3-3-3zm1 17c0 .6-.4 1-1 1h-4.9l-3.5 3.7c-.2.2-.4.3-.6.3-.2 0-.4-.1-.7-.3L7.9 18H3c-.6 0-1-.4-1-1V3c0-.6.4-1 1-1h18c.6 0 1 .4 1 1v14zM19 7H5V5h14v2zm0 4H5V9h14v2zm0 4H5v-2h14v2z"></path>
                                </svg>
                            </div>
                        </div>
                        <div class="item-user">
                            <div class="top-menu-icon user-icon pointer">
                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                                    <path fill-rule="evenodd" d="M15 13h-.4c1.9-1.2 3.3-3.3 3.4-5.8.1-3.8-3.1-7.2-6.9-7.2C7.1 0 4 3.1 4 7c0 2.6 1.3 4.8 3.4 6H7c-3.9 0-7 3.1-7 7v1c0 1.7 1.3 3 3 3h16c1.7 0 3-1.3 3-3v-1c0-3.9-3.1-7-7-7zM6 7c0-2.8 2.2-5 5-5s5 2.2 5 5-2.2 5-5 5-5-2.2-5-5zm13 15H3c-.6 0-1-.4-1-1v-1c0-2.8 2.2-5 5-5h8c2.8 0 5 2.2 5 5v1c0 .6-.4 1-1 1z"></path>
                                </svg>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </nav>

        <div class="content">
            <div class="header-wrapper">
                <div id="app-header">
                    <div class="member-details">
                        <div class="avatar-container">
                            <div class="avatar" style="position: relative;">
                                <img id="avatar" class="avatar-img" style="position: absolute;" src="${sessionScope.userImage.imageAddr}" alt="">
                                <input type="file" id="fileInput" name="profileAvatar" accept="image/*">
                            </div>
                        </div>
                        <div class="member-stats">
                            <div class="member-data">
                                <h3 class="character-name">pulsarmn</h3>
                                <div class="character-level">
                                    <span>@pulsarmn</span>
                                    <!-- <span>•</span>
                                    <span>Уровень 4 воин</span> -->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="tasks-wrapper">
                <div class="user-tasks-page">
                    <div class="tasks-grid">
                        <div class="tasks-navigation">
                            <div class="create-task-area">
                                <div id="create-task-btn" class="create-btn">
                                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 10 10">
                                        <path fill-rule="evenodd" d="M6 4V0H4v4H0v2h4v4h2V6h4V4H6z"></path>
                                    </svg>
                                    <div>Добавить задачу</div>
                                </div>
                            </div>
                        </div>
                        <div class="tasks-columns">
                            <div class="tasks-column habit">
                                <div class="column-title">
                                    <h2>Привычки</h2>
                                </div>
                                <div class="tasks-list">
                                    <textarea class="quick-add" id="taskInput" name="task" rows="1" cols="30" placeholder="Добавить привычку"></textarea>
                                </div>
                            </div>
                            <div class="tasks-column daily">
                                <div class="column-title">
                                    <h2>Ежедневные дела</h2>
                                </div>
                                <div class="tasks-list">
                                    <textarea class="quick-add" placeholder="Добавить ежедневное дело" rows="1"></textarea>
                                    <div class="sortable-tasks">

                                    </div>
                                </div>
                            </div>
                            <div class="tasks-column todo">
                                <div class="column-title">
                                    <h2>Задачи</h2>
                                </div>
                                <div class="tasks-list">
                                    <textarea class="quick-add" placeholder="Добавить задачу" rows="1"></textarea>
                                </div>
                            </div>
                            <div class="tasks-column reward">
                                <div class="column-title">
                                    <h2>Награды</h2>
                                </div>
                                <div class="tasks-list">
                                    <textarea class="quick-add" placeholder="Добавить награду" rows="1"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="../../static/js/home.js?v=1.0"></script>
</html>
