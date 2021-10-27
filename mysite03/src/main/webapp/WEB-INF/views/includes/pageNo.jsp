<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<c:set var='pRange' value='5' />
<div class="pager">
	<ul>
		<!-- ◀ -->
		<c:choose>
			<c:when test='${map.pageNo <= pRange }'>
				<li>◀</li>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test='${map.pageNo % pRange == 0 }'>
						<li><a
							href="${pageContext.servletContext.contextPath }/board/${map.pageNo - pRange }">◀</a></li>
					</c:when>
					<c:otherwise>
						<li><a
							href="${pageContext.servletContext.contextPath }/board/${map.pageNo - (map.pageNo % pRange) }">◀</a></li>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>

		<!-- page numbering -->
		<c:choose>
			<c:when test='${map.pageNo % pRange == 0 }'>
				<c:forEach begin="${map.pageNo -  pRange + 1 }"	end="${map.pageNo }" var="i" step="1">
					<c:choose>
						<c:when test='${i == map.pageNo }'>
							<li class="selected"><a
								href="${pageContext.servletContext.contextPath }/board/${i }">${i }</a></li>
						</c:when>
						<c:when test='${i <= map.pageCount  }'>
							<li><a
								href="${pageContext.servletContext.contextPath }/board/${i }">${i }</a></li>
						</c:when>
						<c:otherwise>
							<li>${i }</li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<c:forEach begin="${map.pageNo - (map.pageNo % pRange) + 1 }" end="${map.pageNo - (map.pageNo % pRange) + pRange }" var="i" step="1">
					<c:choose>
						<c:when test='${i == map.pageNo }'>
							<li class="selected"><a
								href="${pageContext.servletContext.contextPath }/board/${i }">${i }</a></li>
						</c:when>
						<c:when test='${i <= map.pageCount  }'>
							<li><a
								href="${pageContext.servletContext.contextPath }/board/${i }">${i }</a></li>
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
			<c:when test='${map.pageNo % pRange == 0 }'>
				<c:choose>
					<c:when test='${map.pageNo  >= pageCount  }'>
						<li>▶</li>
					</c:when>
					<c:otherwise>
						<li><a
							href="${pageContext.servletContext.contextPath }/board/${map.pageNo + 1 }">▶</a></li>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test='${map.pageNo >= (map.pageCount -( map.pageCount % pRange) +1) }'>
						<li>▶</li>
					</c:when>
					<c:otherwise>
						<li><a
							href="${pageContext.servletContext.contextPath }/board/${(map.pageNo+pRange) - (map.pageNo%pRange) + 1 }">▶</a></li>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</ul>
</div>