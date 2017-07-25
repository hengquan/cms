<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>操作失败</title>
  <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>
  <meta name="format-detection" content="telephone=no">
  <meta name="format-detection" content="email=no">
  <style>
    .box{
      text-align: center;
      margin-top: 80px;
    }
  </style>
</head>
<body>
  <%-- <div class="">
    <img src="${appRoot}/static/wx/img/online.png" alt="操作失败" width="200">
  </div> --%>
  <div style="text-align: center;margin-top: 80px;margin-bottom: 22px;">
	  <img alt="" src="${appRoot}/static/wx/img/online.png" style="width:116px;">
  </div>
  <div style="text-align: center;margin-top: 30px;margin-bottom: 22px;">
	  <p align="center"><font spellcheck="false">操作失败...</font></p>
  </div>
</body>
</html>