import java.util.Date;

public class problem11_3{
	public static void main(String[] args) {
		Account testAccount = new Account();
		SavingsAccount testSavings = new SavingsAccount();
		CheckingAccount testChecking = new CheckingAccount();
		
		System.out.println(testAccount);
		System.out.println(testSavings);
		System.out.println(testChecking);
	}
}

class Account{
	private int id;
	private double balance;
	private double annualInterestRate;
	private Date dateCreated;

	public Account(){
		id = 0;
		balance = 0.0;
		annualInterestRate = 0.0;
		dateCreated = new Date();
	}

	public Account(int id, double bal){
		this.id = id;
		this.balance = bal;
		annualInterestRate = 0.0;
		dateCreated = new Date();
	}

	public int getId(){return id;}
	public void setId(int id){this.id = id;}

	public double getBalance(){return balance;}
	public void setBalance(double bal){balance = bal;}

	public double getAnnualInterestRate(){return annualInterestRate;}
	public void setAnnualInterestRate(double aIR){annualInterestRate = aIR;}

	public Date getDateCreated(){return dateCreated;}

	public double getMonthlyInterestRate(){return annualInterestRate / 12;}
	public double getMonthlyInterest(){return balance * (getMonthlyInterestRate()/100);}
	public void withdraw(double amt){balance -= amt;}
	public void deposit(double amt){balance += amt;}
	
	public String toString(){
		return "Account #: " + id + " Balance: " + balance + " Annual Interest Rate: " + annualInterestRate + " Date Created: " + dateCreated; 
	}
}

class SavingsAccount extends Account{
	public SavingsAccount() {} // JA

	public void withdraw(double amt){
		if(this.getBalance() - amt < 0){System.out.println("This transaction would overdraw your account");}
		else{this.setBalance(this.getBalance() - amt);}
	}
	
	public String toString(){
		return "Account Type: Savings Account #: " + this.getId() + " Balance: " + this.getBalance() + " Annual Interest Rate: " + this.getAnnualInterestRate() + " Date Created: " + this.getDateCreated();
	}
}

class CheckingAccount extends Account{
	private double overdraft;

	public CheckingAccount(){} // JA
	
	public CheckingAccount(double overdraft){
		super();
		this.overdraft = overdraft;
	}
	
	public CheckingAccount(int id, double bal, double overdraft){
		super(id, bal);
		this.overdraft = overdraft;
	}
	
	public void withdraw(double amt){
		if(this.getBalance() - amt < overdraft){System.out.println("This transaction would overdraw your account");}
		else{this.setBalance(this.getBalance() - amt);}
	}
	
	public String toString(){
		return "Account Type: Checking Account #: " + this.getId() + " Balance: " + this.getBalance() + " Annual Interest Rate: " + this.getAnnualInterestRate() + " Date Created: " + this.getDateCreated();
	}
}