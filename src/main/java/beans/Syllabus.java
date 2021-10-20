package beans;

public class Syllabus {

    // 属性
    private String syllabus_id;
    private String subject_code;
    private String subject_name;
    private String teacher_name;
    private String grade;
    private String class_number;
    private String classroom;
    private String semester;
    private String day_of_the_week;
    private String subject_classification;
    private String class_style;
    private String credit;
    private String target;
    private String remark;
    private String sublicense;

    // 初期値を引数に持ったコンストラクタ
    public Syllabus(String syllabus_id, String subject_code, String subject_name, String teacher_name, String grade, String class_number, String classroom, String semester, String day_of_the_week, String subject_classification, String class_style, String credit, String target, String remark, String sublicense) {
        this.syllabus_id = syllabus_id;
        this.subject_code = subject_code;
        this.subject_name = subject_name;
        this.teacher_name = teacher_name;
        this.grade = grade;
        this.class_number = class_number;
        this.classroom = classroom;
        this.semester = semester;
        this.day_of_the_week = day_of_the_week;
        this.subject_classification = subject_classification;
        this.class_style = class_style;
        this.credit = credit;
        this.target = target;
        this.remark = remark;
        this.sublicense = sublicense;
    }

    // 初期値を引数に持たないコンストラクタ
    // Java beansは初期値を持たないコンストラクタが必ず必要
    public Syllabus() {
    }

    // setメソッド
    // Java beansのsetメソッドはsetの後ろに続く文字列が必ず大文字であること
    public void setSyllabusId(String syllabus_id) {this.syllabus_id = syllabus_id;}
    public void setSubjectCode(String subject_code) {this.subject_code = subject_code;}
    public void setSubjectName(String subject_name) {this.subject_name = subject_name;}
    public void setTeacherName(String teacher_name) {this.teacher_name = teacher_name;}
    public void setGrade(String grade) {this.grade = grade;}
    public void setClassNumber(String class_number) {this.class_number = class_number;}
    public void setClassRoom(String classroom) {this.classroom = classroom;}
    public void setSemester(String semester) {this.semester = semester;}
    public void setDayOfTheWeek(String day_of_the_week) {this.day_of_the_week = day_of_the_week;}
    public void setSubjectClassification(String subject_classification) {this.subject_classification = subject_classification;}
    public void setClassStyle(String class_style) {this.class_style = class_style;}
    public void setCredit(String credit) {this.credit = credit;}
    public void setTarget(String target) {this.target = target;}
    public void setRemark(String remark) {this.remark = remark;}
    public void setSublicense(String sublicense) {this.sublicense = sublicense;}

    // getメソッド
    // Java beansのgetメソッドはgetの後ろに続く文字列が必ず大文字であること
    public String getSyllabusId() {return this.syllabus_id;}
    public String getSubjectCode() {return this.subject_code;}
    public String getSubjectName() {return this.subject_name;}
    public String getTeacherName() {return this.teacher_name;}
    public String getGrade() {return this.grade;}
    public String getClassNumber() {return this.class_number;}
    public String getClassRoom() {return this.classroom;}
    public String getSemester() {return this.semester;}
    public String getDayOfTheWeek() {return this.day_of_the_week;}
    public String getSubjectClassification() {return this.subject_classification;}
    public String getClassStyle() {return this.class_style;}
    public String getCredit() {return this.credit;}
    public String getTarget() {return this.target;}
    public String getRemark() {return this.remark;}
    public String getSublicense() {return this.sublicense;}
}
