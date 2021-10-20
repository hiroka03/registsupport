<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.List,java.util.ArrayList,beans.Credit" %>

<% request.setCharacterEncoding("UTF-8");%>
<% String credit_list_id = (String)request.getAttribute("credit_list_id");%>
<% String created_at = (String)request.getAttribute("created_at");%>
<% List<Credit> credits = (List<Credit>)request.getAttribute("credits");%>
<% int department = (int)request.getAttribute("department");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
    <title>取り込んだ単位修得情報を確認</title>
    <link rel="stylesheet" type="text/css" href="css/css.css">
  </head>
  <body>
  <h1>履修登録支援システム</h1>
  <h2>取り込んだ単位修得情報を確認</h2>
  <h3><%=created_at%>に取り込んだ情報</h3>
  <table border="1">
      <tr>
          <th>科目名</th>
          <th>評価</th>
          <th>単位</th>
          <th>履修年度</th>
          <th>履修学期</th>
      </tr>
      <%for(int n=0;n<credits.size();n=n+1){%>
      <tr>
          <td><%=credits.get(n).getSubjectName()%></td>
          <td><%=credits.get(n).getEvaluation()%></td>
          <td><%=credits.get(n).getCredit()%></td>
          <td><%=credits.get(n).getAcquisitionYear()%></td>
          <td><%=credits.get(n).getAcquisitionSemester()%></td>
      </tr>
      <%}%>
  </table>
  <br>
  <form action ="./ShowSubjects" method="POST">
    <input type="hidden" name="credit_list_id" value="<%=credit_list_id%>">
    <input type="hidden" name="created_at" value="<%=created_at%>">
    <%if(department==1){%>
      取得予定の副免許を選択してください<br>
      <input type="checkbox" name="sublicense" value="1">中学校・高校（数学）<br>
      <input type="checkbox" name="sublicense" value="2">特支一種3領域<br>
      <input type="checkbox" name="sublicense" value="3">特支二種3領域<br>
      <input type="checkbox" name="sublicense" value="4">特支一種2領域<br>
      <input type="checkbox" name="sublicense" value="5">特支二種2領域<br>
    <%}%>
    <br>
    <input type="hidden" name="errorMessage" value="null">
    <input type="submit" value="この情報で履修推奨科目を表示" class="button"><br><br>
</form>
  <input type="button" onclick="location.href='./Menu'" value="トップに戻る" class="button"><br><br><br><br><br><br><br>
  </body>
</html>