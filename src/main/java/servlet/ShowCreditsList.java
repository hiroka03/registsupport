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
import beans.Credit;
import control.CreditManager;
import utility.SessionChecker;

//アノテーションの記述
//jspで示してあげると、jspから呼び出さられる
@WebServlet("/ShowCreditsList")

// HttpServletを継承することで、このクラスはServletとして、働くことができる
public class ShowCreditsList extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // doPostメソッドから呼び出される(リダイレクトされる)
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    SessionChecker sessionChecker = new SessionChecker();

    if(sessionChecker.isNotUser(request, response)){
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }else{
        // CreditManagerオブジェクトの生成
        CreditManager manager = new CreditManager();

        // credits_listsテーブルから情報を検索
        HttpSession session =request.getSession();
        String user_id = (String)session.getAttribute("user_id");
        List<String> credit_list = manager.searchCreditsList(user_id);

        if(credit_list!=null){
            request.setAttribute("credit_list",credit_list);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/showCreditsList.jsp");
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
        String credit_list_id = request.getParameter("credit_list_id");

        // CreditManagerオブジェクトの生成
        CreditManager manager = new CreditManager();

        // creditsテーブルから情報を検索
        List<Credit> credits = manager.searchCredits(credit_list_id);

        if(credits!=null){
            // credits_listsテーブルから情報を検索し，日付情報を取得
            HttpSession session =request.getSession();
            String user_id = (String)session.getAttribute("user_id");
            int department = (int)session.getAttribute("department");
            List<String> credit_list = manager.searchCreditsList(user_id);
            for(int n=0;n<credit_list.size();n=n+2){
                if(credit_list.get(n).equals(credit_list_id)){
                    request.setAttribute("credit_list_id",credit_list_id);
                    request.setAttribute("created_at",credit_list.get(n+1));
                    break;
                }
            }
            request.setAttribute("credits",credits);
            request.setAttribute("department",department);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/showCredits.jsp");
            dispatcher.forward(request, response);
        }
    }

  }
}