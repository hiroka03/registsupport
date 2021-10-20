<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>履修登録支援システム</title>
    <link rel="stylesheet" type="text/css" href="css/css.css">
  </head>
  <body>
  <h1>履修登録支援システム</h1>
  <h2>ログイン</h2>
	<form action = "./SignIn" method="post">
		<input type="text" name="user_id" required placeholder="ID" class="inText"><br><br>
		<input type="password" name="password" required placeholder="パスワード" class="inText"><br><br>
		<input type="submit" value="ログイン" class="button"><br><br>
		<input type="button" onclick="location.href='./SignUp'" value="新規登録" class="button"><br>
	</form>
  </body>
</html>