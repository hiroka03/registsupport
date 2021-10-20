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

import beans.Syllabus;
import utility.DriverAccessor;

//時間を扱うためのライブラリ
import java.util.TimeZone;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class SyllabusDAO extends DriverAccessor {

    public static final String SEARCH_SYLLABUS = "select * from syllabuses where subjects_names = ?";
    public static final String SEARCH_SYLLABUS_REMARK = "select * from syllabuses where subjects_names = ? and remarks like ? and grades = ? and semesters ='秋学期'";
    public static final String SEARCH_SYLLABUS_COMPULSORY = "select * from syllabuses where subjects_names = ? and targets like ? and grades = ? and semesters ='秋学期'";
    public static final String SEARCH_SYLLABUS_CHOICE = "select * from syllabuses where targets like ? and grades = ? and semesters ='秋学期'";
    public static final String SEARCH_SYLLABUS_SUBLICENSE = "select * from syllabuses where sublicenses like ? and grades = ? and semesters ='秋学期'";
    public static final String SEARCH_SYLLABUS_ID = "select * from syllabuses where syllabus_id = ?";
    public static final String REGIST_TIMETABLE = "insert into timetables (user_id) values(?)";
    public static final String REGIST_SUBJECT = "insert into subjects(timetable_id,syllabus_id,subjects_codes,subjects_names,teachers_names,grades,classes,classrooms,semesters,day_of_the_week,subjects_classifications,classes_styles,credits,targets,remarks) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public static final String SEARCH_TIMETABLES_LIST = "select * from timetables where user_id = ?";
    public static final String SEARCH_SUBJECTS = "select * from subjects where timetable_id = ?";

    public Syllabus searchSyllabus(String own_subject_name, Connection connection) {
        try {
            PreparedStatement stmt = connection.prepareStatement(SEARCH_SYLLABUS);
            stmt.setString(1, own_subject_name);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            String syllabus_id = rs.getString("syllabus_id");
            String subject_code = rs.getString("subjects_codes");
            String subject_name =rs.getString("subjects_names");
            String teacher_name =rs.getString("teachers_names");
            String grade = rs.getString("grades");
            String class_number = rs.getString("classes");
            String classroom = rs.getString("classrooms");
            String semester = rs.getString("semesters");
            String day_of_the_week = rs.getString("day_of_the_week");
            String subject_classification = rs.getString("subjects_classifications");
            String class_style = rs.getString("classes_styles");
            String credit = rs.getString("credits");
            String target = rs.getString("targets");
            String remark = rs.getString("remarks");
            String sublicense = rs.getString("sublicenses");
            Syllabus syllabus = new Syllabus(syllabus_id, subject_code, subject_name, teacher_name, grade, class_number, classroom, semester, day_of_the_week, subject_classification, class_style, credit, target, remark, sublicense);
            stmt.close();
            rs.close();
            return syllabus;

            
        } catch (SQLException e) {
            // エラーが発生した場合、エラーの原因を出力する
            e.printStackTrace();
            return null;
        } finally {
        }
    }

    public List<Syllabus> searchSyllabusRemark(String own_subject_name, String searched_remark, String my_grade, Connection connection) {
        try {
            List<Syllabus> syllabuslist = new ArrayList<>();
            for(int n=1;n<=Integer.parseInt(my_grade);n=n+1){
                PreparedStatement stmt = connection.prepareStatement(SEARCH_SYLLABUS_REMARK);
                stmt.setString(1, own_subject_name);
                stmt.setString(2, "%"+searched_remark+"%");
                stmt.setString(3, n+"年");
                ResultSet rs = stmt.executeQuery();

                while(rs.next()){
                    String syllabus_id = rs.getString("syllabus_id");
                    String subject_code = rs.getString("subjects_codes");
                    String subject_name =rs.getString("subjects_names");
                    String teacher_name =rs.getString("teachers_names");
                    String grade = rs.getString("grades");
                    String class_number = rs.getString("classes");
                    String classroom = rs.getString("classrooms");
                    String semester = rs.getString("semesters");
                    String day_of_the_week = rs.getString("day_of_the_week");
                    String subject_classification = rs.getString("subjects_classifications");
                    String class_style = rs.getString("classes_styles");
                    String credit = rs.getString("credits");
                    String target = rs.getString("targets");
                    String remark = rs.getString("remarks");
                    String sublicense = rs.getString("sublicenses");
                    Syllabus syllabus = new Syllabus(syllabus_id, subject_code, subject_name, teacher_name, grade, class_number, classroom, semester, day_of_the_week, subject_classification, class_style, credit, target, remark, sublicense);
                    syllabuslist.add(syllabus);
                }
                stmt.close();
                rs.close();
            }
            return syllabuslist;

            
        } catch (SQLException e) {
            // エラーが発生した場合、エラーの原因を出力する
            e.printStackTrace();
            return null;
        } finally {
        }
    }

    public List<Syllabus> searchSyllabusCompulsory(String own_subject_name, String searched_target, String my_grade, Connection connection) {
        try {
            List<Syllabus> syllabuslist = new ArrayList<>();
            for(int n=1;n<=Integer.parseInt(my_grade);n=n+1){
                PreparedStatement stmt = connection.prepareStatement(SEARCH_SYLLABUS_COMPULSORY);
                stmt.setString(1, own_subject_name);
                stmt.setString(2, "%"+searched_target+"%");
                stmt.setString(3, n+"年");
                ResultSet rs = stmt.executeQuery();

                while(rs.next()){
                    String syllabus_id = rs.getString("syllabus_id");
                    String subject_code = rs.getString("subjects_codes");
                    String subject_name =rs.getString("subjects_names");
                    String teacher_name =rs.getString("teachers_names");
                    String grade = rs.getString("grades");
                    String class_number = rs.getString("classes");
                    String classroom = rs.getString("classrooms");
                    String semester = rs.getString("semesters");
                    String day_of_the_week = rs.getString("day_of_the_week");
                    String subject_classification = rs.getString("subjects_classifications");
                    String class_style = rs.getString("classes_styles");
                    String credit = rs.getString("credits");
                    String target = rs.getString("targets");
                    String remark = rs.getString("remarks");
                    String sublicense = rs.getString("sublicenses");
                    Syllabus syllabus = new Syllabus(syllabus_id, subject_code, subject_name, teacher_name, grade, class_number, classroom, semester, day_of_the_week, subject_classification, class_style, credit, target, remark, sublicense);
                    syllabuslist.add(syllabus);
                }
                stmt.close();
                rs.close();
            }
            return syllabuslist;

            
        } catch (SQLException e) {
            // エラーが発生した場合、エラーの原因を出力する
            e.printStackTrace();
            return null;
        } finally {
        }
    }

    public List<Syllabus> searchSyllabusChoice(String searched_target, String my_grade, Connection connection) {
        try {
            List<Syllabus> syllabuslist = new ArrayList<>();
            for(int n=1;n<=Integer.parseInt(my_grade);n=n+1){
                PreparedStatement stmt = connection.prepareStatement(SEARCH_SYLLABUS_CHOICE);
                stmt.setString(1, "%"+searched_target+"%");
                stmt.setString(2, n+"年");
                ResultSet rs = stmt.executeQuery();

                while(rs.next()){
                    String syllabus_id = rs.getString("syllabus_id");
                    String subject_code = rs.getString("subjects_codes");
                    String subject_name =rs.getString("subjects_names");
                    String teacher_name =rs.getString("teachers_names");
                    String grade = rs.getString("grades");
                    String class_number = rs.getString("classes");
                    String classroom = rs.getString("classrooms");
                    String semester = rs.getString("semesters");
                    String day_of_the_week = rs.getString("day_of_the_week");
                    String subject_classification = rs.getString("subjects_classifications");
                    String class_style = rs.getString("classes_styles");
                    String credit = rs.getString("credits");
                    String target = rs.getString("targets");
                    String remark = rs.getString("remarks");
                    String sublicense = rs.getString("sublicenses");
                    Syllabus syllabus = new Syllabus(syllabus_id, subject_code, subject_name, teacher_name, grade, class_number, classroom, semester, day_of_the_week, subject_classification, class_style, credit, target, remark, sublicense);
                    syllabuslist.add(syllabus);
                }
                stmt.close();
                rs.close();
            }
            return syllabuslist;

            
        } catch (SQLException e) {
            // エラーが発生した場合、エラーの原因を出力する
            e.printStackTrace();
            return null;
        } finally {
        }
    }

    public List<Syllabus> searchSyllabusSublicense(String searched_sublicense, String my_grade, Connection connection) {
        try {
            List<Syllabus> syllabuslist = new ArrayList<>();
            for(int n=1;n<=Integer.parseInt(my_grade);n=n+1){
                PreparedStatement stmt = connection.prepareStatement(SEARCH_SYLLABUS_SUBLICENSE);
                stmt.setString(1, "%"+searched_sublicense+"%");
                stmt.setString(2, n+"年");
                ResultSet rs = stmt.executeQuery();

                while(rs.next()){
                    String syllabus_id = rs.getString("syllabus_id");
                    String subject_code = rs.getString("subjects_codes");
                    String subject_name =rs.getString("subjects_names");
                    String teacher_name =rs.getString("teachers_names");
                    String grade = rs.getString("grades");
                    String class_number = rs.getString("classes");
                    String classroom = rs.getString("classrooms");
                    String semester = rs.getString("semesters");
                    String day_of_the_week = rs.getString("day_of_the_week");
                    String subject_classification = rs.getString("subjects_classifications");
                    String class_style = rs.getString("classes_styles");
                    String credit = rs.getString("credits");
                    String target = rs.getString("targets");
                    String remark = rs.getString("remarks");
                    String sublicense = rs.getString("sublicenses");
                    Syllabus syllabus = new Syllabus(syllabus_id, subject_code, subject_name, teacher_name, grade, class_number, classroom, semester, day_of_the_week, subject_classification, class_style, credit, target, remark, sublicense);
                    syllabuslist.add(syllabus);
                }
                stmt.close();
                rs.close();
            }
            return syllabuslist;

            
        } catch (SQLException e) {
            // エラーが発生した場合、エラーの原因を出力する
            e.printStackTrace();
            return null;
        } finally {
        }
    }

    public Syllabus searchSyllabusId(String searched_syllabus_id, Connection connection) {
        try {
            PreparedStatement stmt = connection.prepareStatement(SEARCH_SYLLABUS_ID);
            stmt.setString(1, searched_syllabus_id);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            String syllabus_id = rs.getString("syllabus_id");
            String subject_code = rs.getString("subjects_codes");
            String subject_name =rs.getString("subjects_names");
            String teacher_name =rs.getString("teachers_names");
            String grade = rs.getString("grades");
            String class_number = rs.getString("classes");
            String classroom = rs.getString("classrooms");
            String semester = rs.getString("semesters");
            String day_of_the_week = rs.getString("day_of_the_week");
            String subject_classification = rs.getString("subjects_classifications");
            String class_style = rs.getString("classes_styles");
            String credit = rs.getString("credits");
            String target = rs.getString("targets");
            String remark = rs.getString("remarks");
            String sublicense = rs.getString("sublicenses");
            Syllabus syllabus = new Syllabus(syllabus_id, subject_code, subject_name, teacher_name, grade, class_number, classroom, semester, day_of_the_week, subject_classification, class_style, credit, target, remark, sublicense);
            stmt.close();
            rs.close();
            return syllabus;

            
        } catch (SQLException e) {
            // エラーが発生した場合、エラーの原因を出力する
            e.printStackTrace();
            return null;
        } finally {
        }
    }

    public int registTimetable(String user_id, Connection connection) {
        try {
            PreparedStatement stmt = connection.prepareStatement(REGIST_TIMETABLE);
            stmt.setString(1, user_id);
            stmt.executeUpdate();

            String sql = "select max(timetable_id) from timetables where user_id = ?";
            PreparedStatement stmt2 = connection.prepareStatement(sql);
            stmt2.setString(1, user_id);
            //System.out.println(stmt2);
            ResultSet rs2 = stmt2.executeQuery();
            rs2.first();
            int timetable_id = rs2.getInt("max(timetable_id)");
            stmt2.close();
            rs2.close();

            return timetable_id;

            } catch (SQLException e) {
                // エラーが発生した場合、エラーの原因を出力する
                e.printStackTrace();
                return 0;
            } finally {
        }
    }

    public int registSubject(List<Syllabus> subjectslist, int timetable_id, Connection connection) {
        try {
            for(int n=0;n<subjectslist.size();n=n+1){
                PreparedStatement stmt = connection.prepareStatement(REGIST_SUBJECT);
                stmt.setInt(1,timetable_id);
                stmt.setString(2, subjectslist.get(n).getSyllabusId());
                stmt.setString(3, subjectslist.get(n).getSubjectCode());
                stmt.setString(4, subjectslist.get(n).getSubjectName());
                stmt.setString(5, subjectslist.get(n).getTeacherName());
                stmt.setString(6, subjectslist.get(n).getGrade());
                stmt.setString(7, subjectslist.get(n).getClassNumber());
                stmt.setString(8, subjectslist.get(n).getClassRoom());
                stmt.setString(9, subjectslist.get(n).getSemester());
                stmt.setString(10, subjectslist.get(n).getDayOfTheWeek());
                stmt.setString(11, subjectslist.get(n).getSubjectClassification());
                stmt.setString(12, subjectslist.get(n).getClassStyle());
                stmt.setString(13, subjectslist.get(n).getCredit());
                stmt.setString(14, subjectslist.get(n).getTarget());
                stmt.setString(15, subjectslist.get(n).getRemark());
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

    public List<String> searchTimetablesList(String user_id, Connection connection) {
        try {
            PreparedStatement stmt = connection.prepareStatement(SEARCH_TIMETABLES_LIST);
            stmt.setString(1, user_id);
            ResultSet rs = stmt.executeQuery();

            List<String> timetable_list = new ArrayList<>();
            while(rs.next()){
                String timetable_id =rs.getString("timetable_id");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
                String created_at = simpleDateFormat.format(rs.getTimestamp("created_at"));
                timetable_list.add(timetable_id);
                timetable_list.add(created_at);
            }
            stmt.close();
            rs.close();
            return timetable_list;

            
        } catch (SQLException e) {
            // エラーが発生した場合、エラーの原因を出力する
            e.printStackTrace();
            return null;
        } finally {
        }
    }

    public List<Syllabus> searchSubjects(String timetable_id, Connection connection) {
        try {
            PreparedStatement stmt = connection.prepareStatement(SEARCH_SUBJECTS);
            stmt.setString(1, timetable_id);
            ResultSet rs = stmt.executeQuery();

            List<Syllabus> subjects = new ArrayList<>();
            while(rs.next()){
                String syllabus_id = rs.getString("syllabus_id");
                String subject_code = rs.getString("subjects_codes");
                String subject_name =rs.getString("subjects_names");
                String teacher_name =rs.getString("teachers_names");
                String grade = rs.getString("grades");
                String class_number = rs.getString("classes");
                String classroom = rs.getString("classrooms");
                String semester = rs.getString("semesters");
                String day_of_the_week = rs.getString("day_of_the_week");
                String subject_classification = rs.getString("subjects_classifications");
                String class_style = rs.getString("classes_styles");
                String credit = rs.getString("credits");
                String target = rs.getString("targets");
                String remark = rs.getString("remarks");
                String sublicense = rs.getString("sublicenses");
                Syllabus subject_list = new Syllabus(syllabus_id, subject_code, subject_name, teacher_name, grade, class_number, classroom, semester, day_of_the_week, subject_classification, class_style, credit, target, remark, sublicense);
                subjects.add(subject_list);
            }
            stmt.close();
            rs.close();
            return subjects;

            
        } catch (SQLException e) {
            // エラーが発生した場合、エラーの原因を出力する
            e.printStackTrace();
            return null;
        } finally {
        }
    }
}
