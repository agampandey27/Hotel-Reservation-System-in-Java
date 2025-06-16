# 🏨 Hotel Reservation System

Welcome to the Hotel Reservation System — a Java console-based application built using Java Fundamentals and JDBC with MySQL. Designed for ease and efficiency, this system allows small hotels, inns, and guesthouses to manage reservations smoothly without the need for complex tools or interfaces.

## 📌 Key Features

- 🔐 Make a Reservation

  Book rooms by entering guest details such as name, contact information, and assigning a room number.

- 👀 View All Reservations

   Display current reservations with all relevant details (guest name, room number, contact, reservation date).

- ✏️ Edit Existing Reservations

  Update guest information, change room numbers, or correct contact details.

- 🗑️ Delete Reservations

  Cancel or remove reservations that are no longer required.

## 🛠️ Technologies Used

- Java (Core Concepts & Fundamentals)

- JDBC (Java Database Connectivity)

- MySQL

- MySQL Connector/J

## 🚀 Getting Started

### 📋 Prerequisites
Make sure you have the following installed:

- Java Development Kit (JDK 8 or later)

- MySQL Server

- MySQL Connector/J (JDBC Driver)

## ⚙️ Setup Instructions
1. Clone the repository:
-     git clone https://github.com/agampandey27/Hotel-Reservation-System-in-Java

       cd Hotel-Reservation-System

2. Configure your MySQL database:

- Create a MySQL database named hotel_db.

- Inside your Java file (typically HotelReservationSystem.java), update the DB credentials:

-     private static final String DB_URL = "jdbc:mysql://localhost:3306/hotel_db";
      private static final String DB_USER = "your_mysql_username";
      private static final String DB_PASSWORD = "your_mysql_password";

3. Import the MySQL JDBC driver into your project’s classpath

## 🧑‍💻 Usage Guide

Once the application starts, you’ll see a menu-driven interface:

- Choose from options to Reserve, View, Edit, or Delete a reservation.

- Input the requested data when prompted.

- All operations reflect in the MySQL database in real time.


