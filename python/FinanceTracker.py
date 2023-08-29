from collections import defaultdict
from datetime import datetime, timedelta
import pickle

class FinanceTracker:
    def __init__(self, starting_balance):
        self.balance = starting_balance
        self.fixed_income = defaultdict(self.default_dict)
        self.variable_income = {}
        self.fixed_expenses = defaultdict(self.default_dict)
        self.variable_expenses = {}

    def to_date(self, dt):
        if isinstance(dt, datetime):
            return dt.date()
        return dt

    @staticmethod
    def default_dict():
        return {"amount": 0, "date": None, "frequency": None}

    def save_to_file(self, filename='data.pkl'):
            with open(filename, 'wb') as f:
                pickle.dump(self, f)

    @classmethod
    def load_from_file(cls, filename='data.pkl'):
        try:
            with open(filename, 'rb') as f:
                instance = pickle.load(f)
                return instance
        except FileNotFoundError:
            return None

    def add_fixed_income(self, source, amount, date, frequency):
        self.fixed_income[source]['amount'] = amount
        self.fixed_income[source]['date'] = self.to_date(date)# Convert datetime to date
        self.fixed_income[source]['frequency'] = frequency

    def add_variable_income(self, date, amount):
        self.variable_income[date] = amount

    def add_fixed_expense(self, source, amount, date, frequency):
        self.fixed_expenses[source]['amount'] = amount
        self.fixed_expenses[source]['date'] = self.to_date(date)  # Convert datetime to date
        self.fixed_expenses[source]['frequency'] = frequency

    def add_variable_expense(self, date, amount):
        self.variable_expenses[date] = amount

    def delete_fixed_income(self, source):
        if source in self.fixed_income:
            del self.fixed_income[source]
        else:
            print("Source not found.")

    def delete_variable_income(self, date):
        if date in self.variable_income:
            del self.variable_income[date]
        else:
            print("Date not found.")

    def delete_fixed_expense(self, source):
        if source in self.fixed_expenses:
            del self.fixed_expenses[source]
        else:
            print("Source not found.")

    def delete_variable_expense(self, date):
        if date in self.variable_expenses:
            del self.variable_expenses[date]
        else:
            print("Date not found.")

    def is_applicable(self, today, target_date, date, frequency):
        today = self.to_date(today)
        target_date = self.to_date(target_date)
        date = self.to_date(date)

        if today > target_date:
            return False

        if frequency == "daily":
            return True

        if frequency == "weekly":
            while date <= target_date:
                if date >= today:
                    return True
                date += timedelta(days=7)

        if frequency == "bi-weekly":
            while date <= target_date:
                if date >= today:
                    return True
                date += timedelta(days=14)

        if frequency == "monthly":
            while date <= target_date:
                if date >= today:
                    return True
                # Add a month to date
                if date.month == 12:  # if December
                    date = date.replace(year=date.year+1, month=1)
                else:
                    date = date.replace(month=date.month+1)

        return False




    def calculate_balance(self, today, target_date):
        current_balance = self.balance
        cur_date = self.to_date(today)
        
        while cur_date <= self.to_date(target_date):
            # Daily incomes and expenses
            incomes = [self.fixed_income[source]['amount'] for source in self.fixed_income 
                    if self.is_applicable(cur_date, cur_date, self.fixed_income[source]['date'], self.fixed_income[source]['frequency'])]
            
            total_income = sum(incomes)
            total_income += self.variable_income.get(cur_date, 0)

            expenses = [self.fixed_expenses[source]['amount'] for source in self.fixed_expenses 
                    if self.is_applicable(cur_date, cur_date, self.fixed_expenses[source]['date'], self.fixed_expenses[source]['frequency'])]
            
            total_expenses = sum(expenses)
            total_expenses += self.variable_expenses.get(cur_date, 0)

            current_balance += total_income - total_expenses
            cur_date += timedelta(days=1)

        return current_balance



    def project_balance(self, today, dates):
        projections = {}
        for date in dates:
            projections[date.date()] = self.calculate_balance(today, date.date())
        return projections


# Fixed Income and Expenses
tracker = FinanceTracker(starting_balance=877)
tracker.add_fixed_income("W2 Job", 1117, datetime(2023, 8, 11), "bi-weekly")
tracker.add_fixed_expense("Car Payment", 468, datetime(2023, 8, 22), "monthly")
tracker.add_fixed_expense("Car Insurance", 176, datetime(2023, 8, 12), "monthly")
tracker.add_fixed_expense("Google", 3, datetime(2023, 8, 9), "monthly")
tracker.add_fixed_expense("Cinemark", 10, datetime(2023, 8, 19,), "monthly")
tracker.add_fixed_expense("Youtube", 20, datetime(2023, 8, 20,), "monthly")
tracker.add_fixed_expense("Netflix", 20, datetime(2023, 8, 27,), "monthly")
tracker.add_fixed_expense("Verizon", 225, datetime(2023, 8, 29,), "monthly")

# Variable Income
# Variable Expenses



print(tracker.project_balance(datetime(2023, 8, 22), [datetime(2023, 9, 1)]))
print(tracker.project_balance(datetime(2023, 8, 22), [datetime(2023, 10, 1)]))
print(tracker.project_balance(datetime(2023, 8, 22), [datetime(2023, 11, 1)]))
print(tracker.project_balance(datetime(2023, 8, 22), [datetime(2023, 12, 1)]))

