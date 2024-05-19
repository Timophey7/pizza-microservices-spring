package com.menuservice.menuservice.controller;



import com.menuservice.menuservice.model.CartResponse;
import com.menuservice.menuservice.model.Order;
import com.menuservice.menuservice.model.Product;
import com.menuservice.menuservice.service.OrderService;
import com.menuservice.menuservice.service.ProductService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/manager/tools")
@RequiredArgsConstructor
public class ManagerController {

    private static final String MANAGER_SERVICE = "managerService";
    private final OrderService orderService;
    private final ProductService productService;

    @GetMapping("/")
    public String infoForManagers(){
        return "info-for-managers";
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model){
        Product product = new Product();
        model.addAttribute("product",product);
        return "add-product";
    }



    @PostMapping("/saveProduct")
    public String saveProduct(@Valid @ModelAttribute("product")Product product, Model model, BindingResult result){
        if (result.hasErrors()){
            model.addAttribute("product",product);
            return "add-product";
        }
        productService.saveProduct(product);
        return "redirect:/";

    }


    @GetMapping(value = "/orders")
    @CircuitBreaker(name = MANAGER_SERVICE,fallbackMethod = "fallbackMethod")
    public String ordersList(Model model){
        List<Order> allOrders = orderService.findAllOrders();
        model.addAttribute("orders",allOrders);
        return "orders";

    }

    @GetMapping("fallback")
    private String fallbackMethod(Exception ex){
        return "fallback";
    }

    @GetMapping("/orderComplete/{id}")
    @CircuitBreaker(name = MANAGER_SERVICE,fallbackMethod = "fallbackMethod")
    private String orderComplete(@PathVariable("id")int id){

        productService.completeOrder(id);
        return "redirect:/manager/tools/orders";

    }

}
