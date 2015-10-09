/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

/**
 *
 * @author LI HAO
 */
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthFilter implements Filter {

    public AuthFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try 
        {

            // check whether session variable is set
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;

            HttpSession ses = req.getSession(false);

            String stfType = (String) req.getSession().getAttribute("stfType");
            System.out.println("***Authentication level: " + stfType + " ***");
            //  allow user to proccede if url is login.xhtml or user logged in or user is accessing any page in //public folder
            String reqURI = req.getRequestURI();

            System.err.println("*********************reqURI: " + reqURI);

//            if (reqURI.contains("javax.faces.resource") || reqURI.contains("staffMain.xhtml") || reqURI.contains("login.xhtml")
//                    || reqURI.indexOf("/forgetPwd") >= 0 || reqURI.equals("/MAS-war/") || reqURI.contains("Permission.xhtml")) 
//            {
//
//                System.err.println("********************** inside bypass");
//                chain.doFilter(request, response);
//                return;
//            } 
//            else 
//            {
//                if (stfType == null) 
//                {
//                    res.sendRedirect(req.getContextPath() + "/staffMain.xhtml");  // Anonymous user. Redirect to login page
//                } 
//                else 
//                {
//                    chain.doFilter(request, response);
//                }
//            }
//
//            if (stfType.equals("administrator")) 
//            {
//                System.out.println("inside admin");
//                chain.doFilter(request, response);
//            } 
//            else if (stfType.equals("officeStaff") && (reqURI.contains("staffWorkspace.xhtml") || reqURI.contains("/CMIpages/"))) 
//            {
//                System.out.println("inside admin-else");
//                chain.doFilter(request, response);
//            } 
//            else 
//            {
//                System.err.println("********************** inside permission redirection");
//                request.getRequestDispatcher("/Permission.xhtml").forward(request, response);
//            }

            if ((ses != null && ses.getAttribute("username") != null)
                    || reqURI.contains("javax.faces.resource") || reqURI.indexOf("/staffMain.xhtml") >= 0 || reqURI.indexOf("/login.xhtml") >= 0
                    || reqURI.indexOf("/forgetPwd") >= 0 || reqURI.contains("/CMIpages/")) {
                chain.doFilter(request, response);
            } else // user didn't log in but asking for a page that is not allowed so take user to login page
            {
                res.sendRedirect(req.getContextPath() + "/staffMain.xhtml");  // Anonymous user. Redirect to login page
            }
        } 
        catch (Throwable t) 
        {
            System.out.println(t.getMessage());
        }
    } //doFilter

    @Override
    public void destroy() {

    }
}
