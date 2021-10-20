<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.List,java.util.ArrayList,beans.Syllabus" %>

<% request.setCharacterEncoding("UTF-8");%>
<% List<Syllabus> subjectlist = (List<Syllabus>)request.getAttribute("subjectlist");%>
<% String errorMessage = (String)request.getAttribute("errorMessage");%>
<% String credit_list_id = (String)request.getAttribute("credit_list_id");%>
<% String created_at = (String)request.getAttribute("created_at");%>
<% String[] sublicense = (String[])request.getAttribute("sublicense");%>
<% int department = (int)request.getAttribute("department");%>
<% int ca_a = (int)request.getAttribute("ca_a");%>
<% int ca_b = (int)request.getAttribute("ca_b");%>
<% int ca_c = (int)request.getAttribute("ca_c");%>
<% int ca_abc = (int)request.getAttribute("ca_abc");%>
<% int se = (int)request.getAttribute("se");%>
<% int sp_choice = (int)request.getAttribute("sp_choice");%>
<% int sp_ii = (int)request.getAttribute("sp_ii");%>
<% int major_se = (int)request.getAttribute("major_se");%>
<% int sp = (int)request.getAttribute("sp");%>
<% int sa_sb = (int)request.getAttribute("sa_sb");%>
<% int choice = (int)request.getAttribute("choice");%>
<% int teaching = (int)request.getAttribute("teaching");%>
<% int e_sa_sb = (int)request.getAttribute("e_sa_sb");%>
<% int e_choice = (int)request.getAttribute("e_choice");%>
<% int math_choice = (int)request.getAttribute("math_choice");%>
<% int special1_3learning = (int)request.getAttribute("special1_3learning");%>
<% int special1_3language = (int)request.getAttribute("special1_3language");%>
<% int special1_2learning = (int)request.getAttribute("special1_2learning");%>
<% int special1_2language = (int)request.getAttribute("special1_2language");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
    <title>履修推奨科目一覧</title>
    <link rel="stylesheet" type="text/css" href="css/css.css">
  </head>
  <body>
  <h1>履修登録支援システム</h1>
  <h2><%=created_at%>に取り込んだ単位修得情報をもとにした履修推奨科目</h2>
  <%if(sublicense!=null){%>
    取得予定の副免許：
    <%for(int m=0;m<sublicense.length;m=m+1){%>
      <%if(sublicense[m].equals("1")){%>中学校・高校（数学）<%}%>
      <%if(sublicense[m].equals("2")){%>特支一種3領域<%}%>
      <%if(sublicense[m].equals("3")){%>特支二種3領域<%}%>
      <%if(sublicense[m].equals("4")){%>特支一種2領域<%}%>
      <%if(sublicense[m].equals("5")){%>特支二種2領域<%}%>
      <%if(m+1<sublicense.length){%>，<%}%>
    <%}%>
    <br><br>
  <%}%>
  ＜単位修得情報をもとにした選択科目の残単位数＞<br>
  <table border="1">
    <tr>
      <th>科目</th>
      <th>残り単位</th>
    </tr>
    <tr>
      <td>ＣＡ（Ａ）領域</td>
      <td><%if(ca_a>0){%><%=ca_a%><%}else{%>0<%}%></td>
    </tr>
    <tr>
      <td>ＣＡ（Ｂ）領域</td>
      <td><%if(ca_b>0){%><%=ca_b%><%}else{%>0<%}%></td>
    </tr>
    <tr>
      <td>ＣＡ（Ｃ）領域</td>
      <td><%if(ca_c>0){%><%=ca_c%><%}else{%>0<%}%></td>
    </tr>
    <tr>
      <td>ＣＡ（全体）</td>
      <td><%if(ca_abc>0){%><%=ca_abc%><%}else{%>0<%}%></td>
    </tr>
    <%if(department==1){%>
      <tr>
        <td>教科・教職（共通ＳＥ）</td>
        <td><%if(se>0){%><%=se%><%}else{%>0<%}%></td>
      </tr>
      <tr>
        <td>教科・教職（選択の○○科研究（国語など））</td>
        <td><%if(sp_choice>0){%><%=sp_choice%><%}else{%>0<%}%></td>
      </tr>
      <tr>
        <td>教科・教職（○○科研究Ⅱ（音楽など））</td>
        <td><%if(sp_ii>0){%><%=sp_ii%><%}else{%>0<%}%></td>
      </tr>
      <tr>
        <td>教科・教職（専攻ＳＥ）</td>
        <td><%if(major_se>0){%><%=major_se%><%}else{%>0<%}%></td>
      </tr>
      <tr>
        <td>教科・教職（必修以外の不足分）</td>
        <td><%if(sp>0){%><%=sp%><%}else{%>0<%}%></td>
      </tr>
      <tr>
        <td>選択科目Ａ，Ｂ</td>
        <td><%if(sa_sb>0){%><%=sa_sb%><%}else{%>0<%}%></td>
      </tr>
      <tr>
        <td>自由選択</td>
        <td><%if(choice>0){%><%=choice%><%}else{%>0<%}%></td>
      </tr>
    <%}%>
    <%if(department==2){%>
      <tr>
        <td>教科・教職</td>
        <td><%if(teaching>0){%><%=teaching%><%}else{%>0<%}%></td>
      </tr>
      <tr>
        <td>選択科目Ａ，Ｂ</td>
        <td><%if(e_sa_sb>0){%><%=e_sa_sb%><%}else{%>0<%}%></td>
      </tr>
      <tr>
        <td>自由選択</td>
        <td><%if(e_choice>0){%><%=e_choice%><%}else{%>0<%}%></td>
      </tr>
    <%}%>
    <%if(sublicense!=null){%>
      <%for(int m=0;m<sublicense.length;m=m+1){%>
        <%if(sublicense[m].equals("1")){%>
          <tr>
            <td>数学（選択）</td>
            <td><%if(math_choice>0){%><%=math_choice%><%}else{%>0<%}%></td>
          </tr>
        <%}%>
        <%if(sublicense[m].equals("2")){%>
          <tr>
            <td>特支一種3領域（言語障害選択）</td>
            <td><%if(special1_3learning>0){%><%=special1_3learning%><%}else{%>0<%}%></td>
          </tr>
          <tr>
            <td>特支一種3領域（学習障害選択）</td>
            <td><%if(special1_3language>0){%><%=special1_3language%><%}else{%>0<%}%></td>
          </tr>
        <%}%>
        <%if(sublicense[m].equals("4")){%>
          <tr>
            <td>特支一種2領域（言語障害選択）</td>
            <td><%if(special1_2learning>0){%><%=special1_2learning%><%}else{%>0<%}%></td>
          </tr>
          <tr>
            <td>特支一種2領域（学習障害選択）</td>
            <td><%if(special1_2language>0){%><%=special1_2language%><%}else{%>0<%}%></td>
          </tr>
        <%}%>
      <%}%>
    <%}%>
  </table>
  <br>
  <%if(!errorMessage.equals(null)){%>
    <%if(!errorMessage.equals("null")){%>
      <font color="red">${errorMessage}</font><br><br>
    <%}%>
  <%}%>
  <form action ="./ShowTimetable" method="POST">
    <table border="1">
        <tr>
            <th>選択</th>
            <th>科目コード</th>
            <th>科目名</th>
            <th width="50">担当教員</th>
            <th>学年</th>
            <th>クラス</th>
            <th>学期</th>
            <th>曜日・時限</th>
            <th>単位区分</th>
            <th>単位数</th>
            <th>受講対象</th>
            <th>備考</th>
            <th>副免許</th>
        </tr>
        <%for(int n=0;n<subjectlist.size();n=n+1){%>
        <tr>
            <td><input type="checkbox" name="subject" class="check" value="<%=subjectlist.get(n).getSyllabusId()%>"</td>
            <td><%=subjectlist.get(n).getSubjectCode()%></td>
            <td><%=subjectlist.get(n).getSubjectName()%></td>
            <td><%=subjectlist.get(n).getTeacherName()%></td>
            <td><%=subjectlist.get(n).getGrade()%></td>
            <td><%=subjectlist.get(n).getClassNumber()%></td>
            <td><%=subjectlist.get(n).getSemester()%></td>
            <td><%=subjectlist.get(n).getDayOfTheWeek()%></td>
            <td><%=subjectlist.get(n).getSubjectClassification()%></td>
            <td><%=subjectlist.get(n).getCredit()%></td>
            <td><%=subjectlist.get(n).getTarget()%></td>
            <td><%=subjectlist.get(n).getRemark()%></td>
            <td><%=subjectlist.get(n).getSublicense()%></td>
        </tr>
        <%}%>
    </table>
    <br>
    <input type="hidden" name="credit_list_id" value="<%=credit_list_id%>">
    <input type="hidden" name="created_at" value="<%=created_at%>">
    <%if(sublicense!=null){%>
      <%for(int o=0;o<sublicense.length;o=o+1){%>
        <input type="hidden" name="sublicense" value="<%=sublicense[o]%>">
      <%}%>
    <%}%>
    <input type="submit" id="btn1" value="選択した科目で時間割を作成" class="button"><br><br>
</form>
  <input type="button" onclick="location.href='./Menu'" value="トップに戻る" class="button"><br><br><br><br><br><br><br>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script>
  $(function(){
    // 初期状態のボタンは無効
    $("#btn1").prop("disabled", true);
  
    $("input[type='checkbox']").on('change', function () {
          //チェックされているチェックボックスの数
          if ($(".check:checked").length > 0) {
            //ボタン有効
            $("#btn1").prop("disabled", false);
          } else {
            //ボタン無効
            $("#btn1").prop("disabled", true);
          }
    });
  });
  </script>
  </body>
</html>