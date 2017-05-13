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

/** 功能 检索出入库记录 可以按照时间 商品名 查找
 * Created by Administrator on 2017/3/9 0009.
 */
@WebServlet(name = "SearchNoteServlet")
public class SearchNoteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       doGet(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        String note_date=request.getParameter("note_date");
        String good_name=new String(request.getParameter("good_name").getBytes("iso-8859-1"),"utf-8");
        MySqlConnect connect=new MySqlConnect();
        Connection conn=connect.getConn();
        PrintWriter printWriter=response.getWriter();
        System.out.print(good_name);
        if(note_date.equals("empty") &&!good_name.equals("empty"))
        {
            //按照名称查找
            boolean isNum = good_name.matches("[0-9]+");
            if(!isNum)
            {


                try
                {   String sql = "SELECT * FROM note WHERE good_name='"+good_name+"'";
                    Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql);
                    JsonObject out=new JsonObject();
                    JsonArray json=new JsonArray();
                    while (resultSet.next())
                    {
                        JsonObject jsonObject=new JsonObject();

                        jsonObject.addProperty("note_good_id", resultSet.getInt("good_id"));
                        jsonObject.addProperty("note_quan", resultSet.getInt("note_quan"));
                        jsonObject.addProperty("note_type", resultSet.getInt("note_type"));
                        jsonObject.addProperty("note_good_name", resultSet.getString("good_name"));
                        jsonObject.addProperty("note_id", resultSet.getString("note_id"));
                        jsonObject.addProperty("note_time", resultSet.getString("note_time"));

                        json.add(jsonObject);
                    }
                    out.add("data",json);
                    response.getWriter().print(out.toString());
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                    System.out.print("error_name");

                }



            }
            else
            {
                try
                {
                    int id=Integer.valueOf(good_name);
                    String sql = "SELECT * FROM note WHERE good_id='"+id+"'";
                    Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql);
                    JsonObject out=new JsonObject();
                    JsonArray json=new JsonArray();
                    while (resultSet.next())
                    {
                        JsonObject jsonObject=new JsonObject();

                        jsonObject.addProperty("note_good_id", resultSet.getInt("good_id"));
                        jsonObject.addProperty("note_quan", resultSet.getInt("note_quan"));
                        jsonObject.addProperty("note_type", resultSet.getInt("note_type"));
                        jsonObject.addProperty("note_good_name", resultSet.getString("good_name"));
                        jsonObject.addProperty("note_id", resultSet.getString("note_id"));
                        jsonObject.addProperty("note_time", resultSet.getString("note_time"));

                        json.add(jsonObject);
                    }
                    out.add("data",json);
                    response.getWriter().print(out.toString());
                    conn.close();
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                    System.out.print("error_name");

                }
            }





        }
        else if (!note_date.equals("empty")&&good_name.equals("empty")) {
            try {

                String sql = "SELECT * FROM note WHERE note_time=?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, note_date);
                ResultSet resultSet = preparedStatement.executeQuery();
                JsonObject out=new JsonObject();
                JsonArray json=new JsonArray();
                while (resultSet.next())
                {
                    JsonObject jsonObject=new JsonObject();

                    jsonObject.addProperty("note_good_id", resultSet.getInt("good_id"));
                    jsonObject.addProperty("note_quan", resultSet.getInt("note_quan"));
                    jsonObject.addProperty("note_type", resultSet.getInt("note_type"));
                    jsonObject.addProperty("note_good_name", resultSet.getString("good_name"));
                    jsonObject.addProperty("note_id", resultSet.getString("note_id"));
                    jsonObject.addProperty("note_time", resultSet.getString("note_time"));

                    json.add(jsonObject);
                }
                out.add("data",json);
                response.getWriter().print(out.toString());
                conn.close();


            } catch (Exception e)
            {
                e.printStackTrace();
                System.out.print("error_date");
            }



        }
        else {
            //列出所有
            try {


                String sql = "SELECT * FROM note";

                Statement statement = conn.createStatement();

                ResultSet resultSet = statement.executeQuery(sql);
                JsonObject out=new JsonObject();
                JsonArray json=new JsonArray();
                while (resultSet.next())
                {
                    JsonObject jsonObject=new JsonObject();

                    jsonObject.addProperty("note_good_id", resultSet.getInt("good_id"));
                    jsonObject.addProperty("note_quan", resultSet.getInt("note_quan"));
                    jsonObject.addProperty("note_type", resultSet.getInt("note_type"));
                    jsonObject.addProperty("note_good_name", resultSet.getString("good_name"));
                    jsonObject.addProperty("note_id", resultSet.getString("note_id"));
                    jsonObject.addProperty("note_time", resultSet.getString("note_time"));

                    json.add(jsonObject);
                }
                out.add("data",json);
                response.getWriter().print(out.toString());
                conn.close();

            } catch (Exception e)
            {
               e.printStackTrace();
               System.out.print("error_all");
            }

        }

    }
}
