import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import React, { useState, useEffect } from 'react';
import { getTransactions, getUsers } from './services/api';
import { BarChart, Bar, XAxis, YAxis, Tooltip, Legend } from 'recharts'; // Assuming you are using `recharts` for displaying the graph.

function HomePage() {
  const [transactions, setTransactions] = useState([]);
  const [users, setUsers] = useState([]);
  const [currentBalance, setCurrentBalance] = useState(0);

  useEffect(() => {
      fetchTransactions();
      fetchUsers();
  }, []);

  const fetchTransactions = async () => {
      try {
          const response = await getTransactions();
          setTransactions(response.data);
          calculateCurrentBalance(response.data);
      } catch (error) {
          console.error("Error fetching transactions:", error);
      }
  };

  const fetchUsers = async () => {
      try {
          const response = await getUsers();
          setUsers(response.data);
      } catch (error) {
          console.error("Error fetching users:", error);
      }
  };

  const calculateCurrentBalance = (transactions) => {
      // Here we assume that an income transaction has a positive amount and an expense transaction has a negative amount.
      const balance = transactions.reduce((acc, transaction) => acc + transaction.amount, 0);
      setCurrentBalance(balance);
  };

  const graphData = [
      // Create dummy data for the BarChart. You can process the transactions data to create meaningful statistics.
      {name: 'Income', value: transactions.filter(t => t.amount > 0).reduce((acc, t) => acc + t.amount, 0)},
      {name: 'Expense', value: Math.abs(transactions.filter(t => t.amount < 0).reduce((acc, t) => acc + t.amount, 0))}
  ];

  return (
      <div className="homepage">
          <h2>Home Page</h2>

          {/* Display Current Balance */}
          <div className="balance-summary">
              <h3>Current Balance:</h3>
              <p>${currentBalance}</p>
          </div>

          {/* Display Bar Chart for Income vs Expenses */}
          <div className="chart-section">
              <BarChart width={600} height={300} data={graphData}>
                  <XAxis dataKey="name" />
                  <YAxis />
                  <Tooltip />
                  <Legend />
                  <Bar dataKey="value" fill="#82ca9d" />
              </BarChart>
          </div>

          {/* Display Categorized Expenses (this is a simplified example) */}
          <div className="expense-summary">
              <h3>Categorized Expenses:</h3>
              <ul>
                  {/* This could be more detailed and use actual category data from your backend */}
                  {transactions.filter(t => t.amount < 0).map(expense => (
                      <li key={expense.id}>
                          {expense.description}: ${Math.abs(expense.amount)}
                      </li>
                  ))}
              </ul>
          </div>
      </div>
  );
}

export default HomePage;
