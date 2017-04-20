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
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Administrator on 2017/3/31 0031.
 */
@WebServlet(name = "UpdateRemainServlet")
public class UpdateRemainServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        int good_id=Integer.valueOf(request.getParameter("good_id"));
        int remain=Integer.valueOf(request.getParameter("remain"));
        int out=Integer.valueOf(request.getParameter("out"));
        String good_name=request.getParameter("good_name");

        MySqlConnect connect=new MySqlConnect();
        Connection conn=connect.getConn();
        JsonObject jsonObject=new JsonObject();
        try
        {

            String sql="UPDATE good SET good_quan='"+remain+"' WHERE good_id='"+good_id+"'";
            Statement statement=conn.createStatement();
            statement.executeUpdate(sql);

            Calendar date = Calendar.getInstance(Locale.CHINA);
            int year=date.get(Calendar.YEAR);
            int month=date.get(Calendar.MONTH)+1;
            int day=date.get(Calendar.DAY_OF_MONTH);
            int hour=date.get(Calendar.HOUR_OF_DAY);
            int minute=date.get(Calendar.MINUTE);
            int second=date.get(Calendar.SECOND);
            String  note_id=year+""+month+""+day+""+(hour<10?(12+hour):hour)+""+minute+""+(second<10?0:"")+second;
            String  note_time=year+"-"+month+"-"+day;
            String sql2="INSERT INTO note VALUES ('"+note_id+"','"+note_time+"','2','"+good_name+"','"+good_id+"','"+out+"')";
            Statement ss=conn.createStatement();
            ss.executeUpdate(sql2);


            jsonObject.addProperty("status",8);//返回8成功
            response.getWriter().print(jsonObject.toString());





            conn.close();

        }
        catch (Exception e)
        {

            jsonObject.addProperty("status",9);//返回9失败
            response.getWriter().print(jsonObject.toString());

        }

    }
}
