create database librarydatabase;

use librarydatabase;

CREATE TABLE Book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255),
    description TEXT,
    copies INT,
    copies_available INT,
    category VARCHAR(255),
    img VARCHAR(255)
);

CREATE TABLE Checkout (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_email VARCHAR(255),
    checkout_date DATE,
    return_date DATE,
    book_id BIGINT,
    FOREIGN KEY (book_id) REFERENCES Book(id)
);

CREATE TABLE History (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     user_email VARCHAR(255),
     checkout_date DATE,
     returned_date DATE,
     title VARCHAR(255),
     author VARCHAR(255),
     description TEXT,
     img VARCHAR(255)
 );

CREATE TABLE Messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_email VARCHAR(255),
    title VARCHAR(255),
    question TEXT,
    admin_email VARCHAR(255),
    response TEXT,
    closed BOOLEAN
);

CREATE TABLE Review (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_email VARCHAR(255),
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    rating DOUBLE,
    book_id BIGINT,
    review_description TEXT
);

CREATE TABLE User (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    is_admin_user BOOLEAN default false
);