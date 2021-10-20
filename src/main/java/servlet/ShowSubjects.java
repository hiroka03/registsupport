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
import java.util.Iterator;
import beans.Syllabus;
import control.SyllabusManager;
import beans.Credit;
import control.CreditManager;
import utility.SessionChecker;

//アノテーションの記述
//jspで示してあげると、jspから呼び出さられる
@WebServlet("/ShowSubjects")

// HttpServletを継承することで、このクラスはServletとして、働くことができる
public class ShowSubjects extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // doPostメソッドから呼び出される(リダイレクトされる)
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    SessionChecker sessionChecker = new SessionChecker();

    if(sessionChecker.isNotUser(request, response)){
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }else{
        // CreditManagerオブジェクトの生成
        CreditManager manager = new CreditManager();

        // credits_listsテーブルから情報を検索
        HttpSession session =request.getSession();
        String user_id = (String)session.getAttribute("user_id");
        List<String> credit_list = manager.searchCreditsList(user_id);

        if(credit_list!=null){
            request.setAttribute("credit_list",credit_list);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/showCreditsList.jsp");
            dispatcher.forward(request, response);
        }
    }
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    // requestオブジェクトの文字エンコーディングの設定
    request.setCharacterEncoding("UTF-8");
    SessionChecker sessionChecker = new SessionChecker();

    if(sessionChecker.isNotUser(request, response)){
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }else{

        // requestオブジェクトから登録情報の取り出し
        String errorMessage = request.getParameter("errorMessage");
        String credit_list_id = request.getParameter("credit_list_id");
        String created_at = request.getParameter("created_at");
        String[] want_sublicense = request.getParameterValues("sublicense");
        if(errorMessage==null){
            errorMessage = "同名の授業科目や，集中授業以外で同名の時限の授業科目を選択することはできません";
        }

        // CreditManagerオブジェクトの生成
        CreditManager manager = new CreditManager();

        // creditsテーブルから情報を検索
        List<Credit> credits = manager.searchCredits(credit_list_id);

        if(credits!=null){
            int ca_constitution = 2;//日本国憲法（必修）
            int ca_information = 2;//情報（必修）
            int ca_liberty = 2;//人権教育（必修）
            int ca_a = 2;//CA(A)領域（選択必修）〇
            int ca_b = 2;//CA(B)領域（選択必修）〇
            int ca_c = 2;//CA(C)領域（選択必修）〇
            int ca_abc = 8;//CAはA,B,Cをそれぞれ2単位以上，合計8単位 〇
            int ch_sports = 1;//スポーツ・フィットネス実習（必修）
            int ch_wellness = 1;//ウェルネス概論（必修）
            int cl_a = 1;//英語コミュニケーションA（必修）
            int cl_b = 1;//英語コミュニケーションB（必修）
            int cl_i = 1;//初習語学I（必修）
            int cl_ii = 1;//初習語学II（必修）
            int cl_iii = 1;//初習語学III（必修）
            int cl_iv = 1;//初習語学IV（必修）
            // ここまではA・E類共通
            int ew_teaching = 2;//A教職入門（必修），         E教育基礎論（必修）
            int ef_history = 2;//教育の理念と歴史（必修）
            int ef_organization = 2;//A教育組織論（必修），   E教育組織論（必修）
            int ef_psychology = 2;//A教育心理学（必修），     E教育と発達の心理学（必修）
            int ef_special = 2;//A特別支援教育の理解（必修）， E特別支援教育入門（必修）
            int et_moral = 2;//道徳教育の指導法（必修）
            int et_comprehensive = 2;//特別活動・総合的な学習の時間の指導法（必修）
            int et_elementary = 2;//初等教育の内容と方法（必修）
            int et_japanese = 2;//初等国語科教育法（必修）
            int et_social = 2;//初等社会科教育法（必修）
            int et_math = 2;//初等算数科教育法（必修）
            int et_science = 2;//初等理科教育法（必修）
            int et_music = 2;//初等音楽科教育法（必修）
            int et_art = 2;//初等図画工作科教育法（必修）
            int et_pe = 2;//初等体育科教育法（必修）
            int et_home = 2;//初等家庭科教育法（必修）
            int et_english = 2;//初等英語科教育法（必修）
            int et_life = 2;//初等生活科教育法（必修）
            int ec_counselling = 2;//教育相談の理論と方法（必修）
            int ec_guidance = 2;//生徒指導・進路指導の理論と方法（必修）
            int ee_practice = 2;//教職実践演習（必修）E類は免許取得希望者のみ必修
            int ep_advance = 1;//事前・事後の指導（必修）E類は免許取得希望者のみ必修
            int ep_practice = 4;//教育実地研究I（必修）
            int se = 3;//共通SE（選択必修）〇
            int sp_choice = 2;//国語，社会，生活，家庭，英語科研究（選択必修）〇
            int sp_math = 2;//算数科研究（必修）
            int sp_science = 2;//理科研究（必修）
            int sp_music = 1;//音楽科研究I（必修）
            int sp_art = 1;//図画工作科研究I（必修）
            int sp_pe = 1;//体育科研究I（必修）
            int sp_ii = 1;//音楽，図画工作，体育科研究II（選択必修）〇
            int major_se = 4;//専攻SE（選択必修）〇
            int sp = 6;//共通SE，研究，専攻SEの必修以外の残数 〇
            int s_seminar = 2;//情報教育選修入門セミナー（必修）
            int s_information = 2;//教育と情報（必修）
            int s_ict = 2;//授業におけるICT活用（必修）
            int s_education = 2;//情報教育概論（必修）
            int s_math = 2;//情報数学基礎（必修）
            int sa_sb = 18;//選択科目A,B（選択必修）〇
            int sz = 4;//A卒業研究（必修），                  E卒業研究（必修）
            int choice = 8;//A自由選択 〇
            int teaching = 2;//                             E類教科・教職（選択必修）〇
            int e_ss = 8; //                                E類課程共通科目（教育支援概論など）（必修）
            int e_s = 34; //                                E類必修科目（計算機システム概説など）（必修）
            int field_collaboration = 2; //                 E類フィールドワーク演習orコラボレーション演習（必修）
            int e_sa_sb = 30; //                            E類選択科目A,B（選択必修）〇
            int e_choice = 16; //                           E類自由選択 〇
            int math_choice = 2; //数学（選択）
            int special1_3learning = 2; //特支一種3領域（言語障害選択）
            int special1_3language = 2; //特支一種3領域（学習障害選択）
            int special1_2learning = 2; //特支一種2領域（言語障害選択）
            int special1_2language = 2; //特支一種2領域（学習障害選択）
            HttpSession session =request.getSession();
            String grade = String.valueOf((int)session.getAttribute("grade"));
            int department = (int)session.getAttribute("department");
            if(department == 1){
                for(int n=0;n<credits.size();n=n+1){
                    //必修の授業科目に当てはまるかを検査
                    if(credits.get(n).getSubjectName().equals("日本国憲法")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ca_constitution = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("情報")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ca_information = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("人権教育")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ca_liberty = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("スポーツ・フィットネス実習")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ch_sports = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("ウェルネス概論")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ch_wellness = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("英語コミュニケーションＡ")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            cl_a = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("英語コミュニケーションＢ")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            cl_b = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().contains("語基礎Ⅰ")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            cl_i = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().contains("語基礎Ⅱ")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            cl_ii = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().contains("語基礎Ⅲ")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            cl_iii = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().contains("語基礎Ⅳ")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            cl_iv = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("教職入門")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ew_teaching = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("教育の理念と歴史")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ef_history = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("教育組織論")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ef_organization = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("教育心理学")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ef_psychology = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("特別支援教育の理解")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ef_special = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("道徳教育の指導法")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            et_moral = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("特別活動・総合的な学習の時間の指導法")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            et_comprehensive = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("初等教育の内容と方法")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            et_elementary = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("初等国語科教育法")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            et_japanese = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("初等社会科教育法")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            et_social = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("初等算数科教育法")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            et_math = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("初等理科教育法")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            et_science = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("初等音楽科教育法")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            et_music = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("初等図画工作科教育法")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            et_art = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("初等体育科教育法")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            et_pe = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("初等家庭科教育法")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            et_home = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("初等英語科教育法")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            et_english = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("初等生活科教育法")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            et_life = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("教育相談の理論と方法")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ec_counselling = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("生徒指導・進路指導の理論と方法")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ec_guidance = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("教職実践演習")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ee_practice = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().contains("事前・事後の指導")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ep_advance = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().contains("教育実地研究Ⅰ")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ep_practice = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("算数科研究")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            sp_math = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("理科研究")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            sp_science = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("音楽科研究Ⅰ")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            sp_music = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("図画工作科研究Ⅰ")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            sp_art = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("体育科研究Ⅰ")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            sp_pe = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("情報教育選修入門セミナー")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            s_seminar = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("教育と情報")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            s_information = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("授業におけるＩＣＴ活用")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            s_ict = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("情報教育概論")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            s_education = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("情報数学基礎")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            s_math = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("卒業研究")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            sz = 0;
                        }
                    }
                    else{
                        //必修の授業に当てはまらない→選択科目であるから，どの選択科目かを調査
                        //選択科目は基準を超過すると自由単位となる
                        SyllabusManager syllabusmanager = new SyllabusManager();
                        Syllabus syllabus = syllabusmanager.searchSyllabus(credits.get(n).getSubjectName());
                        //ＣＡはすべて2単位
                        if(syllabus==null){
                            System.out.println(credits.get(n).getSubjectName()+"はシラバスに登録されていません");
                        }
                        //ＣＡ：（Ａ）領域
                        else if(syllabus.getRemark().equals("ＣＡ：（Ａ）領域")){
                            if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                                ca_a = ca_a-Integer.parseInt(credits.get(n).getCredit());
                                if(ca_abc>0){
                                    ca_abc = ca_abc-Integer.parseInt(credits.get(n).getCredit());
                                }
                                else{
                                    choice = choice-Integer.parseInt(credits.get(n).getCredit());
                                }
                            }
                        }
                        //ＣＡ：（Ｂ）領域
                        else if(syllabus.getRemark().equals("ＣＡ：（Ｂ）領域")){
                            if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                                ca_b = ca_b-Integer.parseInt(credits.get(n).getCredit());
                                if(ca_abc>0){
                                    ca_abc = ca_abc-Integer.parseInt(credits.get(n).getCredit());
                                }
                                else{
                                    choice = choice-Integer.parseInt(credits.get(n).getCredit());
                                }
                            }
                        }
                        //ＣＡ：（Ｃ）領域
                        else if(syllabus.getRemark().equals("ＣＡ：（Ｃ）領域")){
                            if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                                ca_c = ca_c-Integer.parseInt(credits.get(n).getCredit());
                                if(ca_abc>0){
                                    ca_abc = ca_abc-Integer.parseInt(credits.get(n).getCredit());
                                }
                                else{
                                    choice = choice-Integer.parseInt(credits.get(n).getCredit());
                                }
                            }
                        }
                        //ＣＡ：その他など
                        else if(syllabus.getTarget().equals("総合学芸領域：選択共通")){
                            if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                                if(ca_abc>0){
                                    ca_abc = ca_abc-Integer.parseInt(credits.get(n).getCredit());
                                }
                                else{
                                    choice = choice-Integer.parseInt(credits.get(n).getCredit());
                                }
                            }
                        }
                        //共通ＳＥ系は1単位あり
                        //共通ＳＥ（要件は3単位）
                        else if(syllabus.getRemark().contains("共通ＳＥ")){
                            if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                                if(se>0){
                                    se = se-Integer.parseInt(credits.get(n).getCredit());
                                    //1-2=-1のパターン
                                    if(se==-1){
                                        sp = sp-1;
                                    }
                                }
                                else{
                                    if(sp>0){
                                        sp = sp-Integer.parseInt(credits.get(n).getCredit());
                                        //1-2=-1のパターン
                                        if(sp==-1){
                                            choice = choice-1;
                                        }
                                    }
                                    else{
                                        choice = choice-Integer.parseInt(credits.get(n).getCredit());
                                    }
                                }
                            }
                        }
                        //国語，社会，生活，家庭，英語科研究
                        //これらはすべて2単位
                        //これらは科目名が分かるので科目名で検索
                        else if(credits.get(n).getSubjectName().equals("国語科研究") || credits.get(n).getSubjectName().equals("社会科研究") || credits.get(n).getSubjectName().equals("生活科研究") || credits.get(n).getSubjectName().equals("家庭科研究") || credits.get(n).getSubjectName().equals("英語科研究")){
                            if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                                if(sp_choice>0){
                                    sp_choice = 0;
                                }
                                else{
                                    if(sp>0){
                                        sp = sp-Integer.parseInt(credits.get(n).getCredit());
                                        //1-2=-1のパターン
                                        if(sp==-1){
                                            choice = choice-1;
                                        }
                                    }
                                    else{
                                        choice = choice-Integer.parseInt(credits.get(n).getCredit());
                                    }
                                }
                            }
                        }
                        //音楽，図画工作，体育科研究II
                        //これらはすべて1単位
                        //これらは科目名が分かるので科目名で検索
                        else if(credits.get(n).getSubjectName().equals("音楽科研究Ⅱ") || credits.get(n).getSubjectName().equals("図画工作科研究Ⅱ") || credits.get(n).getSubjectName().equals("体育科研究Ⅱ")){
                            if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                                if(sp_ii>0){
                                    sp_ii = 0;
                                }
                                else{
                                    if(sp>0){
                                        sp = sp-Integer.parseInt(credits.get(n).getCredit());
                                    }
                                    else{
                                        choice = choice-Integer.parseInt(credits.get(n).getCredit());
                                    }
                                }
                            }
                        }
                        //専攻ＳＥ（教育情報化教材論ＡＢ，教育情報化臨床）
                        //これらはすべて2単位
                        //これらは科目名が分かるので科目名で検索
                        else if(credits.get(n).getSubjectName().equals("教育情報化教材論Ａ") || credits.get(n).getSubjectName().equals("教育情報化教材論Ｂ") || credits.get(n).getSubjectName().equals("教育情報化臨床")){
                            if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                                if(major_se>0){
                                    major_se = major_se-Integer.parseInt(credits.get(n).getCredit());
                                }
                                else{
                                    if(sp>0){
                                        sp = sp-Integer.parseInt(credits.get(n).getCredit());
                                        //1-2=-1のパターン
                                        if(sp==-1){
                                            choice = choice-1;
                                        }
                                    }
                                    else{
                                        choice = choice-Integer.parseInt(credits.get(n).getCredit());
                                    }
                                }
                            }
                        }
                        //選択科目Ａ，Ｂ
                        //1単位あり（演習系）
                        else if(syllabus.getTarget().contains("情報教育選修選択科目")){
                            if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                                if(sa_sb>0){
                                    sa_sb = sa_sb-Integer.parseInt(credits.get(n).getCredit());
                                    //1-2=-1のパターン
                                    if(sa_sb==-1){
                                        choice = choice-1;
                                    }
                                }
                                else{
                                    choice = choice-Integer.parseInt(credits.get(n).getCredit());
                                }
                            }
                        }
                        else{
                            if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                                choice = choice-Integer.parseInt(credits.get(n).getCredit());
                                if(syllabus.getSublicense().contains("数学（選択）")){
                                    math_choice = 0;
                                }
                                if(syllabus.getSublicense().contains("特支一種3領域（言語障害選択")){
                                    special1_3language = special1_3language-Integer.parseInt(credits.get(n).getCredit());
                                }
                                if(syllabus.getSublicense().contains("特支一種3領域（学習障害選択")){
                                    special1_3learning = special1_3learning-Integer.parseInt(credits.get(n).getCredit());
                                }
                                if(syllabus.getSublicense().contains("特支一種2領域（言語障害選択")){
                                    special1_2language = special1_2language-Integer.parseInt(credits.get(n).getCredit());
                                }
                                if(syllabus.getSublicense().contains("特支一種2領域（学習障害選択")){
                                    special1_2learning = special1_2learning-Integer.parseInt(credits.get(n).getCredit());
                                }
                            }
                        }
                    }
                }
                /*コンソールに確認のため出力
                System.out.println("日本国憲法:"+ca_constitution);//日本国憲法（必修）
                System.out.println("情報:"+ca_information);//情報（必修）
                System.out.println("人権教育:"+ca_liberty);//人権教育（必修）
                System.out.println("CA(A):"+ca_a);//CA(A)領域（選択必修）〇
                System.out.println("CA(B):"+ca_b);//CA(B)領域（選択必修）〇
                System.out.println("CA(C):"+ca_c);//CA(C)領域（選択必修）〇
                System.out.println("CA:"+ca_abc);//CAはA,B,Cをそれぞれ2単位以上，合計8単位 〇
                System.out.println("スポーツ・フィットネス実習:"+ch_sports);//スポーツ・フィットネス実習（必修）
                System.out.println("ウェルネス概論:"+ch_wellness);//ウェルネス概論（必修）
                System.out.println("英語コミュニケーションA:"+cl_a);//英語コミュニケーションA（必修）
                System.out.println("英語コミュニケーションB:"+cl_b);//英語コミュニケーションB（必修）
                System.out.println("初習語学I:"+cl_i);//初習語学I（必修）
                System.out.println("初習語学II:"+cl_ii);//初習語学II（必修）
                System.out.println("初習語学III:"+cl_iii);//初習語学III（必修）
                System.out.println("初習語学IV:"+cl_iv);//初習語学IV（必修）
                System.out.println("教職入門:"+ew_teaching);//教職入門（必修）
                System.out.println("教育の理念と歴史:"+ef_history);//教育の理念と歴史（必修）
                System.out.println("教育組織論:"+ef_organization);//教育組織論（必修）
                System.out.println("教育心理学:"+ef_psychology);//教育心理学（必修）
                System.out.println("特別支援教育の理解:"+ef_special);//特別支援教育の理解（必修）
                System.out.println("道徳教育の指導法:"+et_moral);//道徳教育の指導法（必修）
                System.out.println("特別活動・総合的な学習の時間の指導法:"+et_comprehensive);//特別活動・総合的な学習の時間の指導法（必修）
                System.out.println("初等教育の内容と方法:"+et_elementary);//初等教育の内容と方法（必修）
                System.out.println("初等国語科教育法:"+et_japanese);//初等国語科教育法（必修）
                System.out.println("初等社会科教育法:"+et_social);//初等社会科教育法（必修）
                System.out.println("初等算数科教育法:"+et_math);//初等算数科教育法（必修）
                System.out.println("初等理科教育法:"+et_science);//初等理科教育法（必修）
                System.out.println("初等音楽科教育法:"+et_music);//初等音楽科教育法（必修）
                System.out.println("初等図画工作科教育法:"+et_art);//初等図画工作科教育法（必修）
                System.out.println("初等体育科教育法:"+et_pe);//初等体育科教育法（必修）
                System.out.println("初等家庭科教育法:"+et_home);//初等家庭科教育法（必修）
                System.out.println("初等英語科教育法:"+et_english);//初等英語科教育法（必修）
                System.out.println("初等生活科教育法:"+et_life);//初等生活科教育法（必修）
                System.out.println("教育相談の理論と方法:"+ec_counselling);//教育相談の理論と方法（必修）
                System.out.println("生徒指導・進路指導の理論と方法:"+ec_guidance);//生徒指導・進路指導の理論と方法（必修）
                System.out.println("教職実践演習:"+ee_practice);//教職実践演習（必修）
                System.out.println("事前・事後の指導:"+ep_advance);//事前・事後の指導（必修）
                System.out.println("教育実地研究I:"+ep_practice);//教育実地研究I（必修）
                System.out.println("共通SE:"+se);//共通SE（選択必修）〇
                System.out.println("国語，社会，生活，家庭，英語科研究:"+sp_choice);//国語，社会，生活，家庭，英語科研究（選択必修）〇
                System.out.println("算数科研究:"+sp_math);//算数科研究（必修）
                System.out.println("理科研究:"+sp_science);//理科研究（必修）
                System.out.println("音楽科研究I:"+sp_music);//音楽科研究I（必修）
                System.out.println("図画工作科研究I:"+sp_art);//図画工作科研究I（必修）
                System.out.println("体育科研究I:"+sp_pe);//体育科研究I（必修）
                System.out.println("音楽，図画工作，体育科研究II:"+sp_ii);//音楽，図画工作，体育科研究II（選択必修）〇
                System.out.println("専攻SE:"+major_se);//専攻SE（選択必修）〇
                System.out.println("SE系の必修以外の残数:"+sp);//共通SE，研究，専攻SEの必修以外の残数 〇
                System.out.println("情報教育入門セミナー:"+s_seminar);//情報教育選修入門セミナー（必修）
                System.out.println("教育と情報:"+s_information);//教育と情報（必修）
                System.out.println("授業におけるICT活用:"+s_ict);//授業におけるICT活用（必修）
                System.out.println("情報教育概論:"+s_education);//情報教育概論（必修）
                System.out.println("情報数学基礎:"+s_math);//情報数学基礎（必修）
                System.out.println("選択科目A,B:"+sa_sb);//選択科目A,B（選択必修）〇
                System.out.println("卒業研究:"+sz);//卒業研究（必修）
                System.out.println("自由選択:"+choice);//自由選択 〇
                */

                //残単位数が0でないものは履修可能な授業を検索
                SyllabusManager syllabusmanager = new SyllabusManager();
                List<Syllabus> subjectlist = new ArrayList<>();
                List<Syllabus> list = new ArrayList<>();
                String subject_name;
                String target;
                String sublicense;
                String remark;
                if(ca_constitution>0){
                    subject_name ="日本国憲法";
                    remark = "情報";
                    list = syllabusmanager.searchSyllabusRemark(subject_name,remark,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(ca_information>0){
                    subject_name ="情報";
                    remark = "情報";
                    list = syllabusmanager.searchSyllabusRemark(subject_name,remark,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(ca_liberty>0){
                    subject_name ="人権教育";
                    remark = "情報";
                    list = syllabusmanager.searchSyllabusRemark(subject_name,remark,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(ch_sports>0){
                    subject_name ="スポーツ・フィットネス実習";
                    remark = "情報";
                    list = syllabusmanager.searchSyllabusRemark(subject_name,remark,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(ch_wellness>0){
                    subject_name ="ウェルネス概論";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(cl_a>0){
                    subject_name ="英語コミュニケーションＡ";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(cl_b>0){
                    subject_name ="英語コミュニケーションＢ";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(cl_i>0){
                    subject_name ="語基礎Ⅰ";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(cl_ii>0){
                    subject_name ="語基礎Ⅱ";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(cl_iii>0){
                    subject_name ="語基礎Ⅲ";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(cl_iv>0){
                    subject_name ="語基礎Ⅳ";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(ew_teaching>0){
                    subject_name ="教職入門";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(ef_history>0){
                    subject_name ="教育の理念と歴史";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(ef_organization>0){
                    subject_name ="教育組織論";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(ef_psychology>0){
                    subject_name ="教育心理学";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(ef_special>0){
                    subject_name ="特別支援教育の理解";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(et_moral>0){
                    subject_name ="道徳教育の指導法";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(et_comprehensive>0){
                    subject_name ="特別活動・総合的な学習の時間の指導法";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(et_elementary>0){
                    subject_name ="初等教育の内容と方法";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(et_japanese>0){
                    subject_name ="初等国語科教育法";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(et_social>0){
                    subject_name ="初等社会科教育法";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(et_math>0){
                    subject_name ="初等算数科教育法";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(et_science>0){
                    subject_name ="初等理科教育法";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(et_music>0){
                    subject_name ="初等音楽科教育法";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(et_art>0){
                    subject_name ="初等図画工作科教育法";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(et_pe>0){
                    subject_name ="初等体育科教育法";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(et_home>0){
                    subject_name ="初等家庭科教育法";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(et_english>0){
                    subject_name ="初等英語科教育法";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(et_life>0){
                    subject_name ="初等生活科教育法";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(ec_counselling>0){
                    subject_name ="教育相談の理論と方法";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(ec_guidance>0){
                    subject_name ="生徒指導・進路指導の理論と方法";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(ee_practice>0){
                    subject_name ="教職実践演習";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(ep_advance>0){
                    subject_name ="事前・事後の指導";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(sp_math>0){
                    subject_name ="算数科研究";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(sp_science>0){
                    subject_name ="理科研究";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(sp_music>0){
                    subject_name ="音楽科研究Ⅰ";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(sp_art>0){
                    subject_name ="図画工作科研究Ⅰ";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(sp_pe>0){
                    subject_name ="体育科研究Ⅰ";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(s_seminar>0){
                    subject_name ="情報教育選修入門セミナー";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(s_information>0){
                    subject_name ="教育と情報";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(s_ict>0){
                    subject_name ="授業におけるICT活用";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(s_education>0){
                    subject_name ="情報教育概論";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(s_math>0){
                    subject_name ="情報数学基礎";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                //選択科目は，当てはまる科目を全て検索する
                //既に修得済みの科目は除外する
                //p,qはその処理を実現するための変数である．
                int p;
                Syllabus q;
                Iterator<Syllabus> iterator;
                //CA
                if(ca_abc>0){
                    if(ca_abc-ca_a-ca_b-ca_c==2){
                        target = "分野Ａ共通";
                        list = syllabusmanager.searchSyllabusChoice(target,grade);
                        if(list!=null){
                            iterator = list.iterator();
                            //既に修得済みの科目を除外する処理
                            while(iterator.hasNext()){
                                q = iterator.next();
                                for(p=0;p<credits.size();p=p+1){
                                    if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                        if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                            }
                            subjectlist.addAll(list);
                        }
                        target = "分野Ｂ共通";
                        list = syllabusmanager.searchSyllabusChoice(target,grade);
                        if(list!=null){
                            iterator = list.iterator();
                            //既に修得済みの科目を除外する処理
                            while(iterator.hasNext()){
                                q = iterator.next();
                                for(p=0;p<credits.size();p=p+1){
                                    if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                        if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                            }
                            subjectlist.addAll(list);
                        }
                        target = "分野Ｃ共通";
                        list = syllabusmanager.searchSyllabusChoice(target,grade);
                        if(list!=null){
                            iterator = list.iterator();
                            //既に修得済みの科目を除外する処理
                            while(iterator.hasNext()){
                                q = iterator.next();
                                for(p=0;p<credits.size();p=p+1){
                                    if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                        if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                            }
                            subjectlist.addAll(list);
                        }
                        target = "総合学芸領域：選択共通";
                        list = syllabusmanager.searchSyllabusChoice(target,grade);
                        if(list!=null){
                            iterator = list.iterator();
                            //既に修得済みの科目を除外する処理
                            while(iterator.hasNext()){
                                q = iterator.next();
                                for(p=0;p<credits.size();p=p+1){
                                    if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                        if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                            }
                            subjectlist.addAll(list);
                        }
                    }
                    else{
                        if(ca_a>0){
                            target = "分野Ａ共通";
                            list = syllabusmanager.searchSyllabusChoice(target,grade);
                            if(list!=null){
                                iterator = list.iterator();
                                //既に修得済みの科目を除外する処理
                                while(iterator.hasNext()){
                                    q = iterator.next();
                                    for(p=0;p<credits.size();p=p+1){
                                        if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                            if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                                iterator.remove();
                                                break;
                                            }
                                        }
                                    }
                                }
                                subjectlist.addAll(list);
                            }
                        }
                        if(ca_b>0){
                            target = "分野Ｂ共通";
                            list = syllabusmanager.searchSyllabusChoice(target,grade);
                            if(list!=null){
                                iterator = list.iterator();
                                //既に修得済みの科目を除外する処理
                                while(iterator.hasNext()){
                                    q = iterator.next();
                                    for(p=0;p<credits.size();p=p+1){
                                        if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                            if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                                iterator.remove();
                                                break;
                                            }
                                        }
                                    }
                                }
                                subjectlist.addAll(list);
                            }
                        }
                        if(ca_c>0){
                            target = "分野Ｃ共通";
                            list = syllabusmanager.searchSyllabusChoice(target,grade);
                            if(list!=null){
                                iterator = list.iterator();
                                //既に修得済みの科目を除外する処理
                                while(iterator.hasNext()){
                                    q = iterator.next();
                                    for(p=0;p<credits.size();p=p+1){
                                        if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                            if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                                iterator.remove();
                                                break;
                                            }
                                        }
                                    }
                                }
                                subjectlist.addAll(list);
                            }
                        }
                    }
                }
                //共通SE，専攻SE，小学校の教科に関する科目
                if(sp>0){
                    target = "共通ＳＥ";
                    list = syllabusmanager.searchSyllabusChoice(target,grade);
                    if(list!=null){
                        iterator = list.iterator();
                        //既に修得済みの科目を除外する処理
                        while(iterator.hasNext()){
                            q = iterator.next();
                            for(p=0;p<credits.size();p=p+1){
                                if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                    if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                        iterator.remove();
                                        break;
                                    }
                                }
                            }
                        }
                        subjectlist.addAll(list);
                    }
                    subject_name = "国語科研究";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        iterator = list.iterator();
                        //既に修得済みの科目を除外する処理
                        while(iterator.hasNext()){
                            q = iterator.next();
                            for(p=0;p<credits.size();p=p+1){
                                if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                    if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                        iterator.remove();
                                        break;
                                    }
                                }
                            }
                        }
                        subjectlist.addAll(list);
                    }
                    subject_name = "社会科研究";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        iterator = list.iterator();
                        //既に修得済みの科目を除外する処理
                        while(iterator.hasNext()){
                            q = iterator.next();
                            for(p=0;p<credits.size();p=p+1){
                                if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                    if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                        iterator.remove();
                                        break;
                                    }
                                }
                            }
                        }
                        subjectlist.addAll(list);
                    }
                    subject_name = "生活科研究";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        iterator = list.iterator();
                        //既に修得済みの科目を除外する処理
                        while(iterator.hasNext()){
                            q = iterator.next();
                            for(p=0;p<credits.size();p=p+1){
                                if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                    if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                        iterator.remove();
                                        break;
                                    }
                                }
                            }
                        }
                        subjectlist.addAll(list);
                    }
                    subject_name = "家庭科研究";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        iterator = list.iterator();
                        //既に修得済みの科目を除外する処理
                        while(iterator.hasNext()){
                            q = iterator.next();
                            for(p=0;p<credits.size();p=p+1){
                                if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                    if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                        iterator.remove();
                                        break;
                                    }
                                }
                            }
                        }
                        subjectlist.addAll(list);
                    }
                    subject_name = "英語科研究";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        iterator = list.iterator();
                        //既に修得済みの科目を除外する処理
                        while(iterator.hasNext()){
                            q = iterator.next();
                            for(p=0;p<credits.size();p=p+1){
                                if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                    if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                        iterator.remove();
                                        break;
                                    }
                                }
                            }
                        }
                        subjectlist.addAll(list);
                    }
                    subject_name = "音楽科研究Ⅱ";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        iterator = list.iterator();
                        //既に修得済みの科目を除外する処理
                        while(iterator.hasNext()){
                            q = iterator.next();
                            for(p=0;p<credits.size();p=p+1){
                                if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                    if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                        iterator.remove();
                                        break;
                                    }
                                }
                            }
                        }
                        subjectlist.addAll(list);
                    }
                    subject_name = "図画工作科研究Ⅱ";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        iterator = list.iterator();
                        //既に修得済みの科目を除外する処理
                        while(iterator.hasNext()){
                            q = iterator.next();
                            for(p=0;p<credits.size();p=p+1){
                                if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                    if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                        iterator.remove();
                                        break;
                                    }
                                }
                            }
                        }
                        subjectlist.addAll(list);
                    }
                    subject_name = "体育科研究Ⅱ";
                    target = "情報教育選修";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        iterator = list.iterator();
                        //既に修得済みの科目を除外する処理
                        while(iterator.hasNext()){
                            q = iterator.next();
                            for(p=0;p<credits.size();p=p+1){
                                if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                    if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                        iterator.remove();
                                        break;
                                    }
                                }
                            }
                        }
                        subjectlist.addAll(list);
                    }
                    target = "情報教育選修教科・教職（専攻ＳＥ";
                    list = syllabusmanager.searchSyllabusChoice(target,grade);
                    if(list!=null){
                        iterator = list.iterator();
                        //既に修得済みの科目を除外する処理
                        while(iterator.hasNext()){
                            q = iterator.next();
                            for(p=0;p<credits.size();p=p+1){
                                if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                    if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                        iterator.remove();
                                        break;
                                    }
                                }
                            }
                        }
                        subjectlist.addAll(list);
                    }
                }
                if(sp<=0){
                    if(se>0){
                        target = "共通ＳＥ";
                        list = syllabusmanager.searchSyllabusChoice(target,grade);
                        if(list!=null){
                            iterator = list.iterator();
                            //既に修得済みの科目を除外する処理
                            while(iterator.hasNext()){
                                q = iterator.next();
                                for(p=0;p<credits.size();p=p+1){
                                    if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                        if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                            }
                            subjectlist.addAll(list);
                        }
                    }
                    if(sp_choice>0){
                        subject_name = "国語科研究";
                        target = "情報教育選修";
                        list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                        if(list!=null){
                            iterator = list.iterator();
                            //既に修得済みの科目を除外する処理
                            while(iterator.hasNext()){
                                q = iterator.next();
                                for(p=0;p<credits.size();p=p+1){
                                    if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                        if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                            }
                            subjectlist.addAll(list);
                        }
                        subject_name = "社会科研究";
                        target = "情報教育選修";
                        list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                        if(list!=null){
                            iterator = list.iterator();
                            //既に修得済みの科目を除外する処理
                            while(iterator.hasNext()){
                                q = iterator.next();
                                for(p=0;p<credits.size();p=p+1){
                                    if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                        if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                            }
                            subjectlist.addAll(list);
                        }
                        subject_name = "生活科研究";
                        target = "情報教育選修";
                        list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                        if(list!=null){
                            iterator = list.iterator();
                            //既に修得済みの科目を除外する処理
                            while(iterator.hasNext()){
                                q = iterator.next();
                                for(p=0;p<credits.size();p=p+1){
                                    if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                        if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                            }
                            subjectlist.addAll(list);
                        }
                        subject_name = "家庭科研究";
                        target = "情報教育選修";
                        list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                        if(list!=null){
                            iterator = list.iterator();
                            //既に修得済みの科目を除外する処理
                            while(iterator.hasNext()){
                                q = iterator.next();
                                for(p=0;p<credits.size();p=p+1){
                                    if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                        if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                            }
                            subjectlist.addAll(list);
                        }
                        subject_name = "英語科研究";
                        target = "情報教育選修";
                        list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                        if(list!=null){
                            iterator = list.iterator();
                            //既に修得済みの科目を除外する処理
                            while(iterator.hasNext()){
                                q = iterator.next();
                                for(p=0;p<credits.size();p=p+1){
                                    if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                        if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                            }
                            subjectlist.addAll(list);
                        }
                    }
                    if(sp_ii>0){
                        subject_name = "音楽科研究Ⅱ";
                        target = "情報教育選修";
                        list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                        if(list!=null){
                            iterator = list.iterator();
                            //既に修得済みの科目を除外する処理
                            while(iterator.hasNext()){
                                q = iterator.next();
                                for(p=0;p<credits.size();p=p+1){
                                    if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                        if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                            }
                            subjectlist.addAll(list);
                        }
                        subject_name = "図画工作科研究Ⅱ";
                        target = "情報教育選修";
                        list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                        if(list!=null){
                            iterator = list.iterator();
                            //既に修得済みの科目を除外する処理
                            while(iterator.hasNext()){
                                q = iterator.next();
                                for(p=0;p<credits.size();p=p+1){
                                    if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                        if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                            }
                            subjectlist.addAll(list);
                        }
                        subject_name = "体育科研究Ⅱ";
                        target = "情報教育選修";
                        list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                        if(list!=null){
                            iterator = list.iterator();
                            //既に修得済みの科目を除外する処理
                            while(iterator.hasNext()){
                                q = iterator.next();
                                for(p=0;p<credits.size();p=p+1){
                                    if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                        if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                            }
                            subjectlist.addAll(list);
                        }
                    }
                    if(major_se>0){
                        target = "情報教育選修教科・教職（専攻ＳＥ";
                        list = syllabusmanager.searchSyllabusChoice(target,grade);
                        if(list!=null){
                            iterator = list.iterator();
                            //既に修得済みの科目を除外する処理
                            while(iterator.hasNext()){
                                q = iterator.next();
                                for(p=0;p<credits.size();p=p+1){
                                    if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                        if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                            }
                            subjectlist.addAll(list);
                        }
                    }
                }
                if(sa_sb>0){
                    target = "情報教育選修選択科目";
                    list = syllabusmanager.searchSyllabusChoice(target,grade);
                    if(list!=null){
                        iterator = list.iterator();
                        //既に修得済みの科目を除外する処理
                        while(iterator.hasNext()){
                            q = iterator.next();
                            for(p=0;p<credits.size();p=p+1){
                                if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                    if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                        iterator.remove();
                                        break;
                                    }
                                }
                            }
                        }
                        subjectlist.addAll(list);
                    }
                }
                if(want_sublicense!=null){
                    for(int o=0;o<want_sublicense.length;o=o+1){
                        if(want_sublicense[o].equals("1")){
                            //数学（必修）用の科目
                            sublicense = "数学（必修）";
                            list = syllabusmanager.searchSyllabusSublicense(sublicense,grade);
                            if(list!=null){
                                iterator = list.iterator();
                                //既に修得済みの科目を除外する処理
                                while(iterator.hasNext()){
                                    q = iterator.next();
                                    for(p=0;p<credits.size();p=p+1){
                                        if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                            if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                                iterator.remove();
                                                break;
                                            }
                                        }
                                    }
                                }
                                subjectlist.addAll(list);
                            }
                            if(math_choice>0){
                                //数学（選択）用の科目
                                sublicense = "数学（選択）";
                                list = syllabusmanager.searchSyllabusSublicense(sublicense,grade);
                                if(list!=null){
                                    iterator = list.iterator();
                                    //既に修得済みの科目を除外する処理
                                    while(iterator.hasNext()){
                                        q = iterator.next();
                                        for(p=0;p<credits.size();p=p+1){
                                            if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                                iterator.remove();
                                                break;
                                            }
                                        }
                                    }
                                    subjectlist.addAll(list);
                                }
                            }
                        }
                        if(want_sublicense[o].equals("2")){
                            //特支一種3領域（必修）用の科目
                            sublicense = "特支一種3領域（必修）";
                            list = syllabusmanager.searchSyllabusSublicense(sublicense,grade);
                            if(list!=null){
                                //既に修得済みの科目を除外する処理
                                for(iterator = list.iterator(); iterator.hasNext(); ){
                                    q = iterator.next();
                                    for(p=0;p<credits.size();p=p+1){
                                        if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                            if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                                iterator.remove();
                                                break;
                                            }
                                        }
                                    }
                                }
                                //subjectlistに重複がないかを検査する処理
                                for(iterator = list.iterator(); iterator.hasNext(); ){
                                    q = iterator.next();
                                    for(p=0;p<subjectlist.size();p=p+1){
                                        if(q.getSubjectName().equals(subjectlist.get(p).getSubjectName())){
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                                if(list!=null){
                                    subjectlist.addAll(list);
                                }
                            }
                            if(special1_3language>0){
                                //特支一種3領域（言語障害選択）用の科目
                                sublicense = "特支一種3領域（言語障害選択）";
                                list = syllabusmanager.searchSyllabusSublicense(sublicense,grade);
                                if(list!=null){
                                    //既に修得済みの科目を除外する処理
                                    for(iterator = list.iterator(); iterator.hasNext(); ){
                                        q = iterator.next();
                                        for(p=0;p<credits.size();p=p+1){
                                            if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                                if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                                    iterator.remove();
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    //subjectlistに重複がないかを検査する処理
                                    for(iterator = list.iterator(); iterator.hasNext(); ){
                                        q = iterator.next();
                                        for(p=0;p<subjectlist.size();p=p+1){
                                            if(q.getSubjectName().equals(subjectlist.get(p).getSubjectName())){
                                                iterator.remove();
                                                break;
                                            }
                                        }
                                    }
                                    if(list!=null){
                                        subjectlist.addAll(list);
                                    }
                                }
                            }
                            if(special1_3learning>0){
                                //特支一種3領域（学習障害選択）用の科目
                                sublicense = "特支一種3領域（学習障害選択）";
                                list = syllabusmanager.searchSyllabusSublicense(sublicense,grade);
                                if(list!=null){
                                    //既に修得済みの科目を除外する処理
                                    for(iterator = list.iterator(); iterator.hasNext(); ){
                                        q = iterator.next();
                                        for(p=0;p<credits.size();p=p+1){
                                            if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                                if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                                    iterator.remove();
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    //subjectlistに重複がないかを検査する処理
                                    for(iterator = list.iterator(); iterator.hasNext(); ){
                                        q = iterator.next();
                                        for(p=0;p<subjectlist.size();p=p+1){
                                            if(q.getSubjectName().equals(subjectlist.get(p).getSubjectName())){
                                                iterator.remove();
                                                break;
                                            }
                                        }
                                    }
                                    if(list!=null){
                                        subjectlist.addAll(list);
                                    }
                                }
                            }
                        }
                        if(want_sublicense[o].equals("3")){
                            //特支二種3領域（必修）用の科目
                            sublicense = "特支二種3領域（必修）";
                            list = syllabusmanager.searchSyllabusSublicense(sublicense,grade);
                            if(list!=null){
                                //既に修得済みの科目を除外する処理
                                for(iterator = list.iterator(); iterator.hasNext(); ){
                                    q = iterator.next();
                                    for(p=0;p<credits.size();p=p+1){
                                        if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                            if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                                iterator.remove();
                                                break;
                                            }
                                        }
                                    }
                                }
                                //subjectlistに重複がないかを検査する処理
                                for(iterator = list.iterator(); iterator.hasNext(); ){
                                    q = iterator.next();
                                    for(p=0;p<subjectlist.size();p=p+1){
                                        if(q.getSubjectName().equals(subjectlist.get(p).getSubjectName())){
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                                if(list!=null){
                                    subjectlist.addAll(list);
                                }
                            }
                        }
                        if(want_sublicense[o].equals("4")){
                            //特支一種2領域（必修）用の科目
                            sublicense= "特支一種2領域（必修）";
                            list = syllabusmanager.searchSyllabusSublicense(sublicense,grade);
                            if(list!=null){
                                //既に修得済みの科目を除外する処理
                                for(iterator = list.iterator(); iterator.hasNext(); ){
                                    q = iterator.next();
                                    for(p=0;p<credits.size();p=p+1){
                                        if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                            if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                                iterator.remove();
                                                break;
                                            }
                                        }
                                    }
                                }
                                //subjectlistに重複がないかを検査する処理
                                for(iterator = list.iterator(); iterator.hasNext(); ){
                                    q = iterator.next();
                                    for(p=0;p<subjectlist.size();p=p+1){
                                        if(q.getSubjectName().equals(subjectlist.get(p).getSubjectName())){
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                                if(list!=null){
                                    subjectlist.addAll(list);
                                }
                            }
                            if(special1_2language>0){
                                //特支一種2領域（言語障害選択）用の科目
                                sublicense = "特支一種2領域（言語障害選択）";
                                list = syllabusmanager.searchSyllabusSublicense(sublicense,grade);
                                if(list!=null){
                                    //既に修得済みの科目を除外する処理
                                    for(iterator = list.iterator(); iterator.hasNext(); ){
                                        q = iterator.next();
                                        for(p=0;p<credits.size();p=p+1){
                                            if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                                if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                                    iterator.remove();
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    //subjectlistに重複がないかを検査する処理
                                    for(iterator = list.iterator(); iterator.hasNext(); ){
                                        q = iterator.next();
                                        for(p=0;p<subjectlist.size();p=p+1){
                                            if(q.getSubjectName().equals(subjectlist.get(p).getSubjectName())){
                                                iterator.remove();
                                                break;
                                            }
                                        }
                                    }
                                    if(list!=null){
                                        subjectlist.addAll(list);
                                    }
                                }
                            }
                            if(special1_2learning>0){
                                //特支一種2領域（学習障害選択）用の科目
                                sublicense = "特支一種2領域（学習障害選択）";
                                list = syllabusmanager.searchSyllabusSublicense(sublicense,grade);
                                if(list!=null){
                                    //既に修得済みの科目を除外する処理
                                    for(iterator = list.iterator(); iterator.hasNext(); ){
                                        q = iterator.next();
                                        for(p=0;p<credits.size();p=p+1){
                                            if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                                if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                                    iterator.remove();
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    //subjectlistに重複がないかを検査する処理
                                    for(iterator = list.iterator(); iterator.hasNext(); ){
                                        q = iterator.next();
                                        for(p=0;p<subjectlist.size();p=p+1){
                                            if(q.getSubjectName().equals(subjectlist.get(p).getSubjectName())){
                                                iterator.remove();
                                                break;
                                            }
                                        }
                                    }
                                    if(list!=null){
                                        subjectlist.addAll(list);
                                    }
                                }
                            }
                        }
                        if(want_sublicense[o].equals("5")){
                            //特支二種3領域（必修）用の科目
                            sublicense = "特支二種3領域（必修）";
                            list = syllabusmanager.searchSyllabusSublicense(sublicense,grade);
                            if(list!=null){
                                //既に修得済みの科目を除外する処理
                                for(iterator = list.iterator(); iterator.hasNext(); ){
                                    q = iterator.next();
                                    for(p=0;p<credits.size();p=p+1){
                                        if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                            if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                                iterator.remove();
                                                break;
                                            }
                                        }
                                    }
                                }
                                //subjectlistに重複がないかを検査する処理
                                for(iterator = list.iterator(); iterator.hasNext(); ){
                                    q = iterator.next();
                                    for(p=0;p<subjectlist.size();p=p+1){
                                        if(q.getSubjectName().equals(subjectlist.get(p).getSubjectName())){
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                                if(list!=null){
                                    subjectlist.addAll(list);
                                }
                            }
                        }
                    }
                }
                for(int x=0;x<subjectlist.size();x=x+1){
                    System.out.println(subjectlist.get(x).getSubjectName());
                }
                request.setAttribute("subjectlist",subjectlist);
                request.setAttribute("errorMessage",errorMessage);
                request.setAttribute("credit_list_id",credit_list_id);
                request.setAttribute("created_at",created_at);
                request.setAttribute("sublicense",want_sublicense);
                request.setAttribute("department",department);
                request.setAttribute("ca_a",ca_a);
                request.setAttribute("ca_b",ca_b);
                request.setAttribute("ca_c",ca_c);
                request.setAttribute("ca_abc",ca_abc);
                request.setAttribute("se",se);
                request.setAttribute("sp_choice",sp_choice);
                request.setAttribute("sp_ii",sp_ii);
                request.setAttribute("major_se",major_se);
                request.setAttribute("sp",sp);
                request.setAttribute("sa_sb",sa_sb);
                request.setAttribute("choice",choice);
                request.setAttribute("teaching",teaching);
                request.setAttribute("e_sa_sb",e_sa_sb);
                request.setAttribute("e_choice",e_choice);
                request.setAttribute("math_choice",math_choice);
                request.setAttribute("special1_3learning",special1_3learning);
                request.setAttribute("special1_3language",special1_3language);
                request.setAttribute("special1_2learning",special1_2learning);
                request.setAttribute("special1_2language",special1_2language);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/showSubjects.jsp");
                dispatcher.forward(request, response);
            }
            //E類用の判定
            if(department==2){
                for(int n=0;n<credits.size();n=n+1){
                    //必修の授業科目に当てはまるかを検査
                    if(credits.get(n).getSubjectName().equals("日本国憲法")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ca_constitution = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("情報")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ca_information = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("人権教育")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ca_liberty = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("スポーツ・フィットネス実習")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ch_sports = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("ウェルネス概論")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ch_wellness = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("英語コミュニケーションＡ")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            cl_a = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("英語コミュニケーションＢ")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            cl_b = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().contains("語基礎Ⅰ")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            cl_i = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().contains("語基礎Ⅱ")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            cl_ii = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().contains("語基礎Ⅲ")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            cl_iii = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().contains("語基礎Ⅳ")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            cl_iv = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("教育基礎論")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ew_teaching = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("教育組織論")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ef_organization = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("教育と発達の心理学")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ef_psychology = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("特別支援教育入門")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            ef_special = 0;
                        }
                    }
                    else if(credits.get(n).getSubjectName().equals("卒業研究")){
                        if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                            sz = 0;
                        }
                    }
                    else{
                        //必修の授業に当てはまらない→選択科目であるから，どの選択科目かを調査
                        //選択科目は基準を超過すると自由単位となる
                        SyllabusManager syllabusmanager = new SyllabusManager();
                        Syllabus syllabus = syllabusmanager.searchSyllabus(credits.get(n).getSubjectName());
                        //ＣＡはすべて2単位
                        //ＣＡ：（Ａ）領域
                        if(syllabus==null){
                            System.out.println(credits.get(n).getSubjectName()+"はシラバスに登録されていません");
                        }
                        else if(syllabus.getRemark().equals("ＣＡ：（Ａ）領域")){
                            if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                                ca_a = ca_a-Integer.parseInt(credits.get(n).getCredit());
                                if(ca_abc>0){
                                    ca_abc = ca_abc-Integer.parseInt(credits.get(n).getCredit());
                                }
                                else{
                                    e_choice = e_choice-Integer.parseInt(credits.get(n).getCredit());
                                }
                            }
                        }
                        //ＣＡ：（Ｂ）領域
                        else if(syllabus.getRemark().equals("ＣＡ：（Ｂ）領域")){
                            if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                                ca_b = ca_b-Integer.parseInt(credits.get(n).getCredit());
                                if(ca_abc>0){
                                    ca_abc = ca_abc-Integer.parseInt(credits.get(n).getCredit());
                                }
                                else{
                                    e_choice = e_choice-Integer.parseInt(credits.get(n).getCredit());
                                }
                            }
                        }
                        //ＣＡ：（Ｃ）領域
                        else if(syllabus.getRemark().equals("ＣＡ：（Ｃ）領域")){
                            if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                                ca_c = ca_c-Integer.parseInt(credits.get(n).getCredit());
                                if(ca_abc>0){
                                    ca_abc = ca_abc-Integer.parseInt(credits.get(n).getCredit());
                                }
                                else{
                                    e_choice = e_choice-Integer.parseInt(credits.get(n).getCredit());
                                }
                            }
                        }
                        //ＣＡ：その他など
                        else if(syllabus.getTarget().equals("総合学芸領域：選択共通")){
                            if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                                if(ca_abc>0){
                                    ca_abc = ca_abc-Integer.parseInt(credits.get(n).getCredit());
                                }
                                else{
                                    e_choice = e_choice-Integer.parseInt(credits.get(n).getCredit());
                                }
                            }
                        }
                        //E類教科・教職
                        else if(syllabus.getTarget().contains("情報教育コース教科・教職")){
                            if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                                if(teaching>0){
                                    teaching = teaching-Integer.parseInt(credits.get(n).getCredit());
                                }
                                else{
                                    e_choice = e_choice-Integer.parseInt(credits.get(n).getCredit());
                                }
                            }
                        }
                        //E類課程共通科目
                        else if(syllabus.getTarget().contains("情報教育コース課程共通科目")){
                            if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                                e_ss = e_ss-Integer.parseInt(credits.get(n).getCredit());
                            }
                        }
                        //E類必修科目
                        else if(syllabus.getTarget().contains("情報教育コース必修科目")){
                            if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                                e_ss = e_ss-Integer.parseInt(credits.get(n).getCredit());
                            }
                        }
                        //Ｅ類選択科目Ａ，Ｂ
                        //1単位あり（演習系）
                        else if(syllabus.getTarget().contains("情報教育コース選択科目")){
                            if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                                if(syllabus.getSubjectName().equals("情報教育支援フィールドワーク演習") || syllabus.getSubjectName().equals("教育支援コラボレーション演習")){
                                    field_collaboration = 0;
                                }
                                if(e_sa_sb>0){
                                    e_sa_sb = e_sa_sb-Integer.parseInt(credits.get(n).getCredit());
                                    //1-2=-1のパターン
                                    if(e_sa_sb==-1){
                                        e_choice = e_choice-1;
                                    }
                                }
                                else{
                                    e_choice = e_choice-Integer.parseInt(credits.get(n).getCredit());
                                }
                            }
                        }
                        else{
                            if(!credits.get(n).getEvaluation().equals("F") && !credits.get(n).getEvaluation().equals("否") && !credits.get(n).getEvaluation().equals("失")){
                                e_choice = e_choice-Integer.parseInt(credits.get(n).getCredit());
                            }
                        }
                    }
                }
                /*コンソールに確認のため出力
                System.out.println("日本国憲法:"+ca_constitution);//日本国憲法（必修）
                System.out.println("情報:"+ca_information);//情報（必修）
                System.out.println("人権教育:"+ca_liberty);//人権教育（必修）
                System.out.println("CA(A):"+ca_a);//CA(A)領域（選択必修）〇
                System.out.println("CA(B):"+ca_b);//CA(B)領域（選択必修）〇
                System.out.println("CA(C):"+ca_c);//CA(C)領域（選択必修）〇
                System.out.println("CA:"+ca_abc);//CAはA,B,Cをそれぞれ2単位以上，合計8単位 〇
                System.out.println("スポーツ・フィットネス実習:"+ch_sports);//スポーツ・フィットネス実習（必修）
                System.out.println("ウェルネス概論:"+ch_wellness);//ウェルネス概論（必修）
                System.out.println("英語コミュニケーションA:"+cl_a);//英語コミュニケーションA（必修）
                System.out.println("英語コミュニケーションB:"+cl_b);//英語コミュニケーションB（必修）
                System.out.println("初習語学I:"+cl_i);//初習語学I（必修）
                System.out.println("初習語学II:"+cl_ii);//初習語学II（必修）
                System.out.println("初習語学III:"+cl_iii);//初習語学III（必修）
                System.out.println("初習語学IV:"+cl_iv);//初習語学IV（必修）
                System.out.println("教職入門:"+ew_teaching);//教職入門（必修）
                System.out.println("教育の理念と歴史:"+ef_history);//教育の理念と歴史（必修）
                System.out.println("教育組織論:"+ef_organization);//教育組織論（必修）
                System.out.println("教育心理学:"+ef_psychology);//教育心理学（必修）
                System.out.println("特別支援教育の理解:"+ef_special);//特別支援教育の理解（必修）
                System.out.println("道徳教育の指導法:"+et_moral);//道徳教育の指導法（必修）
                System.out.println("特別活動・総合的な学習の時間の指導法:"+et_comprehensive);//特別活動・総合的な学習の時間の指導法（必修）
                System.out.println("初等教育の内容と方法:"+et_elementary);//初等教育の内容と方法（必修）
                System.out.println("初等国語科教育法:"+et_japanese);//初等国語科教育法（必修）
                System.out.println("初等社会科教育法:"+et_social);//初等社会科教育法（必修）
                System.out.println("初等算数科教育法:"+et_math);//初等算数科教育法（必修）
                System.out.println("初等理科教育法:"+et_science);//初等理科教育法（必修）
                System.out.println("初等音楽科教育法:"+et_music);//初等音楽科教育法（必修）
                System.out.println("初等図画工作科教育法:"+et_art);//初等図画工作科教育法（必修）
                System.out.println("初等体育科教育法:"+et_pe);//初等体育科教育法（必修）
                System.out.println("初等家庭科教育法:"+et_home);//初等家庭科教育法（必修）
                System.out.println("初等英語科教育法:"+et_english);//初等英語科教育法（必修）
                System.out.println("初等生活科教育法:"+et_life);//初等生活科教育法（必修）
                System.out.println("教育相談の理論と方法:"+ec_counselling);//教育相談の理論と方法（必修）
                System.out.println("生徒指導・進路指導の理論と方法:"+ec_guidance);//生徒指導・進路指導の理論と方法（必修）
                System.out.println("教職実践演習:"+ee_practice);//教職実践演習（必修）
                System.out.println("事前・事後の指導:"+ep_advance);//事前・事後の指導（必修）
                System.out.println("教育実地研究I:"+ep_practice);//教育実地研究I（必修）
                System.out.println("共通SE:"+se);//共通SE（選択必修）〇
                System.out.println("国語，社会，生活，家庭，英語科研究:"+sp_choice);//国語，社会，生活，家庭，英語科研究（選択必修）〇
                System.out.println("算数科研究:"+sp_math);//算数科研究（必修）
                System.out.println("理科研究:"+sp_science);//理科研究（必修）
                System.out.println("音楽科研究I:"+sp_music);//音楽科研究I（必修）
                System.out.println("図画工作科研究I:"+sp_art);//図画工作科研究I（必修）
                System.out.println("体育科研究I:"+sp_pe);//体育科研究I（必修）
                System.out.println("音楽，図画工作，体育科研究II:"+sp_ii);//音楽，図画工作，体育科研究II（選択必修）〇
                System.out.println("専攻SE:"+major_se);//専攻SE（選択必修）〇
                System.out.println("SE系の必修以外の残数:"+sp);//共通SE，研究，専攻SEの必修以外の残数 〇
                System.out.println("情報教育入門セミナー:"+s_seminar);//情報教育選修入門セミナー（必修）
                System.out.println("教育と情報:"+s_information);//教育と情報（必修）
                System.out.println("授業におけるICT活用:"+s_ict);//授業におけるICT活用（必修）
                System.out.println("情報教育概論:"+s_education);//情報教育概論（必修）
                System.out.println("情報数学基礎:"+s_math);//情報数学基礎（必修）
                System.out.println("選択科目A,B:"+sa_sb);//選択科目A,B（選択必修）〇
                System.out.println("卒業研究:"+sz);//卒業研究（必修）
                System.out.println("自由選択:"+choice);//自由選択 〇
                */

                //残単位数が0でないものは履修可能な授業を検索
                SyllabusManager syllabusmanager = new SyllabusManager();
                List<Syllabus> subjectlist = new ArrayList<>();
                List<Syllabus> list = new ArrayList<>();
                String subject_name;
                String target;
                String sublicense;
                String remark;
                if(ca_constitution>0){
                    subject_name ="日本国憲法";
                    remark = "情報";
                    list = syllabusmanager.searchSyllabusRemark(subject_name,remark,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(ca_information>0){
                    subject_name ="情報";
                    remark = "情報";
                    list = syllabusmanager.searchSyllabusRemark(subject_name,remark,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(ca_liberty>0){
                    subject_name ="人権教育";
                    remark = "情報";
                    list = syllabusmanager.searchSyllabusRemark(subject_name,remark,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(ch_sports>0){
                    subject_name ="スポーツ・フィットネス実習";
                    remark = "情報";
                    list = syllabusmanager.searchSyllabusRemark(subject_name,remark,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(ch_wellness>0){
                    subject_name ="ウェルネス概論";
                    target = "情報教育コース";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(cl_a>0){
                    subject_name ="英語コミュニケーションＡ";
                    target = "情報教育コース";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(cl_b>0){
                    subject_name ="英語コミュニケーションＢ";
                    target = "情報教育コース";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(cl_i>0){
                    subject_name ="語基礎Ⅰ";
                    target = "情報教育コース";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(cl_ii>0){
                    subject_name ="語基礎Ⅱ";
                    target = "情報教育コース";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(cl_iii>0){
                    subject_name ="語基礎Ⅲ";
                    target = "情報教育コース";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(cl_iv>0){
                    subject_name ="語基礎Ⅳ";
                    target = "情報教育コース";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(ew_teaching>0){
                    subject_name ="教育基礎論";
                    target = "情報教育コース";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(ef_organization>0){
                    subject_name ="教育組織論";
                    target = "情報教育コース";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(ef_psychology>0){
                    subject_name ="教育と発達の心理学";
                    target = "情報教育コース";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                if(ef_special>0){
                    subject_name ="特別支援教育入門";
                    target = "情報教育コース";
                    list = syllabusmanager.searchSyllabusCompulsory(subject_name,target,grade);
                    if(list!=null){
                        subjectlist.addAll(list);
                    }
                }
                //選択科目は，当てはまる科目を全て検索する
                //既に修得済みの科目は除外する
                //p,qはその処理を実現するための変数である．
                int p;
                Syllabus q;
                Iterator<Syllabus> iterator;
                //CA
                if(ca_abc>0){
                    if(ca_abc-ca_a-ca_b-ca_c==2){
                        target = "分野Ａ共通";
                        list = syllabusmanager.searchSyllabusChoice(target,grade);
                        if(list!=null){
                            iterator = list.iterator();
                            //既に修得済みの科目を除外する処理
                            while(iterator.hasNext()){
                                q = iterator.next();
                                for(p=0;p<credits.size();p=p+1){
                                    if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                        if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                            }
                            subjectlist.addAll(list);
                        }
                        target = "分野Ｂ共通";
                        list = syllabusmanager.searchSyllabusChoice(target,grade);
                        if(list!=null){
                            iterator = list.iterator();
                            //既に修得済みの科目を除外する処理
                            while(iterator.hasNext()){
                                q = iterator.next();
                                for(p=0;p<credits.size();p=p+1){
                                    if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                        if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                            }
                            subjectlist.addAll(list);
                        }
                        target = "分野Ｃ共通";
                        list = syllabusmanager.searchSyllabusChoice(target,grade);
                        if(list!=null){
                            iterator = list.iterator();
                            //既に修得済みの科目を除外する処理
                            while(iterator.hasNext()){
                                q = iterator.next();
                                for(p=0;p<credits.size();p=p+1){
                                    if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                        if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                            }
                            subjectlist.addAll(list);
                        }
                        target = "総合学芸領域：選択共通";
                        list = syllabusmanager.searchSyllabusChoice(target,grade);
                        if(list!=null){
                            iterator = list.iterator();
                            //既に修得済みの科目を除外する処理
                            while(iterator.hasNext()){
                                q = iterator.next();
                                for(p=0;p<credits.size();p=p+1){
                                    if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                        if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                }
                            }
                            subjectlist.addAll(list);
                        }
                    }
                    else{
                        if(ca_a>0){
                            target = "分野Ａ共通";
                            list = syllabusmanager.searchSyllabusChoice(target,grade);
                            if(list!=null){
                                iterator = list.iterator();
                                //既に修得済みの科目を除外する処理
                                while(iterator.hasNext()){
                                    q = iterator.next();
                                    for(p=0;p<credits.size();p=p+1){
                                        if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                            if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                                iterator.remove();
                                                break;
                                            }
                                        }
                                    }
                                }
                                subjectlist.addAll(list);
                            }
                        }
                        if(ca_b>0){
                            target = "分野Ｂ共通";
                            list = syllabusmanager.searchSyllabusChoice(target,grade);
                            if(list!=null){
                                iterator = list.iterator();
                                //既に修得済みの科目を除外する処理
                                while(iterator.hasNext()){
                                    q = iterator.next();
                                    for(p=0;p<credits.size();p=p+1){
                                        if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                            if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                                iterator.remove();
                                                break;
                                            }
                                        }
                                    }
                                }
                                subjectlist.addAll(list);
                            }
                        }
                        if(ca_c>0){
                            target = "分野Ｃ共通";
                            list = syllabusmanager.searchSyllabusChoice(target,grade);
                            if(list!=null){
                                iterator = list.iterator();
                                //既に修得済みの科目を除外する処理
                                while(iterator.hasNext()){
                                    q = iterator.next();
                                    for(p=0;p<credits.size();p=p+1){
                                        if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                            if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                                iterator.remove();
                                                break;
                                            }
                                        }
                                    }
                                }
                                subjectlist.addAll(list);
                            }
                        }
                    }
                }
                //E類教科・教職
                if(teaching>0){
                    target = "情報教育コース教科・教職";
                    list = syllabusmanager.searchSyllabusChoice(target,grade);
                    if(list!=null){
                        iterator = list.iterator();
                        //既に修得済みの科目を除外する処理
                        while(iterator.hasNext()){
                            q = iterator.next();
                            for(p=0;p<credits.size();p=p+1){
                                if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                    if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                        iterator.remove();
                                        break;
                                    }
                                }
                            }
                        }
                        subjectlist.addAll(list);
                    }
                }
                //E類課程共通科目
                if(e_ss>0){
                    target = "情報教育コース課程共通科目";
                    list = syllabusmanager.searchSyllabusChoice(target,grade);
                    if(list!=null){
                        iterator = list.iterator();
                        //既に修得済みの科目を除外する処理
                        while(iterator.hasNext()){
                            q = iterator.next();
                            for(p=0;p<credits.size();p=p+1){
                                if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                    if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                        iterator.remove();
                                        break;
                                    }
                                }
                            }
                        }
                        subjectlist.addAll(list);
                    }
                }
                //E類必修科目
                if(e_s>0){
                    target = "情報教育コース必修科目";
                    list = syllabusmanager.searchSyllabusChoice(target,grade);
                    if(list!=null){
                        iterator = list.iterator();
                        //既に修得済みの科目を除外する処理
                        while(iterator.hasNext()){
                            q = iterator.next();
                            for(p=0;p<credits.size();p=p+1){
                                if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                    if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                        iterator.remove();
                                        break;
                                    }
                                }
                            }
                        }
                        subjectlist.addAll(list);
                    }
                }
                //E類選択科目A,B
                if(e_sa_sb>0){
                    target = "情報教育コース選択科目";
                    list = syllabusmanager.searchSyllabusChoice(target,grade);
                    if(list!=null){
                        iterator = list.iterator();
                        //既に修得済みの科目を除外する処理
                        while(iterator.hasNext()){
                            q = iterator.next();
                            for(p=0;p<credits.size();p=p+1){
                                if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                    if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                        iterator.remove();
                                        break;
                                    }
                                }
                            }
                        }
                        subjectlist.addAll(list);
                    }
                }
                //高校（情報）用の科目
                sublicense = "高校（情報）";
                list = syllabusmanager.searchSyllabusSublicense(sublicense,grade);
                if(list!=null){
                    iterator = list.iterator();
                    //既に修得済みの科目を除外する処理
                    while(iterator.hasNext()){
                        q = iterator.next();
                        for(p=0;p<credits.size();p=p+1){
                            if(q.getSubjectName().equals(credits.get(p).getSubjectName())){
                                if(!credits.get(p).getEvaluation().equals("F") && !credits.get(p).getEvaluation().equals("否") && !credits.get(p).getEvaluation().equals("失")){
                                    iterator.remove();
                                    break;
                                }
                            }
                        }
                    }
                    subjectlist.addAll(list);
                }
                for(int x=0;x<subjectlist.size();x=x+1){
                    System.out.println(subjectlist.get(x).getSubjectName());
                }
                request.setAttribute("subjectlist",subjectlist);
                request.setAttribute("errorMessage",errorMessage);
                request.setAttribute("credit_list_id",credit_list_id);
                request.setAttribute("created_at",created_at);
                request.setAttribute("sublicense",want_sublicense);
                request.setAttribute("department",department);
                request.setAttribute("ca_a",ca_a);
                request.setAttribute("ca_b",ca_b);
                request.setAttribute("ca_c",ca_c);
                request.setAttribute("ca_abc",ca_abc);
                request.setAttribute("se",se);
                request.setAttribute("sp_choice",sp_choice);
                request.setAttribute("sp_ii",sp_ii);
                request.setAttribute("major_se",major_se);
                request.setAttribute("sp",sp);
                request.setAttribute("sa_sb",sa_sb);
                request.setAttribute("choice",choice);
                request.setAttribute("teaching",teaching);
                request.setAttribute("e_sa_sb",e_sa_sb);
                request.setAttribute("e_choice",e_choice);
                request.setAttribute("math_choice",math_choice);
                request.setAttribute("special1_3learning",special1_3learning);
                request.setAttribute("special1_3language",special1_3language);
                request.setAttribute("special1_2learning",special1_2learning);
                request.setAttribute("special1_2language",special1_2language);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/showSubjects.jsp");
                dispatcher.forward(request, response);
            }
        }
    }
  }
}