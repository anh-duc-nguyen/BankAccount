import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NguyenBank
{
  private static final String filename = "bank.dat";
  private static ArrayList<NguyenBankAccount> accounts = new ArrayList<>();
  private static RandomAccessFile bank;
  
  public NguyenBank() 
  {
    try
    {
      bank = new RandomAccessFile(filename, "rw");
      long numberOfRecords = bank.length() / NguyenBankAccount.RECORD_SIZE;
      for (int i = 0; i < numberOfRecords; i++)
      {
        accounts.add(readAccount(bank, i));
        
      }
      System.out.print(bank.length());
    }
    catch (IOException e)
    {
      System.out.println("Died during read");
      e.printStackTrace();
    }
  }

  /**
   * Read the account information
   * @param file the file for the bank
   * @param position the account number
   * @return account information
   */
  public static NguyenBankAccount readAccount(RandomAccessFile file, int position)
    throws IOException
  {
    file.seek(position * NguyenBankAccount.RECORD_SIZE);
    char type = file.readChar();
    
    if (type == 'c')
    {
      NguyenBankAccount account1 = new NguyenCheckingAccount(100.0, 2, "Jon");
      System.out.println(account1.toString());
      int accountNumber = file.readInt();
      double balance = file.readDouble();
      int numberOfWithdrawal = file.readInt();
      String name = file.readUTF();
      name.trim();
      return  new NguyenCheckingAccount(balance, numberOfWithdrawal, name);
    }
    else
    {
      int accountNumber = file.readInt();
      double balance = file.readDouble();
      double minbal = file.readDouble();
      double intrate = file.readDouble();
      String name = file.readUTF();
      return new NguyenSavingsAccount(balance, minbal, intrate, name);
    }
  }
    
  public static void writeAccount(RandomAccessFile file, NguyenBankAccount a, int position)
    throws IOException
  {
    file.seek(position * NguyenBankAccount.RECORD_SIZE);
    if (a.getType() == 'c')
    {
      file.writeChar('c');
      file.writeInt(a.getAccountNumber());
      file.writeDouble(a.getBalance());
      file.writeInt(a.getNumberOfWithdrawal());
      file.writeUTF(a.getPaddedName());
      file.writeUTF(a.padCheckingAccount());
    }
    else
    {
      file.seek(position * NguyenBankAccount.RECORD_SIZE);
      file.writeChar('s');
      file.writeInt(a.getAccountNumber());
      file.writeDouble(a.getBalance());
      file.writeDouble(a.getMinBal());
      file.writeDouble(a.getInterest());
      file.writeUTF(a.getPaddedName());
      
    }
  }

  public static void displayAll()
  {
    System.out.println("Acct Type Name                  Balance  Withdrawals  Min Bal   Int Rate");
    System.out.println("---- ---- --------------------  -------     ----      -------   --------");
    for (NguyenBankAccount a : accounts)
    {
      System.out.printf("%d    %c  %s   %4.2f         %d       %4.2f     %1.1f\n",
                        a.getAccountNumber(), a.getType(), a.getPaddedName(),
                        a.getBalance(), a.getNumberOfWithdrawal(), a.getMinBal(), a.getInterest());
    }
  }
  
  public int newAccount (String name)
  {
    NguyenBankAccount account = new NguyenCheckingAccount(name);
    accounts.add(account);
    return account.getAccountNumber();
  }
  
  public int newAccount (String name, double intrate)
  {
    
    NguyenBankAccount account = new NguyenSavingsAccount(name, intrate);
    accounts.add(account);
    return account.getAccountNumber();
  }
  
  public void processEndMonth()
  {
    for (NguyenBankAccount a : accounts)
    {
      a.monthEnd();
    }
  }
  
  private static final Scanner in = new Scanner(System.in);
  public NguyenBankAccount selectAccount()
  {
    System.out.println ("Enter account number: ");
    int accountNumber = in.nextInt();
    NguyenBankAccount account = accounts.get(accountNumber - 100);
    return (account);
    
  }
  
  public void close() 
  {
    try
    {
      for (NguyenBankAccount a : accounts)
      {
        updateAccount(a);
        System.out.println(a.toString());
      }
      bank.close();
    }
    catch (IOException e)
    {
      System.out.println("Died during write");
      e.printStackTrace();
    }
  }

  public void updateAccount(NguyenBankAccount account)
  {
    try
    {
      writeAccount(bank, account, (accounts.indexOf(account)));
    }
    catch (IOException e)
    {
      System.out.println("Died during write");
      e.printStackTrace();
    }
  }
}