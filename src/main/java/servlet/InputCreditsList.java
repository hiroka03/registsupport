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
import beans.Credit;
import control.CreditManager;
import utility.SessionChecker;

//ファイルを扱うためのライブラリ
import javax.servlet.http.Part;
import javax.servlet.annotation.MultipartConfig;
import java.awt.geom.Rectangle2D;
import java.io.File;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripperByArea;

//アノテーションの記述
//jspで示してあげると、jspから呼び出さられる
@WebServlet("/InputCreditsList")
//ファイルを扱うために必要
@MultipartConfig

// HttpServletを継承することで、このクラスはServletとして、働くことができる
public class InputCreditsList extends HttpServlet {
  private static final long serialVersionUID = 1L;

  // doPostメソッドから呼び出される(リダイレクトされる)
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    SessionChecker sessionChecker = new SessionChecker();

    if(sessionChecker.isNotUser(request, response)){
      RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
      dispatcher.forward(request, response);
    }
    else{
      String errorMessage = "null";
      request.setAttribute("errorMessage",errorMessage);
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/inputCreditsList.jsp");
      dispatcher.forward(request, response);
    }
  }
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    // requestオブジェクトの文字エンコーディングの設定
    request.setCharacterEncoding("UTF-8");
    SessionChecker sessionChecker = new SessionChecker();

    if(sessionChecker.isNotUser(request, response)){
      RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
      dispatcher.forward(request, response);
    }
    else{
      HttpSession session = request.getSession();
      String user_id = (String)session.getAttribute("user_id");
      Part part = request.getPart("file");
      part.write(getServletContext().getRealPath("/WEB-INF/pdf") + "/" + user_id + ".pdf");
      File file = new File(getServletContext().getRealPath("/WEB-INF/pdf") + "/" + user_id + ".pdf");

      try {
        PDDocument try_document = Loader.loadPDF(file);
      } catch (IOException e) {
        file.delete();
        request.setAttribute("errorMessage","大学指定の成績通知書をアップロードしてください");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/inputCreditsList.jsp");
        dispatcher.forward(request, response);
      }

      PDDocument document = Loader.loadPDF(file);

      int result_page = 1;
      //左上の座標を基準として読み込む範囲を矩形で定義する(単位はpt)
      double result_x = 105.96/25.4*72;//左上のx座標
      double result_y = 10.37/25.4*72;//左上のy座標
      double result_w = 77.59/25.4*72;//矩形の横幅
      double result_h = 8.33/25.4*72;//矩形の縦幅
      Rectangle2D result_area = new Rectangle2D.Double(result_x, result_y, result_w, result_h);

      PDFTextStripperByArea result_stripper = new PDFTextStripperByArea();
      //抽出対象の範囲を指定する（名前は任意）
      result_stripper.addRegion("list", result_area);
      //抽出対象のページから範囲ごとにテキストを抽出する（getPageに渡すpageIndexは0〜）
      result_stripper.extractRegions(document.getPage(result_page - 1));
      //抽出結果を取得する
      String result_text = result_stripper.getTextForRegion("list");
      if(!result_text.contains("成　 績　 通　 知　 書")){
        file.delete();
        request.setAttribute("errorMessage","大学指定の成績通知書をアップロードしてください");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/inputCreditsList.jsp");
        dispatcher.forward(request, response);
      }

      else{
        //加盟店を管理するListを定義する
        List<Credit> creditsList = new ArrayList<Credit>();

        for(int n=1;n<=document.getNumberOfPages();n=n+1){
          //取得する店舗一覧のページ
          int page = n;
          //左上の座標を基準として読み込む範囲を矩形で定義する(単位はpt)
          double x = 10.58/25.4*72;//左上のx座標
          double y = 46.79/25.4*72;//左上のy座標
          double w = 277.50/25.4*72;//矩形の横幅
          double h = 118.24/25.4*72;//矩形の縦幅
          Rectangle2D area = new Rectangle2D.Double(x, y, w, h);

          PDFTextStripperByArea stripper = new PDFTextStripperByArea();
          //抽出対象の範囲を指定する（名前は任意）
          stripper.addRegion("list", area);
          //抽出対象のページから範囲ごとにテキストを抽出する（getPageに渡すpageIndexは0〜）
          stripper.extractRegions(document.getPage(page - 1));
          //抽出結果を取得する
          String text = stripper.getTextForRegion("list");

          //改行で区切り、行ごとに処理する
          for (String line : text.split("\n")) {
            //半角スペースで区切り、要素ごとに処理する
            String[] words = line.split(" ");
            if(words.length!=1) {
              Credit credit = new Credit(words[0],words[1],words[2],words[3], words[4]);
              creditsList.add(credit);
            }
          }
        }
        // CreditManagerオブジェクトの生成
        CreditManager id_manager = new CreditManager();
        CreditManager manager = new CreditManager();

        // credits_listsテーブルに情報を登録
        int department = (int)session.getAttribute("department");
        int credit_list_id = id_manager.registList(user_id);

        // 登録
        int count = manager.registCreditsList(creditsList,credit_list_id);
        //System.out.println(count);
        file.delete();

        //登録した情報を持ってくる
        if(count==1){
          List<String> credit_list = manager.searchCreditsList(user_id);

          if(credit_list!=null){
            // creditsテーブルからcredit_list_idに紐づくcreated_atを検索
            List<Credit> credits = manager.searchCredits(String.valueOf(credit_list_id));
            if(credits!=null){
              request.setAttribute("credit_list_id",String.valueOf(credit_list_id));
              request.setAttribute("created_at",credit_list.get(credit_list.size()-1));
              request.setAttribute("credits",credits);
              request.setAttribute("department",department);
              RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/showCredits.jsp");
              dispatcher.forward(request, response);
            }
          }
        }
      }
    }
  }
}