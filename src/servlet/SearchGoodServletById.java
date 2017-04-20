package servlet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import tool.MySqlConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by Administrator on 2017/4/20 0020.
 */
@WebServlet(name = "SearchGoodServletById")
public class SearchGoodServletById extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String goods_id=new String(request.getParameter("good_id").getBytes("iso-8859-1"),"utf-8");
        int good_id=Integer.valueOf(goods_id);
        System.out.print(goods_id);
        MySqlConnect connect=new MySqlConnect();
        Connection conn=connect.getConn();
        PrintWriter printWriter=response.getWriter();


        try {

                String sql = "SELECT * FROM good WHERE good_id=?";

                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1,good_id);
                ResultSet resultSet =statement.executeQuery() ;

                  JsonObject json=new JsonObject();
                while (resultSet.next())
                {


                    json.addProperty("good_id", resultSet.getInt("good_id"));
                    json.addProperty("good_price", resultSet.getInt("good_price"));
                    json.addProperty("good_quan", resultSet.getInt("good_quan"));
                    json.addProperty("good_name", resultSet.getString("good_name"));
                    json.addProperty("good_spec", resultSet.getString("good_spec"));
                    json.addProperty("good_url", resultSet.getString("good_url"));
                    printWriter.print(json.toString());
                    conn.close();
                    return;
                }
               json.addProperty("good_id", -1);
               json.addProperty("good_price", -1);
               json.addProperty("good_quan", -1);
               json.addProperty("good_name", "null");
               json.addProperty("good_spec","null");
               json.addProperty("good_url", "null");
               printWriter.print(json.toString());
               conn.close();


                conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }




    }
}
