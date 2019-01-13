import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class App {


    public static void main(String args[]) throws Exception {
        Double note1, note2, note3, note4, moy = 0.0d;

        File file = new File("C:\\Users\\ismail\\Desktop\\test.Xls");
        FileInputStream fis = new FileInputStream(file);

        //Get the workbook instance for XLSX file
        HSSFWorkbook workbook = new HSSFWorkbook(fis);

        if(file.isFile() && file.exists())
        {
            System.out.println(
                    "openworkbook.xlsx file open successfully.");
        }
        else
        {
            System.out.println(
                    "Error to open openworkbook.xlsx file.");
        }

        int nbstudents = 0;
        System.out.println("NumberOfSheets  = " + workbook.getNumberOfSheets());

        for (int i = 0; i < workbook.getNumberOfSheets()-3; i++) {
            System.out.println("i  = " + i);


            HSSFSheet sheet = workbook.getSheetAt(i);
            FileOutputStream fos = new FileOutputStream(file);
            int r = 8;

            while (!sheet.getRow(r).getCell(1).getStringCellValue().isEmpty()) {
                nbstudents++;
                System.out.println(" row num " + r);
            HSSFCell cell = sheet.getRow(r).getCell(3);
            note1 = sheet.getRow(r).getCell(3).getNumericCellValue();

            note3 = sheet.getRow(r).getCell(5).getNumericCellValue();
            note4 = sheet.getRow(r).getCell(6).getNumericCellValue();

            if (sheet.getRow(r).getCell(4).getNumericCellValue() != 0) {
                note2 = sheet.getRow(r).getCell(4).getNumericCellValue();
                moy = (note1 + note2 + note3 + (note4 * 2)) / 5;
            } else {
                moy = (note1 + note3 + (note4 * 2)) / 4;
            }
            System.out.println(moy);
            //if (nbstudents < 96) {

                    sheet.getRow(r).getCell(7).setCellValue(takdirate(moy));
                    sheet.getRow(r).getCell(8).setCellValue(irchadate(moy));

            //}

                System.out.println("number students = " + nbstudents);

                r++;
                ;


        }
            workbook.write(fos);
            fos.close();
        }

    }

    private static String irchadate(Double moy) {
        if (moy >= 0 && moy <= 8) {
            return "انتبه، عليك بالإهتمام أكثر بالدراسة";
        } else if (moy > 8 && moy <= 9.99) {
            return "  عليك بمضاعفة الجهود";

        } else if (moy >= 10 && moy < 13) {
            return "بإمكانك تحقيق نتائج أفضل";

        } else if (moy >= 13 && moy < 14) {
            return "باستطاعتك التقدم أكثر";

        } else if (moy >= 14 && moy < 16) {
            return "واصل علي هذا المنوال";

        } else if (moy >= 16 && moy <= 20) {
            return "أشجعك واصل";

        }
        return "";
    }

    private static String takdirate(Double moy) {
        if (moy >= 0 && moy <= 8) {
            return "عمل ناقص";
        } else if (moy > 8 && moy <= 9.99) {
            return "تحت المتوسط";

        } else if (moy >= 10 && moy < 12) {
            return "متوسط";

        } else if (moy >= 12 && moy < 13) {
            return "عمل مقبول";

        } else if (moy >= 13 && moy < 14) {
            return "حسن";

        } else if (moy >= 14 && moy < 15.50) {
            return "جيد";

        } else if (moy >= 15.50 && moy < 17) {
            return "جيد جدا";

        } else if (moy >= 17 && moy < 18.50) {
            return "ممتاز ";

        } else if (moy >= 18.50 && moy <= 20) {
            return "ممتاز جدا ";

        }
        return "";
    }
}