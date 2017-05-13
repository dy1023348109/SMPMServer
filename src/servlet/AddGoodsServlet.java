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
import java.sql.Statement;
import java.util.Calendar;
import java.util.Locale;

/** 给数据库添加物品信息
 *   先判断该物品是否存在 存在直接更改数量 否则只需更改数据
 * Created by Administrator on 2017/3/8 0008.
 */
@WebServlet(name = "AddGoodsServlet")
public class AddGoodsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        String good_name=new String(request.getParameter("good_name").getBytes("iso-8859-1"),"utf-8");;
        String good_spec=new String(request.getParameter("good_spec").getBytes("iso-8859-1"),"utf-8");
        System.out.print(good_name);
        int good_price=Integer.valueOf(request.getParameter("good_price"));
        int good_quan=Integer.valueOf(request.getParameter("good_quan"));
        int good_id=Integer.valueOf(request.getParameter("good_id"));
        int type=Integer.valueOf(request.getParameter("type"));//0为更新 1位插入新的
        MySqlConnect connect=new MySqlConnect();
        Connection conn=connect.getConn();
        if(type==0)//更新数据
        {
            try
            {
                String sql="SELECT good_quan from good WHERE good_id=?";
              //  String sql=new String(s.getBytes("iso-8859-1"),"utf-8");
                PreparedStatement preparedStatement=conn.prepareStatement(sql);
                preparedStatement.setInt(1,good_id);
                ResultSet resultSet=preparedStatement.executeQuery();
                int quan=0;
                while(resultSet.next())
                {
                    quan=resultSet.getInt("good_quan");
                }
                good_quan= good_quan+quan;
                sql="UPDATE good SET good_name='"+good_name+"',good_price='"+good_price+"',good_spec='"+good_spec+"',good_quan='"+good_quan+"' WHERE good_id='"+good_id+"'";
                Statement statement=conn.createStatement();
                statement.executeUpdate(sql);
                conn.close();
            }
            catch (Exception e)
            {

                JsonObject jsonObject=new JsonObject();
                jsonObject.addProperty("status",4);
                response.getWriter().print(jsonObject.toString());

                return;
            }
        }
        else//插入
        {
            try
            {
                String sql="INSERT INTO good VALUES ('"+good_id+"','"+good_name+"','"+good_spec+"','"+good_price+"','"+good_quan+"','null')";
                //String s="SELECT good_quan from good WHERE good_id=?";
//                String sql=new String(s.getBytes("iso-8859-1"),"utf-8");
                Statement ss=conn.createStatement();
                ss.executeUpdate(sql);


            }
            catch (Exception e)
            {
                JsonObject jsonObject=new JsonObject();
                jsonObject.addProperty("status",4);
                response.getWriter().print(jsonObject.toString());

                return;
            }
        }

        try
        {
            Calendar date = Calendar.getInstance(Locale.CHINA);
            int year=date.get(Calendar.YEAR);
            int month=date.get(Calendar.MONTH)+1;
            int day=date.get(Calendar.DAY_OF_MONTH);
            int hour=date.get(Calendar.HOUR_OF_DAY);
            int minute=date.get(Calendar.MINUTE);
            int second=date.get(Calendar.SECOND);
            String  note_id=year+""+month+""+day+""+(hour<10?(12+hour):hour)+""+(minute<10?0:"")+minute+""+(second<10?0:"")+second;
            String note_time=year+"-"+month+"-"+day;
            String sql2="INSERT INTO note VALUES ('"+note_id+"','"+note_time+"','1','"+good_name+"','"+good_id+"','"+good_quan+"')";
            Statement statement=conn.createStatement();
            statement.executeUpdate(sql2);


        }catch (Exception e)
        {
            e.printStackTrace();
        }

        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("status",3);
        response.getWriter().print(jsonObject.toString());

        return;

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
