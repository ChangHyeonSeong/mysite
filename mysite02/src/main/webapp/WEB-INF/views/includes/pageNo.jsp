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
			<c:when test='${pageNo <= pRange }'>
				<li>◀</li>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test='${pageNo % pRange == 0 }'>
						<li><a
							href="${pageContext.servletContext.contextPath }/board?p=${pageNo - pRange }">◀</a></li>
					</c:when>
					<c:otherwise>
						<li><a
							href="${pageContext.servletContext.contextPath }/board?p=${pageNo - (pageNo % pRange) }">◀</a></li>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>

		<!-- page numbering -->
		<c:choose>
			<c:when test='${pageNo % pRange == 0 }'>
				<c:forEach begin="${pageNo -  pRange + 1 }"	end="${pageNo }" var="i" step="1">
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
				<c:forEach begin="${pageNo - (pageNo % pRange) + 1 }" end="${pageNo - (pageNo % pRange) + pRange }" var="i" step="1">
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
			<c:when test='${pageNo % pRange == 0 }'>
				<c:choose>
					<c:when test='${pageNo  >= pageCount  }'>
						<li>▶</li>
					</c:when>
					<c:otherwise>
						<li><a
							href="${pageContext.servletContext.contextPath }/board?p=${pageNo + 1 }">▶</a></li>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test='${pageNo >= (pageCount -( pageCount % pRange) +1) }'>
						<li>▶</li>
					</c:when>
					<c:otherwise>
						<li><a
							href="${pageContext.servletContext.contextPath }/board?p=${(pageNo+pRange) - (pageNo%pRange) + 1 }">▶</a></li>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</ul>
</div>