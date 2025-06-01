import java.io.CharArrayReader;
import java.util.HashMap;
import java.util.Scanner;

class EmployeeException extends RuntimeException {
    private String exceptionMessage;

    EmployeeException(String message) {
        exceptionMessage = message;
    }

    String getExceptionMessage() {
        return exceptionMessage;
    }
}

abstract class Employee {
    private String employeeName;

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    Account lookUp(Bank b, String accountHolderName) {
        Account account = null;
        if (b.bankAccounts.containsKey(accountHolderName)) {
            account = b.bankAccounts.get(accountHolderName);
        } else {
            throw new AccountException("Account not exist.");
        }
        return account;
    }

    void approveLoan(Bank b) {
        throw new EmployeeException("You don't have permission for this operation");
    }

    void changeInterestRate(String accountType, double newRate) {
        throw new EmployeeException("You don't have permission for this operation");
    }

    double seeInternalFund(Bank b) {
        throw new EmployeeException("You don't have permission for this operation");
    }
}

class ManagingDirector extends Employee {
    ManagingDirector(String employeeName) {
        setEmployeeName(employeeName);
    }

    void approveLoan(Bank b) {
        for (String accountHolderName : b.loanRequest.keySet()) {
            Account account = b.bankAccounts.get(accountHolderName);
            try {
                account.loanRequest(b.loanRequest.get(accountHolderName));
                System.out.println("Loan for " + accountHolderName + " approved.");
            } catch (AccountException ae) {
                System.out.println(ae.getExceptionMessage());
            }
        }
        b.loanRequest.clear();
    }

    void changeInterestRate(String accountType, double newRate) {
        if (accountType.equals("Student")) {
            StudentAccount.setInterestRate(newRate);
        } else if (accountType.equals("Savings")) {
            SavingsAccount.setInterestRate(newRate);
        } else if (accountType.equals("FixedDeposit")) {
            FixedDipositAccount.setInterestRate(newRate);
        }
    }

    double seeInternalFund(Bank b) {
        return b.getBankFund();
    }
}

class Officer extends Employee {
    Officer(String employeeName) {
        setEmployeeName(employeeName);
    }

    void approveLoan(Bank b) {
        for (String accountHolderName : b.loanRequest.keySet()) {
            Account account = b.bankAccounts.get(accountHolderName);
            try {
                account.loanRequest(b.loanRequest.get(accountHolderName));
                System.out.println("Loan for " + accountHolderName + " approved.");
            } catch (AccountException ae) {
                System.out.println(ae.getExceptionMessage());
            }
        }
        b.loanRequest.clear();
    }
}

class Cashier extends Employee {
    Cashier(String employeeName) {
        setEmployeeName(employeeName);
    }
}

abstract class Account {
    private String accountHolderName;
    private double accountBalance;
    private double accountLoan = 0;
    private int year = 0;
    private static double loanInterestRate = 10.0 / 100;

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountLoan(double accountLoan) {
        this.accountLoan = accountLoan;
    }

    public double getAccountLoan() {
        return accountLoan;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public static void setLoanInterestRate(double loanInterestRate) {
        Account.loanInterestRate = loanInterestRate;
    }

    public static double getLoanInterestRate() {
        return loanInterestRate;
    }

    abstract void updateAccountBalance();

    abstract void newDeposit(double deposit);

    abstract void withdraw(double ammount);

    abstract void loanRequest(double amount);
}

class AccountException extends RuntimeException {
    private String exceptionMessage;

    AccountException(String message) {
        exceptionMessage = message;
    }

    String getExceptionMessage() {
        return exceptionMessage;
    }
}

class SavingsAccount extends Account {
    private static double interestRate = 10.0 / 100;
    private static double maximumLoan = 10000;

    SavingsAccount(String userName, double initialDeposit) {
        setAccountHolderName(userName);
        setAccountBalance(initialDeposit);
    }

    static void setInterestRate(double interestRate) {
        SavingsAccount.interestRate = interestRate;
    }

    static double getInterestRate() {
        return interestRate;
    }

    void updateAccountBalance() {
        double accountBalance = getAccountBalance();
        double loan = getAccountLoan();
        double loanInterestRate = getLoanInterestRate();
        accountBalance = accountBalance * interestRate + accountBalance - loanInterestRate * loan - 500;
        setYear(getYear() + 1);
        setAccountBalance(accountBalance);
    }

    void newDeposit(double deposit) {
        double accountBalance = getAccountBalance();
        setAccountBalance(accountBalance + deposit);
    }

    void withdraw(double ammount) {

        double accountBalance = getAccountBalance();
        if (accountBalance - ammount < 1000)
            throw new AccountException("Invalid Transection; Current Balance " + accountBalance + "$");
        else
            accountBalance -= ammount;
        setAccountBalance(accountBalance);
    }

    void loanRequest(double amount) {
        double loan = getAccountLoan();
        if (loan + amount > maximumLoan)
            throw new AccountException("Loan cannot be more than " + maximumLoan + "$ for " + getAccountHolderName());
        loan += amount;
        setAccountBalance(getAccountBalance() + amount);
        setAccountLoan(loan);
    }
}

class StudentAccount extends Account {
    private static double interestRate = 5.0 / 100;
    private static double maximumLoan = 1000;

    StudentAccount(String userName, double initialDeposit) {
        setAccountHolderName(userName);
        setAccountBalance(initialDeposit);
    }

    static void setInterestRate(double interestRate) {
        StudentAccount.interestRate = interestRate;
    }

    static double getInterestRate() {
        return interestRate;
    }

    void updateAccountBalance() {
        double accountBalance = getAccountBalance();
        double loan = getAccountLoan();
        double loanInterestRate = getLoanInterestRate();
        accountBalance = accountBalance * interestRate + accountBalance - loanInterestRate * loan;
        setYear(getYear() + 1);
        setAccountBalance(accountBalance);
    }

    void newDeposit(double deposit) {
        double accountBalance = getAccountBalance();
        setAccountBalance(accountBalance + deposit);
    }

    void withdraw(double ammount) {
        double accountBalance = getAccountBalance();
        if (ammount > 10000 || accountBalance < ammount)
            throw new AccountException("Invalid Transaction; Current Balance " + accountBalance + "$");
        else
            accountBalance -= ammount;
        setAccountBalance(accountBalance);
    }

    void loanRequest(double amount) {
        double loan = getAccountLoan();
        if (loan + amount > maximumLoan)
            throw new AccountException("Loan cannot be more than " + maximumLoan + "$ for " + getAccountHolderName());
        loan += amount;
        setAccountBalance(getAccountBalance() + amount);
        setAccountLoan(loan);
    }
}

class FixedDipositAccount extends Account {
    private static double interestRate = 15.0 / 100;
    private static double maximumLoan = 100000;

    FixedDipositAccount(String userName, double initialDeposit) {
        if (initialDeposit < 100000)
            throw new AccountException("First deposit is less than 100,000$.");
        setAccountHolderName(userName);
        setAccountBalance(initialDeposit);
    }

    static void setInterestRate(double interestRate) {
        FixedDipositAccount.interestRate = interestRate;
    }

    static double getInterestRate() {
        return interestRate;
    }

    void updateAccountBalance() {
        double accountBalance = getAccountBalance();
        double loan = getAccountLoan();
        double loanInterestRate = getLoanInterestRate();
        accountBalance = accountBalance * interestRate + accountBalance - loanInterestRate * loan - 500;
        setYear(getYear() + 1);
        setAccountBalance(accountBalance);
    }

    void newDeposit(double deposit) {
        double accountBalance = getAccountBalance();
        if (deposit < 50000)
            throw new AccountException("Invalid Transaction; Current Balance " + accountBalance + "$");
        accountBalance += deposit;
        setAccountBalance(accountBalance);
    }

    void withdraw(double ammount) {
        double accountBalance = getAccountBalance();
        int year = getYear();
        if (accountBalance < ammount || year < 1)
            throw new AccountException("Invalid Transaction; Current Balance " + accountBalance + "$");
        accountBalance -= ammount;
        setAccountBalance(accountBalance);
    }

    void loanRequest(double amount) {
        double loan = getAccountLoan();
        if (loan + amount > maximumLoan)
            throw new AccountException("Loan cannot be more than " + maximumLoan + "$ for " + getAccountHolderName());
        loan += amount;
        setAccountBalance(getAccountBalance() + amount);
        setAccountLoan(loan);
    }
}

class Bank {
    private double bankFund;
    HashMap<String, Account> bankAccounts = new HashMap<String, Account>();
    HashMap<String, Employee> bankEmployee = new HashMap<String, Employee>();
    HashMap<String, Double> loanRequest = new HashMap<String, Double>();

    Bank(double bankFund) {
        this.bankFund = bankFund;
    }

    public void setBankFund(double bankFund) {
        this.bankFund = bankFund;
    }

    public double getBankFund() {
        return this.bankFund;
    }

    Account createAccount(String accountType, String userName, Double initialDeposit) {
        Account account = null;
        if (bankAccounts.containsKey(userName)) {
            System.out.println("Account already exist.");
        } else {
            setBankFund(bankFund + initialDeposit);
            if (accountType.equals("Savings")) {
                SavingsAccount savingsAccount = new SavingsAccount(userName, initialDeposit);
                account = savingsAccount;

            } else if (accountType.equals("Student")) {
                StudentAccount studentAccount = new StudentAccount(userName, initialDeposit);
                account = studentAccount;

            } else if (accountType.equals("FixedDeposit")) {
                FixedDipositAccount fixedDipositAccount = new FixedDipositAccount(userName, initialDeposit);
                account = fixedDipositAccount;
            }
            bankAccounts.put(userName, account);
        }
        return account;
    }

    Account query(String accountHolderName) {
        Account account = null;
        if (bankAccounts.containsKey(accountHolderName))
            account = bankAccounts.get(accountHolderName);
        return account;
    }

    void INC() {
        for (String accountHolderName : bankAccounts.keySet()) {
            Account account = bankAccounts.get(accountHolderName);
            account.updateAccountBalance();
        }
    }

    void deposit(double amount) {
        bankFund += amount;
    }

    void withdraw(double amount) {
        bankFund -= amount;
    }

    void request(String accountHolderName, double amount) {
        loanRequest.put(accountHolderName, amount);
    }

}

public class Main {
    public static void main(String[] args) {
        int loggedVar = 0;
        Bank bank = new Bank(1000000);
        ManagingDirector MD = new ManagingDirector("MD");
        Officer O1 = new Officer("O1"), O2 = new Officer("O2");
        Cashier C1 = new Cashier("C1"), C2 = new Cashier("C2"), C3 = new Cashier("C3");
        Cashier C4 = new Cashier("C4"), C5 = new Cashier("C5"), C6 = new Cashier("C6");
        bank.bankEmployee.put("MD", MD);
        bank.bankEmployee.put("O1", O1);
        bank.bankEmployee.put("O2", O2);
        bank.bankEmployee.put("C1", C1);
        bank.bankEmployee.put("C2", C2);
        bank.bankEmployee.put("C3", C3);
        bank.bankEmployee.put("C4", C4);
        bank.bankEmployee.put("C5", C5);
        bank.bankEmployee.put("C6", C6);
        System.out.println("Bank Created; MD, O1, O2, C1, C2, C3, C4, C5 created.");
        Scanner scn = new Scanner(System.in);
        Employee currentEmployee = null;
        Account currentAccount = null;
        while (true) {
            String command = scn.next();
            if (loggedVar == 0) {
                if (command.equals("Create")) {
                    String userName = scn.next();
                    String accountType = scn.next();
                    Double intialDeposit = scn.nextDouble();
                    try {
                        currentAccount = bank.createAccount(accountType, userName, intialDeposit);
                    } catch (AccountException ae) {
                        System.out.println(ae.getExceptionMessage());
                    }
                    if (currentAccount != null) {
                        loggedVar = 1;
                        System.out.println(accountType + " account for " + userName + " Created; Initial Balance "
                                + intialDeposit + "$");
                    }
                } else if (command.equals("Open")) {
                    String userName = scn.next();
                    if (bank.bankAccounts.containsKey(userName)) {
                        currentAccount = bank.bankAccounts.get(userName);
                        loggedVar = 1;
                        System.out.println("Welcome back, " + currentAccount.getAccountHolderName());
                    } else if (bank.bankEmployee.containsKey(userName)) {
                        currentEmployee = bank.bankEmployee.get(userName);
                        loggedVar = 2;
                        System.out.println(
                                currentEmployee.getEmployeeName() + " active, there are loan approvals pending.");
                    } else {
                        System.out.println("Not Found.");
                    }

                } else if (command.equals("INC")) {
                    bank.INC();
                    System.out.println("1 year passed.");
                } else {
                    System.out.println("Invalid Operation Request.");
                }
            } else if (loggedVar == 1) {
                if (command.equals("Deposit")) {
                    double amount = scn.nextDouble();
                    try {
                        currentAccount.newDeposit(amount);
                        bank.deposit(amount);
                        System.out.println(
                                amount + "$ deposited; Current balance " + currentAccount.getAccountBalance() + "$");

                    } catch (AccountException ae) {
                        System.out.println(ae.getExceptionMessage());
                    }
                } else if (command.equals("Withdraw")) {
                    double amount = scn.nextDouble();
                    try {
                        currentAccount.withdraw(amount);
                        System.out.println(
                                amount + "$ withdrawed; current balance " + currentAccount.getAccountBalance() + "$");
                        bank.withdraw(amount);
                    } catch (AccountException ae) {
                        System.out.println(ae.getExceptionMessage());
                    }
                } else if (command.equals("Query")) {
                    System.out.println("Current balance " + currentAccount.getAccountBalance() + "$, loan "
                            + currentAccount.getAccountLoan() + "$");
                } else if (command.equals("Request")) {
                    double amount = scn.nextDouble();
                    bank.request(currentAccount.getAccountHolderName(), amount);
                    System.out.println("Loan request successful, sent for approval");
                } else if (command.equals("Close")) {
                    loggedVar = 0;
                    System.out.println("Transaction for " + currentAccount.getAccountHolderName() + " closed.");
                    currentAccount = null;
                } else {
                    System.out.println("Invalid Operation Request.");
                }
            } else if (loggedVar == 2) {
                if (command.equals("Lookup")) {
                    String accountHolderName = scn.next();
                    try {
                        currentAccount = currentEmployee.lookUp(bank, accountHolderName);
                        System.out.println(currentAccount.getAccountHolderName() + "'s current balance is "
                                + currentAccount.getAccountBalance());
                        currentAccount = null;

                    } catch (EmployeeException ee) {
                        System.out.println(ee.getExceptionMessage());
                    }
                } else if (command.equals("Change")) {
                    String accountType = scn.next();
                    double newRate = scn.nextDouble();
                    try {
                        currentEmployee.changeInterestRate(accountType, newRate);
                        System.out.println("Interest Rate of " + accountType + " has been changed.");
                    } catch (EmployeeException ee) {
                        System.out.println(ee.getExceptionMessage());
                    }
                } else if (command.equals("See")) {
                    try {
                        double internalFund = currentEmployee.seeInternalFund(bank);
                        System.out.println("Internal fund of the bank is " + internalFund + "$");
                    } catch (EmployeeException ee) {
                        System.out.println(ee.getExceptionMessage());
                    }
                } else if (command.equals("Approve")) {
                    String str = scn.next();
                    try {
                        currentEmployee.approveLoan(bank);
                    } catch (EmployeeException ee) {
                        System.out.println(ee.getExceptionMessage());
                    }
                } else if (command.equals("Close")) {
                    loggedVar = 0;
                    System.out.println("Operationss for " + currentEmployee.getEmployeeName() + " closed.");
                    currentEmployee = null;
                } else {
                    System.out.println("Invalid Operation Request.");
                }
            }
        }
    }
}