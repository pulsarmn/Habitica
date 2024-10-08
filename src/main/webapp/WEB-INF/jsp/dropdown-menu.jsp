<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="bundles.jsp"%>
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
                <span class="text"><fmt:message key="home.dropdown.edit" bundle="${labels}"/></span>
            </span>
        </div>
        <div class="dropdown-item delete-task-item">
            <span class="dropdown-icon-item">
                <span class="edit-svg-icon delete-svg-icon">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 14 16">
                        <path fill-rule="evenodd" d="M3 14h8V4H3v10zM14 4h-1v10a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V4H0V2h4V1a1 1 0 0 1 1-1h4a1 1 0 0 1 1 1v1h4v2zm-6 8h1V6H8v6zm-3 0h1V6H5v6z"></path>
                    </svg>
                </span>
                <span class="text"><fmt:message key="home.dropdown.delete" bundle="${labels}"/></span>
            </span>
        </div>
    </div>
</div>