<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fn"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>파일 업로드 예제</h1>
<form method="post" action="${pageContext.request.contextPath }/gallery/upload"  enctype="multipart/form-data">
    <c:if test='${not empty list  }'>
        <c:forEach items='${list }' var='vo' >
		    <div class="result-images">
				<img src="${pageContext.request.contextPath }${vo.url }" style="width:150px"><br>
			</div>
		</c:forEach>
    </c:if>
	<label>comments:</label>
	<input type="text" name="comments" value="">
	<br><br>
	
	<label>파일1</label>
	<input type="file" name="file">
	<br><br>
	
	<br>
	<input type="submit" value="upload">
</form>
</body>
</html>