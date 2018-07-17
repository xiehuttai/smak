package com.sanmo.smak.framework.example.controller;

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
import java.util.List;

@WebServlet("/customerCreate")
public class CustomerCreateServlet extends HttpServlet {

    private static final Logger logger= LoggerFactory.getLogger(CustomerCreateServlet.class);

    private CustomerService customerService=new CustomerService();

    /* 进入创建客户界面 */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Customer> customers = customerService.getCustomerList();
        req.setAttribute("customers",customers);
        req.getRequestDispatcher("WEB-INF/view/customer.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}
