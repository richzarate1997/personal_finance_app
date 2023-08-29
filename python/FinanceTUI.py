from FinanceTracker import FinanceTracker
from datetime import datetime, timedelta


class FinanceTUI:
    def __init__(self, tracker):
        self.tracker = tracker

    def display_menu(self):
        print("Finance Tracker Terminal UI")
        print("1. Add Fixed Income")
        print("2. Add Variable Income")
        print("3. Add Fixed Expense")
        print("4. Add Variable Expense")
        print("5. Project Balance")
        print("6. Quit")
        print("7. Delete Fixed Income")
        print("8. Delete Variable Income")
        print("9. Delete Fixed Expense")
        print("10. Delete Variable Expense")

    def get_choice(self):
        choice = int(input("Enter your choice: "))
        return choice
    
    def get_date_from_user(self, prompt_message):
        while True:
            date_str = input(prompt_message)
            # Try to parse the date in multiple formats.
            for fmt in ('%Y-%m-%d', '%Y/%m/%d'):
                try:
                    return datetime.strptime(date_str, fmt).date()
                except ValueError:
                    continue
            print("Date format not recognized. Please enter a date in YYYY-MM-DD or YYYY/MM/DD format.")


    def run(self):
        while True:
            self.display_menu()
            choice = self.get_choice()

            if choice == 1:
                self.add_fixed_income()
            elif choice == 2:
                self.add_variable_income()
            elif choice == 3:
                self.add_fixed_expense()
            elif choice == 4:
                self.add_variable_expense()
            elif choice == 5:
                self.project_balance()
            elif choice == 7:
                self.delete_fixed_income()
            elif choice == 8:
                self.delete_variable_income()
            elif choice == 9:
                self.delete_fixed_expense()
            elif choice == 10:
                self.delete_variable_expense()
            elif choice == 6:
                print("Goodbye!")
                break

    def add_fixed_income(self):
        source = input("Enter source of income: ")
        amount = float(input("Enter amount: "))
        
        # Convert the string date input to a date object
        date_obj = self.get_date_from_user("Enter date (YYYY-MM-DD or YYYY/MM/DD): ")

        frequency = input("Enter frequency (daily, weekly, bi-weekly, monthly): ")
        self.tracker.add_fixed_income(source, amount, date_obj, frequency)


    def add_variable_income(self):
        # Convert the string date input to a date object
        date_obj = self.get_date_from_user("Enter date (YYYY-MM-DD or YYYY/MM/DD): ")

        amount = float(input("Enter amount: "))
        self.tracker.add_variable_income(date_obj, amount)


    def add_fixed_expense(self):
        expense_name = input("Enter the name of the expense: ")
        amount = float(input("Enter the amount of the expense: "))

        # Convert the string date input to a date object
        date_obj = self.get_date_from_user("Enter date (YYYY-MM-DD or YYYY/MM/DD): ")

        frequency = input("Enter the frequency (daily, weekly, bi-weekly, monthly): ")

        # Add this expense to the FinanceTracker object using the date object
        self.tracker.add_fixed_expense(expense_name, amount, date_obj, frequency)

        print(f"Added fixed expense {expense_name} of amount {amount} due on {date_obj} with frequency {frequency}.")

    def add_variable_expense(self):
        # Convert the string date input to a date object
        date_obj = self.get_date_from_user("Enter date (YYYY-MM-DD or YYYY/MM/DD): ")

        amount = float(input("Enter amount: "))
        self.tracker.add_variable_income(date_obj, amount)

    # Similarly, you can create methods `add_fixed_expense` and `add_variable_expense`

    def delete_fixed_income(self):
        source = input("Enter the source of the fixed income you want to delete: ")
        self.tracker.delete_fixed_income(source)

    def delete_variable_income(self):
        date_str = input("Enter the date of the variable income you want to delete (YYYY-MM-DD or YYYY/MM/DD): ")
        date = self.get_date_from_user(date_str)
        self.tracker.delete_variable_income(date)

    def delete_fixed_expense(self):
        source = input("Enter the source of the fixed expense you want to delete: ")
        self.tracker.delete_fixed_expense(source)

    def delete_variable_expense(self):
        date_str = input("Enter the date of the variable expense you want to delete (YYYY-MM-DD or YYYY/MM/DD): ")
        date = self.get_date_from_user(date_str)
        self.tracker.delete_variable_expense(date)

    def project_balance(self):
        today = self.get_date_from_user("Enter today's date (YYYY-MM-DD or YYYY/MM/DD): ")
        future_date = self.get_date_from_user("Enter target date (YYYY-MM-DD or YYYY/MM/DD): ")

        if today and future_date:  # Make sure both dates are not None
            print(f"Projected balance on {future_date}: {self.tracker.calculate_balance(today, future_date)}")

        # Convert the input strings to date objects
        today = datetime.strptime(today_str, "%Y-%m-%d").date()
        future_date = datetime.strptime(future_date_str, "%Y-%m-%d").date()

        print(f"Projected balance on {future_date}: {self.tracker.calculate_balance(today, future_date)}")

    def get_date_from_user(self, date_str):
        # Try to parse the date in multiple formats.
        for fmt in ('%Y-%m-%d', '%Y/%m/%d'):
            try:
                return datetime.strptime(date_str, fmt).date()
            except ValueError:
                continue
        print("Date format not recognized. Please enter a date in YYYY-MM-DD or YYYY/MM/DD format.")
        return None

if __name__ == "__main__":
    tracker = FinanceTracker.load_from_file()
    if tracker is None:
        tracker = FinanceTracker(877)
    tui = FinanceTUI(tracker)
    tui.run()
    tracker.save_to_file()

