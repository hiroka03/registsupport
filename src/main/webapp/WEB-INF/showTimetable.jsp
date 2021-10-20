<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.List,java.util.ArrayList,beans.Syllabus" %>

<% request.setCharacterEncoding("UTF-8");%>
<% String created_at = (String)request.getAttribute("created_at");%>
<% List<Syllabus> subjectlist = (List<Syllabus>)request.getAttribute("subjectlist");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
    <title>作成した時間割を確認</title>
    <link rel="stylesheet" type="text/css" href="css/css.css">
  </head>
  <body>
  <h1>履修登録支援システム</h1>
  <h2>作成した時間割を表示</h2>
  <h3><%=created_at%>に作成した時間割</h3>
  （注意）CAP制については反映されませんので履修登録の際は十分注意してください。<br><br>
  <table border="1">
      <tr>
          <th></th>
          <th width="150">月</th>
          <th width="150">火</th>
          <th width="150">水</th>
          <th width="150">木</th>
          <th width="150">金</th>
      </tr>
      <tr>
          <td>1</td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("月１")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("火１")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("水１")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("木１")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("金１")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
      </tr>
      <tr>
          <td>2</td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("月２")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("火２")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("水２")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("木２")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("金２")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
      </tr>
      <tr>
          <td>3</td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("月３")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("火３")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("水３")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("木３")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("金３")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
      </tr>
      <tr>
          <td>4</td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("月４")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("火４")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("水４")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("木４")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("金４")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
      </tr>
      <tr>
          <td>5</td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("月５")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("火５")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("水５")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("木５")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
          <td><%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("金５")){%><%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<br><%=subjectlist.get(n).getTeacherName()%><%}%><%}%></td>
      </tr>
  </table>
  <%for(int n=0;n<subjectlist.size();n=n+1){%><%if(subjectlist.get(n).getDayOfTheWeek().equals("集中")){%>集中：<%=subjectlist.get(n).getSubjectName()%>（<%=subjectlist.get(n).getClassNumber()%>）<%=subjectlist.get(n).getTeacherName()%><br><%}%><%}%>
  <br>
  <input type="button" onclick="location.href='./Menu'" value="トップに戻る" class="button"><br><br><br><br><br><br><br>
  </body>
</html>