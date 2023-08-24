import React, { useState, useEffect } from 'react';
import Calendar from 'react-calendar';
import { getRecurringTransactions } from '../services/api';

function CalendarView() {
    const [date, setDate] = useState(new Date());
    const [recurringTransactions, setRecurringTransactions] = useState([]);

    useEffect(() => {
        fetchRecurringTransactions();
    }, []);

    const fetchRecurringTransactions = async () => {
        try {
            const response = await getRecurringTransactions();
            setRecurringTransactions(response.data);
        } catch (error) {
            console.error("Error fetching recurring transactions:", error);
        }
    };

    const handleDateChange = newDate => {
        setDate(newDate);
        // You can implement any logic here to display transactions specific to the selected date
    };

    return (
        <div>
            <Calendar
                onChange={handleDateChange}
                value={date}
            />
            {/* Display the transactions for the selected date below the calendar or in any other suitable format */}
            <div>
                <h3>Recurring Transactions for {date.toLocaleDateString()}:</h3>
                <ul>
                    {recurringTransactions.filter(tran => new Date(tran.date).toDateString() === date.toDateString()).map(tran => (
                        <li key={tran.id}>
                            {tran.description}: ${tran.amount}
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
}

export default CalendarView;
