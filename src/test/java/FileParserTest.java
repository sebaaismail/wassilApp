import com.sebaainf.ismPoiLib.IPoiObject;
import com.sebaainf.main.ClasseRoomSheet;
import com.sebaainf.main.MyFileParser;
import com.sebaainf.main.Student;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.Map;

/**
 * Created by Ismail on 26/08/2019.
 */
public class FileParserTest {

    public static void main(String[] args) {
        File enterFile = new File("C:\\MySources\\Java\\wassilApp\\src\\test\\java//testFile.xls");
        //System.out.println("getParentFile : " + enterFile.getAbsolutePath());
        MyFileParser fp = new MyFileParser(enterFile);
        fp.parse();
        System.out.println(fp.prepareFileResult().toString());

    }

    @Test
    public void prepareFileResultTest() throws Exception{
        File enterFile = new File("C:\\MySources\\Java\\wassilApp\\src\\test\\java\\testFile.xls");
        MyFileParser fp = new MyFileParser(enterFile);
        File resultFile=  fp.prepareFileResult();
        fp.parse();
        for(ClasseRoomSheet cl:fp.getClasseRoomsCollection().values()){
            System.out.println(" ClasseRoom : " + cl.getSheet().getSheetName() + " ,"
                    + cl.getObjectsCollection().size() + " students");

            for(IPoiObject stdObj:cl.getObjectsCollection().values()){
                Student std = (Student) stdObj;
                System.out.println("Student : " + std.getNom() + " " + std.getPrenom());

            }
        }

        assertEquals(resultFile.getName(),"testFile + results.xls");
    }
}
