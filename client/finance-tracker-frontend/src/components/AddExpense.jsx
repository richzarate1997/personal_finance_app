import React, { useState } from 'react';
import { createExpense } from '../services/api';

function AddExpense() {
  const [amount, setAmount] = useState('');
  const [description, setDescription] = useState('');
  const [date, setDate] = useState(''); 
  // Add more states if needed, for example: category, type, etc.

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Note: You may want to ensure that the amount is a negative number since it's an expense.
    const expenseData = {
      amount: -Math.abs(amount), // This ensures the amount is always negative.
      description,
      date
      // ... other fields as necessary
    };

    try {
      const response = await createExpense(expenseData);
      if (response.status === 200 || response.status === 201) {
        alert('Expense added successfully!');
      } else {
        alert('Error adding expense.');
      }
    } catch (error) {
      console.error("There was an error adding the expense", error);
      alert('Error adding expense.');
    }
  };

  return (
    <div>
      <h2>Add Expense</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Amount: </label>
          <input type="number" value={amount} onChange={(e) => setAmount(e.target.value)} />
        </div>
        <div>
          <label>Description: </label>
          <input type="text" value={description} onChange={(e) => setDescription(e.target.value)} />
        </div>
        <div>
          <label>Date: </label>
          <input type="date" value={date} onChange={(e) => setDate(e.target.value)} />
        </div>
        {/* Add more input fields as necessary */}
        <button type="submit">Add Expense</button>
      </form>
    </div>
  );
}

export default AddExpense;
