# MediStock - Pharmacy Stock and Billing System

MediStock is a Java console application that helps a small pharmacy manage medicine stock and generate customer bills. This is my first hackathon and my first GitHub project, built with Java and Eclipse. The entire program is written in a single class, MediStock.

## Problem
Small pharmacies often keep stock and bills on paper. This leads to calculation mistakes, expired medicines being sold, and stock running out unnoticed. MediStock automates these tasks.

## Features
- Add, display, search (by name or ID), delete and restock medicines
- Sell medicine and print a bill with discount and 12% GST
- Blocks sale of expired medicines and over-selling
- Low stock alert (5 units or fewer) and near-expiry alert (3 months or fewer)
- Total stock value and daily sales report
- Safe input: typing letters instead of numbers does not crash the program

## Sample Bill
========== CUSTOMER BILL ==========

Medicine Name : Paracetamol
Quantity      : 5
Price         : Rs.20.0
Discount      : 10.0%
Amount        : Rs.90.0
GST (12%)     : Rs.10.8
-----------------------------------
Total Bill    : Rs.100.8
===================================
## Concepts Used
Static methods, method overloading (calculateBill), ArrayList, loops, switch case, validation using if-else, and exception handling (try-catch).

## How to Run
1. Open Eclipse and create a Java project.
2. Copy MediStock.java from the src folder into your project.
3. Right-click the file and choose Run As > Java Application.
4. Type the menu number in the console.

Data is stored only while the program runs.

## Future Improvements
- Permanent storage using files or MySQL
- Graphical interface (JavaFX or Swing)
- Multiple medicines in one bill
- Staff login

## Author
Nandini Rangapuram - ECE Student, Hyderabad, India

Learning Java and building my way up, one project at a time.
