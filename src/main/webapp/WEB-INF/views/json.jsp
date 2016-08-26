<%@page contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.io.BufferedOutputStream"%>
<%
	out.clear();
	out = pageContext.pushBody();

	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	ObjectMapper om = new ObjectMapper();

	byte[] r = om.writeValueAsBytes(request.getAttribute("xResult"));
	response.setContentLength(r.length);

	BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
	bos.write(r);
	bos.flush();
	bos.close();
%>