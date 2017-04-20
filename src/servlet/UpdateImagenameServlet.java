package servlet;

import com.google.gson.JsonObject;
import tool.MySqlConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
@WebServlet(name = "UpdateImagenameServlet")
public class UpdateImagenameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

         String filename=request.getParameter("filename");
         String good_id=request.getParameter("good_id");
         System.out.print(filename);
         File f=new File(SaveImageServlet.path);
         File file=new File(SaveImageServlet.path+"/"+filename);
         String end="";
        if (filename.endsWith(".png")) {
            end = ".png";

        }
        if (filename.endsWith(".jpg")) {
            end = ".jpg";

        }
        if (filename.endsWith(".gif")) {
            end = ".gif";


        }
        if (filename.endsWith(".bmp")) {
            end = ".bmp";

        }
        File newFile=new File(SaveImageServlet.path+"/"+good_id+end);
        file.renameTo(newFile);

        file.delete();
        System.out.print(file.getAbsoluteFile());



         response.setCharacterEncoding("UTF-8");
         response.setContentType("application/json; charset=utf-8");
        MySqlConnect connect=new MySqlConnect();
        Connection conn=connect.getConn();
        JsonObject jsonObject=new JsonObject();
        try
        {
            String sql="UPDATE good SET good_url='"+newFile.getName()+"' WHERE good_id='"+good_id+"'";
            Statement statement=conn.createStatement();
            statement.executeUpdate(sql);
            jsonObject.addProperty("status",7);
            response.getWriter().print(jsonObject.toString());
            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            jsonObject.addProperty("status",8);
            response.getWriter().print(jsonObject.toString());

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       doPost(request,response);
    }
}
