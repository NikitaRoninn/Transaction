package transactions;

import java.util.List;
import java.util.Map;

public class TransactionReportGenerator {

    public void printBalanceReport(double totalBalance) {
        System.out.println("Загальний баланс: " + totalBalance);
    }

    public void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.println("Кількість транзакцій за " + monthYear + ": " + count);
    }

    public void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("10 найбільших витрат:");
        for (Transaction expense : topExpenses) {
            System.out.println(expense.getDescription() + ": " + expense.getAmount());
        }
    }

    public void printExpenseReportByCategory(Map<String, Double> expensesByCategory) {
        System.out.println("\n--- Звіт по витратах за категоріями ---");
        for (Map.Entry<String, Double> entry : expensesByCategory.entrySet()) {
            String category = entry.getKey();
            double amount = Math.abs(entry.getValue()); // Берем модуль для красоты
            int stars = (int) (amount / 1000); // 1 звездочка = 1000 у.е. (можно настроить)

            String graph = "*".repeat(Math.max(0, stars));
            System.out.printf("%-20s | %-10.2f | %s%n", category, amount, graph);
        }
    }
}
