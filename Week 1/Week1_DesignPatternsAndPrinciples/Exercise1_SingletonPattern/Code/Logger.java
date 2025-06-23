import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

// Singleton logger class - only one instance exists throughout the application
// Thread-safe implementation using eager initialization
public class Logger {
    
    // Format for timestamps in log messages
    private static final DateTimeFormatter TIMESTAMP_FORMAT = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    
    // Single instance created when class loads
    private static final Logger instance = new Logger();
    
    // Different severity levels for log messages
    public enum LogLevel {
        DEBUG("DEBUG", "\u001B[36m"),   // Cyan
        INFO("INFO", "\u001B[32m"),     // Green  
        WARN("WARN", "\u001B[33m"),     // Yellow
        ERROR("ERROR", "\u001B[31m"),   // Red
        FATAL("FATAL", "\u001B[35m");   // Magenta
        
        private final String name;
        private final String color;
        
        LogLevel(String name, String color) {
            this.name = name;
            this.color = color;
        }
        
        public String getName() { return name; }
        public String getColor() { return color; }
    }
    
    // Store log messages and settings
    private final List<String> logHistory;
    private final ReentrantLock logLock;
    private LogLevel currentLogLevel;
    private boolean enableFileLogging;
    private boolean enableConsoleColors;
    
    private static final String RESET_COLOR = "\u001B[0m";

    // Private constructor prevents creating multiple instances
    private Logger() {
        logHistory = new ArrayList<>();
        logLock = new ReentrantLock();
        currentLogLevel = LogLevel.INFO;
        enableFileLogging = false;
        enableConsoleColors = true;
        
        System.out.println("Logger created at: " + 
                         TIMESTAMP_FORMAT.format(LocalDateTime.now()));
    }
    
    // Get the single logger instance
    public static Logger getInstance() {
        return instance;
    }
    
    // Main logging method that handles different severity levels
    public void log(LogLevel level, String message) {
        // Skip if message level is below current threshold
        if (level.ordinal() < currentLogLevel.ordinal()) {
            return;
        }
        
        logLock.lock();
        try {
            String timestamp = TIMESTAMP_FORMAT.format(LocalDateTime.now());
            String formattedMessage = String.format("[%s] %s: %s", 
                                                   timestamp, level.getName(), message);
            
            // Print to console with colors if enabled
            if (enableConsoleColors) {
                System.out.println(level.getColor() + formattedMessage + RESET_COLOR);
            } else {
                System.out.println(formattedMessage);
            }
            
            // Keep history of all messages
            logHistory.add(formattedMessage);
            
            // Write to file if enabled
            if (enableFileLogging) {
                simulateFileLogging(formattedMessage);
            }
            
        } finally {
            logLock.unlock();
        }
    }
    
    // Convenience methods for each log level
    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }
    
    public void info(String message) {
        log(LogLevel.INFO, message);
    }
    
    public void warn(String message) {
        log(LogLevel.WARN, message);
    }
    
    public void error(String message) {
        log(LogLevel.ERROR, message);
    }
    
    public void fatal(String message) {
        log(LogLevel.FATAL, message);
    }
    
    // Default to info level for simple log() calls
    public void log(String message) {
        info(message);
    }
    
    // Change minimum log level to show
    public void setLogLevel(LogLevel level) {
        this.currentLogLevel = level;
        info("Log level changed to: " + level.getName());
    }
    
    // Toggle file logging on/off
    public void enableFileLogging(boolean enable) {
        this.enableFileLogging = enable;
        info("File logging " + (enable ? "enabled" : "disabled"));
    }
    
    // Toggle colored console output
    public void enableConsoleColors(boolean enable) {
        this.enableConsoleColors = enable;
    }
    
    // Get copy of all logged messages
    public List<String> getLogHistory() {
        logLock.lock();
        try {
            return new ArrayList<>(logHistory);
        } finally {
            logLock.unlock();
        }
    }
    
    // Remove all stored log messages
    public void clearHistory() {
        logLock.lock();
        try {
            logHistory.clear();
            info("Log history cleared");
        } finally {
            logLock.unlock();
        }
    }
    
    // Show current logger configuration
    public void printStatistics() {
        logLock.lock();
        try {
            System.out.println("\n=== LOGGER STATUS ===");
            System.out.println("Current Log Level: " + currentLogLevel.getName());
            System.out.println("Total Log Entries: " + logHistory.size());
            System.out.println("File Logging: " + (enableFileLogging ? "Enabled" : "Disabled"));
            System.out.println("Console Colors: " + (enableConsoleColors ? "Enabled" : "Disabled"));
            System.out.println("Logger Instance Hash: " + this.hashCode());
        } finally {
            logLock.unlock();
        }
    }
    
    // Simulate writing to a log file
    private void simulateFileLogging(String message) {
        try {
            Thread.sleep(1); // Pretend file operation takes time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    // Test method for concurrent logging
    public void logWithDelay(String message, long delayMs) {
        try {
            Thread.sleep(delayMs);
            info("Delayed message: " + message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            error("Thread interrupted during delayed logging");
        }
    }
}