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
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
@WebServlet(name = "VerifyGoodIdServlet")
public class VerifyGoodIdServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String id=request.getParameter("good_id");
        int good_id=Integer.valueOf(id);
        MySqlConnect connect=new MySqlConnect();
        Connection conn=connect.getConn();
        try
        {
            String sql="SELECT * FROM good WHERE good_id=?";
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setInt(1,good_id);
            ResultSet resultSet=preparedStatement.executeQuery();
            JsonObject jsonObject=new JsonObject();
            while(resultSet.next())//有结果表示 id存在 返回0
            {

                jsonObject.addProperty("good_id", resultSet.getInt("good_id"));
                jsonObject.addProperty("good_price", resultSet.getInt("good_price"));
                jsonObject.addProperty("good_quan", resultSet.getInt("good_quan"));
                jsonObject.addProperty("good_name", resultSet.getString("good_name"));
                jsonObject.addProperty("good_spec", resultSet.getString("good_spec"));
                jsonObject.addProperty("good_url", resultSet.getString("good_url"));
                response.getWriter().print(jsonObject.toString());
                conn.close();
                return;
            }//无此ID 返回1
            jsonObject.addProperty("good_id", -1);
            jsonObject.addProperty("good_price", -1);
            jsonObject.addProperty("good_quan", -1);
            jsonObject.addProperty("good_name", "null");
            jsonObject.addProperty("good_spec","null");
            jsonObject.addProperty("good_url", "null");
            response.getWriter().print(jsonObject.toString());
            conn.close();



        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
