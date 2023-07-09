package com.company;

import java.io.*;
import java.net.*;
import java.util.*;


public class Server {

    public static ArrayList<PrintWriter> al = new ArrayList<>();
    public static void main(String[] args) throws Exception  {
        System.out.println("Сервер включен");
        ServerSocket ss = new ServerSocket(8080);// серверсокет прослушивает
        for (int i = 0; i < 10; i++) {
            //   объявить о своем запуске
            Socket soc = ss.accept();// accept() будет ждать пока кто-нибудь не захочет подключиться
            Conversation c = new Conversation(soc);
            c.start();
        }
        System.out.println("Сервер выключен");
    }

}
class Conversation extends Thread {

    Socket soc;
    public Conversation(Socket soc) {
        this.soc = soc;
    }

    @Override
    public void run() {
        System.out.println("Поток  "+ Thread.currentThread().getName() + "   Подключен");
        try {          // установив связь и воссоздав сокет для общения с клиентом можно перейти к созданию потоков ввода/вывода теперь мы можем принимать сообщения

            BufferedReader nis = new BufferedReader(
                    new InputStreamReader(
                            soc.getInputStream()
                    )
            );
            PrintWriter nos = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    soc.getOutputStream()
                            )
                    ), true// выталкиваем все из буфера
            );
            Server.al.add(nos);
            String str = nis.readLine();
            while(!str.equals("End")){
                System.out.println("Пользователь  "+(str));
                for(PrintWriter o : Server.al){
                    o.println(str);
                }
                str = nis.readLine();
            }
            nos.println("End");
        }
        catch(Exception e){

        }
        System.out.println("Поток  "+ Thread.currentThread().getName() + "   Выключен");
    }
}