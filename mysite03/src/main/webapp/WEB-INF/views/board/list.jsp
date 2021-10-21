<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					
					<c:forEach items='${limitList }' var='vo' varStatus='status'>
						<tr>
							<td>${(count - (pageNo - 1) * row )- status.index }</td>
							<c:choose>
								<c:when test='${vo.orderNo > 0 }'>
									<td style="text-align: left; padding-left: ${20 * (vo.depth) + 20 }px">
									<img src='${pageContext.servletContext.contextPath }/assets/images/reply.png' />
										<a	href="${pageContext.servletContext.contextPath }/board/view/${vo.no }?p=${pageNo }">${vo.title }</a>
									</td>
								</c:when>
								<c:otherwise>
									<td style="text-align: left; padding-left: 20px">
										<a	href="${pageContext.servletContext.contextPath }/board/view/${vo.no }?p=${pageNo }">${vo.title }</a>
									</td>
								</c:otherwise>
							</c:choose>
							<td>${vo.name }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<td>
							<c:choose>
								<c:when test='${authUser.no == vo.userNo }'>
								    <a href="${pageContext.servletContext.contextPath }/board/delete/${vo.no }?p=${pageNo }" >삭제</a>
								</c:when>
								<c:otherwise>
									<a>-</a>
								</c:otherwise>
							</c:choose>
							</td>
						</tr>
					</c:forEach>
				</table>

				<!-- pager 추가 -->
				<c:import url="/WEB-INF/views/includes/pageNo.jsp"/>


				<div class="bottom">
					<a href="${pageContext.servletContext.contextPath }/board/write?p=${pageNo }" id="new-book">글쓰기</a>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>