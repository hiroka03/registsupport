<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String errorMessage = (String)request.getAttribute("errorMessage");%>
<% int grade = (int)request.getAttribute("grade");%>
<% int department = (int)request.getAttribute("department");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
      <title>ユーザ情報を変更</title>
      <link rel="stylesheet" type="text/css" href="css/css.css">
    </head>
    <body>
    <h1>履修登録支援システム</h1>
    <h2>ユーザ情報を変更</h2>
    <form action = "./UpdateUser" method="post">
      <%if(!(errorMessage.equals("null"))){%>
        <font color="red">${errorMessage}</font><br><br>
      <%}%>
      <input type="password" name="password" required class="inText" placeholder="パスワード"><br><br>
      <input type="password" name="password_con" required class="inText" placeholder="パスワード（確認）"><br><br>
      <select name="grade" class="inText">
        <%if(grade==1){%>
          <option value="1" selected>1年</option>
          <option value="2">2年</option>
          <option value="3">3年</option>
          <option value="4">4年</option>
        <%}%>
        <%if(grade==2){%>
          <option value="1">1年</option>
          <option value="2" selected>2年</option>
          <option value="3">3年</option>
          <option value="4">4年</option>
        <%}%>
        <%if(grade==3){%>
          <option value="1">1年</option>
          <option value="2">2年</option>
          <option value="3" selected>3年</option>
          <option value="4">4年</option>
        <%}%>
        <%if(grade==4){%>
          <option value="1">1年</option>
          <option value="2">2年</option>
          <option value="3">3年</option>
          <option value="4" selected>4年</option>
        <%}%>
      </select><br><br>
      <select name="department" class="inText">
        <%if(department==1){%>
          <option value="1" selected>A類情報教育</option>
          <option value="2">E類情報教育</option>
        <%}%>
        <%if(department==2){%>
          <option value="1">A類情報教育</option>
          <option value="2" selected>E類情報教育</option>
        <%}%>
      </select><br><br>
      <input type="submit" value="登録する" class="button"><br><br>
      <input type="button" onclick="location.href='./Menu'" value="トップに戻る" class="button"><br><br><br><br><br><br><br>
    </form>
  </body>
</html>