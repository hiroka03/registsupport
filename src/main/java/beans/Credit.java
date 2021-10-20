package beans;

public class Credit {

    // 属性
    private String subject_name;
    private String evaluation;
    private String credit;
    private String acquisition_year;
    private String acquisition_semester;

    // 初期値を引数に持ったコンストラクタ
    public Credit(String subject_name, String evaluation, String credit, String acquisition_year, String acquisition_semester) {
        this.subject_name = subject_name;
        this.evaluation = evaluation;
        this.credit = credit;
        this.acquisition_year = acquisition_year;
        this.acquisition_semester = acquisition_semester;
    }

    // 初期値を引数に持たないコンストラクタ
    // Java beansは初期値を持たないコンストラクタが必ず必要
    public Credit() {
    }

    // setメソッド
    // Java beansのsetメソッドはsetの後ろに続く文字列が必ず大文字であること
    public void setSubjectName(String subject_name) {this.subject_name = subject_name;}
    public void setEvaluation(String evaluation) {this.evaluation = evaluation;}
    public void setCredit(String credit) {this.credit = credit;}
    public void setAcquisitionYear(String acquisition_year) {this.acquisition_year = acquisition_year;}
    public void setAcquisitionSemester(String acquisition_semester) {this.acquisition_semester = acquisition_semester;}

    // getメソッド
    // Java beansのgetメソッドはgetの後ろに続く文字列が必ず大文字であること
    public String getSubjectName() {return this.subject_name;}
    public String getEvaluation() {return this.evaluation;}
    public String getCredit() {return this.credit;}
    public String getAcquisitionYear() {return this.acquisition_year;}
    public String getAcquisitionSemester() {return this.acquisition_semester;}
}
