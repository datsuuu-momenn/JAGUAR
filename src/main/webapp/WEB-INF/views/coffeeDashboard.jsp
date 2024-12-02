<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>コーヒーのおすすめ</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/css-jsf.css">
</head>
<body>
    <div class="container">
        <h1>あなたの好みのコーヒーダッシュボード</h1>
        
        <div class="recommendations">
            <c:if test="${not empty recommendations}">
                <c:forEach items="${recommendations}" var="recommendation">
                    <div class="coffee-card">
                        <h2>${recommendation.coffeeType}</h2>
                        <div class="recommendation">
                            <div class="bean-name">おすすめのコーヒー豆：${recommendation.recommendedBean}</div>
                            <p>${recommendation.beanDescription}</p>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            
            <c:if test="${empty recommendations}">
                <p>コーヒーの種類が選択されていません。</p>
            </c:if>
        </div>
        
        <div class="actions">
            <a href="index-jsf.xhtml" class="button">トップページに戻る</a>
        </div>
    </div>
</body>
</html> 