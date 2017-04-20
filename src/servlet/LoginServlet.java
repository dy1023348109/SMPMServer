package servlet;
import com.google.gson.JsonObject;
import tool.MySqlConnect;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.http.*;
/**
 * Created by Administrator on 2017/3/7 0007.
 */
public class LoginServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request, response);

    }

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String username=request.getParameter("username");
        String password=request.getParameter("password");

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");


        MySqlConnect connect=new MySqlConnect();
        Connection conn=connect.getConn();
        try {
            String sql="SELECT * FROM user WHERE user_name=?";
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,username);
            JsonObject jsonObject=new JsonObject();
            ResultSet resultSet=preparedStatement.executeQuery();

                while (resultSet.next()) {//这个结果集只有一个元素
                    String s = resultSet.getString("user_password");
                    if (s.equals(password)) {
                       //登陆成功 账号密码正确
                        int type= resultSet.getInt("user_permission");


                        jsonObject.addProperty("status",type);
                        response.getWriter().print(jsonObject.toString());
                        conn.close();
                        return;
                    }
                    else
                    {   //密码不对  0
                        jsonObject.addProperty("status",0);

                        response.getWriter().print(jsonObject.toString());
                        conn.close();
                        return;

                    }
                }
                     //无此用户 返回 -1
                    jsonObject.addProperty("status",-1);
                    response.getWriter().print(jsonObject.toString());

                    conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
