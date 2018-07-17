package com.sanmo.smak.framework.example.controller;

import com.sanmo.smak.framework.example.dao.util.CastUtil;
import com.sanmo.smak.framework.example.model.Customer;
import com.sanmo.smak.framework.example.service.CustomerService;
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

@WebServlet("/customer_show")
public class CustomerShowServlet extends HttpServlet {

    private static final Logger logger= LoggerFactory.getLogger(CustomerShowServlet.class);

    private CustomerService customerService=new CustomerService();

    /* 进入展示界面 */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Type", "text/html;charset=UTF-8");
        String queryString = req.getQueryString();
        long id = CastUtil.stringToLong(CastUtil.queryStringToMap(queryString).get("id"));
        logger.info("id {}",id);
        Customer customer = customerService.getCustomer(id);
        req.setAttribute("customer",customer);
        req.getRequestDispatcher("/WEB-INF/view/customer_show.jsp").forward(req,resp);
    }
}
