package document.factories;

import document.products.*;

// Abstract factory class that defines the factory method pattern
// Subclasses implement createDocument() to create specific document types
public abstract class DocumentFactory {

    // Factory method - subclasses must implement this
    public abstract Document createDocument();

    // Template method that uses the factory method
    // Creates a document and performs common management operations
    public void manageDocument() {
        Document doc = createDocument();
        System.out.println("--- Managing " + doc.getType() + " document ---");
        doc.open();
        doc.save();
        doc.close();
        System.out.println("--- Finished managing " + doc.getType() + " document ---");
    }
}