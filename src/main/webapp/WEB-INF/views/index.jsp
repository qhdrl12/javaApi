<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hello Millky</title>
</head>
<body>
<c:out value="<xmp>" escapeXml="true"></c:out>
<h2>Hello! ${test}</h2>
<%--<c:set var="num1" value="200"></c:set>--%>
<%--<c:set var="num1" value="200" scope="page"></c:set>--%>

<%--<c:out value="${num1}"></c:out>--%>

<c:choose>
    <c:when test="${empty num1}">
        데이터가 없습니다
    </c:when>
    <c:when test="${num1 != null}">
        존재합니다
    </c:when>
    <c:otherwise>
        <c:out value="${num1}"></c:out>
    </c:otherwise>
</c:choose>


<c:forEach var="i" begin="1" end="100" step="1">
    ${i}
</c:forEach>




<div>JSP version</div>
</body>
</html>

