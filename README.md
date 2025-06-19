# Hotel Management System 🏨

A desktop-based Java application developed as a group project for the Object Oriented Programming (OOP) module at SLIIT. The system provides full hotel management capabilities including reservations, customer handling, room management, payments, and user roles with a Super Admin panel.

---

## 🔧 Features

- 🔐 **Login System with Role-Based Access**
  - Super Admin: Manage all users and system settings
  - Standard Users: Perform bookings, payments, and check-ins
- 👤 **User Management (by Super Admin)**
  - Add, edit, or delete system users
  - View all user accounts
- 🏨 **Room Booking and Management**
  - Create, update, delete rooms
  - Check room availability
- 🧾 **Customer Registration**
- 📅 **Reservation System**
  - Reservation form, calendar, and ID linking
- 💳 **Billing and Payment**
  - Generate bill
  - View payment history
- ✅ Full CRUD Operations via JDBC + MySQL

---

## 🗃️ Database Structure

This system connects to a MySQL database with the following tables:
- `users` – System login credentials (admin/user)
- `rooms` – Room details
- `customers` – Customer data
- `reservations` – Booking information
- `payments` – Billing details

📝 **Sample data**: At least 5 entries in each table for viva session  
🗂️ **Database script**: [hotel_db.sql] *(upload this file if available)*

---

## 🛠 Technologies Used

- **Java** (OOP Principles)
- **Swing** for GUI
- **JDBC** for database connection
- **MySQL** as RDBMS
- **MVC** Architecture
- IDE: **Eclipse / IntelliJ IDEA**

---

## 📁 Project Structure

src/
├── controller/ → Handles all user actions
├── dao/ → Database access logic
├── model/ → POJOs for Room, Customer, User, etc.
├── service/ → Business logic
├── util/ → Utility classes (e.g., DB connection)
├── view/ → GUI screens (e.g., LoginForm, Dashboard)
├── images/ → UI icons / graphics
└── module-info.java


---


📂 **Database Script**: `hotel_db.sql`  
📌 Import this into MySQL before running the project.



## 🚀 How to Run

1. Clone this repository or download ZIP  
2. Open in Eclipse or IntelliJ IDEA  
3. Set up your MySQL DB using `hotel_db.sql`  
4. Update your DB credentials in the DB connection class  
5. Run `LoginForm.java` or the designated main class  

🛑 **Note:** Make sure MySQL is running and the correct database is selected.

---

## 🔑 Default Login Credentials

> 🧑‍💼 Super Admin  
> Username: `admin`  
> Password: `admin`  

> 👨‍💻 Standard User  
> Username: `sign up email`  
> Password: `sign up password`


---

## 👤 Author

**Thisara Sandeepa**  
🎓 BSc (Hons) in IT – Specializing in Computer Systems & Network Engineering  
📍 SLIIT  
🔗 [LinkedIn Profile](https://www.linkedin.com/in/gamaethigethisara/)

---

## 📄 License

This project is developed for educational purposes as part of the IE2021 Object Oriented Programming module at SLIIT. Redistribution or commercial use is not permitted without prior permission.


