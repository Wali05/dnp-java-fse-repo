// Financial forecasting using recursive algorithms
// Calculates future value of investments over time
public class FinancialForecaster {

    // Store previous calculations to avoid recalculating same values
    private static java.util.Map<String, Double> cache = new java.util.HashMap<>();

    // Basic recursive calculation: FV = PV * (1 + rate)^periods
    // Each call handles one period, then recurses for remaining periods
    public static double calculateFutureValueRecursive(double presentValue, double growthRate, int periods) {
        if (periods == 0) {
            return presentValue;
        }
        
        double nextValue = presentValue * (1 + growthRate);
        return calculateFutureValueRecursive(nextValue, growthRate, periods - 1);
    }

    public static double calculateFutureValueOptimized(double presentValue, double growthRate, int periods) {
        String key = String.format("%.2f_%.4f_%d", presentValue, growthRate, periods);
        
        if (cache.containsKey(key)) {
            System.out.println("Using cached result");
            return cache.get(key);
        }
        
        double result = calculateFutureValueRecursive(presentValue, growthRate, periods);
        cache.put(key, result);
        
        return result;
    }

    // Non-recursive version for comparison
    // Usually faster and doesn't risk stack overflow
    public static double calculateFutureValueIterative(double presentValue, double growthRate, int periods) {
        double result = presentValue;
        for (int i = 0; i < periods; i++) {
            result *= (1 + growthRate);
        }
        return result;
    }

    // Calculate annual growth rate from start and end values
    public static double calculateCAGR(double beginningValue, double endingValue, int years) {
        if (years == 0) return 0.0;
        return Math.pow(endingValue / beginningValue, 1.0 / years) - 1;
    }

    // Show different outcomes based on various growth rates
    public static void performScenarioAnalysis(double initialInvestment, int years) {
        double[] rates = {0.03, 0.05, 0.07, 0.10};
        String[] names = {"Conservative", "Moderate", "Aggressive", "Very Aggressive"};
        
        System.out.println("\n=== INVESTMENT SCENARIOS ===");
        System.out.printf("Starting amount: $%.2f\n", initialInvestment);
        System.out.printf("Time period: %d years\n\n", years);
        
        for (int i = 0; i < rates.length; i++) {
            double futureValue = calculateFutureValueOptimized(initialInvestment, rates[i], years);
            double profit = futureValue - initialInvestment;
            double profitPercent = (profit / initialInvestment) * 100;
            
            System.out.printf("%-15s (%.1f%%): $%,.2f (Profit: $%,.2f, %.1f%%)\n", 
                            names[i], rates[i] * 100, futureValue, profit, profitPercent);
        }
    }

    public static void main(String[] args) {
        System.out.println("FINANCIAL FORECASTING DEMO");
        System.out.println("===========================\n");

        double investment = 10000.0;
        double growthRate = 0.07; // 7% per year
        int years = 10;

        System.out.println("=== BASIC CALCULATION ===");
        System.out.printf("Investment: $%,.2f\n", investment);
        System.out.printf("Growth rate: %.1f%% per year\n", growthRate * 100);
        System.out.printf("Time period: %d years\n\n", years);

        // Test recursive method
        long start = System.nanoTime();
        double recursiveResult = calculateFutureValueRecursive(investment, growthRate, years);
        long recursiveTime = System.nanoTime() - start;

        System.out.printf("Recursive result: $%,.2f\n", recursiveResult);
        System.out.printf("Time taken: %,d nanoseconds\n\n", recursiveTime);

        // Test optimized method
        System.out.println("=== OPTIMIZED CALCULATION ===");
        start = System.nanoTime();
        double optimizedResult = calculateFutureValueOptimized(investment, growthRate, years);
        long optimizedTime = System.nanoTime() - start;

        System.out.printf("Optimized result: $%,.2f\n", optimizedResult);
        System.out.printf("Time taken: %,d nanoseconds\n", optimizedTime);

        // Test cache performance
        System.out.println("\nTesting cache...");
        start = System.nanoTime();
        double cachedResult = calculateFutureValueOptimized(investment, growthRate, years);
        long cachedTime = System.nanoTime() - start;
        System.out.printf("Cached result: $%,.2f\n", cachedResult);
        System.out.printf("Cached time: %,d nanoseconds\n", cachedTime);

        // Compare with iterative approach
        System.out.println("\n=== ITERATIVE COMPARISON ===");
        start = System.nanoTime();
        double iterativeResult = calculateFutureValueIterative(investment, growthRate, years);
        long iterativeTime = System.nanoTime() - start;

        System.out.printf("Iterative result: $%,.2f\n", iterativeResult);
        System.out.printf("Time taken: %,d nanoseconds\n", iterativeTime);

        // Show different scenarios
        performScenarioAnalysis(investment, years);

        // Test edge cases
        System.out.println("\n=== EDGE CASES ===");
        System.out.printf("Zero years: $%.2f\n", 
                        calculateFutureValueRecursive(1000.0, 0.05, 0));
        System.out.printf("Zero growth: $%.2f\n", 
                        calculateFutureValueRecursive(1000.0, 0.0, 5));
        System.out.printf("Negative growth: $%.2f\n", 
                        calculateFutureValueRecursive(1000.0, -0.02, 5));

        // Explain the approaches
        System.out.println("\n=== APPROACH COMPARISON ===");
        System.out.println("Recursive:");
        System.out.println("- Easy to understand and write");
        System.out.println("- Matches the mathematical definition");
        System.out.println("- Can cause stack overflow for large inputs");
        
        System.out.println("\nOptimized (with cache):");
        System.out.println("- Remembers previous calculations");
        System.out.println("- Much faster for repeated scenarios");
        System.out.println("- Uses more memory to store cache");
        
        System.out.println("\nIterative:");
        System.out.println("- Usually fastest approach");
        System.out.println("- No stack overflow risk");
        System.out.println("- Less intuitive than recursive");

        System.out.println("\nDone!");
    }
}