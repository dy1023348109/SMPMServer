package servlet;

import com.google.gson.JsonObject;
import tool.MySqlConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Administrator on 2017/3/8 0008.
 */
@WebServlet(name = "RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        MySqlConnect connect=new MySqlConnect();
        Connection conn=connect.getConn();
        JsonObject jsonObject=new JsonObject();
        try{
            String sql="INSERT INTO user VALUES('"+username+"','"+password+"','3')";
            Statement statement=conn.createStatement();
            statement.executeUpdate(sql);
            jsonObject.addProperty("status",10);//成功
            response.getWriter().print(jsonObject.toString());
        }
        catch (Exception e)
        {
            jsonObject.addProperty("status",11);//失败
            response.getWriter().print(jsonObject.toString());

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doPost(request,response);
    }
}
