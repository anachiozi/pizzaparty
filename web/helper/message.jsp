<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    
<c:set var="obj" value="${message}"/>

<c:forEach items="${obj.getMessages()}" var="msg">
    <div class="alert alert-info">
        <c:out value="${msg}" />
    </div>
</c:forEach>

<c:forEach items="${obj.getWarnings()}" var="wng">
    <div class="alert alert-warning">
        <c:out value="${wng}" />
    </div>
</c:forEach>