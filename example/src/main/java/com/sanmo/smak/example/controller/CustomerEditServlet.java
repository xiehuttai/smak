package com.sanmo.smak.example.controller;

import com.sanmo.smak.example.dao.util.CastUtil;
import com.sanmo.smak.example.model.Customer;
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
import java.util.Map;

@WebServlet("/customer_edit")
public class CustomerEditServlet extends HttpServlet {

    private static final Logger logger= LoggerFactory.getLogger(CustomerEditServlet.class);

    private CustomerService customerService=new CustomerService();

    /* 进入编辑界面 */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String queryString = req.getQueryString();
        Map<String, String> stringStringMap = CastUtil.queryStringToMap(queryString);
        String id = stringStringMap.get("id");
        Customer customer = customerService.getCustomer(CastUtil.stringToLong(id));
        req.setAttribute("customer",customer);
        req.getRequestDispatcher("/WEB-INF/view/customer_edit.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Type", "text/html;charset=UTF-8");
        String queryString = req.getQueryString();
        long id = CastUtil.stringToLong(CastUtil.queryStringToMap(queryString).get("id"));
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
        customerService.updateCustomer(id,map);
        resp.sendRedirect(req.getContextPath()+"/customer_show?id="+id);
    }

}
