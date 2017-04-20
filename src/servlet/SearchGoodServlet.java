package servlet;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import tool.MySqlConnect;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by Administrator on 2017/3/20 0020.
 */
public class SearchGoodServlet extends HttpServlet
{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String goods_name=new String(request.getParameter("good_name").getBytes("iso-8859-1"),"utf-8");
        System.out.print(goods_name);
        MySqlConnect connect=new MySqlConnect();
        Connection conn=connect.getConn();
        PrintWriter printWriter=response.getWriter();


        try {
            if (!goods_name.equals("empty")) {//参数不是“empty”

                String sql = "SELECT * FROM good WHERE good_name='"+goods_name+"'";
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                JsonObject jsonObject = new JsonObject();
                while (resultSet.next()) {
                    System.out.print(resultSet.getString("good_name"));
                    jsonObject.addProperty("good_id", resultSet.getInt("good_id"));
                    jsonObject.addProperty("good_price", resultSet.getInt("good_price"));
                    jsonObject.addProperty("good_quan", resultSet.getInt("good_quan"));
                    jsonObject.addProperty("good_name", resultSet.getString("good_name"));
                    jsonObject.addProperty("good_spec", resultSet.getString("good_spec"));
                    jsonObject.addProperty("good_url", resultSet.getString("good_url"));
                    printWriter.print(jsonObject.toString());
                    conn.close();
                    return;
                }
                jsonObject.addProperty("good_id", -1);
                jsonObject.addProperty("good_price", -1);
                jsonObject.addProperty("good_quan", -1);
                jsonObject.addProperty("good_name", "null");
                jsonObject.addProperty("good_spec","null");
                jsonObject.addProperty("good_url", "null");
                printWriter.print(jsonObject.toString());
                conn.close();

            }
            else
            {
                String sql = "SELECT * FROM good ";

                Statement statement = conn.createStatement();

                ResultSet resultSet = statement.executeQuery(sql);
                JsonObject out=new JsonObject();
                JsonArray json=new JsonArray();
                while (resultSet.next())
                {
                    JsonObject jsonObject=new JsonObject();

                    jsonObject.addProperty("good_id", resultSet.getInt("good_id"));
                    jsonObject.addProperty("good_price", resultSet.getInt("good_price"));
                    jsonObject.addProperty("good_quan", resultSet.getInt("good_quan"));
                    jsonObject.addProperty("good_name", resultSet.getString("good_name"));
                    jsonObject.addProperty("good_spec", resultSet.getString("good_spec"));
                    jsonObject.addProperty("good_url", resultSet.getString("good_url"));

                    json.add(jsonObject);
                }
                out.add("data",json);
                printWriter.print(out.toString());
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
