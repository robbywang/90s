package web;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.Date;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/user", asyncSupported = true)
public class AsyncServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws IOException, ServletException {
		
        Principal principal = req.getUserPrincipal();
        String userName = principal.getName();
        System.out.println(userName);
		
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("进入Servlet的时间：" + new Date() + ".");
        out.flush();

        //在子线程中执行业务调用，并由其负责输出响应，主线程退出
        AsyncContext ctx = req.startAsync();
        ctx.start(new Executor(ctx));
//        new Thread(new Executor(ctx)).start();

        out.println("结束Servlet的时间：" + new Date() + ".");
        out.flush();
    }
}
