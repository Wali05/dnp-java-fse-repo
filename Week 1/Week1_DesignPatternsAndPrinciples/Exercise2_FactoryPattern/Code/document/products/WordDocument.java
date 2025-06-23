package document.products;

// Word document implementation with specific Word operations
public class WordDocument implements Document {

    @Override
    public void open() {
        System.out.println("Opening Word document in Microsoft Word...");
    }

    @Override
    public void save() {
        System.out.println("Saving Word document as .docx file...");
    }

    @Override
    public void close() {
        System.out.println("Closing Word document...");
    }

    @Override
    public String getType() {
        return "Word";
    }
}