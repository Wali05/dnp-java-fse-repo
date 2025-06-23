package document.factories;

import document.products.*;

// Concrete factory for creating Word documents
public class WordDocumentFactory extends DocumentFactory {

    @Override
    public Document createDocument() {
        return new WordDocument();
    }
}
