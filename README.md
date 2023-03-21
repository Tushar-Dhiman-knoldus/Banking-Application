# Banking-Application

## Problem Statement

Create a banking application using Map[Long, Double] 
account number -> Long
account balance -> Double
case class Transactions(transactionId: Long, accountNumber: Long, transactionType: String, amount: Double)
Create methods for following operations:

   Create new account by allocating random account number and depositing balance in account
  // def createAccount(openingBalance: Double): Map[Long, Double] = ???

   List all accounts with balance
  // def listAccounts(): Map[Long, Double] = ???

   Fetch account balance using account number
  // def fetchAccountBalance(accountNumber: Long): Double = ???

   You have a list of Transactions having account number, transaction type (credit/debit) and amount. Create a method to update account balance that will take List of Transactions, as per transaction type it will credit or debit amount in account (Use Pattern matching)
  // def updateBalance(transactions: List[Transactions]): Map[Long, Int] = ???

   Delete account using account number
  // def deleteAccount(accountNumber: Long): Boolean = ???
  
  
  ## Code for the Problem
  
  ```
  package com.knoldus.bankingapplication

import scala.collection.mutable
import scala.util.Random

// case class for storing transaction information
case class Transactions(transactionId: Long, accountNumber: Long, transactionType: String, amount: Double)

class BankingApplication {

  // Using mutable map to store multiple account details
  private val accounts: mutable.Map[Long, Double] = mutable.Map()

  // creating the accounts of the users
  def createAccount(openingBalance: Double): mutable.Map[Long, Double] = {
    val accountNumber: Long = Random.nextLong().abs
    accounts += (accountNumber -> openingBalance)
  }

  // return all the accounts in the map
  def listAllAccounts(): mutable.Map[Long, Double] = {
    accounts
  }

  // return the account balance for the particular account
  def fetchAccountBalance(accountNumber: Long): Double = {
    accounts.getOrElse(accountNumber, 0.0)
  }

  // For updating the particular account with debit or credit.
  def updateBalance(transactions: List[Transactions]): mutable.Map[Long, Double] = {
    transactions.foreach(transaction => {
      val currentBalance = accounts.getOrElse(transaction.accountNumber, 0.0)
      transaction.transactionType match {
        case "Credit" => accounts += (transaction.accountNumber -> (currentBalance + transaction.amount))
        case "Debit" => if (currentBalance >= transaction.amount) {
          val updatedBalance = currentBalance - transaction.amount
          accounts += transaction.accountNumber -> updatedBalance
        } else throw new IllegalArgumentException("Balance is low")
        case _ => throw new IllegalArgumentException("Invalid transaction type")
      }
    })
    accounts
  }

  // For deleting a particular account from the map
  def deleteAccount(accountNumber: Long): Boolean = {
    if (accounts.contains(accountNumber)) {
      accounts.remove(accountNumber)
      true
    }
    else false
  }
}
```


## Testing Code for the Problem.

```
package com.knoldus.bankingapplication

import org.scalatest.funsuite.AnyFunSuite
import scala.collection.mutable
import scala.util.Random

class BankingApplicationTest extends AnyFunSuite {

  val bankingApplication = new BankingApplication()

  bankingApplication.createAccount(10000)
  bankingApplication.createAccount(5000)
  bankingApplication.createAccount(20000)
  bankingApplication.createAccount(30000)

  val listOfAllAccounts: mutable.Map[Long, Double] = bankingApplication.listAllAccounts()
  val listOfAccountNumbers: List[Long] = listOfAllAccounts.keySet.toList
  val currentBalance: Double = bankingApplication.fetchAccountBalance(listOfAccountNumbers.head)

  test("fetchAccountBalance should return the balance of the given account") {
    assert(bankingApplication.fetchAccountBalance(listOfAccountNumbers.head) === currentBalance)
  }

  test("updateBalance should credit the balance of the given account based on the given transaction") {
    val transaction = List(Transactions(Random.nextInt().abs, listOfAccountNumbers.head, "Credit", 1000))
    val beforeUpdateBalance = bankingApplication.fetchAccountBalance(listOfAccountNumbers.head)
    val updatedBalance = bankingApplication.updateBalance(transaction)
    assert(updatedBalance(listOfAccountNumbers.head) > beforeUpdateBalance)
  }

  test("updateBalance should debit the balance of the given account based on the given transaction") {
    val transaction = List(Transactions(Random.nextInt().abs, listOfAccountNumbers.head, "Debit", 1000))
    val beforeUpdateBalance = bankingApplication.fetchAccountBalance(listOfAccountNumbers.head)
    val updatedBalance = bankingApplication.updateBalance(transaction)
    assert(updatedBalance(listOfAccountNumbers.head) < beforeUpdateBalance)
  }

  test("deleteAccount should delete the account successful") {
    assert(bankingApplication.deleteAccount(listOfAccountNumbers(3)) === true)
  }

}
```

