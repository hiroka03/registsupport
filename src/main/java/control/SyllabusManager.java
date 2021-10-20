package control;

//  自分が格納されているフォルダの外にある必要なクラス
import java.sql.Connection;
import java.util.List; //Listのインポート
import java.util.ArrayList;

import beans.Syllabus;
import dao.SyllabusDAO;
import javax.servlet.http.HttpServlet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



public class SyllabusManager extends HttpServlet {
    // 属性
    private Connection connection = null;

    // 引数を持たないコンストラクタ
    public SyllabusManager() {
    }

    // 科目名からシラバス情報を検索
    public Syllabus searchSyllabus(String subject_name) {

        // CreditDAOオブジェクト生成
        SyllabusDAO syllabusDAO = new SyllabusDAO();

        // DataBaseへ接続し、コネクションオブジェクトを生成する
        this.connection = syllabusDAO.createConnection();

        Syllabus syllabus = syllabusDAO.searchSyllabus(subject_name, this.connection);

        // DataBaseとの接続を切断する
        syllabusDAO.closeConnection(this.connection);

        // コネクションオブジェクトを破棄する
        this.connection = null;

        return syllabus;
    }

    // 科目名と備考からシラバス情報を検索（日本国憲法，情報，人権教育，スポーツ・フィットネス実習）
    public List<Syllabus> searchSyllabusRemark(String subject_name, String remark, String grade) {

        // CreditDAOオブジェクト生成
        SyllabusDAO syllabusDAO = new SyllabusDAO();

        // DataBaseへ接続し、コネクションオブジェクトを生成する
        this.connection = syllabusDAO.createConnection();

        List<Syllabus> syllabus = syllabusDAO.searchSyllabusRemark(subject_name, remark, grade, this.connection);

        // DataBaseとの接続を切断する
        syllabusDAO.closeConnection(this.connection);

        // コネクションオブジェクトを破棄する
        this.connection = null;

        return syllabus;
    }

    // 科目名と受講対象からシラバス情報を検索（必修の授業）
    public List<Syllabus> searchSyllabusCompulsory(String subject_name, String target, String grade) {

        // CreditDAOオブジェクト生成
        SyllabusDAO syllabusDAO = new SyllabusDAO();

        // DataBaseへ接続し、コネクションオブジェクトを生成する
        this.connection = syllabusDAO.createConnection();

        List<Syllabus> syllabus = syllabusDAO.searchSyllabusCompulsory(subject_name, target, grade, this.connection);

        // DataBaseとの接続を切断する
        syllabusDAO.closeConnection(this.connection);

        // コネクションオブジェクトを破棄する
        this.connection = null;

        return syllabus;
    }

    // 受講対象からシラバス情報を検索（選択の授業）
    public List<Syllabus> searchSyllabusChoice(String target, String grade) {

        // CreditDAOオブジェクト生成
        SyllabusDAO syllabusDAO = new SyllabusDAO();

        // DataBaseへ接続し、コネクションオブジェクトを生成する
        this.connection = syllabusDAO.createConnection();

        List<Syllabus> syllabus = syllabusDAO.searchSyllabusChoice(target, grade, this.connection);

        // DataBaseとの接続を切断する
        syllabusDAO.closeConnection(this.connection);

        // コネクションオブジェクトを破棄する
        this.connection = null;

        return syllabus;
    }

    // 副免許からシラバス情報を検索（選択の授業）
    public List<Syllabus> searchSyllabusSublicense(String sublicense, String grade) {

        // CreditDAOオブジェクト生成
        SyllabusDAO syllabusDAO = new SyllabusDAO();

        // DataBaseへ接続し、コネクションオブジェクトを生成する
        this.connection = syllabusDAO.createConnection();

        List<Syllabus> syllabus = syllabusDAO.searchSyllabusSublicense(sublicense, grade, this.connection);

        // DataBaseとの接続を切断する
        syllabusDAO.closeConnection(this.connection);

        // コネクションオブジェクトを破棄する
        this.connection = null;

        return syllabus;
    }

    // シラバスIDからシラバス情報を検索
    public Syllabus searchSyllabusId(String syllabus_id) {

        // CreditDAOオブジェクト生成
        SyllabusDAO syllabusDAO = new SyllabusDAO();

        // DataBaseへ接続し、コネクションオブジェクトを生成する
        this.connection = syllabusDAO.createConnection();

        Syllabus syllabus = syllabusDAO.searchSyllabusId(syllabus_id, this.connection);

        // DataBaseとの接続を切断する
        syllabusDAO.closeConnection(this.connection);

        // コネクションオブジェクトを破棄する
        this.connection = null;

        return syllabus;
    }

    // timetablesテーブルにIDを登録
    public int registTimetable(String user_id) {

        // SyllabusDAOオブジェクト生成
        SyllabusDAO syllabusDAO = new SyllabusDAO();

        // DataBaseへ接続し、コネクションオブジェクトを生成する
        this.connection = syllabusDAO.createConnection();

        int timetable_id = syllabusDAO.registTimetable(user_id, this.connection);

        // DataBaseとの接続を切断する
        syllabusDAO.closeConnection(this.connection);

        // コネクションオブジェクトを破棄する
        this.connection = null;

        return timetable_id;
    }

    // 追加
    // 引数はCreditオブジェクト
    public int registSubject(List<Syllabus> subjectlist, int timetable_id) {

        // SyllabusDAOオブジェクト生成
        SyllabusDAO syllabusDAO = new SyllabusDAO();

        // DataBaseへ接続し、コネクションオブジェクトを生成する
        this.connection = syllabusDAO.createConnection();

        int count = syllabusDAO.registSubject(subjectlist, timetable_id, this.connection);

        // DataBaseとの接続を切断する
        syllabusDAO.closeConnection(this.connection);

        // コネクションオブジェクトを破棄する
        this.connection = null;

        return count;
    }

    //user_idからtimetables_listsを検索
    public List<String> searchTimetablesList(String user_id) {

        // SyllabusDAOオブジェクト生成
        SyllabusDAO syllabusDAO = new SyllabusDAO();

        // DataBaseへ接続し、コネクションオブジェクトを生成する
        this.connection = syllabusDAO.createConnection();

        List<String> timetable_list = syllabusDAO.searchTimetablesList(user_id, this.connection);

        // DataBaseとの接続を切断する
        syllabusDAO.closeConnection(this.connection);

        // コネクションオブジェクトを破棄する
        this.connection = null;

        return timetable_list;
    }

    //timetable_idからsubjectsを検索
    public List<Syllabus> searchSubjects(String timetable_id) {

        // SyllabusDAOオブジェクト生成
        SyllabusDAO syllabusDAO = new SyllabusDAO();

        // DataBaseへ接続し、コネクションオブジェクトを生成する
        this.connection = syllabusDAO.createConnection();

        List<Syllabus> subjects = syllabusDAO.searchSubjects(timetable_id, this.connection);

        // DataBaseとの接続を切断する
        syllabusDAO.closeConnection(this.connection);

        // コネクションオブジェクトを破棄する
        this.connection = null;

        return subjects;
    }
}