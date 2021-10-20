package control;

//  自分が格納されているフォルダの外にある必要なクラス
import java.sql.Connection;
import java.util.List; //Listのインポート
import java.util.ArrayList;

import beans.Credit;
import dao.CreditDAO;
import javax.servlet.http.HttpServlet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



public class CreditManager extends HttpServlet {
    // 属性
    private Connection connection = null;

    // 引数を持たないコンストラクタ
    public CreditManager() {
    }

    // credits_listsテーブルにidを追加
    public int registList(String user_id) {

        // CreditDAOオブジェクト生成
        CreditDAO creditDAO = new CreditDAO();

        // DataBaseへ接続し、コネクションオブジェクトを生成する
        this.connection = creditDAO.createConnection();

        int credit_list_id = creditDAO.registList(user_id, this.connection);

        // DataBaseとの接続を切断する
        creditDAO.closeConnection(this.connection);

        // コネクションオブジェクトを破棄する
        this.connection = null;

        return credit_list_id;
    }

    // 追加
    // 引数はCreditオブジェクト
    public int registCreditsList(List<Credit> creditslist, int credit_list_id) {

        // CreditDAOオブジェクト生成
        CreditDAO creditDAO = new CreditDAO();

        // DataBaseへ接続し、コネクションオブジェクトを生成する
        this.connection = creditDAO.createConnection();

        int count = creditDAO.registCreditsList(creditslist, credit_list_id, this.connection);

        // DataBaseとの接続を切断する
        creditDAO.closeConnection(this.connection);

        // コネクションオブジェクトを破棄する
        this.connection = null;

        return count;
    }

    //user_idからcredits_listsを検索
    public List<String> searchCreditsList(String user_id) {

        // CreditDAOオブジェクト生成
        CreditDAO creditDAO = new CreditDAO();

        // DataBaseへ接続し、コネクションオブジェクトを生成する
        this.connection = creditDAO.createConnection();

        List<String> credit_list = creditDAO.searchCreditsList(user_id, this.connection);

        // DataBaseとの接続を切断する
        creditDAO.closeConnection(this.connection);

        // コネクションオブジェクトを破棄する
        this.connection = null;

        return credit_list;
    }

    //credit_list_idからcredits_listsを検索
    public List<Credit> searchCredits(String credit_list_id) {

        // CreditDAOオブジェクト生成
        CreditDAO creditDAO = new CreditDAO();

        // DataBaseへ接続し、コネクションオブジェクトを生成する
        this.connection = creditDAO.createConnection();

        List<Credit> credits = creditDAO.searchCredits(credit_list_id, this.connection);

        // DataBaseとの接続を切断する
        creditDAO.closeConnection(this.connection);

        // コネクションオブジェクトを破棄する
        this.connection = null;

        return credits;
    }
}