# Spring Boot To-Do List Application

A full-stack web application built with Spring Boot and Thymeleaf that allows users to manage their daily tasks and habits. It features secure user authentication, a modern dynamic UI, and several productivity tools designed to provide a seamless and engaging user experience.



## ✨ Features

* **User Authentication:** Secure user registration and login system handled by Spring Security.
* **CRUD for Tasks:** Full Create, Read, Update, and Delete functionality for to-do items.
* **Task Attributes:** Add details to your tasks including a title, description, due date/time, and priority level (High, Medium, Low).
* **Interactive UI:** A clean, dual-pane layout with modals for adding and editing tasks without full page reloads.
* **Gamified Dashboard:** A progress dashboard showing daily completion statistics in a pictorial graph and a "streak" counter to encourage consistency.
* **Navigable Calendar:** A full monthly calendar view that displays tasks on their due dates and allows navigation through different months and years.
* **Weekly Habit Tracker:** Add and track daily habits on a visual weekly grid.
* **Priority Filtering:** Filter the main task list to show "All", "High", "Medium", or "Low" priority tasks.
* **Dark/Light Mode:** Includes a beautiful dark mode that can sync with your device's system theme or be toggled manually. The user's preference is saved in their browser.



## 🛠️ Tech Stack

* **Backend:** Spring Boot, Spring Security, Spring Data JPA
* **Frontend:** Thymeleaf, HTML5, CSS3 (with variables for theming), JavaScript
* **Database:** MySQL
* **Build Tool:** Maven

##  Prerequisites

Before you begin, ensure you have the following installed on your system:
* Java Development Kit (JDK) 17 or higher
* Apache Maven
* MySQL Server
* An IDE like Eclipse (with Spring Tools Suite) or IntelliJ IDEA

## 🚀 Setup and Installation

Follow these steps to get the application running on your local machine.

### 1. Clone the Repository
```bash
git clone <your-repository-url>
cd todolist-app