# 🛒 Ecommerce-Platform-Backend

This is the backend service for the **Ecommerce Platform** project — a multi-vendor e-commerce platform where users can register, browse, purchase, and review products from various sellers.

> Built with **Java Spring MVC**, **MySQL**, and **RESTful APIs**.

---

## 📌 Features

### 🔐 Authentication & Roles
- User registration (buyer, vendor: individual or company)
- Avatar upload on signup
- Role-based login (Admin, Staff, Seller, User)
- Manual seller approval by system staff

### 🛍️ Product & Store Management
- Seller dashboard to manage stores and products
- Each product includes name, price, image, category, etc.

### 🔎 Product Browsing & Search
- Filter products by name, price, store
- Sort by name or price
- Pagination (20 products per page)

### 💬 Reviews & Ratings
- Product and seller reviews (1–5 stars + comment)
- Reply to comments

### 💳 Order & Payments
- Cash on Delivery or Online Payment via:
  - PayPal
  - Stripe
  - ZaloPay
  - MoMo
- All transactions are recorded

### 📊 Statistics & Reports
- Seller revenue by month, quarter, year
- Admin analytics: total products, sales frequency per store

### 🧠 Smart Features
- Product comparison across stores
- Real-time chat via Firebase

---

## 🏗️ Tech Stack

| Layer        | Technology                      |
|--------------|----------------------------------|
| Backend      | Java Spring MVC                 |
| ORM          | Hibernate                       |
| Database     | MySQL                           |
| Security     | Spring Security / JWT / OAuth2  |
| REST API     | Spring Web                      |
| File Upload  | MultipartFile / Cloudinary      |
| Chat         | Firebase Realtime Database      |

---

## 📂 Project Structure


---

## ⚙️ Getting Started

### 🔧 Prerequisites
- Java 17+
- Maven 3.8+
- MySQL Server (8+)

### 🔑 Setup Environment

1. Clone the repository:
```bash
git clone https://github.com/ThuanProfessor/ecommerce-platform-backend.git
cd ecommerce-platform-backend
```

## 👥 Contributors

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/thuanprofessor">
        <img src="https://github.com/thuanprofessor.png" width="100" height="100" style="border-radius: 50%; object-fit: cover;"><br>
        <sub><b>Thuan Professor</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/anhhao247">
        <img src="https://github.com/trunghau810.png" width="100" height="100" style="border-radius: 50%; object-fit: cover;"><br>
        <sub><b>Trung Hau</b></sub>
      </a>
    </td>
  </tr>
</table>

## 📝 License

This project is licensed under multiple open source licenses:

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)
[![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-yellow.svg)](https://opensource.org/licenses/)
[![AGPL License](https://img.shields.io/badge/license-AGPL-blue.svg)](http://www.gnu.org/licenses/agpl-3.0)

## 📞 Support

For support and inquiries, please contact:
- 📧 Email: [2251050029hau@ou.edu.vn](mailto:2251050029hau@ou.edu.vn)
- 📧 Email: [hoangthuandev04@gmail.com](mailto:hoangthuandev04@gmail.com)
- 💬 Issues: [GitHub Issues](https://github.com/ThuanProfessor/ecommerce-platform-backend/issues)


