package document.products;

// Excel document implementation with spreadsheet-specific operations
public class ExcelDocument implements Document {

    @Override
    public void open() {
        System.out.println("Opening Excel spreadsheet in Microsoft Excel...");
    }

    @Override
    public void save() {
        System.out.println("Saving Excel spreadsheet as .xlsx file...");
    }

    @Override
    public void close() {
        System.out.println("Closing Excel spreadsheet...");
    }

    @Override
    public String getType() {
        return "Excel";
    }
}