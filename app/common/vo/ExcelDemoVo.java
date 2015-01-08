package common.vo;

/**
 * Created by guxuelong on 2015/1/7.
 */
public class ExcelDemoVo {

    private String fileName;
    private ExcelHeader header;
    private ExcelContainer container;
    private ExcelFooter footer;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ExcelHeader getHeader() {
        return header;
    }

    public void setHeader(ExcelHeader header) {
        this.header = header;
    }

    public ExcelContainer getContainer() {
        return container;
    }

    public void setContainer(ExcelContainer container) {
        this.container = container;
    }

    public ExcelFooter getFooter() {
        return footer;
    }

    public void setFooter(ExcelFooter footer) {
        this.footer = footer;
    }
}
