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
					<c:set var='count' value='${fn:length(list) }' />
					
					
					<c:forEach items='${limitList }' var='vo' varStatus='status'>
						<tr>
							<td>${count-status.index }</td>
							<c:choose>
								<c:when test='${vo.orderNo > 0 }'>
									<td style="text-align: left; padding-left: ${20 * (vo.depth) + 20 }px">
									<img src='${pageContext.servletContext.contextPath }/assets/images/reply.png' />
									<a href="">${vo.title }</a>
									</td>
								</c:when>
								<c:otherwise>
									<td style="text-align: left; padding-left: 20px">
									<a href="">${vo.title }</a>
									</td>
								</c:otherwise>
							</c:choose>
							<td>${vo.name }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<td><a href="" >삭제</a></td>
						</tr>
					</c:forEach>
				</table>

				<!-- pager 추가 -->
				<c:set var='pIndex' value='5' />
				<c:choose>
					<c:when test='${(count % row) > 0 }'>
						<c:set var='pageCount' value='${(count/row) - (count/row)%1  + 1 }' />
	
					</c:when>
					<c:otherwise>
						<c:set var='pageCount' value='${(count/row) - (count/row)%1   }' />
					</c:otherwise>
				</c:choose>

				<div class="pager">
					<ul>
						<!-- ◀ -->
						<c:choose>
							<c:when test='${pageNo <= pIndex }'>
								<li><a href="">◀</a></li>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test='${pageNo % pIndex == 0 }'>
										<li><a
											href="${pageContext.servletContext.contextPath }/board?p=${pageNo - pIndex }">◀</a></li>
									</c:when>
									<c:otherwise>
										<li><a
											href="${pageContext.servletContext.contextPath }/board?p=${pageNo - (pageNo%pIndex) }">◀</a></li>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>

						<!-- page numbering -->
						<c:choose>
							<c:when test='${pageNo % pIndex == 0 }'>
								<c:forEach begin="${pageNo -  pIndex + 1 }"
									end="${(pageNo -  pIndex + 1) +(pIndex - 1) }" var="i" step="1">
									<c:choose>
										<c:when test='${i == pageNo }'>
											<li class="selected"><a
												href="${pageContext.servletContext.contextPath }/board?p=${i }">${i }</a></li>
										</c:when>
										<c:when test='${i <= pageCount  }'>
											<li><a
												href="${pageContext.servletContext.contextPath }/board?p=${i }">${i }</a></li>
										</c:when>
										<c:otherwise>
											<li>${i }</li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:forEach begin="${pageNo - (pageNo % pIndex) + 1 }"
									end="${(pageNo - (pageNo % pIndex) + 1) +(pIndex - 1) }"
									var="i" step="1">
									<c:choose>
										<c:when test='${i == pageNo }'>
											<li class="selected"><a
												href="${pageContext.servletContext.contextPath }/board?p=${i }">${i }</a></li>
										</c:when>
										<c:when test='${i <= pageCount  }'>
											<li><a
												href="${pageContext.servletContext.contextPath }/board?p=${i }">${i }</a></li>
										</c:when>
										<c:otherwise>
											<li>${i }</li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:otherwise>
						</c:choose>

						<!-- ▶ -->
						<c:choose>
							<c:when test='${pageNo % pIndex == 0 }'>
								<c:choose>
									<c:when test='${pageNo  >= pageCount  }'>
										<li><a href="">▶</a></li>
									</c:when>
									<c:otherwise>
										<li><a
											href="${pageContext.servletContext.contextPath }/board?p=${pageNo + 1 }">▶</a></li>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when
										test='${pageNo >= (pageCount -( pageCount % pIndex) +1) }'>
										<li><a href="">▶</a></li>
									</c:when>
									<c:otherwise>
										<li><a
											href="${pageContext.servletContext.contextPath }/board?p=${(pageNo+pIndex) - (pageNo%pIndex) + 1 }">▶</a></li>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>


				<div class="bottom">
					<a href="" id="new-book">글쓰기</a>
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