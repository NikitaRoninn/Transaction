package transactions;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TransactionAnalyzer {
    private final List<Transaction> transactions;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public double calculateTotalBalance() {
        return transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public int countTransactionsByMonth(String monthYear) {
        return (int) transactions.stream()
                .filter(t -> {
                    LocalDate date = t.getLocalDate();
                    String transactionMonthYear = date.format(DateTimeFormatter.ofPattern("MM-yyyy"));
                    return transactionMonthYear.equals(monthYear);
                })
                .count();
    }

    public List<Transaction> findTopExpenses() {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .sorted(Comparator.comparing(Transaction::getAmount))
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Transaction> findLargestAndSmallestExpenses(String startDateStr, String endDateStr) {
        LocalDate start = LocalDate.parse(startDateStr, dateFormatter);
        LocalDate end = LocalDate.parse(endDateStr, dateFormatter);

        List<Transaction> periodExpenses = transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .filter(t -> {
                    LocalDate d = t.getLocalDate();
                    return !d.isBefore(start) && !d.isAfter(end);
                })
                .collect(Collectors.toList());

        if (periodExpenses.isEmpty()) return List.of();

        Transaction largestExpense = periodExpenses.stream()
                .min(Comparator.comparing(Transaction::getAmount))
                .orElse(null);

        Transaction smallestExpense = periodExpenses.stream()
                .max(Comparator.comparing(Transaction::getAmount))
                .orElse(null);

        return List.of(largestExpense, smallestExpense);
    }

    public Map<String, Double> getExpensesByCategory() {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(
                        Transaction::getDescription,
                        Collectors.summingDouble(Transaction::getAmount)
                ));
    }
}