package servlet;

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
import control.UserManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;




//アノテーションの記述
//jspで示してあげると、jspから呼び出さられる
@WebServlet("/SignOut")

// HttpServletを継承することで、このクラスはServletとして、働くことができる
public class SignOut extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // doPostメソッドから呼び出される(リダイレクトされる)
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // requestオブジェクトの文字エンコーディングの設定

        HttpSession session = request.getSession();
        session.invalidate();

        response.sendRedirect("/registsupport/index.jsp");
    }

    // requestオブジェクトには、フォームで入力された文字列などが格納されている。
    // responseオブジェクトを使って、次のページを表示する
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        
    }
}