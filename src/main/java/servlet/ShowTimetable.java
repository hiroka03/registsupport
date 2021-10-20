package servlet;

//自分が格納されているフォルダの外にある必要なクラス
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import beans.Syllabus;
import control.SyllabusManager;
import utility.SessionChecker;

//アノテーションの記述
//jspで示してあげると、jspから呼び出さられる
@WebServlet("/ShowTimetable")

// HttpServletを継承することで、このクラスはServletとして、働くことができる
public class ShowTimetable extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SessionChecker sessionChecker = new SessionChecker();

        if(sessionChecker.isNotUser(request, response)){
          RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
          dispatcher.forward(request, response);
      }else{

      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/menu.jsp");
      dispatcher.forward(request, response);
      }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // requestオブジェクトの文字エンコーディングの設定
        request.setCharacterEncoding("UTF-8");
        SessionChecker sessionChecker = new SessionChecker();

        if(sessionChecker.isNotUser(request, response)){
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }else{
            String[] subject = request.getParameterValues("subject");
            String credit_list_id = request.getParameter("credit_list_id");
            String created_at = request.getParameter("created_at");
            String[] want_sublicense = request.getParameterValues("sublicense");
            List<Syllabus> subjectlist = new ArrayList<>();
            // SyllabusManagerオブジェクトの生成
            SyllabusManager manager = new SyllabusManager();
            Syllabus subjectdetail = new Syllabus();
            if(subject!=null){
                for(int n=0;n<subject.length;n=n+1){
                    // シラバスIDからシラバスを検索
                    subjectdetail = manager.searchSyllabusId(subject[n]);
                    System.out.println(subjectdetail.getSubjectName());
                    subjectlist.add(subjectdetail);
                }
                //同名の科目があったときの処理
                int name_flag = 0;
                name: for(int p=0;p<subjectlist.size();p=p+1){
                    for(int q=0;q<subjectlist.size();q=q+1){
                        if(p!=q && subjectlist.get(p).getSubjectName().equals(subjectlist.get(q).getSubjectName())){
                            name_flag = 1;
                            String errorMessage = "同名の授業科目を選択することはできません";
                            request.setAttribute("errorMessage",errorMessage);
                            request.setAttribute("credit_list_id",credit_list_id);
                            request.setAttribute("created_at",created_at);
                            request.setAttribute("sublicense",want_sublicense);
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/ShowSubjects");
                            dispatcher.forward(request, response);
                            break name;
                        }
                    }
                }
                if(name_flag==0){
                    //同名の曜日・時限があったときの処理
                    int day_flag = 0;
                    day: for(int r=0;r<subjectlist.size();r=r+1){
                        for(int s=0;s<subjectlist.size();s=s+1){
                            if(r!=s && !subjectlist.get(r).getDayOfTheWeek().equals("集中") && subjectlist.get(r).getDayOfTheWeek().equals(subjectlist.get(s).getDayOfTheWeek())){
                                day_flag = 1;
                                String errorMessage = "集中授業以外で同じ時限の授業科目を選択することはできません";
                                request.setAttribute("errorMessage",errorMessage);
                                request.setAttribute("credit_list_id",credit_list_id);
                                request.setAttribute("created_at",created_at);
                                request.setAttribute("sublicense",want_sublicense);
                                RequestDispatcher dispatcher = request.getRequestDispatcher("/ShowSubjects");
                                dispatcher.forward(request, response);
                                break day;
                            }
                        }
                    }
                    if(day_flag==0){
                        // timetablesテーブルに情報を登録
                        HttpSession session =request.getSession();
                        String user_id = (String)session.getAttribute("user_id");
                        int timetable_id = manager.registTimetable(user_id);
                        // timetableidを使ってsubjectsテーブルに情報を登録
                        int count = manager.registSubject(subjectlist,timetable_id);
                        //System.out.println(count);

                        // subjectlistをjspに転送する
                        if(count==1){
                            List<String> timetable_list = manager.searchTimetablesList(user_id);
                            request.setAttribute("created_at",timetable_list.get(timetable_list.size()-1));
                            if(subjectlist!=null){
                                request.setAttribute("subjectlist",subjectlist);
                                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/showTimetable.jsp");
                                dispatcher.forward(request, response);
                            }
                        }
                    }
                }
            }
        }
    }
}