package servlet;

import com.google.gson.JsonObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
@WebServlet(name = "SaveImageServlet")
public class SaveImageServlet extends HttpServlet {
    public static String path="E:\\J2EEPRO\\WORK\\web\\images";
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        request.setCharacterEncoding("utf-8");  //设置编码
        //获得磁盘文件条目工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //获取文件需要上传到的路径
//        String path ="E:\\J2EEPRO\\WORK\\web\\images";
        File file=new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        factory.setRepository(new File(path));
        //设置 缓存的大小
        factory.setSizeThreshold(1024*1024) ;

        //文件上传处理
        ServletFileUpload upload = new ServletFileUpload(factory);

        JsonObject jsonObject=new JsonObject();
        try {
            //可以上传多个文件
            List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
            for(FileItem item : list){
                //获取属性名字
                String name = item.getFieldName();
                //如果获取的 表单信息是普通的 文本 信息
                if(item.isFormField()){
                    //获取用户具体输入的字符串,因为表单提交过来的是 字符串类型的
                    String value = item.getString() ;
                    request.setAttribute(name, value);
                }else{
                    //获取路径名
                    String value = item.getName() ;
                    //索引到最后一个反斜杠
                    int start = value.lastIndexOf("\\");
                    //截取 上传文件的 字符串名字，加1是 去掉反斜杠，
                    String filename = value.substring(start+1);
                    request.setAttribute(name, filename);
                    //写到磁盘上
                    item.write( new File(path,filename) );//第三方提供的
                    //上传成功
                    jsonObject.addProperty("status",5);
                    jsonObject.addProperty("filename",filename);
                    response.getWriter().print(jsonObject.toString());
                    System.out.println("上传成功："+filename);
                    System.out.println(path);
//                    response.getWriter().print(filename);//将路径返回给客户端
                }
            }

        } catch (Exception e) {
            //上传失败
            jsonObject.addProperty("status",6);
            response.getWriter().print(jsonObject.toString());
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
