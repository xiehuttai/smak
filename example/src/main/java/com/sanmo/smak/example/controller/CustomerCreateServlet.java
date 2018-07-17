package com.sanmo.smak.example.controller;

import com.sanmo.smak.example.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/customer_create")
public class CustomerCreateServlet extends HttpServlet {

    private static final Logger logger= LoggerFactory.getLogger(CustomerCreateServlet.class);

    private CustomerService customerService=new CustomerService();

    /* 进入创建界面 */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/view/customer_create.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Type", "text/html;charset=UTF-8");
        HashMap<String, Object> map = new HashMap<>();
        String name = req.getParameter("name");
        String contact = req.getParameter("contact");
        String telephone = req.getParameter("telephone");
        String email = req.getParameter("email");
        String remark = req.getParameter("remark");
        map.put("name",name);
        map.put("contact",contact);
        map.put("telephone",telephone);
        map.put("email",email);
        map.put("remark",remark);
        logger.info("request info is {}",map);
        customerService.createCustomer(map);
        logger.info(" req.getContextPath() :{}",req.getContextPath());
        resp.sendRedirect(req.getContextPath()+"/customer");
    }

}
