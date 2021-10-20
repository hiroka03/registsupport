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

import beans.Credit;
import utility.DriverAccessor;

//時間を扱うためのライブラリ
import java.util.TimeZone;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CreditDAO extends DriverAccessor {

    public static final String REGIST_LIST = "insert into credits_lists (user_id) values(?)";
    public static final String REGIST_CREDITS_LIST = "insert into credits(credit_list_id, subjects_names, evaluations, credits, acquisitions_years, acquisitions_semesters) values(?,?,?,?,?,?)";
    public static final String SEARCH_CREDITS_LIST = "select * from credits_lists where user_id = ?";
    public static final String SEARCH_CREDITS = "select * from credits where credit_list_id = ?";

    public int registList(String user_id, Connection connection) {
        try {
            PreparedStatement stmt = connection.prepareStatement(REGIST_LIST);
            stmt.setString(1, user_id);
            stmt.executeUpdate();

            String sql = "select max(credit_list_id) from credits_lists where user_id = ?";
            PreparedStatement stmt2 = connection.prepareStatement(sql);
            stmt2.setString(1, user_id);
            //System.out.println(stmt2);
            ResultSet rs2 = stmt2.executeQuery();
            rs2.first();
            int credit_list_id = rs2.getInt("max(credit_list_id)");
            stmt2.close();
            rs2.close();

            return credit_list_id;

            } catch (SQLException e) {
                // エラーが発生した場合、エラーの原因を出力する
                e.printStackTrace();
                return 0;
            } finally {
        }
    }

    public int registCreditsList(List<Credit> creditslist, int credit_list_id, Connection connection) {
        try {
            for(int n=0;n<creditslist.size();n=n+1){
                PreparedStatement stmt = connection.prepareStatement(REGIST_CREDITS_LIST);
                stmt.setInt(1,credit_list_id);
                stmt.setString(2, creditslist.get(n).getSubjectName());
                stmt.setString(3, creditslist.get(n).getEvaluation());
                stmt.setString(4, creditslist.get(n).getCredit());
                stmt.setString(5, creditslist.get(n).getAcquisitionYear());
                stmt.setString(6,creditslist.get(n).getAcquisitionSemester());
                //System.out.println(stmt);
                stmt.executeUpdate();
            }

            return 1;

            
        } catch (SQLException e) {
            // エラーが発生した場合、エラーの原因を出力する
            e.printStackTrace();
            return 0;
        } finally {
        }
    }

    public List<String> searchCreditsList(String user_id, Connection connection) {
        try {
            PreparedStatement stmt = connection.prepareStatement(SEARCH_CREDITS_LIST);
            stmt.setString(1, user_id);
            ResultSet rs = stmt.executeQuery();

            List<String> credit_list = new ArrayList<>();
            while(rs.next()){
                String credit_list_id =rs.getString("credit_list_id");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
                String created_at = simpleDateFormat.format(rs.getTimestamp("created_at"));
                credit_list.add(credit_list_id);
                credit_list.add(created_at);
            }
            stmt.close();
            rs.close();
            return credit_list;

            
        } catch (SQLException e) {
            // エラーが発生した場合、エラーの原因を出力する
            e.printStackTrace();
            return null;
        } finally {
        }
    }

    public List<Credit> searchCredits(String credit_list_id, Connection connection) {
        try {
            PreparedStatement stmt = connection.prepareStatement(SEARCH_CREDITS);
            stmt.setString(1, credit_list_id);
            ResultSet rs = stmt.executeQuery();

            List<Credit> credits = new ArrayList<>();
            while(rs.next()){
                String subject_name =rs.getString("subjects_names");
                String evaluation =rs.getString("evaluations");
                String credit = rs.getString("credits");
                String acquisition_year = rs.getString("acquisitions_years");
                String acquisition_semester = rs.getString("acquisitions_semesters");
                Credit credit_list = new Credit(subject_name, evaluation, credit, acquisition_year, acquisition_semester);
                credits.add(credit_list);
            }
            stmt.close();
            rs.close();
            return credits;

            
        } catch (SQLException e) {
            // エラーが発生した場合、エラーの原因を出力する
            e.printStackTrace();
            return null;
        } finally {
        }
    }
}
