package servlet;

//自分が格納されているフォルダの外にある必要なクラス
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import beans.User;
import control.UserManager;
import utility.SessionChecker;

//アノテーションの記述
//jspで示してあげると、jspから呼び出さられる
@WebServlet("/SignUp")

// HttpServletを継承することで、このクラスはServletとして、働くことができる
public class SignUp extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // doPostメソッドから呼び出される(リダイレクトされる)
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // requestオブジェクトの文字エンコーディングの設定
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");

        SessionChecker sessionChecker = new SessionChecker();

        String errorMessage = "null";
        request.setAttribute("errorMessage", errorMessage);

        // forwardはrequestオブジェクトを引数として、次のページに渡すことができる
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/signUp.jsp");
        dispatcher.forward(request, response);

    }

    // requestオブジェクトには、フォームで入力された文字列などが格納されている。
    // responseオブジェクトを使って、次のページを表示する
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // requestオブジェクトの文字エンコーディングの設定
        request.setCharacterEncoding("UTF-8");

        // requestオブジェクトから登録情報の取り出し
        String user_id = escape(request.getParameter("user_id"));
        String password = request.getParameter("password");
        String password_con = request.getParameter("password_con");
        int grade = Integer.parseInt(request.getParameter("grade"));
        int department = Integer.parseInt(request.getParameter("department"));

        if (!password.equals(password_con)) {
            // 確認用パスワードが異なる(失敗処理)
            request.setAttribute("errorMessage", "パスワード（確認）が一致しません");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/signUp.jsp");
            dispatcher.forward(request, response);
        } else {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            String pwd_hash = encoder.encode(password);
            // System.out.println(pwd_hash);

            // Userオブジェクトに情報を格納
            User user = new User(user_id, pwd_hash, grade, department);

            // StudentManagerオブジェクトの生成
            UserManager manager = new UserManager();

            // 登録
            int count = manager.SignUp(user);
            //System.out.println(count);

            if (count == 0) {
                // ID重複時の可能性ありの処理(失敗処理)
                request.setAttribute("errorMessage", "ユーザIDが他のユーザと重複しています。");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/signUp.jsp");
                dispatcher.forward(request, response);
            } else {
                // 成功処理
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/signUpSuc.jsp");
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