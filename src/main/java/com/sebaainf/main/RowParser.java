package com.sebaainf.main;

import com.sebaainf.ismPoiLib.ConfigSheet;
import com.sebaainf.ismPoiLib.IPoiObject;
import com.sebaainf.ismPoiLib.IPoiParams;
import org.apache.poi.ss.usermodel.Row;

/**
 * Created by Ismail on 25/08/2019.
 */
public class RowParser {
    public IPoiObject generate(Row row) {
        IPoiParams notes;



        Student student = new Student();
        student.setId(row.getCell(ConfigSheet.ID_STUDENT_COL).getStringCellValue());
        student.setNom(row.getCell(ConfigSheet.NAME_STUDENT_COL).getStringCellValue());
        student.setPrenom(row.getCell(ConfigSheet.LASTName_STUDENT_COL).getStringCellValue());
        //student.setDateNaiss(row.getCell(ConfigSheet.DATENaiss_STUDENT_COL).getStringCellValue());


        if(row.getCell(ConfigSheet.NOTETP_STUDENT_COL) == null){
            notes = new NoTpNotesTrim();
        } else{
            notes = new WithTpNotesTrim();
            ((WithTpNotesTrim) notes).setNoteTp(row.getCell(ConfigSheet.NOTETP_STUDENT_COL).getNumericCellValue());
        }
        ((NotesTrimestre) notes).setNoteCont(row.getCell(ConfigSheet.NOTECONT_STUDENT_COL).getNumericCellValue());
        ((NotesTrimestre) notes).setNoteDev(row.getCell(ConfigSheet.NOTETEST_STUDENT_COL).getNumericCellValue());
        ((NotesTrimestre) notes).setNoteEx(row.getCell(ConfigSheet.NOTEEXAM_STUDENT_COL).getNumericCellValue());
        notes.calculate();


        student.setNotes(notes);

        return student;
    }
}
