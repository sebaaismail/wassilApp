package com.sebaainf.main;

//import org.jdatepicker.impl.JDatePickerImpl;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class contain common functions
 * Created by admin on 04/02/2015.
 */
class IsmCommonUtils {


    public ActionListener actionDeces = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }

    };

    public static void encryptDES(String pathInputFile, String pathOutputFile, String key) {
        FileInputStream file = null;
        try {
            file = new FileInputStream(pathInputFile);

            FileOutputStream output = new FileOutputStream(pathOutputFile);
            byte j[] = key.getBytes();
            SecretKeySpec kye = new SecretKeySpec(j, "DES");
            System.out.println(kye);
            Cipher enc = Cipher.getInstance("DES");
            enc.init(Cipher.ENCRYPT_MODE, kye);
            CipherOutputStream cos = new CipherOutputStream(output, enc);
            byte[] buf = new byte[1024];
            int read;
            while ((read = file.read(buf)) != -1) {
                cos.write(buf, 0, read);
            }
            file.close();
            output.flush();
            cos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage decryptDES_picture(String pathInputFile, String key) {
        FileInputStream file = null;
        BufferedImage image = null;
        try {
            file = new FileInputStream(pathInputFile);
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte j[] = key.getBytes();
            SecretKeySpec kye = new SecretKeySpec(j, "DES");
            System.out.println(kye);
            Cipher enc = Cipher.getInstance("DES");
            enc.init(Cipher.DECRYPT_MODE, kye);
            CipherOutputStream cos = new CipherOutputStream(output, enc);
            byte[] buf = new byte[1024];
            int read;
            while ((read = file.read(buf)) != -1) {
                cos.write(buf, 0, read);
            }
            file.close();
            output.flush();
            cos.close();

            byte[] data = output.toByteArray();
            ByteArrayInputStream input = new ByteArrayInputStream(data);
            image = ImageIO.read(input);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static FileOutputStream decryptDES(String pathInputFile, String key) {
        FileInputStream file = null;
        FileOutputStream output = null;
        try {
            file = new FileInputStream(pathInputFile);
            output = new FileOutputStream("");

            byte j[] = key.getBytes();
            SecretKeySpec kye = new SecretKeySpec(j, "DES");
            System.out.println(kye);
            Cipher enc = Cipher.getInstance("DES");
            enc.init(Cipher.DECRYPT_MODE, kye);
            CipherOutputStream cos = new CipherOutputStream(output, enc);
            byte[] buf = new byte[1024];
            int read;
            while ((read = file.read(buf)) != -1) {
                cos.write(buf, 0, read);
            }
            file.close();
            output.flush();
            cos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

/*    public static void setListComponentsEnabled(ArrayList<JComponent> listComponents, boolean b) {

        for (JComponent comp : listComponents) {

            if (comp.getClass().getSimpleName().equals("JTextField"))
                ((JTextField) comp).setEditable(b);
            else if (comp.getClass().getSimpleName().equals("JCheckBox"))
                ((JCheckBox) comp).setEnabled(b);
            else if (comp.getClass().getSimpleName().equals("JComboBox"))
                ((JComboBox) comp).setEnabled(b);
            else if (comp.getClass().getSimpleName().equals("JRadioButton"))
                ((JRadioButton) comp).setEnabled(b);
            else if (comp.getClass().getSimpleName().equals("JDatePickerImpl")) {
                ((JDatePickerImpl) comp).setTextEditable(b);
                // set the button disabled
                ((JDatePickerImpl) comp).getComponent(1).setEnabled(b);
            }

        }

    }*/

    public static SimpleDateFormat getMyFrDateFormat() {
        DateFormatSymbols dfs = new DateFormatSymbols(Locale.FRENCH);

        dfs.setMonths(new String[]{
                "janvier",
                "fevrier",
                "mars",
                "Avril",
                "mai",
                "juin",
                "juillet",
                "août",
                "septembre",
                "octobre",
                "novebre",
                "décembre",

        });
        return new SimpleDateFormat("dd MMMM yyyy", dfs);
    }

    public static DateFormat getMyFrDateFormat3() {
        DateFormatSymbols dfs = new DateFormatSymbols(new Locale("fr"));
        dfs.setMonths(new String[]{
                "janvier",
                "fevrier",
                "mars",
                "Avril",
                "mai",
                "juin",
                "juillet",
                "août",
                "septembre",
                "octobre",
                "novebre",
                "décembre",

        });

        Map map = new HashMap<String, String>();
        map.put("01", "un");
        String[] jours = new String[31];
        jours[0] = "premier";
        jours[1] = "deux";
        jours[2] = "trois";
        jours[3] = "quatre";
        jours[4] = "cinq";
        jours[5] = "six";
        jours[6] = "sept";
        jours[7] = "huit";
        jours[8] = "neuf";
        jours[9] = "dix";
        jours[10] = "onze";
        jours[11] = "douze";
        jours[12] = "treize";
        jours[13] = "quatorze";
        jours[14] = "quinze";
        jours[15] = "seize";
        jours[16] = "dix-sept";
        jours[17] = "dix-huit";
        jours[18] = "dix-neuf";
        jours[19] = "vingt";
        jours[20] = "vingt et un";
        jours[29] = "trente";
        jours[30] = "trente et un";
        for (int i = 21; i < 29; i++) {
            jours[i] = "vingt " + "et " + jours[i - 20];
        }
        //TODO finish the algo

        return new SimpleDateFormat("'map('dd')' MMMM yyyy", dfs);
        //return new SimpleDateFormat("'jours['dd']' MMMM yyyy", dfs);
    }

    public static SimpleDateFormat getMyFrDateFormat2() {
        DateFormatSymbols dfs = new DateFormatSymbols(Locale.FRENCH);

        Map map = new HashMap<String, String>();
        map.put("01", "un");
        String[] jours = new String[31];
        jours[0] = "premier";
        jours[1] = "deux";
        jours[2] = "trois";
        jours[3] = "quatre";
        jours[4] = "cinq";
        jours[5] = "six";
        jours[6] = "sept";
        jours[7] = "huit";
        jours[8] = "neuf";
        jours[9] = "dix";
        jours[10] = "onze";
        jours[11] = "douze";
        jours[12] = "treize";
        jours[13] = "quatorze";
        jours[14] = "quinze";
        jours[15] = "seize";
        jours[16] = "dix-sept";
        jours[17] = "dix-huit";
        jours[18] = "dix-neuf";
        jours[19] = "vingt";
        jours[20] = "vingt et un";
        jours[29] = "trente";
        jours[30] = "trente et un";
        for (int i = 21; i < 29; i++) {
            jours[i] = "vingt " + "et " + jours[i - 20];
        }
        //TODO finish the algo

        return new SimpleDateFormat("'map('dd')' MMMM yyyy", dfs);
        //return new SimpleDateFormat("'jours['dd']' MMMM yyyy", dfs);
    }

    public static SimpleDateFormat getMyArDateFormat() {
        DateFormatSymbols dfs = new DateFormatSymbols(new Locale("ar"));
        dfs.setMonths(new String[]{
                "جانفي",
                "فيفري",
                "مارس",
                "أفريل",
                "ماي",
                "جوان",
                "جويلية",
                "أوت",
                "سبتمبر",
                "أكتوبر",
                "نوفمبر",
                "ديسمبر",

        });
        return new SimpleDateFormat("dd MMMM yyyy", dfs);
    }


    public static String getJourMoisText(Date date_naiss_cit) {
        String jourText, moisText;
        Map map = new HashMap<String, String>();

        Calendar cal = GregorianCalendar.getInstance();

        cal.setTime(date_naiss_cit);
        int day = cal.get(cal.DAY_OF_MONTH);//having jour


        DateFormatSymbols dfs = new DateFormatSymbols(Locale.FRENCH);
        SimpleDateFormat dateformat = new SimpleDateFormat("MMM", dfs);
        moisText = dateformat.format(cal.getTime());
        jourText = getDecimalNumberText(day - 1);


        return jourText + " " + moisText;
    }

    public static String getAnneeText(Date date_naiss_cit) {
        Calendar cal = GregorianCalendar.getInstance();
        String text = "";
        cal.setTime(date_naiss_cit);
        int year = cal.get(cal.YEAR);//having year

        if (year >= 2000) {

        } else if (year >= 1900) {
            text = "neuf cent";
        } else if (year >= 1800) {
            text = "huit cent";
        } else {
            JOptionPane.showMessageDialog(null, "Erreur dans l'annee de Naissance");
        }
        if ((year % 100) != 00) {
            text = text + " " + getDecimalNumberText((year % 100) - 1);
        }

        return text;
    }

    public static String getDecimalNumberText(int num) {

        Map map = new HashMap<String, String>();
        String[] numbers = new String[99];
        numbers[0] = "un";//
        numbers[1] = "deux";
        numbers[2] = "trois";
        numbers[3] = "quatre";
        numbers[4] = "cinq";
        numbers[5] = "six";
        numbers[6] = "sept";
        numbers[7] = "huit";
        numbers[8] = "neuf";
        numbers[9] = "dix";
        numbers[10] = "onze";
        numbers[11] = "douze";
        numbers[12] = "treize";
        numbers[13] = "quatorze";
        numbers[14] = "quinze";
        numbers[15] = "seize";
        numbers[16] = "dix-sept";
        numbers[17] = "dix-huit";
        numbers[18] = "dix-neuf";
        numbers[19] = "vingt";
        numbers[20] = "vingt et un";
        numbers[29] = "trente";
        numbers[39] = "quarante";
        numbers[49] = "cinquante";
        numbers[59] = "soixante";
        numbers[69] = "soixante-dix"; //todo
        numbers[79] = "quatre-vingt"; //todo
        numbers[89] = "quatre-vingt-dix"; //todo
        //numbers[30]= "trente et un";
        //for(int i=21; i<29; i++) {
        for (int i = 21; i < 69; i++) {
            if ((i % 10) != 9) {
                numbers[i] = numbers[(i - (i % 10)) - 1] + " et " + numbers[i - (i - (i % 10))];
            }
        }
        for (int i = 70; i < 99; i++) {
            if ((i % 10) != 9) {
                numbers[i] = numbers[(i - (i % 10)) - 11] + "-" + numbers[i - (i - (i % 10)) + 10];
            }
        }

        String text = "";
        if (num == 0) {
            text = "premier";
        } else {
            text = numbers[num];
        }
        return text;
    }
}