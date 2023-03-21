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


