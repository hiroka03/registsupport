  
package utility;

import beans.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class SessionChecker {

    public boolean isNotUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String user_id = (String) session.getAttribute("user_id");
        if(user_id==null){
            return true;
        }
        return false;
    }
}