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
import java.util.Collections;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import beans.User;
import control.UserManager;
import utility.SessionChecker;

//アノテーションの記述
//jspで示してあげると、jspから呼び出さられる
@WebServlet("/UpdateUser")

// HttpServletを継承することで、このクラスはServletとして、働くことができる
public class UpdateUser extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // doPostメソッドから呼び出される(リダイレクトされる)
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // requestオブジェクトの文字エンコーディングの設定
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");

        SessionChecker sessionChecker = new SessionChecker();

        if(sessionChecker.isNotUser(request, response)){
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }else{

        String errorMessage = "null";
        request.setAttribute("errorMessage", errorMessage);
        HttpSession session = request.getSession();
        int old_grade = (int)session.getAttribute("grade");
        int old_department = (int)session.getAttribute("department");
        request.setAttribute("grade",old_grade);
        request.setAttribute("department",old_department);
        // forwardはrequestオブジェクトを引数として、次のページに渡すことができる
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/updateUser.jsp");
        dispatcher.forward(request, response);
        }
    }

    // requestオブジェクトには、フォームで入力された文字列などが格納されている。
    // responseオブジェクトを使って、次のページを表示する
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // requestオブジェクトの文字エンコーディングの設定
        request.setCharacterEncoding("UTF-8");

        // requestオブジェクトから登録情報の取り出し
        HttpSession session = request.getSession();
        String user_id = (String)session.getAttribute("user_id");
        String password = request.getParameter("password");
        String password_con = request.getParameter("password_con");
        int grade = Integer.parseInt(request.getParameter("grade"));
        int department = Integer.parseInt(request.getParameter("department"));

        if (!password.equals(password_con)) {
            // 確認用パスワードが異なる(失敗処理)
            int old_grade = (int)session.getAttribute("grade");
            int old_department = (int)session.getAttribute("department");
            request.setAttribute("grade",old_grade);
            request.setAttribute("department",old_department);
            request.setAttribute("errorMessage", "パスワード（確認）が一致しません");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/updateUser.jsp");
            dispatcher.forward(request, response);
        } else {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String pwd_hash = encoder.encode(password);

        // UserManagerオブジェクトの生成
        UserManager manager = new UserManager();
            
        // 登録
        //HttpSession session = request.getSession(true);
        User user = new User(user_id, pwd_hash, grade, department);
        int count = manager.UpdateUser(user);

        if(count == 0){
            //失敗処理
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/updateUser.jsp");
            dispatcher.forward(request, response);
        }
        else{
            //成功処理
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/updateUserSuc.jsp");
            session.setAttribute("grade", user.getGrade());
            session.setAttribute("department", user.getDepartment());
            dispatcher.forward(request, response);
        }
            

            
    }
    }
    private static String escape(String val) {
        if (val == null) return "";
        val = val.replaceAll("&", "");
        val = val.replaceAll("<", "");
        val = val.replaceAll(">", "");
        val = val.replaceAll("\"", "");
        val = val.replaceAll("'", "");
        return val;
      }
}