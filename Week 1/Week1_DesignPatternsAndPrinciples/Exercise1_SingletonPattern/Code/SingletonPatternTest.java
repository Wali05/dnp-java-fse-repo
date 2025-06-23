import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Test program to verify the Singleton Logger works correctly
// Tests basic functionality, singleton behavior, and thread safety
public class SingletonPatternTest {

    public static void main(String[] args) {
        System.out.println("=== SINGLETON LOGGER TEST ===\n");

        // Run different test scenarios
        testBasicLogging();
        testSingletonBehavior();
        testServiceUsage();
        testLoggerFeatures();
        testConcurrentAccess();
        
        System.out.println("\nAll tests completed!");
    }

    // Test basic logging functionality
    private static void testBasicLogging() {
        System.out.println("=== TEST 1: BASIC LOGGING ===");
        
        Logger logger = Logger.getInstance();
        logger.info("Testing basic logging");
        logger.warn("This is a warning");
        logger.error("This is an error");
        
        System.out.println("Basic logging test passed!\n");
    }

    // Test that only one instance exists
    private static void testSingletonBehavior() {
        System.out.println("=== TEST 2: SINGLETON BEHAVIOR ===");
        
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        Logger logger3 = Logger.getInstance();
        
        logger1.info("Message from first reference");
        logger2.info("Message from second reference"); 
        logger3.info("Message from third reference");
        
        System.out.println("\nChecking if all references point to same instance:");
        System.out.println("Logger1 hash: " + logger1.hashCode());
        System.out.println("Logger2 hash: " + logger2.hashCode());
        System.out.println("Logger3 hash: " + logger3.hashCode());
        
        boolean sameInstance = (logger1 == logger2) && (logger2 == logger3);
        System.out.println("Same instance: " + sameInstance);
        
        if (sameInstance) {
            System.out.println("Singleton test passed!");
        } else {
            System.out.println("Singleton test failed!");
        }
        System.out.println();
    }
    
    // Test using logger from different service classes
    private static void testServiceUsage() {
        System.out.println("=== TEST 3: SERVICE USAGE ===");
        
        UserService userService = new UserService();
        PaymentService paymentService = new PaymentService();
        NotificationService notificationService = new NotificationService();
        
        userService.createUser("john_doe");
        paymentService.processPayment("TXN_001", 299.99);
        notificationService.sendNotification("Payment confirmed");
        
        System.out.println("Service integration test passed!\n");
    }
    
    // Test different logger features
    private static void testLoggerFeatures() {
        System.out.println("=== TEST 4: LOGGER FEATURES ===");
        
        Logger logger = Logger.getInstance();
        
        // Test different log levels
        logger.debug("Debug message (might not show)");
        logger.info("Info message");
        logger.warn("Warning message"); 
        logger.error("Error message");
        logger.fatal("Fatal message");
        
        // Change log level and test again
        logger.setLogLevel(Logger.LogLevel.DEBUG);
        logger.debug("Debug message (should show now)");
        
        // Test file logging
        logger.enableFileLogging(true);
        logger.info("Message logged to file");
        logger.enableFileLogging(false);
        
        // Show statistics
        logger.printStatistics();
        
        // Show log history sample
        List<String> history = logger.getLogHistory();
        System.out.println("\nLog history (last 3 entries):");
        int start = Math.max(0, history.size() - 3);
        for (int i = start; i < history.size(); i++) {
            System.out.println((i + 1) + ". " + history.get(i));
        }
        
        System.out.println("Logger features test passed!\n");
    }
    
    // Test thread safety
    private static void testConcurrentAccess() {
        System.out.println("=== TEST 5: THREAD SAFETY ===");
        
        final int NUM_THREADS = 5;
        final int MESSAGES_PER_THREAD = 3;
        
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        
        // Submit tasks to multiple threads
        for (int i = 0; i < NUM_THREADS; i++) {
            final int threadId = i;
            executor.submit(() -> {
                Logger logger = Logger.getInstance();
                for (int j = 0; j < MESSAGES_PER_THREAD; j++) {
                    logger.info(String.format("Thread-%d Message-%d (Hash: %d)", 
                                            threadId, j, logger.hashCode()));
                    try {
                        Thread.sleep(10); // Small delay
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
        }
        
        // Wait for completion
        executor.shutdown();
        try {
            if (executor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("Thread safety test completed successfully");
                
                Logger logger = Logger.getInstance();
                List<String> history = logger.getLogHistory();
                
                System.out.println("Total log entries: " + history.size());
                System.out.println("Expected minimum: " + (NUM_THREADS * MESSAGES_PER_THREAD));
                
            } else {
                System.out.println("Thread safety test timed out");
            }
        } catch (InterruptedException e) {
            System.out.println("Thread safety test interrupted");
            Thread.currentThread().interrupt();
        }
        
        System.out.println();
    }
}

// Sample service classes that use the logger
class UserService {
    private final Logger logger = Logger.getInstance();
    
    public void createUser(String username) {
        logger.info("UserService: Creating user - " + username);
        try {
            Thread.sleep(50); // Simulate work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logger.info("UserService: User created - " + username);
    }
}

class PaymentService {
    private final Logger logger = Logger.getInstance();
    
    public void processPayment(String transactionId, double amount) {
        logger.info(String.format("PaymentService: Processing payment %s for $%.2f", 
                                transactionId, amount));
        try {
            Thread.sleep(100); // Simulate payment processing
        } catch (InterruptedException e) {
            logger.error("PaymentService: Payment interrupted");
            Thread.currentThread().interrupt();
            return;
        }
        logger.info("PaymentService: Payment processed - " + transactionId);
    }
}

class NotificationService {
    private final Logger logger = Logger.getInstance();
    
    public void sendNotification(String message) {
        logger.info("NotificationService: Sending - " + message);
        try {
            Thread.sleep(30); // Simulate notification
        } catch (InterruptedException e) {
            logger.error("NotificationService: Notification interrupted");
            Thread.currentThread().interrupt();
            return;
        }
        logger.info("NotificationService: Notification sent");
    }
}
