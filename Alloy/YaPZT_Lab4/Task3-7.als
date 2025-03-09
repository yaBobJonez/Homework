sig Client {
	accounts: some Account
}
sig Account {
	balance: one Int,
	transactions: set Transaction
}
sig Transaction {
	to: one Account,
	amount: one Int
}

fact NoNegativeAmount {
	all a: Account | a.balance >= 0
	all t: Transaction | t.amount >= 0
}
fact NoOvercharging {
	all a: Account | sum(a.transactions.amount) <= a.balance
}

pred newTransaction(from: Account, t: Transaction) {
	from.transactions = from.transactions + t
	from.balance = from.balance - t.amount
	t.to.balance = t.to.balance + t.amount
}
run newTransaction for 5

assert NoNegativeTransactions {
	no t: Transaction | t.amount < 0
}
assert NoClientWithoutAccounts {
	no c: Client | no c.accounts
}

check NoNegativeTransactions for 5
check NoClientWithoutAccounts for 5
