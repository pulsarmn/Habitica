<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="habit-wrapper task-wrapper">
    <span class="habit-id" style="display: none">${habit.id}</span>
    <div class="task transition">
        <div class="d-flex">
            <div class="left-control control d-flex align-items-center justify-content-center task-neutral-bg-color">
                <div class="habit-control" role="button" tabindex="0">
                    <div data-v-89eab042="" class="svg-icon positive-svg">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 10 10">
                            <path path fill-rule="evenodd" d="M6 4V0H4v4H0v2h4v4h2V6h4V4H6z"></path>
                        </svg>
                    </div>
                </div>
            </div>
            <div class="task-content">
                <div class="d-flex justify-content-between pl-75">
                    <h3 class="task-title markdown">
                        <p>${habit.heading}</p>
                    </h3>
                    <%@ include file="dropdown-menu.jsp"%>
                </div>
                <div class="task-notes small-text markdown pl-75">
                    <p>${habit.description}</p>
                </div>
                <div class="icons-right d-flex justify-content-end">
                    <span class="">+${habit.goodSeries}</span>
                    <span>&nbsp;|&nbsp;</span>
                    <span>-${habit.badSeries}</span>
                </div>
            </div>
            <div class="right-control d-flex align-items-center justify-content-center task-neutral-bg-color">
                <div class="habit-control" role="checkbox" tabindex="0">
                    <div data-v-89eab042="" class="svg-icon negative-svg">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 10 2">
                            <path fill-rule="evenodd" d="M0 0h10v2H0z"></path>
                        </svg>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>