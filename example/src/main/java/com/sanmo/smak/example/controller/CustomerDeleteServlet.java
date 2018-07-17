package com.sanmo.smak.example.controller;

import com.sanmo.smak.example.dao.util.CastUtil;
import com.sanmo.smak.example.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/customer_delete")
public class CustomerDeleteServlet extends HttpServlet {

    private static final Logger logger= LoggerFactory.getLogger(CustomerDeleteServlet.class);

    private CustomerService customerService=new CustomerService();

    /* 删除 */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String queryString = req.getQueryString();
        Map<String, String> stringStringMap = CastUtil.queryStringToMap(queryString);
        String id = stringStringMap.get("id");
        logger.info("id {}",id);
        boolean success = customerService.deleteCustomer(CastUtil.stringToLong(id));
        req.setAttribute("success",success);
        req.getRequestDispatcher("WEB-INF/view/customer_delete.jsp").forward(req,resp);
    }
}
