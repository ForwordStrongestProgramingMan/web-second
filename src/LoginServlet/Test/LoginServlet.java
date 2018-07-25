package LoginServlet.Test;

import LoginServlet.User.User;
import LoginServlet.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hx on 2017/11/25.
 */
@WebServlet(name = "LoginServlet" ,urlPatterns = "/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        this.getServletContext().setAttribute("count" , 0);
    }

    public void doGet(HttpServletRequest request , HttpServletResponse response){
        //设置count为访问人数
        int count ;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try{
            User user = null;
            QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
            String sql = "select * from user where username=? and password=?";
            user = queryRunner.query(sql, new BeanHandler<User>(User.class), username, password);
            if(user != null){
                count = (int)this.getServletContext().getAttribute("count");
                count++;
                response.getWriter().write("login is success \n" + user.toString() + "\n" + "your are  " + count);
                this.getServletContext().setAttribute("count" , count);
            }else{
                response.getWriter().write("登录失败");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
