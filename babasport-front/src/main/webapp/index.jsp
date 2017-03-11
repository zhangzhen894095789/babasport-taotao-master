<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>索引页</title>
</head>
<body>
<%response.sendRedirect("/product/display/list.shtml?keyword=" + URLEncoder.encode("瑜伽服", "UTF-8")); %>
</body>
</html>