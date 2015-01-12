package common.util;

import common.exceptions.ConverterException;
import common.vo.ExcelContainer;
import common.vo.ExcelDemoVo;
import common.vo.ExcelFooter;
import common.vo.ExcelHeader;
import dto.BaseXlsDto;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import play.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guxuelong on 2014/12/16.
 */
public class ExcelUtil {
    private static List<String> xlsType = new ArrayList<>();

    static {
        xlsType.add("xls");
        xlsType.add("XLS");
    }

    /**
     * 读取Excel文件内容 并存入指定对象列表
     *
     * @param file
     * @param fileType
     * @param xlsDto
     * @return
     * @throws Exception
     */
    public static List<BaseXlsDto> readExcel(File file, String fileType, BaseXlsDto xlsDto) throws Exception {
        if (xlsType.contains(fileType)) {
            return readXls(file, xlsDto);
        }
        return readXlsx(file, xlsDto);
    }


    /**
     * 导出Excel
     *
     * @param excelDemoVo
     */
    public static File exportExcel(ExcelDemoVo excelDemoVo) throws Exception{

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ExcelHeader header = excelDemoVo.getHeader();
        ExcelContainer container = excelDemoVo.getContainer();
        ExcelFooter footer = excelDemoVo.getFooter();
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(header.getSheetName());
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFFont front  = wb.createFont();
        front.setFontHeightInPoints((short) 10);// 字号
        front.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
        HSSFCellStyle styleTitle = wb.createCellStyle();
        styleTitle.setFont(front);

        HSSFCellStyle style = wb.createCellStyle();
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("");
        cell = row.createCell((short) 1);
        cell.setCellValue(header.getTitles());
        cell.setCellStyle(styleTitle);
        row = sheet.createRow((int) 1);

        //第五步，写入实体数据 实际应用中这些数据从数据库得到
        // container
        CellStyle styleFiledName = wb.createCellStyle();
        styleFiledName.setFillPattern(HSSFCellStyle.ALT_BARS);
        styleFiledName.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        styleFiledName.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);

        List<String> fieldName = container.getFieldName();
        row = sheet.createRow((int) 2);

        for (int i = 0; i < fieldName.size(); i++)
        {
            cell = row.createCell((short) i);
            cell.setCellValue(fieldName.get(i));
            cell.setCellStyle(styleFiledName);
        }
        int rowNum = 0;
        List<List<String>> fieldContexts = container.getFieldContext();
        for (int i = 0; i < fieldContexts.size(); i++)
        {
            row = sheet.createRow((int) 3 + i);
            List<String> context = fieldContexts.get(i);
            for (int j = 0; j < context.size();j++){
                cell = row.createCell((short) j);
                cell.setCellValue(context.get(j));
                cell.setCellStyle(style);
            }
            rowNum++;
        }

        // footer
        fieldName = footer.getFieldName();
        row = sheet.createRow((int) 3 + rowNum);
        for (int i = 0; i < fieldName.size(); i++)
        {
            cell = row.createCell((short) i);
            cell.setCellValue(fieldName.get(i));
            cell.setCellStyle(styleFiledName);
        }

        fieldContexts = footer.getFieldContext();
        for (int i = 0; i < fieldContexts.size(); i++)
        {
            row = sheet.createRow((int) 4 + rowNum + i);
            List<String> context = fieldContexts.get(i);
            for (int j = 0; j < context.size();j++){
                cell = row.createCell((short) j);
                cell.setCellValue(context.get(j));
                cell.setCellStyle(style);
            }
        }
        // 第六步，将文件存到指定位置
        File file = null;
        BufferedOutputStream stream = null;
        try {
            wb.write(bos);
            file = new File(excelDemoVo.getFileName());
            FileOutputStream fstream= new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            wb.write(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            stream.close();
        }
        return file;
    }


    /**
     *  * 读取xls文件内容
     *  *
     *  * @return List<T>对象
     *  * @throws IOException
     *  *    输入/输出(i/o)异常
     *  
     */
    private static List<BaseXlsDto> readXls(File file, BaseXlsDto xlsDto) throws Exception {
        InputStream is = new FileInputStream(file);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        List<BaseXlsDto> list = new ArrayList<>();
        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 循环行Row
            for (int rowNum = xlsDto.getStartRow(); rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow == null) {
                    continue;
                }
                BaseXlsDto xlsDtoTemp = xlsDto.getClass().newInstance();
                Map<String, Object> map = setMapValues(xlsDto, hssfRow);
                try {
                    ConverterUtil.convertMap2Object(map, xlsDtoTemp);
                } catch (ConverterException e) {
                    Logger.info("转换map2bean失败，请检查定义的xlsDto的格式是否正确！");
                }
                list.add(xlsDtoTemp);
            }
        }
        return list;
    }


    /**
     *  * 读取xls文件内容
     *  *
     *  * @return List<T>对象
     *  * @throws IOException
     *  *    输入/输出(i/o)异常
     *  
     */
    private static List<BaseXlsDto> readXlsx(File file, BaseXlsDto xlsDto) throws Exception {
        InputStream is = new FileInputStream(file);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        List<BaseXlsDto> list = new ArrayList<>();
        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // 循环行Row
            for (int rowNum = xlsDto.getStartRow(); rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow == null) {
                    continue;
                }
                BaseXlsDto xlsDtoTemp = xlsDto.getClass().newInstance();
                Map<String, Object> map = setMapValues(xlsDto, xssfRow);
                try {
                    ConverterUtil.convertMap2Object(map, xlsDtoTemp);
                } catch (ConverterException e) {
                    Logger.info("转换map2bean失败，请检查定义的xlsDto的格式是否正确！");
                }
                list.add(xlsDtoTemp);
            }
        }
        return list;
    }


    private static Map<String, Object> setMapValues(BaseXlsDto xlsDto, Row row) throws Exception {
        List<String> nameList = xlsDto.getNameList();
        List<String[]> nameAndRowNoList = xlsDto.getNameAndRowNoList();
        Map<String, Object> map = new HashMap<>();
        if (!nameList.isEmpty()) {
            for (int i = 0; i < xlsDto.getLength(); i++) {
                map.put(nameList.get(i), getValue(row.getCell(i)));
            }
            return map;
        }
        if (!nameAndRowNoList.isEmpty()) {
            for (int i = 0; i < xlsDto.getLength(); i++) {
                String[] strings = nameAndRowNoList.get(i);
                map.put(strings[0], getValue(row.getCell(Integer.valueOf(strings[1]))));
            }
        }
        return map;
    }

    /**
     *  * 得到Excel表中的值
     *  *
     *  * @param cell
     *  *     Excel中的每一个格子
     *  * @return Excel中每一个格子中的值
     *  
     */
    @SuppressWarnings("static-access")
    private static String getValue(Cell cell) {
        if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
            // 返回数值类型的值
            return String.valueOf(cell.getNumericCellValue());
        } else {
            // 返回字符串类型的值
            return String.valueOf(cell.getStringCellValue());
        }
    }
}