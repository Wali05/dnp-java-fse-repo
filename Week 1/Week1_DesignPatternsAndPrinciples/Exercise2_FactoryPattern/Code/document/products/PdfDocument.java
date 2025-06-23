package document.products;

// PDF document implementation with PDF-specific operations
public class PdfDocument implements Document {

    @Override
    public void open() {
        System.out.println("Opening PDF document in PDF viewer...");
    }

    @Override
    public void save() {
        System.out.println("Saving PDF document (read-only format)...");
    }

    @Override
    public void close() {
        System.out.println("Closing PDF document...");
    }

    @Override
    public String getType() {
        return "PDF";
    }
}