package main;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ismail on 12/08/2019.
 */
public class GroupsMaker {
    private File preparedFile;


    public GroupsMaker(File file){
        this.preparedFile = file;
    }

    //*************************************************

    public File run(){

        Student student;
        UneClasse classeRes;
        String fileName = preparedFile.getParentFile() + "\\" + this.preparedFile.getName().replace("تحضير", "");

        File fileResult = new File(fileName);

        FileInputStream fis = null;

        try {
            fis = new FileInputStream(preparedFile);

            //Get the workbook instance for XLSX file
            HSSFWorkbook workbook = new HSSFWorkbook(fis);


            int nbstudents = 0;
            System.out.println("NumberOfSheets  = " + workbook.getNumberOfSheets());

            //for (int i = 0; i < workbook.getNumberOfSheets() - 3; i++) { // old version files

            FileOutputStream fos = new FileOutputStream(fileResult);


            int n = workbook.getNumberOfSheets();
            //iterate classes
            for (int i = 0; i < n; i++) { // for each i there is classe

                classeRes = new UneClasse();
                HSSFSheet sheet = workbook.getSheetAt(i);
                String nameSheet = sheet.getSheetName();
                workbook.setSheetName(i, "to Delete " + i);

                HSSFSheet newSheet = workbook.createSheet(nameSheet);


                System.out.println("i  = " + i);



                newSheet.setRightToLeft(true);


                System.out.println("feuille : " + sheet.getSheetName());


                //FileOutputStream fos = new FileOutputStream(file);

                int r = 5;


                //Cell ccc = sheet.getRow(34).getCell(1);

                //System.out.println(sheet.getRow(34).getCell(1).getStringCellValue());
                Iterator<Row> rowIterator = sheet.rowIterator();


                    //iterate students
                    while (rowIterator.hasNext()) {

                        Row row = rowIterator.next();

                        //if ((row.getRowNum() >= 8 && nbstudents != 26 && nbstudents != 76 & i!=3)|| (row.getRowNum() >= 8 && i==3 && r != 20)){
                        if (sheet.getRow(r) != null && sheet.getRow(r).getRowNum() >= 5
                                && !sheet.getRow(r).getCell(6).getStringCellValue().contains("اللقب")) {
                            //System.out.println("row cell" + row.getCell(0).getStringCellValue());
                            //while (!sheet.getRow(r).getCell(1).getStringCellValue().isEmpty()) {  //old version of files
                            nbstudents++;

                            System.out.println(" current sheet :  " + nameSheet);
                            System.out.println(" sheet.getRow(r) :  " + sheet.getRow(r));

                            student = new Student(sheet.getRow(r), true);

                            student.setNumRow(r);
                            System.out.println(" row num " + r);
                            System.out.println(" student : " + student.getFullName());

                            classeRes.addStudent(student);

                            System.out.println("number students = " + nbstudents);
                            //globalRes.add(classeRes);

                        }
                        r++;

                    }

                int size = classeRes.getListStudents().size();
                classeRes.setGroupeA(new ArrayList<>());
                classeRes.setGroupeB(new ArrayList<>());

                ArrayList<ComputerMembers> listCMA = new ArrayList<ComputerMembers>();
                ArrayList<ComputerMembers> listCMB = new ArrayList<ComputerMembers>();
                ComputerMembers pcMembrsA, pcMembrsB;


                // to set list students of group A and B
                int j=0;

                for (Student st : classeRes.getListStudents()) {

                    if (j < (size+1) / 2) {
                        classeRes.getGroupeA().add(st);
                    } else {
                        classeRes.getGroupeB().add(st);
                    }

                    j++;
                }
                //----------------------------------

                for(int id = 1; id <=15; id++ ) {
                    pcMembrsA = new ComputerMembers();
                    pcMembrsA.setIdComputer(-1);
                    pcMembrsA.setGroup("A");
                    listCMA.add(pcMembrsA);

                    pcMembrsB = new ComputerMembers();
                    pcMembrsB.setIdComputer(-1);
                    pcMembrsB.setGroup("B");
                    listCMB.add(pcMembrsB);

                }

                dispatchStudents(classeRes.getGroupeA(), listCMA);
                dispatchStudents(classeRes.getGroupeB(), listCMB);

                Collections.sort(listCMA);
                Collections.sort(listCMB);

/*

                //TODO a refaire
                int size = classeRes.getListStudents().size();
                classeRes.setGroupeA(new ArrayList<>());
                classeRes.setGroupeB(new ArrayList<>());
                int j = 0;

                ArrayList<ComputerMembers> listCMA = new ArrayList<ComputerMembers>();
                ArrayList<ComputerMembers> listCMB = new ArrayList<ComputerMembers>();


                for(int id = 1; id <=15; id++ ) {
                    ComputerMembers pcMembrs = new ComputerMembers();
                    pcMembrs.setIdComputer(-1);
                    pcMembrs.setMembers(new ArrayList<>());
                    listCMA.add(pcMembrs);
                    listCMB.add(pcMembrs);

                }

                ComputerMembers cm;
                for (Student st : classeRes.getListStudents()) {

                    if (j < (size+1) / 2) {

                        cm = addToCM(st, listCMA);
                        cm.setGroup("A");

                    } else {
                        cm = addToCM(st, listCMB);
                        cm.setGroup("B");
                    }

                    j++;
                }
//*/
                //listCMA = redispatch(listCMA);
                //listCMB = redispatch(listCMB);

                CellStyle style = workbook.createCellStyle();
                HSSFFont font =workbook.createFont();
                font.setFontHeightInPoints((short)20);

                style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
                style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
                style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
                style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

                CellStyle style2 =  workbook.createCellStyle();
                style2.setFillForegroundColor(HSSFColor.RED.index);
                style2.setFont(font);

                newSheet.createRow(2);
                newSheet.getRow(2).createCell(6);
                newSheet.getRow(2).getCell(6).setCellStyle(style2);
                //newSheet.getRow(2).getCell(6).setCellValue("ضع حرف H امام التلاميذ ذكور");

                newSheet.createRow(4);
                newSheet.getRow(4).createCell(5);
                newSheet.getRow(4).createCell(6);
                newSheet.getRow(4).createCell(7);

                newSheet.getRow(4).getCell(5).setCellStyle(style);
                newSheet.getRow(4).getCell(6).setCellStyle(style);
                newSheet.getRow(4).getCell(7).setCellStyle(style);


                newSheet.getRow(4).getCell(5).setCellValue("الإسم واللقب");
                newSheet.getRow(4).getCell(6).setCellValue("الحاسوب");
                newSheet.getRow(4).getCell(7).setCellValue("الفوج");


                newSheet.createRow(((size+1) / 2) + 7);
                newSheet.getRow(((size+1) / 2) + 7).createCell(5);
                newSheet.getRow(((size+1) / 2) + 7).createCell(6);
                newSheet.getRow(((size+1) / 2) + 7).createCell(7);

                newSheet.getRow(((size+1) / 2) + 7).getCell(5).setCellValue("الإسم واللقب");
                newSheet.getRow(((size+1) / 2) + 7).getCell(6).setCellValue("الحاسوب");
                newSheet.getRow(((size+1) / 2) + 7).getCell(7).setCellValue("الفوج");

                newSheet.getRow(((size+1) / 2) + 7).getCell(5).setCellStyle(style);
                newSheet.getRow(((size+1) / 2) + 7).getCell(6).setCellStyle(style);
                newSheet.getRow(((size+1) / 2) + 7).getCell(7).setCellStyle(style);

                //workbook.write(fos);
                newSheet.autoSizeColumn(6);

                // for test
                //ArrayList<ComputerMembers> all =  listCMA;
                //all.addAll(listCMB);

                int numRow = 10;
                for(ComputerMembers comptMem :listCMA) {
                    for (Student st : comptMem.getMembers()) {

                            newSheet.createRow(numRow);
                            newSheet.getRow(numRow).createCell(5);
                            newSheet.getRow(numRow).createCell(6);
                            newSheet.getRow(numRow).createCell(7);

                            newSheet.getRow(numRow).getCell(5).setCellValue(st.getFullName());
                            newSheet.getRow(numRow).getCell(6).setCellValue(comptMem.getIdComputer());
                            newSheet.getRow(numRow).getCell(7).setCellValue(comptMem.getGroup());

                            newSheet.getRow(numRow).getCell(5).setCellStyle(style);
                            newSheet.getRow(numRow).getCell(6).setCellStyle(style);
                            newSheet.getRow(numRow).getCell(7).setCellStyle(style);
                        numRow++;

                        }

                    numRow++;
                    }
                for(ComputerMembers comptMem :listCMB) {
                    for (Student st : comptMem.getMembers()) {

                        newSheet.createRow(numRow);
                        newSheet.getRow(numRow).createCell(5);
                        newSheet.getRow(numRow).createCell(6);
                        newSheet.getRow(numRow).createCell(7);

                        newSheet.getRow(numRow).getCell(5).setCellValue(st.getFullName());
                        newSheet.getRow(numRow).getCell(6).setCellValue(comptMem.getIdComputer());
                        newSheet.getRow(numRow).getCell(7).setCellValue(comptMem.getGroup());

                        newSheet.getRow(numRow).getCell(5).setCellStyle(style);
                        newSheet.getRow(numRow).getCell(6).setCellStyle(style);
                        newSheet.getRow(numRow).getCell(7).setCellStyle(style);
                        numRow++;

                    }

                    numRow++;
                }

                newSheet.autoSizeColumn(5);

            }


            ArrayList listIndexes = new ArrayList();
            String nameSheet;
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                nameSheet = workbook.getSheetAt(i).getSheetName();
                if (!org.apache.commons.lang3.StringUtils.isNumeric(nameSheet)) {
                    listIndexes.add(nameSheet);
                    //workbook.removeSheetAt(i);
                    //workbook.write(fos);
                    System.out.println(workbook.getSheetAt(i).getSheetName() + " is deleted ........");
                }
            }

            for(Object name :listIndexes){
                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                    if (workbook.getSheetAt(i).getSheetName().equals(name)) {
                        workbook.removeSheetAt((Integer) i);
                    }
                }
            }

            workbook.write(fos);
            fos.close();




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return fileResult;
    }

    private void dispatchStudents(ArrayList<Student> groupeX, ArrayList<ComputerMembers> listCMX) {

        for(Student st:groupeX){
            addToCM(st, listCMX);
        }

        //equilibrer le poids nobre student / compute in term of male female

        int nbH=0, nbF=0 , nbdoubleH=0 ,nbdoubleF=0;
        Student hst, fst = null;
        int idCReady;
        for(ComputerMembers cm:listCMX){
            if(cm.getMembers().size() == 2){
                if(cm.getMembers().get(0).isMale()) nbdoubleH++;
                else nbdoubleF++;
            } else if(cm.getMembers().size() == 1){
                if(cm.getMembers().get(0).isMale()) nbH++;
                else nbF++;
            }
        }

        System.out.println("nbdoubleF : " + nbdoubleF + "nbdoubleH : " + nbdoubleH);
        System.out.println("nbF : " + nbF + "nbH : " + nbH);
        boolean equilibrated = false;
        while((nbdoubleF + nbdoubleH) > 2 && !equilibrated) {
            System.out.println(" equilibrating ... ");
            if(nbdoubleF - nbdoubleH > 2 && nbH > 3) {
                outerloop:
                for(ComputerMembers cm:listCMX){
                    if(cm.getMembers().size()==2 && !cm.getMembers().get(0).isMale()) {
                        fst = cm.getMembers().get(1);
                        cm.remove(fst);
                        nbdoubleF--;
                        for(ComputerMembers cm2:listCMX){
                            if(cm2.getMembers().size()==1 && cm2.getMembers().get(0).isMale()) {
                                hst = cm2.getMembers().get(0);
                                cm2.remove(hst);
                                cm2.add(fst);
                                System.out.println(fst.getFullName() + " take place alone");
                                nbH--;
                                nbF++;

                                for(ComputerMembers cm3:listCMX){
                                    if(cm3.getMembers().size()==1 && cm3.getMembers().get(0).isMale()) {
                                        cm3.add(hst);
                                        nbdoubleH++;
                                        System.out.println(hst.getFullName() + " move place");
                                        break outerloop;
                                    }
                                }
                            }
                        }
                    }
                }




            } else{
                equilibrated = true;
            }
        }


        // seting id computer
        ArrayList listIds = new ArrayList();
        for(ComputerMembers cm:listCMX){
            if(cm.getMembers().size() == 2){
                if(cm.getMembers().get(0).isMale()){
                    for(int i = 1; i <=7; i=i+2){
                        if(!listIds.contains(i)){
                            cm.setIdComputer(i);
                            listIds.add(i);
                            System.out.println(cm.getMembers().get(0).getFullName() + " in pc num : " + i );
                            break;
                        } else if(!listIds.contains(16-i)){
                            cm.setIdComputer(16-i);
                            listIds.add(16-i);
                            System.out.println(cm.getMembers().get(0).getFullName() + " in pc num : " + (16-i) );
                            break;

                        }
                    }

                }else {//is female
                    for(int i = 8; i >=2; i=i-2){
                        if(!listIds.contains(i)){
                            cm.setIdComputer(i);
                            listIds.add(i);
                            System.out.println(cm.getMembers().get(0).getFullName() + " in pc num : " + i );
                            break;
                        } else if(i > 2 && !listIds.contains(18-i)){
                            cm.setIdComputer(18-i);
                            listIds.add(18-i);
                            System.out.println(cm.getMembers().get(0).getFullName() + " in pc num : " + (18-i) );
                            break;

                        }
                    }
                }
            } else if(cm.getMembers().size() == 1){// size == 1
                if(cm.getMembers().get(0).isMale()){
                    for(int i=1; i<=8; i++){
                        if(!listIds.contains(i)){
                            cm.setIdComputer(i);
                            listIds.add(i);
                            break;
                        } else if(!listIds.contains(16-i)){
                            cm.setIdComputer(16-i);
                            listIds.add(16-i);
                            break;
                        }
                    }
                } else { // is female
                    for(int i=1; i<=15; i++){
                        if(!listIds.contains(i)){
                            cm.setIdComputer(i);
                            listIds.add(i);
                            break;
                        }
                    }
                }
            }
        }

    }

    private ArrayList<ComputerMembers> redispatch(ArrayList<ComputerMembers> listCM) {

        int n = 0;
        Student st;

        for(ComputerMembers cm:listCM){
            if(cm.getMembers().size() < 1) {
                if(n%2 == 0){ //for males
                    for(ComputerMembers cm2:listCM){
                        if(cm2.getMembers().size() == 2 && cm2.getMembers().get(0).isMale()) {
                            st = cm2.getMembers().get(0);
                            cm2.remove(st);
                            cm.add(st);
                            n++;
                            break;
                        }
                    }
                } else{
                    for(ComputerMembers cm2:listCM){
                        if(cm2.getMembers().size() == 2 && !cm2.getMembers().get(0).isMale()) {
                            st = cm2.getMembers().get(0);
                            cm2.remove(st);
                            cm.add(st);
                            n++;
                            break;
                        }
                    }
                }
            }
        }

        // dispatching males / females
        ArrayList  occupedPc = new ArrayList();
        int id = 0;
        for(ComputerMembers cm:listCM) {
            if(cm.getMembers().size() > 0){ // for test TODO
            if (cm.getMembers().get(0).isMale()) {
                for (int i = 1; i <= 8; i++) {
                    if (occupedPc.contains(i)) {
                        if (occupedPc.contains(16 - i)) {
                            break;
                        } else {
                            cm.setIdComputer(16 - i);
                            occupedPc.add(16 - i);
                        }
                    } else {
                        cm.setIdComputer(i);
                        occupedPc.add(i);

                    }
                }

            } else {
                for (int i = 8; i >= 1; i--) {
                    if (occupedPc.contains(i)) {
                        if (occupedPc.contains(16 - i)) {
                            break;
                        } else {
                            cm.setIdComputer(16 - i);
                            occupedPc.add(16 - i);
                        }
                    } else {
                        cm.setIdComputer(i);
                        occupedPc.add(i);

                    }
                }
            }

        }

        }

        return listCM;

    }

    private ComputerMembers addToCM(Student st, ArrayList<ComputerMembers> list) {

        ComputerMembers res;
        for(ComputerMembers cm:list) {
            if (cm.getMembers().size() == 0) {
                cm.add(st);
                //res = cm;
                return cm;
            }
        }

        for(ComputerMembers cm:list) {
            if (cm.getMembers().size() == 1 && cm.getMembers().get(0).isMale() == st.isMale()) {
                cm.add(st);
                //res = cm;
                return cm;
            }
        }

        return null;
/*        boolean donne = false;
        ComputerMembers theCM = null;

        for(ComputerMembers cm:list) {
            if (cm.getMembers().size() == 0) {
                cm.add(st);
                theCM = cm;
                donne = true;
                break;
            }
        }
        if(!donne) {
            for(ComputerMembers cm:list) {

                if (cm.getMembers().size() == 1) {
                    if(cm.getMembers().get(0).isMale() == st.isMale()) {
                        cm.add(st);
                        theCM = cm;
                        donne = true;
                        break;
                    }
                }

                }

            }

        if(!donne) {
            for(ComputerMembers cm:list){
                if(cm.getMembers().size() == 2) {
                    if(cm.getMembers().get(0).isMale() == st.isMale()) {
                        cm.add(st);
                        theCM = cm;
                        donne = true;
                        break;
                    }
                }
            }
        }
        return theCM;*/
    }


    public File getPreparedFile() {
        return preparedFile;
    }

    public void setPreparedFile(File preparedFile) {
        this.preparedFile = preparedFile;
    }
}
