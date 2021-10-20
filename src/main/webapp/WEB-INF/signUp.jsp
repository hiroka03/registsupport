<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%String errorMessage = (String)request.getAttribute("errorMessage");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
    <title>新規登録</title>
    <link rel="stylesheet" type="text/css" href="css/css.css">
  </head>
  <body>
  <h1>履修登録支援システム</h1>
  <h2>新規登録</h2>
  <form action = "./SignUp" method="post">
    <%if(!(errorMessage.equals("null"))){%>
      <font color="red">${errorMessage}</font><br><br>
    <%}%>
		<input type="text" name="user_id" required placeholder="ID" class="inText" maxlength="16" minlength="8"><br><br>
    <input type="password" name="password" required placeholder="パスワード" class="inText" maxlength="16" minlength="8"><br><br>
    <input type="password" name="password_con" required placeholder="パスワード（確認）" class="inText" maxlength="16" minlength="8"><br><br>
    <select name="grade" class="inText">
      <option value="1">1年</option>
      <option value="2">2年</option>
      <option value="3">3年</option>
      <option value="4">4年</option>
    </select><br><br>
    <select name="department" class="inText">
      <option value="1">A類情報教育</option>
      <option value="2">E類情報教育</option>
    </select><br><br>
		<input type="submit" value="登録する" class="button"><br><br>
		<input type="button" onclick="location.href='./index.jsp'" value="トップに戻る" class="button"><br><br><br><br><br><br><br>
	</form>
  </body>
</html>