import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;

public class Main {
    public static void main(String[] args) {

        String filePath = "src/files/Nums.txt";

        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ExecutorService executor = Executors.newFixedThreadPool(lines.size());

        try {


            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length != 2) {
                    System.err.println("Invalid line: " + line);
                    continue;
                }

                int firstNum = Integer.parseInt(parts[0].trim());
                int secondNum = Integer.parseInt(parts[1].trim());

                executor.submit(() -> {
                    int result = calculateGCD.apply(firstNum, secondNum);
                    System.out.println("Thread # " + Thread.currentThread().getId() + " - GCD of " + firstNum + " and " + secondNum + " is " + result);
                });
            }

        } catch (NumberFormatException e) {
            System.err.println("Invalid number format: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }

    private static final BiFunction<Integer, Integer, Integer> calculateGCD = (firstNum, secondNum) -> {
        while (secondNum != 0) {
            int temp = secondNum;
            secondNum = firstNum % secondNum;
            firstNum = temp;
        }
        return firstNum;
    };
}
