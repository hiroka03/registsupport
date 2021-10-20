<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.List,java.util.ArrayList" %>

<% request.setCharacterEncoding("UTF-8");%>
<% List<String> timetable_list = (List<String>)request.getAttribute("timetable_list");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
    <title>過去に作成した時間割選択</title>
    <link rel="stylesheet" type="text/css" href="css/css.css">
  </head>
  <body>
  <h1>履修登録支援システム</h1>
  <h2>過去に作成した時間割を選択</h2>
  <%if(timetable_list == null || timetable_list.size() == 0){%>
    過去に作成した時間割がありません<br>
    単位修得情報を取り込み，時間割を作成してください<br><br>
    <input type="button" onclick="location.href='./Menu'" value="トップに戻る" class="button"><br><br><br><br><br><br><br>
  <%}%>
  <%else{%>
    <form action = "./ShowTimetablesList" method="post">
      <select name="timetable_id" class="select">
        <%for(int n=0;n<timetable_list.size();n=n+2){%>
        <option value="<%=timetable_list.get(n)%>"><%=timetable_list.get(n+1)%>に作成した時間割</option>
        <%}%>
      </select><br><br>
      <input type="submit" value="表示する" class="button"><br><br>
      <input type="button" onclick="location.href='./Menu'" value="トップに戻る" class="button"><br><br><br><br><br><br><br>
    </form>
  <%}%>
  </body>
</html>