package main.java.eat;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Created by atarasevich on 26.07.16.
 */
public class MyServlet extends HttpServlet {
    final static Logger logger = Logger.getLogger(MyServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        if ((!req.getParameter("emailTO").equals("")) && (!req.getParameter("emailFROM").equals("")) && (!req.getParameter("passFROM").equals(""))){
            String serv = req.getParameter("server");
            String emailFrom = req.getParameter("emailFROM");
            String passFrom = req.getParameter("passFROM");
            String emailTo = req.getParameter("emailTO");

            if(req.getParameter("server").equals("gmail")){
                emailFrom ="shop.meduza@gmail.com";
                passFrom = "a741852963";
            }
            Sender sender = new Sender(serv, emailFrom, passFrom);
            String str_text = "Привет с работы!";
            String str_zag = "Новая задача";

            sender.send(serv, str_zag,str_text,emailFrom, emailTo);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Запрос получен PUT");

        req.setCharacterEncoding("UTF-8");
        String bufferedReader = req.getQueryString();
        String[] pari;
        /*
        logger.info("bufferedReader - " + bufferedReader);
        StringBuffer stringBuffer = new StringBuffer();
        logger.info("stringBuffer - " + stringBuffer);
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
            logger.info("stringBuffer: str - " + stringBuffer);
        }
*/
        HashMap<String,String> stringHashMap = new HashMap<String, String>();
        if(bufferedReader.equals("") == false) {
            pari = bufferedReader.split("&");
            for (int i = 0; i < pari.length; i++) {
                int pos = pari[i].indexOf("=");
                stringHashMap.put(pari[i].substring(0,pos),pari[i].substring(pos+1,pari[i].length()));
            }
        }

        resp.setStatus(200);
        PrintWriter printWriter = resp.getWriter();
        printWriter.println("OK");
        printWriter.flush();
    }
}
