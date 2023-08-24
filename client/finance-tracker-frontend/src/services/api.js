import axios from 'axios';

const BASE_URL = "http://localhost:8080"; // Your Spring Boot server URL

// Category APIs
export const getAllCategories = () => {
    return axios.get(`${BASE_URL}/categories`);
};

export const getCategoryById = (id) => {
    return axios.get(`${BASE_URL}/categories/${id}`);
};

// ... Add other category-related calls ...

// RecurringTransaction APIs
export const getRecurringTransactions = () => {
    return axios.get(`${BASE_URL}/recurringTransactions`);
};

// ... Add other recurring transaction-related calls ...

// Transaction APIs
export const getTransactions = () => {
    return axios.get(`${BASE_URL}/transactions`);
};

// ... Add other transaction-related calls ...

// Assuming incomes are treated as a type of transaction in your backend
export const createIncome = (incomeData) => {
    return axios.post(`${BASE_URL}/transactions`, incomeData);
};


// User APIs
export const getUsers = () => {
    return axios.get(`${BASE_URL}/users`);
};

// ... Add other user-related calls ...
