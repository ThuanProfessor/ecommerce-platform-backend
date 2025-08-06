// package com.htw.repositories.impl;

// import com.htw.configs.HibernateConfigs;

// import java.util.Date;
// import java.util.List;

// import com.htw.pojo.Cart;
// import com.htw.pojo.OrderDetail;
// import com.htw.pojo.SaleOrder;

// import org.hibernate.Session;

// public class ReceiptRepositoryImpl {
//     public void addReceipt(List<Cart> carts){
//         if(carts != null){
//             try(Session s = this.factory.getObject().getCurrentSession();){
//                 SaleOrder order = new SaleOrder();
//                 order.setUserId(new UserRepositoryImpl().getUserByUsername("demo"));
//                 order.setCreatedDate(new Date);
//                 s.persist((order));

//                 for (var x:carts){
//                     OrderDetail d = new OrderDetail();
//                     d.setQuantity(x.getQuantity());
//                     d.setUnitPrice(x.getPrice());
//                     d.setProductId(new ProductRepositotyImpl().getProductById(x.getId()));

//                     d.setOrderId(order);

//                     s.persist(d);
                    
//                 }
//             }
//         }


//     }
// }
