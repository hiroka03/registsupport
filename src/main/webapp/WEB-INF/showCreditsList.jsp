<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.List,java.util.ArrayList" %>

<% request.setCharacterEncoding("UTF-8");%>
<% List<String> credit_list = (List<String>)request.getAttribute("credit_list");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
    <title>過去に取り込んだ単位修得情報選択</title>
    <link rel="stylesheet" type="text/css" href="css/css.css">
  </head>
  <body>
  <h1>履修登録支援システム</h1>
  <h2>過去に取り込んだ単位修得情報を選択</h2>
  <%if(credit_list == null || credit_list.size() == 0){%>
    過去に取り込んだ単位修得情報がありません<br>
    メニューから，単位修得情報を取り込んでください<br><br>
    <input type="button" onclick="location.href='./Menu'" value="トップに戻る" class="button"><br><br><br><br><br><br><br>
  <%}%>
  <%else{%>
    <form action = "./ShowCreditsList" method="post">
      <select name="credit_list_id" class="select">
        <%for(int n=0;n<credit_list.size();n=n+2){%>
        <option value="<%=credit_list.get(n)%>"><%=credit_list.get(n+1)%>に取り込んだ情報</option>
        <%}%>
      </select>
      <br><br>
      <input type="submit" value="表示する" class="button"><br><br>
      <input type="button" onclick="location.href='./Menu'" value="トップに戻る" class="button"><br><br><br><br><br><br><br>
    </form>
  <%}%>
  </body>
</html>