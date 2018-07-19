package com.sanmo.smak.core;

import com.sanmo.smak.common.CastUtil;
import com.sanmo.smak.common.CodecUtil;
import com.sanmo.smak.common.JsonUtil;
import com.sanmo.smak.common.StreamUtil;
import com.sanmo.smak.core.bean.Data;
import com.sanmo.smak.core.bean.Param;
import com.sanmo.smak.core.bean.View;
import com.sanmo.smak.helper.bean.BeanHelper;
import com.sanmo.smak.helper.config.ConfigHelper;
import com.sanmo.smak.helper.controller.ControllerHelper;
import com.sanmo.smak.helper.controller.bean.Handler;
import com.sanmo.smak.helper.reflect.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    private static final Logger logger= LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        /*初始化相关类*/
        Loader.init();
        /*获取servlet上下文*/
        ServletContext servletContext = servletConfig.getServletContext();
        /*获取注册处理jsp的servlet*/
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getJspPath()+"*");
        /*注册处理静态资源的servlet*/
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAssetPath()+"*");
    }

    /*处理并分发请求*/
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        String path = req.getPathInfo();
        logger.debug("request:{},{}",method,path);
        Handler handler = ControllerHelper.getHandler(method, path);

        if (handler!=null){
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            /*解析请求*/
            Param param = parseParam(req);
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            /*解析结果*/
            parseResult(req,resp,result);
        }else {
            req.getRequestDispatcher(ConfigHelper.getJspPath()+"customer.jsp").forward(req,resp);
        }
    }

    /*解析请求参数*/
    public Param parseParam(HttpServletRequest req) throws IOException {
        HashMap<String, Object> params = new HashMap<>();
        Enumeration<String> attributeNames = req.getAttributeNames();
        while (attributeNames.hasMoreElements()){
            String element = attributeNames.nextElement();
            String value = req.getParameter(element);
            params.put(element,value);
        }
        /*解析url中的参数*/
        String queryString = req.getQueryString();
        if (StringUtils.isNotEmpty(queryString)){
            queryString=CodecUtil.decodeUrl(queryString);
            CastUtil.parseString(params,queryString);
        }
        /*解析请求体中的参数*/
        String body = CodecUtil.decodeUrl(StreamUtil.getString(req.getInputStream()));
        if (StringUtils.isNotEmpty(body)){
            CastUtil.parseString(params,body);
        }
        logger.debug("params:{}",params);
        return new Param(params);
    }


    /*处理响应结果*/
    public void parseResult(HttpServletRequest req,HttpServletResponse resp,Object result) throws IOException, ServletException {
        if (result instanceof View){
            View view = (View) result;
            String path = view.getPath();
            if (path.startsWith("/")){
                resp.sendRedirect(req.getContextPath()+path);
            }else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String ,Object> entry: model.entrySet()){
                    req.setAttribute(entry.getKey(),entry.getValue());
                }
                String forwardPath = ConfigHelper.getJspPath() + path;
                logger.info("forwardPath:{}",forwardPath);
                req.getRequestDispatcher(forwardPath).forward(req,resp);
            }
        }else if (result instanceof Data){
            Data data = (Data) result;
            Object model = data.getModel();
            if (model!=null){
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                PrintWriter writer = resp.getWriter();
                String json = JsonUtil.toJson(model);
                writer.write(json);
                writer.flush();
                writer.close();
            }
        }
    }
}
