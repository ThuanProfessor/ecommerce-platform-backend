package com.htw.controllers;

import com.htw.pojo.OrderDetail;
import com.htw.services.OrderDetailService;
import com.htw.services.ProductService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Trung Hau
 */
@Controller
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private ProductService productService;

    @GetMapping("/order-detail/{orderId}")
    public String viewOrderDetail(Model model, @PathVariable(value = "orderId") int orderId,
            @RequestParam Map<String, String> params) {
        model.addAttribute("orderDetails", this.orderDetailService.getAllOrderDetail(orderId));
        model.addAttribute("products", this.productService.getProducts(params));
        return "order-detail";
    }

    @PostMapping("/order-detail/update-quantity")
    public String updateQuantity(@ModelAttribute(value = "orderDetail") OrderDetail orderDetail,
            RedirectAttributes redirectAttributes) {

        this.orderDetailService.updateQuantity(orderDetail);
        int orderId = orderDetail.getOrderId().getId();
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật số lượng thành công!");
        return "redirect:/order-detail/" + orderId;
    }
}
