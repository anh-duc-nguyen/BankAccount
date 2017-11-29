import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NguyenSavingsAccount extends NguyenBankAccount
{
  private int accountNumber;
  private double balance;
  private double minbal;
  private double intrate;
  private String name;
  private int numberOfWithdrawal;
  
  public NguyenSavingsAccount (double initialBalance, double minbal, double intrate, String name)
  {
    super(name);
    this.accountNumber = super.getAccountNumber();
    balance = initialBalance;
    this.minbal = minbal;
    this.intrate = intrate;
  }
  
  public NguyenSavingsAccount (String name, double intrate)
  {
    super(name);
    this.accountNumber = super.getAccountNumber();
    balance = 0.0;
    minbal = balance;
    this.intrate = intrate;
  }
  
  public void withdraw(double amount)
  {
    balance = balance - amount;
    minbal = balance;
  }
    
  
  public int getNumberOfWithdrawal()
  {
    return numberOfWithdrawal;
  }
  
  public double getInterest()
  {
    return intrate;
  }
   public double getMinBal()
   {
     return minbal;
   }
  
  public void monthEnd()
  {
    balance = balance + minbal * intrate;
    minbal = balance;
  }
  
  public char getType()
  {
    return 's';
  }
  
  public String toString()
  {
    return super.toString() + String.format("[int=%1.2f,minbal=%1.2f]",
                                                intrate, minbal);
  }

}