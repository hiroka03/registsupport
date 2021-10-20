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
import beans.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;




//アノテーションの記述
//jspで示してあげると、jspから呼び出さられる
@WebServlet("/SignIn")

// HttpServletを継承することで、このクラスはServletとして、働くことができる
public class SignIn extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // doPostメソッドから呼び出される(リダイレクトされる)
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // requestオブジェクトの文字エンコーディングの設定
        request.setCharacterEncoding("UTF-8");
        
        //String[] errorMessage = {"null"};
        
        String errorMessage = "null";
        request.setAttribute("errorMessage",errorMessage);
        // forwardはrequestオブジェクトを引数として、次のページに渡すことができる
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    // requestオブジェクトには、フォームで入力された文字列などが格納されている。
    // responseオブジェクトを使って、次のページを表示する
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // requestオブジェクトの文字エンコーディングの設定
        request.setCharacterEncoding("UTF-8");

        // requestオブジェクトから登録情報の取り出し
        String user_id = request.getParameter("user_id");
        String password = request.getParameter("password");

        // コンソールに確認するために出力
        //System.out.println("idは" + user_id + "です！");
        //System.out.println("passは" + password + "です！");


        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        UserManager manager = new UserManager();

        String pwd_hash = encoder.encode(password);

        boolean signin = encoder.matches(password,manager.getPass(user_id));

        if(signin){
            User signin_user = manager.getUserInformation(user_id);
            HttpSession session = request.getSession(true);
            session.setAttribute("user_id",user_id);
            session.setAttribute("grade",signin_user.getGrade());
            session.setAttribute("department",signin_user.getDepartment());

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/menu.jsp");
            dispatcher.forward(request, response);
        }else{
            
            //String[] errorMessage = new String[1];
            //errorMessage[0] = "ユーザIDまたはパスワードが違います。";
            //request.setAttribute("errorMessage",errorMessage);
            
            request.setAttribute("errorMessage","ユーザID又はパスワードが違います。");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/signIn.jsp");
            dispatcher.forward(request, response);
        }

    }
}