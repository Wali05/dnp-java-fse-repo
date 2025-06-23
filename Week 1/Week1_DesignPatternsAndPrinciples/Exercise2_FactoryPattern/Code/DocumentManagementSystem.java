import document.factories.DocumentFactory;
import document.factories.ExcelDocumentFactory;
import document.factories.PdfDocumentFactory;
import document.factories.WordDocumentFactory;
import document.products.Document;

// Demo program showing the Factory Method pattern in action
// Different factories create different types of documents
public class DocumentManagementSystem {

    public static void main(String[] args) {
        DocumentFactory wordFactory = new WordDocumentFactory();
        DocumentFactory pdfFactory = new PdfDocumentFactory();
        DocumentFactory excelFactory = new ExcelDocumentFactory();

        System.out.println("=== DOCUMENT FACTORY DEMO ===");
        
        // Create and use a Word document
        System.out.println("\n--- Word Document ---");
        Document wordDoc = wordFactory.createDocument();
        System.out.println("Created: " + wordDoc.getType() + " Document");
        wordDoc.open();
        wordDoc.save();
        wordDoc.close();

        // Create and use a PDF document
        System.out.println("\n--- PDF Document ---");
        Document pdfDoc = pdfFactory.createDocument();
        System.out.println("Created: " + pdfDoc.getType() + " Document");
        pdfDoc.open();
        pdfDoc.save();
        pdfDoc.close();

        // Create and use an Excel document
        System.out.println("\n--- Excel Document ---");
        Document excelDoc = excelFactory.createDocument();
        System.out.println("Created: " + excelDoc.getType() + " Document");
        excelDoc.open();
        excelDoc.save();
        excelDoc.close();

        // Show factory's built-in document management
        System.out.println("\n=== FACTORY MANAGEMENT ===");
        wordFactory.manageDocument();
        pdfFactory.manageDocument();
        excelFactory.manageDocument();
        
        System.out.println("\nDemo completed!");
    }
}