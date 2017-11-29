import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NguyenCheckingAccount extends NguyenBankAccount
{
  private int numberOfWithdrawal = 0;
  private final double WITHDRAWAL_FEE = 1.00;
  private double balance;
  private double minbal;
  private double intrate;
  private String name;
  
  public NguyenCheckingAccount (double initialBalance, int numberOfWithdrawal, String name)
  {
    super(name);
    balance = initialBalance;
    this.numberOfWithdrawal = numberOfWithdrawal;
  }
  
  public NguyenCheckingAccount (String name)
  {
    super(name);
    balance = 0.0;
    numberOfWithdrawal = 0;
  }

  public void withdraw(double amount)
  {
    numberOfWithdrawal += 1;
    if (numberOfWithdrawal > 3)
    {
      balance = balance - amount - WITHDRAWAL_FEE;
    }
    else
    {
      balance -= amount;
    }
  }
  
  public int getNumberOfWithdrawal()
  {
    return numberOfWithdrawal;
  }
  
  public void monthEnd()
  {
    numberOfWithdrawal = 0;
  }
  
  public double getInterest()
  {
    return intrate;
  }
  
  public double getMinBal()
  {
    return minbal;
  }
  
  public char getType()
  {
    return 'c';
  }
  
  public String toString()
  {
    return super.toString() + String.format("[withs=%d]",
                                                numberOfWithdrawal);
  }

}


  