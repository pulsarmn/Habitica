<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="task-wrapper">
  <span class="task-id" style="display: none">${task.id}</span>
  <div class="task transition">
    <div class="d-flex">
      <div class="left-control d-flex justify-content-center task-neutral-bg-color">
        <div class="task-control daily-todo-control task-neutral-control-inner-daily-todo" role="checkbox" tabindex="0">
          <div class="svg-icon check">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16">
              <path d="M6.54,13c-.3,0-.59-.13-.81-.35l-3.73-3.9,1.62-1.69,2.86,2.98L12.26,3l1.74,1.56L7.41,12.58c-.21,.25-.51,.4-.83,.42-.01,0-.03,0-.04,0Z" fill-rule="evenodd"></path>
            </svg>
          </div>
        </div>
      </div>
      <div class="task-content">
        <div class="d-flex justify-content-between pl-75">
          <h3 class="task-title markdown">
            <p>${task.heading}</p>
          </h3>
          <%@ include file="dropdown-menu.jsp"%>
        </div>
        <div class="task-notes small-text markdown pl-75">
          <p>${task.description}</p>
        </div>
      </div>
    </div>
  </div>
</div>