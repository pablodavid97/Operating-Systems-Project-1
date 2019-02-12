import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.lang.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;


public class Shell {
    private static LinkedList<String> commandHistory;
    private static String cdDirection;
    static String operativeSistem;
    public int errorMsg;
    public Shell() {
        commandHistory = new LinkedList<>();
    }

    public void commandParser(String commandLine, String startLocation) {
        String separator = Pattern.quote("^");
        String[] commands = commandLine.split(separator);

        for (String command : commands) {
            String[] secondCommand = command.split(" ");

            ArrayList<String>secondComandFinal = new ArrayList<>();

            for(String i : secondCommand)
                secondComandFinal.add(i);


            if(operativeSistem=="windows"&& secondComandFinal.get(0)=="ifconfig")
            {
                secondComandFinal.set(0,"ipconfig");
            }else if(operativeSistem.contains("Linux") && secondComandFinal.get(0).contains("ip")){
                secondComandFinal.set(0,"ifconfig");
            }
            System.out.println("\n" + secondComandFinal + ":");

            switch (secondComandFinal.get(0)) {


                case "ls":
                    //rocessBuilder pb = new ProcessBuilder(secondCommand);
                    if(secondComandFinal.size()<=2)
                        secondComandFinal.add(cdDirection);

                    String temporal = new String();
                    for(String i: secondComandFinal)
                    {
                        temporal+=" "+i;
                    }
                    ProcessBuilder pb = new ProcessBuilder();

                    if(operativeSistem.contains("indows"))
                    {
                        pb.command("cmd.exe", "/c", temporal);

                    }else if(operativeSistem.contains("inux")){
                        pb.command("/bin/bash", "-c",temporal);

                    }else if(operativeSistem.contains("mac"))
                    {
                        pb.command("/bin/sh", "-c",temporal);
                    }

                    try {
                        Process process = pb.start();
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String s = null;
                        while ((s = stdInput.readLine()) != null) {
                            System.out.println(s);
                        }
                        errorMsg = process.waitFor();
                        System.out.println("Exited with error code: " + errorMsg);
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }

                    addCommandHistory(command);
                    break;
                case "cd":

                    pb = new ProcessBuilder();
                    String temporalcd= new String();
                    for(String i: secondComandFinal)
                    {
                        temporalcd+=" "+i;
                    }
                    //ProcessBuilder pb = new ProcessBuilder();

                    if(operativeSistem.contains("indows"))
                    {
                        pb.command("cmd.exe", "/c", temporalcd);

                    }else if(operativeSistem.contains("inux")){
                        pb.command("/bin/bash", "-c",temporalcd);

                    }else if(operativeSistem.contains("mac"))
                    {
                        pb.command("/bin/sh", "-c",temporalcd);
                    }
                    try {
                        Process process = pb.start();
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String s = null;
                        while ((s = stdInput.readLine()) != null) {
                            System.out.println(s);

                        }
                        errorMsg = process.waitFor();
                        System.out.println("Exited with error code: " + errorMsg);
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                    if(secondComandFinal.size()<2) {
                        pb.command("pwd");

                        // startinf the process
                        try {
                            Process process = pb.start();
                            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            String s = null;
                            while ((s = stdInput.readLine()) != null) {
                                cdDirection = s;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    else if (secondComandFinal.get(1).startsWith("..")){

                        int pos = 0;
                        if(secondComandFinal.get(1).contains("/") ){
                            String[] goBacks = secondComandFinal.get(1).split("/");

                            for(String goBack : goBacks){
                                pos = cdDirection.lastIndexOf('/');
                                cdDirection = cdDirection.substring(0, pos);
                            }
                        } else {
                            pos = cdDirection.lastIndexOf('/');
                            cdDirection = cdDirection.substring(0, pos);
                        }

                        pb.directory(new File(cdDirection));
                    }
                    else if (! secondComandFinal.get(1).contains("home")) {
                        cdDirection += "/" + secondCommand[1];
                        pb.directory(new File(cdDirection));
                    }else {
                        cdDirection=secondComandFinal.get(1);
                        pb.directory(new File(cdDirection));
                    }
                    System.out.println(cdDirection);
                    addCommandHistory(command);
                    break;

                case "echo":
                    pb = new ProcessBuilder();
                    String temporalecho= new String();
                    for(String i: secondComandFinal)
                    {
                        temporalecho+=" "+i;
                    }
                    //ProcessBuilder pb = new ProcessBuilder();

                    if(operativeSistem.contains("indows"))
                    {
                        pb.command("cmd.exe", "/c", temporalecho);

                    }else if(operativeSistem.contains("inux")){
                        pb.command("/bin/bash", "-c",temporalecho);

                    }else if(operativeSistem.contains("mac"))
                    {
                        pb.command("/bin/sh", "-c",temporalecho);
                    }
                    try {
                        Process process = pb.start();
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String s = null;
                        while ((s = stdInput.readLine()) != null) {
                            System.out.println(s);
                        }
                        errorMsg = process.waitFor();
                        System.out.println("Exited with error code: " + errorMsg);
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                    addCommandHistory(command);
                    break;
                case "ping":
                    pb = new ProcessBuilder();
                    String temporalping= new String();
                    for(String i: secondComandFinal)
                    {
                        temporalping+=" "+i;
                    }
                    //ProcessBuilder pb = new ProcessBuilder();

                    if(operativeSistem.contains("indows"))
                    {
                        pb.command("cmd.exe", "/c", temporalping);

                    }else if(operativeSistem.contains("inux")){
                        pb.command("/bin/bash", "-c",temporalping);

                    }else if(operativeSistem.contains("mac"))
                    {
                        pb.command("/bin/sh", "-c",temporalping);
                    }
                    try {
                        Process process = pb.start();
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String s = null;
                        while ((s = stdInput.readLine()) != null) {
                            System.out.println(s);
                        }
                        errorMsg = process.waitFor();
                        System.out.println("Exited with error code: " + errorMsg);
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                    addCommandHistory(command);
                    break;
                case "ipconfig":
                    pb = new ProcessBuilder();
                    String temporalip= new String();
                    for(String i: secondComandFinal)
                    {
                        temporalip+=" "+i;
                    }
                    //ProcessBuilder pb = new ProcessBuilder();

                    if(operativeSistem.contains("indows"))
                    {
                        pb.command("cmd.exe", "/c", temporalip);

                    }else if(operativeSistem.contains("inux")){
                        pb.command("/bin/bash", "-c",temporalip);

                    }else if(operativeSistem.contains("mac"))
                    {
                        pb.command("/bin/sh", "-c",temporalip);
                    }
                    try {
                        Process process = pb.start();
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String s = null;
                        while ((s = stdInput.readLine()) != null) {
                            System.out.println(s);
                        }
                        errorMsg = process.waitFor();
                        System.out.println("Exited with error code: " + errorMsg);
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                    addCommandHistory(command);
                    break;
                case "ifconfig":
                    pb = new ProcessBuilder();
                    String temporalif= new String();
                    for(String i: secondComandFinal)
                    {
                        temporalif+=" "+i;
                    }
                    //ProcessBuilder pb = new ProcessBuilder();

                    if(operativeSistem.contains("indows"))
                    {
                        pb.command("cmd.exe", "/c", temporalif);

                    }else if(operativeSistem.contains("inux")){
                        pb.command("/bin/bash", "-c",temporalif);

                    }else if(operativeSistem.contains("mac")) {
                        pb.command("/bin/sh", "-c", temporalif);
                    }
                        try {
                        Process process = pb.start();
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String s = null;
                        while ((s = stdInput.readLine()) != null) {
                            System.out.println(s);
                        }
                        errorMsg = process.waitFor();
                        System.out.println("Exited with error code: " + errorMsg);
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                    addCommandHistory(command);
                    break;
                case "history":

                    getCommandHistory();
                    break;
                case "exit":
                    break;
                default:
                    String commandForHistory = secondComandFinal.get(0);
                    if(commandForHistory.startsWith("!")){
                        int historyNum = Integer.parseInt(String.valueOf(commandForHistory.charAt(1)));
                        int historySize = commandHistory.size();

                        if (historyNum > historySize){
                            System.err.println("Comando Invalido!");
                        } else {
                            String historyCommand = commandHistory.get(historySize-historyNum);
                            commandParser(historyCommand, cdDirection);
                        }
                    } else {
                        System.out.println("Comando invalido: "+secondComandFinal);
                        //System.err.println("Comando Invalido!");
                    }
            }
            ;
        }
    }

    public void addCommandHistory(String command){
        int size = commandHistory.size();

        if (size == 20){
            commandHistory.removeFirst();
        }
        commandHistory.add(command);
    }

    public static void getCommandHistory() {
        Iterator<String> iterator = commandHistory.descendingIterator();

        int i = 0;
        while (iterator.hasNext()) {
            i++;
            System.out.println(i + " " + iterator.next());
        }
    }

    public static void testOS()
    {
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            //processBuilder.command("cmd.exe", "/c", command);
            operativeSistem="Windows";
        }
        else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            // processBuilder.command("/bin/sh", "-c", command.replace("ipconfig","ifconfig"));
            operativeSistem="mac";
        }
        else if (System.getProperty("os.name").toLowerCase().contains("sunos")) {
            // processBuilder.command("/bin/ksh", "-c", command);
            operativeSistem="sunos";
        }
        else {
            //processBuilder.command("/bin/bash", "-c", command.replace("ipconfig","ifconfig"));
            operativeSistem="Linux";
        }
        System.out.println(System.getProperty("os.name"));

    }

    public static void main(String[] args) {
        Shell shell = new Shell();
        Scanner input = new Scanner(System.in);
        String commandLine = "";
        String starRepo = "pwd";
        ProcessBuilder pb1 = new ProcessBuilder(starRepo);


        testOS();

        // startinf the process
        try {
            Process process = pb1.start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(" Currect Directory: " + s);
                cdDirection = s;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        do {
            System.out.println(">");
            commandLine = input.nextLine();

            shell.commandParser(commandLine, cdDirection);
        }
        while (!commandLine.equals("exit"));
    }

}
