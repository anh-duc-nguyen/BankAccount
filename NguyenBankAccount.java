import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *  A bank account has a balance that can be changed by
 *  deposits and withdrawals.
 *  @author Jon Beck
 *  @version 10 November 2017
 */
public abstract class NguyenBankAccount extends NguyenBank
{
  private String name;
  private double balance;
  private static int accountNumber;
  private static int nextAccountNumberToUse = 100;
  public static final int MAX_NAME_LENGTH = 20;
  public static final int RECORD_SIZE = 52;
  public static final int PADDED_FOR_CHECKING_ACCOUNT = 52 - 40 - 2;

  /**
   * Constructs a bank account with a zero balance.
   * @param name the name of the account holder
   */
  public NguyenBankAccount(String name)
  {
    this.name = name;
    balance = 0.0;
    accountNumber = nextAccountNumberToUse;
    nextAccountNumberToUse++;
  }

  /**
   * Constructs a bank account with a given balance.
   * @param name the name of the account holder
   * @param initialBalance the initial balance
   */
  public NguyenBankAccount(String name, double initialBalance)
  {
    this.name = name;
    balance = initialBalance;
    accountNumber = nextAccountNumberToUse;
    nextAccountNumberToUse++;
  }

  /**
   * The last account number used is the number of the most recent
   * account created.
   * @return the number of the most recently created account
   */
  public static int getLastAccountNumberUsed()
  {
    return nextAccountNumberToUse - 1;
  }

  /**
   * Deposits money into the account.
   * @param amount the amount of money to deposit
   */
  private static int amount;
  public void deposit(double amount)
  {
    balance += amount;
  }

  /**
   * Withdraws money from the account.
   * @param amount the amount of money to withdraw
   */
  public void withdraw(double amount)
  {
    balance -= amount;
  }

  /**
   * Gets the account balance.
   * @return the account balance
   */
  public double getBalance()
  {
    return balance;
  }

  /**
   * Find out this account's number
   * @return the account number
   */
  public int getAccountNumber()
  {
    return accountNumber;
  }

  /**
   * Accessor for the name on the account
   * @return the name on the account
   */
  public String getName()
  {
    return name;
  }

  /**
   * Generate the fixed-length string of the account name padded
   * with spaces necessary for writing to the data file
   * @return a space-padded fixed-length string containing the account name
   */
  public String getPaddedName()
  {
    String paddedName = name;
    if (paddedName.length() > MAX_NAME_LENGTH)
    {
      return paddedName.substring(0, MAX_NAME_LENGTH);
    }

    int spaces_to_add = MAX_NAME_LENGTH - paddedName.length();
    for (int i = 0; i < spaces_to_add; i++)
    {
      paddedName += " ";
    }
    return paddedName;
  }
  
  public String padCheckingAccount()
  {
    String addedSpace = "";
    for (int i = 0; i < PADDED_FOR_CHECKING_ACCOUNT; i++)
    {
      addedSpace += " ";
    }
    return addedSpace;
  }

  /**
   * Override Object toString for simple reporting of the account info
   * @return a string representation of the account
   */
  public String toString()
  {
    return getClass().getName() + String.format("[%s,acct#=%d,bal=%1.2f]",
                                                name, accountNumber, balance);
  }

  /**
   * Do the month-end processing
   */
  public abstract void monthEnd();
  public abstract double getInterest();
  public abstract double getMinBal();
  public abstract int getNumberOfWithdrawal();
  public abstract char getType();
  /**
   * Read the account information
   * @param file the file for the bank
   * @param position the account number
   * @return account information
   */
  

}