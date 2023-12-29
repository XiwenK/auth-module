const generateFinanceTransaction = () => ({
  id: Math.floor(Math.random() * 10000),
  amount: Math.floor(Math.random() * 10000),
  date: new Date(),
  comment: 'Some comment ' + Math.floor(Math.random() * 100),
  terminal: 'Terminal ' + Math.floor(Math.random() * 100),
  card: 'Card ' + Math.floor(Math.random() * 100),
  merchant: 'Merchant ' + Math.floor(Math.random() * 100),
  mcc: 'MCC ' + Math.floor(Math.random() * 1000),
  currency: 'RUB',
  respCode: Math.floor(Math.random() * 100) + '',
});

const transactions = [];

for (let i = 0; i < 1000; i++) { transactions.push(generateFinanceTransaction()); }

const filterTransactions = (string, page, fetchCount, sortBy, descending) => {
  return [...transactions].splice(page * fetchCount, fetchCount)
};


const loadTransactions = (string, page, fetchCount, sortBy, descending) => {
  return Promise.resolve(filterTransactions(string, page, fetchCount, sortBy, descending));
};

const loadTransactionsCount = (string) => {
  return Promise.resolve(transactions.length);
}

export default {loadTransactions, loadTransactionsCount};
