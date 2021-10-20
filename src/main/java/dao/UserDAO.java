package dao;

//  自分が格納されているフォルダの外にある必要なクラス
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList; //ArrayListのインポート
import java.util.List; //Listのインポート
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.SynthSpinnerUI;
import javax.swing.text.TabExpander;

import java.lang.Object;

import beans.User;
import utility.DriverAccessor;

public class UserDAO extends DriverAccessor {

    public static final String SIGNUP = "insert into users (user_id, passwords, grades, departments) values(?, ?, ?, ?)";
    public static final String UPDATE_USER = "update users set passwords = ?, grades = ?, departments = ? where user_id = ?";
    public static final String RESET_PASS = "update users set passwords = ? where user_id = ?";

    public int SignUp(User user, Connection connection) {
        try {
            //IDが重複していないかを検査
            String sql = "select count(*) from users where user_id=?";
            PreparedStatement stmt2 = connection.prepareStatement(sql);
            stmt2.setString(1, user.getUserId());
            ResultSet rs2 = stmt2.executeQuery();
            rs2.first();
            String count = rs2.getString("count(*)");
            stmt2.close();
            rs2.close();

            if (count.equals("0") == true) {
                PreparedStatement stmt = connection.prepareStatement(SIGNUP);
                stmt.setString(1, user.getUserId());
                stmt.setString(2, user.getPassword());
                stmt.setInt(3, user.getGrade());
                stmt.setInt(4, user.getDepartment());
                stmt.executeUpdate();
                return 1;
            } else {
                return 0;
            }

        } catch (SQLException e) {
            // エラーが発生した場合、エラーの原因を出力する
            e.printStackTrace();
            return 0;
        } finally {
        }
    }

    public int UpdateUser(User user, Connection connection) {
        try {
            PreparedStatement stmt = connection.prepareStatement(UPDATE_USER);
            stmt.setString(1, user.getPassword());
            stmt.setInt(2, user.getGrade());
            stmt.setInt(3, user.getDepartment());
            stmt.setString(4, user.getUserId());
            //System.out.println(stmt);
            stmt.executeUpdate();

            return 1;

        } catch (SQLException e) {
            // エラーが発生した場合、エラーの原因を出力する
            e.printStackTrace();
            return 0;
        } finally {
        }
    }

    public void resetPass(String user_id,String pass, Connection connection) {
        try {


            PreparedStatement stmt = connection.prepareStatement(RESET_PASS);
            stmt.setString(1, pass);
            stmt.setString(2, user_id);
            stmt.executeUpdate();
            
            
        } catch (SQLException e) {
            // エラーが発生した場合、エラーの原因を出力する
            e.printStackTrace();
            
        } finally {
        }
    }

    public String getPass(String user_id, Connection connection) {
        try {
            String SIGNIN = "select * from users where user_id = ?";

            PreparedStatement stmt = connection.prepareStatement(SIGNIN);
            stmt.setString(1, user_id);
            ResultSet rs = stmt.executeQuery();
            rs.first();
            String dbid = rs.getString("user_id");
            //入力したloginidが大文字小文字完全一致してるか判断
            if(dbid.equals(user_id)){
                String pass = rs.getString("passwords");
                stmt.close();
                rs.close();
                return pass;
            }
            else{
                return null;
            }
        } catch (SQLException e) {

            // エラーが発生した場合、エラーの原因を出力する
            e.printStackTrace();
            return null;

        } finally {
        }
    }

    public User getUserInformation(String user_id, Connection connection) {
        try {
            String SIGNIN_USER = "select * from users where user_id = ?";
            //System.out.println(SIGNIN_USER);
            PreparedStatement stmt = connection.prepareStatement(SIGNIN_USER);
            stmt.setString(1, user_id);
            ResultSet rs = stmt.executeQuery();
            rs.first();

            // if(rs.next()){
            User signin_user = new User(rs.getString("user_id"), rs.getString("passwords"), rs.getInt("grades"), rs.getInt("departments"));
            stmt.close();
            rs.close();
            return signin_user;
            // }
            // else{
            // return null;
            // }
        } catch (SQLException e) {

            // エラーが発生した場合、エラーの原因を出力する
            e.printStackTrace();
            return null;

        } finally {
        }
    }
}
