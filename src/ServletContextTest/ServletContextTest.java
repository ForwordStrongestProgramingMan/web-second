package ServletContextTest;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hx on 2017/11/26.
 */
@WebServlet(name = "ServletContextTest", urlPatterns = "/context")
public class ServletContextTest extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获得ServletContext的两种方法
//        ServletContext servletContext = this.getServletConfig().getServletContext();
        //常用写法，其实两种写法的实质都是一样的
        ServletContext servletContext = this.getServletContext();

        //作用一：获得web.xml的初始化参数
        String namespace_value = servletContext.getInitParameter("namespace");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print(namespace_value);
        outputStream.flush();
        outputStream.close();

        //作用二：获得web应用中任何资源的绝对路径(相对于部署后的目录【web-app or out】 这个很重要)
        String realPath_b = servletContext.getRealPath("/WEB-INF/classes/b.txt");
        String realPath_c = servletContext.getRealPath("/c.text");
        String realPath_d = servletContext.getRealPath("/WEB-INF/d.txt");
        //注意：a.text获取不到 因为它没有部署到tomcat中，因为是相对于部署到tomcat的目录。
        System.out.println(realPath_b);
        System.out.println(realPath_c);
        System.out.println(realPath_d);
            //在读取src(classes)下的资源是可以用同类加载器----专门加载classes下的文件(它是相对于classes目录)
        String path_d = ServletContextTest.class.getClassLoader().getResource("b.txt").getPath();
        System.out.println(path_d);

        //作用三：ServletContext是一个域对象 HttpServletSession ServletRequest PageContext(存储数据的区域就是域对象)
        servletContext.setAttribute("hx" , "handsome");
    }
}
