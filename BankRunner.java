import java.util.Scanner;

/**
 * Run the bank.
 * @author Jon Beck
 * @version 10 November 2017
 */
public class BankRunner
{
  private static final Scanner in = new Scanner(System.in);
  private static final NguyenBank bank = new NguyenBank();

  public static void main(String[] args)
  {
    boolean bankIsOpen = true;

    while (bankIsOpen)
    {
      System.out.println("\nC)reate account");
      System.out.println("D)eposit");
      System.out.println("W)ithdraw");
      System.out.println("S) diSplay all accounts");
      System.out.println("M)onth end");
      System.out.println("Q)uit");
      System.out.print("Choice: ");
      char choice = Character.toUpperCase(in.nextLine().charAt(0));
      if (choice == 'C')
      {
        createAccount();
      }
      else if (choice == 'D')
      {
        doDeposit();
      }
      else if (choice == 'W')
      {
        doWithdrawal();
      }
      else if (choice == 'S')
      {
        bank.displayAll();
      }
      else if (choice == 'M')
      {
        bank.processEndMonth();
      }
      else if (choice == 'Q')
      {
        System.out.println("Bank is now closed");
        bank.close();
        bankIsOpen = false;
      }
      else
      {
        System.out.println("Invalid input");
      }
    }
  }

  public static void createAccount()
  {
    System.out.print("Account name: ");
    String name = in.nextLine();
    System.out.print("C)hecking or S)avings: ");
    char choice = Character.toUpperCase(in.nextLine().charAt(0));
    if (choice == 'C')
    {
      int newAccountNumber = bank.newAccount(name);
      System.out.println("Created account " + newAccountNumber);
    }
    else if (choice == 'S')
    {
      System.out.print("Interest rate: ");
      double intrate = in.nextDouble();
      in.nextLine();
      int newAccountNumber = bank.newAccount(name, intrate);
      System.out.println("Created account " + newAccountNumber);
    }
    else
    {
      System.out.println("Invalid account type specified");
    }
  }

  public static void doDeposit()
  {
    NguyenBankAccount account = bank.selectAccount();
    if (account != null)
    {
      System.out.print("Enter deposit amount: ");
      String amountString = in.nextLine();
      double amount = 0.0;
      try
      {
        amount = Double.parseDouble(amountString);
      }
      catch (NumberFormatException e)
      {
        System.out.println("Invalid amount specified");
        return;
      }
      account.deposit(amount);
      bank.updateAccount(account);
    }
    else
    {
      System.out.println("No such account exists");
    }
  }

  public static void doWithdrawal()
  {
    NguyenBankAccount account = bank.selectAccount();
    if (account != null)
    {
      System.out.print("Enter withdrawal amount: ");
      String amountString = in.nextLine();
      double amount = 0.0;
      try
      {
        amount = Double.parseDouble(amountString);
      }
      catch (NumberFormatException e)
      {
        System.out.println("Invalid amount specified");
        return;
      }
      if (account.getBalance() >= amount)
      {
        account.withdraw(amount);
        bank.updateAccount(account);
      }
      else
      {
        System.out.println("Withdrawal not allowed");
      }
    }
    else
    {
      System.out.println("No such account exists");
    }
  }
}