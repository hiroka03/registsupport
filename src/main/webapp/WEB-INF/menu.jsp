<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>メニュー</title>
    <link rel="stylesheet" type="text/css" href="css/css.css">
  </head>
  <body>
  <h1>履修登録支援システム</h1>
  <h2>メニュー</h2>
  このシステムでは，みなさんの単位修得情報をもとにして履修可能な授業科目を表示し，それらを選択することで擬似的な時間割を作成することができます。<br>
  まずは，「単位修得情報を取り込む」から単位修得情報を取り込んでください。<br><br>
  <input type="button" onclick="location.href='./InputCreditsList'" value="単位修得情報を取り込む" class="button"><br><br>
  <input type="button" onclick="location.href='./ShowCreditsList'" value="過去に取り込んだ単位修得情報を確認する" class="button"><br><br>
  <input type="button" onclick="location.href='./ShowTimetablesList'" value="過去に作成した時間割を確認する" class="button"><br><br>
  <input type="button" onclick="location.href='./UpdateUser'" value="ユーザ情報を変更" class="button"><br><br>
  <input type="button" onclick="location.href='./SignOut'" value="ログアウト" class="button"><br><br><br><br><br><br><br>
  </body>
</html>
