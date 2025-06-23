package document.factories;

import document.products.*;

// Concrete factory for creating Excel documents
public class ExcelDocumentFactory extends DocumentFactory {

    @Override
    public Document createDocument() {
        return new ExcelDocument();
    }
}