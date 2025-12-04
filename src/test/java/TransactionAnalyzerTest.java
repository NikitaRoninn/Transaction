import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import transactions.Transaction;
import transactions.TransactionAnalyzer;

import java.util.Arrays;
import java.util.List;

class TransactionAnalyzerTest {

    // Тест 1: Проверка расчета баланса (из презентации)
    @Test
    public void testCalculateTotalBalance() {
        Transaction t1 = new Transaction("01-01-2023", 100.0, "Дохід");
        Transaction t2 = new Transaction("02-01-2023", -50.0, "Витрата");
        Transaction t3 = new Transaction("03-01-2023", 150.0, "Дохід");
        List<Transaction> transactions = Arrays.asList(t1, t2, t3);

        TransactionAnalyzer analyzer = new TransactionAnalyzer(transactions);
        double result = analyzer.calculateTotalBalance();

        Assertions.assertEquals(200.0, result, "Розрахунок загального балансу неправильний");
    }

    // Тест 2: Проверка количества транзакций за месяц (из презентации)
    @Test
    public void testCountTransactionsByMonth() {
        Transaction t1 = new Transaction("01-02-2023", 50.0, "Дохід");
        Transaction t2 = new Transaction("15-02-2023", -20.0, "Витрата");
        Transaction t3 = new Transaction("05-03-2023", 100.0, "Дохід");
        List<Transaction> transactions = Arrays.asList(t1, t2, t3);

        TransactionAnalyzer analyzer = new TransactionAnalyzer(transactions);
        int countFeb = analyzer.countTransactionsByMonth("02-2023");

        Assertions.assertEquals(2, countFeb, "Кількість транзакцій за лютий неправильна");
    }

    // --- ИНДИВИДУАЛЬНОЕ ЗАДАНИЕ: Тесты ---

    // Тест 3: Проверка определения 10 найбільших витрат (проверяем логику сортировки)
    @Test
    public void testFindTopExpenses() {
        Transaction t1 = new Transaction("01-01-2023", -100.0, "Мало");
        Transaction t2 = new Transaction("02-01-2023", -5000.0, "Багато");
        Transaction t3 = new Transaction("03-01-2023", -200.0, "Середньо");
        List<Transaction> transactions = Arrays.asList(t1, t2, t3);

        TransactionAnalyzer analyzer = new TransactionAnalyzer(transactions);
        List<Transaction> top = analyzer.findTopExpenses();

        // Ожидаем, что первым будет -5000 (самая большая трата по модулю, самое маленькое число)
        Assertions.assertEquals(-5000.0, top.get(0).getAmount());
        Assertions.assertEquals("Багато", top.get(0).getDescription());
    }

    // Тест 4: Тест парсинга (имитация CSVReader)
    @Test
    public void testCsvParsingLogic() {
        String csvLine = "05-12-2023, -450, Сільпо";
        String[] values = csvLine.split(",");

        Transaction t = new Transaction(values[0].trim(), Double.parseDouble(values[1].trim()), values[2].trim());

        Assertions.assertEquals("05-12-2023", t.getDate());
        Assertions.assertEquals(-450.0, t.getAmount());
        Assertions.assertEquals("Сільпо", t.getDescription());
    }
}