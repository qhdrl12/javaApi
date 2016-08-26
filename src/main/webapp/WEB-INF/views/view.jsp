<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--<% String cp = request.getContextPath(); %>--%>

<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Data View</title>

    <%--<script src="https://code.jquery.com/jquery-2.1.3.min.js"></script>--%>
    <%--<script src="https://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>--%>
    <%----%>
    <%--<link rel="stylesheet" href="https://netdna.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">--%>
    <%--<link rel="stylesheet" href="https://netdna.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">--%>

    <style type="text/css">
        p{
            text-align: center;
        }
        #rel_table{
            font-family: "Lato", "sans-serif";
        }

        #rel_table tr th{
            padding: 1em;
            background: #e8503a;
            color: white;
        }

        #rel_table tr{
            height: 1em;
        }

        #rel_table tr:nth-child(even){
            background-color: #eee;
        }

        #rel_table tr:nth-child(odd){
            background-color: #fff;
        }

        #rel_table #header_cid{
            width : 280px;
        }
        #rel_table #header_score{
            width : 80px;
        }
        #rel_table #header_title{
            width : 200px;
        }
        #rel_table #header_rid{
            width : 280px;
        }

        #rel_table td{
            text-align: center;
            padding: 1em;
        }

    </style>

</head>
<body>

<c:set var="totalCount" scope="session" value="${fn:length(result)}"/>
<c:set var="pageBlock" scope="session" value="10"/>
<c:set var="currentPage" value="${param.page}"/>
<c:set var="totalPage" value="${(totalCount / pageBlock) + ((totalCount % pageBlock == 0) ? 0 : 1)}"/>

<table id="rel_table">
    <tr>
        <th id="header_cid">기준CID</th>
        <th id="header_score">점수</th>
        <th id="header_title">제목</th>
        <th id="header_rid">연관CID</th>
    </tr>
    <c:forEach items="${result}" var="entry" varStatus="mloop">

        <c:forEach items="${entry.value}" var="item" varStatus="loop">
            <tr>
                <td>
                    <c:choose>
                        <c:when test="${loop.index == 0}">
                            ${entry.key}
                        </c:when>
                    </c:choose>
                </td>
                <c:forEach items="${item}" var="subEntry">
                    <td>${subEntry.value}</td>
                </c:forEach>
            </tr>
        </c:forEach>
    </c:forEach>
</table>

<%--<div class="bs-docs-example" >--%>
<%--<p class="well demo content1">--%>
<%--Dynamic content here.--%>
<%--</p>--%>
<%--<p class="demo demo1"></p>--%>
<%--</div>--%>

<%--<script type="text/javascript">--%>
<%--$('.demo1').bootpag({--%>
<%--total: '${pageBlock}'--%>
<%--}).on("page", function(event, num){--%>
<%--console.dir(event);--%>
<%--$(".content1").html("Page " + num); // or some ajax content loading...--%>
<%--$(this).bootpag({total: '${totalPage}', maxVisible: 10});--%>
<%--});--%>
<%--</script>--%>
</body>
</html>

