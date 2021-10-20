//  自分が格納されているフォルダ名
package beans;

public class User {

    // 属性
    private String user_id; // id
    private String password; // パスワード
    private int grade;
    private int department;

    // 初期値を引数に持ったコンストラクタ
    public User(String user_id, String password, int grade, int department) {
        this.user_id = user_id;
        this.password = password;
        this.grade = grade;
        this.department = department;
    }

    // 初期値を引数に持たないコンストラクタ
    // Java beansは初期値を持たないコンストラクタが必ず必要
    public User() {
    }

    // setメソッド
    // Java beansのsetメソッドはsetの後ろに続く文字列が必ず大文字であること
    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    // getメソッド
    // Java beansのgetメソッドはgetの後ろに続く文字列が必ず大文字であること
    public String getUserId() {
        return this.user_id;
    }

    public String getPassword() {
        return this.password;
    }

    public int getGrade() {
        return this.grade;
    }

    public int getDepartment() {
        return this.department;
    }

}