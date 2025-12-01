🧾 Wealth Tracker Application

A simple and secure web application that helps users track their assets, liabilities, financial summary, and personal details in one place.
This project is built using Java Spring Boot (Backend) and React.js (Frontend) with a modern, responsive UI and strong authentication flow.

Features
Secure Authentication

Aadhaar + PAN verification

OTP-based login

JWT (JSON Web Token) authentication

Automatic session expiry handling

Dashboard

Shows assets, liabilities, and financial overview

Clean table layout with real-time data

Profile card with name, PAN, mobile number & last login

🌙 Dark & Light Mode

Fully responsive theme

User’s theme preference stored locally

📄 Export to PDF

Download the complete financial dashboard as a PDF

Works in both Light & Dark modes

🧭 Sidebar Navigation

Profile section

Dashboard link

Assets & Liabilities

Mobile-friendly layout

📱 Mobile Responsive UI

Optimized for mobile, tablet, and desktop

Smooth animations using Framer Motion

🛠️ Tech Stack
Backend

Java

Spring Boot

Spring MVC

Spring Security (JWT)

JPA / Hibernate

MySQL / PostgreSQL

Frontend

React.js

Vite

TailwindCSS

Axios

Framer Motion

html2canvas + jsPDF

🏗️ Project Architecture
frontend/
   └── wealth/
        ├── src/
        │   ├── pages/
        │   │   ├── Login.jsx
        │   │   └── Dashboard.jsx
        │   ├── components/
        │   │   └── Sidebar.jsx
        │   ├── lib/
        │   ├── App.jsx
        │   ├── App.css
        │   └── index.css
        ├── package.json
        ├── vite.config.js
        └── README.md

⚙️ Backend APIs (Overview)
Endpoint	Method	Description
/auth/verify-aadhaar	POST	Aadhaar verification
/auth/verify-pan	POST	PAN verification
/auth/send-otp	POST	Send OTP to user
/auth/verify-otp	POST	Validate OTP & issue JWT
/wealth/dashboard	GET	Fetch assets & liabilities
/user/by-pan	GET	Get user profile details
💡 Why I Built This Project

I wanted to build a complete system that combines:

Backend skills (Java + Spring Boot)

Frontend UI/UX (React + Tailwind)

Authentication with real-world logic

PDF generation and dark mode

Clean architecture and API handling

This project shows my ability to develop secure, efficient, and user-friendly applications end-to-end.

▶️ How to Run the Project
1. Run Backend

Open the Spring Boot project

Configure application.properties for your database

Run:

mvn spring-boot:run

2. Run Frontend

Inside frontend folder:

npm install
npm run dev


Access the app at:

http://localhost:5173

👤 Author

Uday Kiran
Java Backend Developer
Passionate about building clean, secure, and scalable applications.
