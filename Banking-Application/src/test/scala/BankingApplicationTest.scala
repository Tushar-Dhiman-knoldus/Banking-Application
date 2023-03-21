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

