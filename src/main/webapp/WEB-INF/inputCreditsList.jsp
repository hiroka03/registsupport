<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%String errorMessage = (String)request.getAttribute("errorMessage");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
      <title>単位修得情報取り込み</title>
      <link rel="stylesheet" type="text/css" href="css/css.css">
    </head>
    <body>
    <h1>履修登録支援システム</h1>
    <h2>単位修得情報を取り込む</h2>
    東京学芸大学の学生情報トータルシステムから，<br>
    "seisekiStudentPdfOutputCst.pdf"または<br>
    "seisekiStudentPdfOutputCst.do"を<br>
    ダウンロードして，システムに取り込んでください。<br>
    （ファイル名は任意です）<br><br>
    ファイルのダウンロード手順：<br>
    学芸ポータル→学生情報トータルシステム→成績情報の参照→印刷 で出てくるファイルを保存<br>
    スマートフォンの方は，「"ファイル"に保存」を選択して保存してください<br><br>
    （注意）アップロードされたファイルは，単位修得情報の収集，その情報をもとにした履修推奨科目の表示のみに使用します。<br>単位修得情報を外部に公開したり，ファイルから氏名等の個人情報を取得したりすることはありません。<br><br>
    <%if(!(errorMessage.equals("null"))){%>
      <font color="red">${errorMessage}</font><br><br>
    <%}%>
    <form action = "./InputCreditsList" method="post" enctype="multipart/form-data">
      <input type="file" name="file" required>
      <input type="submit" value="取り込む" class="button"><br><br>
      <input type="button" onclick="location.href='./Menu'" value="トップに戻る" class="button"><br><br><br><br><br><br><br>
    </form>
  </body>
</html>