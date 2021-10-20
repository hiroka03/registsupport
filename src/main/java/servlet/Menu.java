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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import utility.SessionChecker;

//アノテーションの記述
//jspで示してあげると、jspから呼び出さられる
@WebServlet("/Menu")

// HttpServletを継承することで、このクラスはServletとして、働くことができる
public class Menu extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // doPostメソッドから呼び出される(リダイレクトされる)
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
}