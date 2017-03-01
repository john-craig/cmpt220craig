import java.util.Date;

public class problem9_7{
	public static void main(String[] args) {
		Account tester = new Account(1122, 20000);

		tester.setAnnualInterestRate(4.5);
		tester.withdraw(2500);
		tester.deposit(3000);
		
		System.out.println("The account's balance is " + tester.getBalance() + ", its monthly interest is " + tester.getMonthlyInterest() + ", and it was created on " + tester.getDateCreated());
	}
}

class Account{
	private int id;
	private double balance;
	private double annualInterestRate;
	private Date dateCreated;

	Account(){
		id = 0;
		balance = 0.0;
		annualInterestRate = 0.0;
		dateCreated = new Date();
	}

	Account(int id, double bal){
		this.id = id;
		this.balance = bal;
		annualInterestRate = 0.0;
		dateCreated = new Date();
	}

	int getId(){return id;}
	void setId(int id){this.id = id;}

	double getBalance(){return balance;}
	void setBalance(double bal){balance = bal;}

	double getAnnualInterestRate(){return annualInterestRate;}
	void setAnnualInterestRate(double aIR){annualInterestRate = aIR;}

	Date getDateCreated(){return dateCreated;}

	double getMonthlyInterestRate(){return annualInterestRate / 12;}
	double getMonthlyInterest(){return balance * (getMonthlyInterestRate()/100);}
	void withdraw(double amt){balance -= amt;}
	void deposit(double amt){balance += amt;}
}