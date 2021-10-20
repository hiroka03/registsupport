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
@WebServlet("/ShowTimetablesList")

// HttpServletを継承することで、このクラスはServletとして、働くことができる
public class ShowTimetablesList extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // doPostメソッドから呼び出される(リダイレクトされる)
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    SessionChecker sessionChecker = new SessionChecker();

    if(sessionChecker.isNotUser(request, response)){
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }else{
        // SyllabusManagerオブジェクトの生成
        SyllabusManager manager = new SyllabusManager();

        // credits_listsテーブルから情報を検索
        HttpSession session =request.getSession();
        String user_id = (String)session.getAttribute("user_id");
        List<String> timetable_list = manager.searchTimetablesList(user_id);

        if(timetable_list!=null){
            request.setAttribute("timetable_list",timetable_list);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/showTimetablesList.jsp");
            dispatcher.forward(request, response);
        }
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
        // requestオブジェクトの文字エンコーディングの設定
        request.setCharacterEncoding("UTF-8");

        // requestオブジェクトから登録情報の取り出し
        String timetable_id = request.getParameter("timetable_id");

        // CreditManagerオブジェクトの生成
        SyllabusManager manager = new SyllabusManager();

        // creditsテーブルから情報を検索
        List<Syllabus> subjectlist = manager.searchSubjects(timetable_id);

        if(subjectlist!=null){
            // subjects_listsテーブルから情報を検索し，日付情報を取得
            HttpSession session =request.getSession();
            String user_id = (String)session.getAttribute("user_id");
            List<String> timetable_list = manager.searchTimetablesList(user_id);
            for(int n=0;n<timetable_list.size();n=n+2){
                if(timetable_list.get(n).equals(timetable_id)){
                    request.setAttribute("created_at",timetable_list.get(n+1));
                    break;
                }
            }
            request.setAttribute("subjectlist",subjectlist);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/showTimetable.jsp");
            dispatcher.forward(request, response);
        }
    }

  }
}