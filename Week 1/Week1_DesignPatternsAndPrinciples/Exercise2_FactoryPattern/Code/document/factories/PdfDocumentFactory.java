package document.factories;

import document.products.*;

// Concrete factory for creating PDF documents
public class PdfDocumentFactory extends DocumentFactory {

    @Override
    public Document createDocument() {
        return new PdfDocument();
    }
}