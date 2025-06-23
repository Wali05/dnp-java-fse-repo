// Search utility for finding products in the e-commerce system
import java.util.Arrays;
import java.util.Comparator;

public class ProductSearchUtility {
    
    // Linear search through product array
    // Checks each element one by one until target is found
    public static Product findUsingLinearSearch(Product[] products, String targetName) {
        for (int i = 0; i < products.length; i++) {
            if (products[i].getProductName().equalsIgnoreCase(targetName)) {
                System.out.printf("Linear Search: Found '%s' at index %d after %d comparisons\n", 
                                targetName, i, i + 1);
                return products[i];
            }
        }
        System.out.printf("Linear Search: '%s' not found after %d comparisons\n", 
                        targetName, products.length);
        return null;
    }

    // Binary search for sorted product arrays
    // More efficient than linear search for large datasets
    public static Product findUsingBinarySearch(Product[] products, String targetName) {
        int left = 0, right = products.length - 1;
        int comparisons = 0;

        while (left <= right) {
            comparisons++;
            int middle = left + (right - left) / 2; // Avoid overflow
            int comparison = products[middle].getProductName().compareToIgnoreCase(targetName);

            if (comparison == 0) {
                System.out.printf("Binary Search: Found '%s' at index %d after %d comparisons\n", 
                                targetName, middle, comparisons);
                return products[middle];
            } else if (comparison < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        System.out.printf("Binary Search: '%s' not found after %d comparisons\n", 
                        targetName, comparisons);
        return null;
    }

    // Sort products alphabetically by name
    public static void sortProductsByName(Product[] products) {
        Arrays.sort(products, Comparator.comparing(
            product -> product.getProductName().toLowerCase()
        ));
    }

    // Find all products in a specific category
    public static Product[] searchByCategory(Product[] products, String category) {
        return Arrays.stream(products)
                    .filter(p -> p.getCategory().equalsIgnoreCase(category))
                    .toArray(Product[]::new);
    }

    // Show performance comparison between search algorithms
    public static void displayAnalysis() {
        System.out.println("\n=== SEARCH ALGORITHM COMPARISON ===");
        System.out.println("LINEAR SEARCH:");
        System.out.println("- Time: O(n) - checks every element");
        System.out.println("- Best case: O(1) if item is first");
        System.out.println("- Worst case: O(n) if item is last or missing");
        System.out.println("- Works on unsorted data");
        
        System.out.println("\nBINARY SEARCH:");
        System.out.println("- Time: O(log n) - eliminates half each step");
        System.out.println("- Best case: O(1) if item is in middle");
        System.out.println("- Worst case: O(log n)");
        System.out.println("- Requires sorted data");
        
        System.out.println("\nWHEN TO USE:");
        System.out.println("- Small datasets: Linear search is fine");
        System.out.println("- Large datasets: Binary search is much faster");
        System.out.println("- Real applications often use hash tables or databases");
    }
}
