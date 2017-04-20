package servlet;

import com.google.gson.JsonObject;
import tool.MySqlConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by Administrator on 2017/4/13 0013.
 */
@WebServlet(name = "ChangeServlet")
public class ChangeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

     String username=request.getParameter("username");
     String newpassword=request.getParameter("newpassword");
     String newpermission=request.getParameter("newpermission");
        MySqlConnect connect=new MySqlConnect();
        Connection conn=connect.getConn();
        JsonObject jsonObject=new JsonObject();
     if (newpassword.endsWith("empty"))
     {
         try
         {
             String s="UPDATE user SET user_permission='"+newpermission+"' WHERE user_name='"+username+"'";

             String sql=new String(s.getBytes("iso-8859-1"),"utf-8");
             Statement statement=conn.createStatement();
             statement.executeUpdate(sql);
             jsonObject.addProperty("status",4);//成功
             response.getWriter().print(jsonObject.toString());

             conn.close();
         }
         catch (Exception e)
         {
             jsonObject.addProperty("status",-2);//失败
             response.getWriter().print(jsonObject.toString());

         }
     }
     else
     {
         try
         {
             String s="UPDATE user SET user_password='"+newpassword+"' WHERE user_name='"+username+"'";

             String sql=new String(s.getBytes("iso-8859-1"),"utf-8");

             Statement statement=conn.createStatement();
             statement.executeUpdate(sql);
             jsonObject.addProperty("status",4);//成功
             response.getWriter().print(jsonObject.toString());

             conn.close();
         }
         catch (Exception e)
         {
             jsonObject.addProperty("status",-2);//失败
             response.getWriter().print(jsonObject.toString());

         }
     }

    }
}
