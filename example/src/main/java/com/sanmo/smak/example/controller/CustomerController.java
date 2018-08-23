package com.sanmo.smak.example.controller;

import com.sanmo.smak.annotation.Action;
import com.sanmo.smak.annotation.Controller;
import com.sanmo.smak.annotation.Inject;
import com.sanmo.smak.mvc.pojo.Param;
import com.sanmo.smak.mvc.pojo.View;
import com.sanmo.smak.example.model.Customer;
import com.sanmo.smak.example.service.CustomerService;

import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {

    @Inject
    private CustomerService customerService;

    @Action("get:/customer")
    public View toCustomerList(){
        List<Customer> customerList = customerService.getCustomerList();
        return new View("customer.jsp").addModel("customers",customerList);
    }

    @Action("get:/customer_create")
    public View toCustomeCreate(){
        return new View("customer_create.jsp");
    }

    @Action("post:/customer_create")
    public View doCustomerCreate(Param param){
        Map<String, Object> map = param.getParamMap();
        boolean customer = customerService.createCustomer(map);
        return new View("/customer");
    }

    @Action("get:/customer_delete")
    public View toCustomerDelete(Param param){
        long id = param.getLong("id");
        boolean b = customerService.deleteCustomer(id);
        return new View("customer_delete.jsp").addModel("success",b);
    }

    @Action("get:/customer_edit")
    public View toCustomerEdit(Param param){
        long id = param.getLong("id");
        Customer customer = customerService.getCustomer(id);
        return  new View("customer_edit.jsp").addModel("customer",customer);
    }

    @Action("post:/customer_edit")
    public View doCustomerEdit(Param param){
        long id = param.getLong("id");
        Map<String, Object> map = param.getParamMap();
        boolean b = customerService.updateCustomer(id, map);
        return new View("/customer_show?id="+id);
    }

    @Action("get:/customer_show")
    public View toCustomerShow(Param param){
        long id = param.getLong("id");
        Customer customer = customerService.getCustomer(id);
        return new View("customer_show.jsp").addModel("customer",customer);
    }

}
