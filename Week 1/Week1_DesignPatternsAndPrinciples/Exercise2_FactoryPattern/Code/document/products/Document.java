package document.products;

// Interface that all document types must implement
// Defines common operations for working with documents
public interface Document {
    void open();
    void save();
    void close();
    String getType();
}
