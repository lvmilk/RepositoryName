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
        try {

            // check whether session variable is set
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession ses = req.getSession(true);
            String path = req.getServletPath();
            


            if (path.startsWith("/javax.faces.resource")
                    || path.equals("/login.xhtml") || path.equals("/staffMain.xhtml") || path.equals("/Permission.xhtml") || path.equals("/CMIpages/forgetPwd.xhtml")
                    || path.equals("/CMIpages/resetPwd.xhtml") || path.equals("/DDSpages/ddsLogin.xhtml")) {
                chain.doFilter(request, response);
            } else {
                if (ses.getAttribute("stfType") == null) {
                    res.sendRedirect("/MAS-war/login.xhtml");
                } else {
                    String stfType = (String) ses.getAttribute("stfType");
                    System.out.println(stfType);
                    if (stfType.equals("officeStaff")) {
                        if (path.contains("sAdm") || path.equals("/EditStaffPage.xhtml") || path.equals("/EditCockpitPage.xhtml")) {
                            res.sendRedirect("/MAS-war/Permission.xhtml");
                        }
                    } else if (stfType.equals("groundStaff")) {
                        if (!path.equals("/staffWorkspace.xhtml") && !path.startsWith("/CMIpages") && !path.startsWith("/DCSpages") ) {
                            res.sendRedirect("/MAS-war/Permission.xhtml");
                        }
                    } else if (stfType.equals("cabin")) {
                        if (!path.equals("/staffWorkspace.xhtml") && !path.startsWith("/CMIpages")) {
                            res.sendRedirect("/MAS-war/Permission.xhtml");
                        }
                    } else if (stfType.equals("cockpit")) {
                        if (!path.equals("/staffWorkspace.xhtml") && !path.startsWith("/CMIpages")) {
                            res.sendRedirect("/MAS-war/Permission.xhtml");
                        }
                    } else if (stfType.equals("agency")) {
                        if (!path.startsWith("/DDSpages")) {
                            res.sendRedirect("/MAS-war/Permission.xhtml");
                        }
                    }

                    // Other staff role
                    chain.doFilter(request, response);
                }
            }

//            System.out.println("***Authentication level: " + stfType + " ***");
//            //  allow user to proccede if url is login.xhtml or user logged in or user is accessing any page in //public folder
//            String reqURI = req.getRequestURI();
//
//            System.err.println("*********************reqURI: " + reqURI);
//
//            if ((ses != null && ses.getAttribute("username") != null)
//                    || reqURI.contains("javax.faces.resource") || reqURI.indexOf("/staffMain.xhtml") >= 0 || reqURI.indexOf("/login.xhtml") >= 0
//                    || reqURI.indexOf("/forgetPwd") >= 0 || reqURI.contains("/CMIpages/")) {
//                chain.doFilter(request, response);
//            } else // user didn't log in but asking for a page that is not allowed so take user to login page
//            {
//                res.sendRedirect(req.getContextPath() + "/staffMain.xhtml");  // Anonymous user. Redirect to login page
//            }
        } catch (Throwable t) {
            System.out.println(t.getMessage());
        }
    } //doFilter

    @Override
    public void destroy() {

    }
}
