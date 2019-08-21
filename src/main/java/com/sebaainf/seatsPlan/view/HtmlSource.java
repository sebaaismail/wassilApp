package com.sebaainf.seatsPlan.view;

/**
 * Created by Ismail on 16/08/2019.
 */
public class HtmlSource {
    public static final String HEADER = "<!doctype html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<title>Group </title>\n" +
            "<meta charset=\"utf-8\">\n" +
            "<style>\n" +
            "\tcanvas {\n" +
            "\t\tborder: 1px solid black;\n" +
            "\t}\n" +
            "</style>\n" +
            "\n" +
            "<script>\n" +
            "\twindow.onload = function(){\n" +
            "\t\tvar cnv = document.getElementById(\"cnv\");\n" +
            "\t\tlet context = cnv.getContext(\"2d\");\n" +
            "\t\t\n" +
            "\t\tdrawMasqueAndText(cnv);\n" +
            "\t\t\n" +
            "\t\tdocument.getElementById(\"generatePdf-btn\").onclick = function(){\n" +
            "\t\n" +
            "\t}\n" +
            "\t\t\n" +
            "\t\t\n" +
            "\t\t//we use image in javascript here to get more control and to ensure that\n" +
            "\t\t//the image is loaded before draw it on canvas\n" +
            "\t\tfunction drawMasqueAndText(canvas){\n" +
            "\t\tlet context = canvas.getContext(\"2d\");\n" +
            "\t\tcontext.font = \"13pt Times \";\n" +
            "\t\t//context.fillText(\"I Saw this tweet \", 50, 80, 300)\n" +
            "\t\tlet masque = new Image();\n" +
            "\t\tmasque.src = \"masque.png\";\n" +
            "\t\tmasque.onload = function(){\n" +
            "\t\tcontext.drawImage(masque, 0, 0);\n" +
            "\t\t//here we cut and add text from java objects to the canvas \n" ;

    public static final String FOOTER = "\n" +
            "\t\t}\n" +
            "\n" +
            "\t\t}\n" +
            "\t\t\n" +
            "\t\t\n" +
            "\t//End window.onload function\t\n" +
            "\t};\n" +
            "</script>\n" +
            "</head>\n" +
            "<body>\n" +
            "<canvas id=\"cnv\" width=\"842\" height=\"595\" dir=\"rtl\">\n" +
            " You should upgrade your browser!!</canvas>\n" +
            "\n" +
            " <form>\n" +
            " <p>\n" +
            " <input type=\"button\" id=\"generatePdf-btn\" value=\"Preview\">\n" +
            " </p>\n" +
            " \n" +
            " </form>\n" +
            " </body>\n" +
            "</html>";

}
