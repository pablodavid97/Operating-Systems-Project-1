/**
 * Adrian Duque y Pablo Llanes
 * Sistemas Operativos
 * Proyect 1: Shell.java
 * <p>
 * Custom command lines for shell that work with different Operating Systems.
 * The program uses the ProcessBuilder API to achieve the desired results.
 */


import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.lang.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;


public class Shell {
    private static LinkedList<String> commandHistory;
    private static String cdDirection; // variable to detect the class
    private static String operativeSistem; // variable to detect the operating system

    public Shell() {
        commandHistory = new LinkedList<>();
    }

    public void commandParser(String commandLine, String startLocation) {
        String separator = Pattern.quote("^");
        String[] commands = commandLine.split(separator);

        for (String command : commands) {
            String[] secondCommand = command.split(" ");

            ArrayList<String> secondComandFinal = new ArrayList<>();

            for (String i : secondCommand)
                secondComandFinal.add(i);

            // detects the operating system in order to use ifconfig command
            if (operativeSistem == "windows" && secondComandFinal.get(0) == "ifconfig") {
                secondComandFinal.set(0, "ipconfig");
            } else if (operativeSistem.contains("linux") && secondComandFinal.get(0).contains("ip")) {
                secondComandFinal.set(0, "ifconfig");
            }
            System.out.println("\n" + secondComandFinal + ":");

            // analyzes different commands and takes action depending on the command
            switch (secondComandFinal.get(0)) {
                case "ls":
                    if (secondComandFinal.size() <= 2) secondComandFinal.add(cdDirection);

                    String temporal = new String();
                    for (String i : secondComandFinal) {
                        temporal += " " + i;
                    }
                    ProcessBuilder pb = new ProcessBuilder();

                    // executes commands depending on the operating system
                    if (operativeSistem.contains("windows")) {
                        pb.command("c:\\cygwin64\\bin\\bash", "-c", temporal);
                    } else if (operativeSistem.contains("linux")) {
                        pb.command("/bin/bash", "-c", temporal);
                    } else if (operativeSistem.contains("mac")) {
                        pb.command("/bin/sh", "-c", temporal);
                    }

                    try {
                        Process process = pb.start();
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String s = null;
                        while ((s = stdInput.readLine()) != null) {
                            System.out.println(s);
                        }
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }

                    addCommandHistory(command);
                    break;
                case "cd":

                    pb = new ProcessBuilder();
                    String temporalcd = new String();

                    if(operativeSistem.equals("windows")){
                        cdDirection = cdDirection.replace("/cygdrive/c", "c:");
                    }
                    pb.directory(new File(cdDirection));

                    for (String i : secondComandFinal) {
                        temporalcd += " " + i;
                    }

                    if (operativeSistem.contains("windows")) {
                        pb.command("c:\\cygwin64\\bin\\bash", "-c", temporalcd);
//                        System.out.println(temporalcd);

                    } else if (operativeSistem.equals("linux")) {
                        pb.command("/bin/bash", "-c", temporalcd);

                    } else if (operativeSistem.equals("mac")) {
                        pb.command("/bin/sh", "-c", temporalcd);
                    }

                    try {
                        Process process = pb.start();
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String s = null;
                        while ((s = stdInput.readLine()) != null) {
                            System.out.println(s);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (secondComandFinal.size() < 2) {

                        if (operativeSistem.contains("windows")) {
                            pb.command("c:\\cygwin64\\bin\\bash", "-c", "pwd");
//                        System.out.println(temporalcd);

                        } else if (operativeSistem.equals("linux")) {
                            pb.command("/bin/bash", "-c", "pwd");

                        } else if (operativeSistem.equals("mac")) {
                            pb.command("/bin/sh", "-c", "pwd");
                        }

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

                        if(operativeSistem.equals("windows")){
                            cdDirection = cdDirection.replace("/cygdrive/c", "c:");
                        }
                    } else if (secondComandFinal.get(1).startsWith("..")) {

                        int pos = 0;
                        if (secondComandFinal.get(1).contains("/")) {
                            String[] goBacks = secondComandFinal.get(1).split("/");

                            for (String goBack : goBacks) {
                                pos = cdDirection.lastIndexOf('/');
                                cdDirection = cdDirection.substring(0, pos);
                            }
                        } else {
                            pos = cdDirection.lastIndexOf('/');
                            cdDirection = cdDirection.substring(0, pos);
                        }
                    } else if (secondComandFinal.get(1).contains("home") || secondComandFinal.get(1).contains("c:")) {
                        cdDirection = secondComandFinal.get(1);
                    }
                    else {

                        if (!secondCommand[1].startsWith("/")) {
                            cdDirection += "/";
                        }
                        cdDirection += secondCommand[1];

                        int size = cdDirection.length();
                        if (cdDirection.charAt(size - 1) == '/') {
                            cdDirection = cdDirection.substring(0, size - 1);
                        }
                    }
                    addCommandHistory(command);
                    pb.directory(new File(cdDirection));
                    System.out.println(cdDirection);
                    break;

                case "echo":
                    pb = new ProcessBuilder();
                    String temporalecho = new String();
                    for (String i : secondComandFinal) {
                        temporalecho += " " + i;
                    }

                    if (operativeSistem.contains("indows")) {
                        pb.command("c:\\cygwin64\\bin\\bash", "-c", temporalecho);

                    } else if (operativeSistem.contains("inux")) {
                        pb.command("/bin/bash", "-c", temporalecho);

                    } else if (operativeSistem.contains("mac")) {
                        pb.command("/bin/sh", "-c", temporalecho);
                    }
                    try {
                        Process process = pb.start();
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String s = null;
                        while ((s = stdInput.readLine()) != null) {
                            System.out.println(s);
                        }
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                    addCommandHistory(command);
                    break;
                case "ping":
                    pb = new ProcessBuilder();
                    String temporalping = new String();
                    for (String i : secondComandFinal) {
                        temporalping += " " + i;
                    }

                    if (operativeSistem.contains("indows")) {
                        pb.command("c:\\cygwin64\\bin\\bash", "-c", temporalping);

                    } else if (operativeSistem.contains("inux")) {
                        pb.command("/bin/bash", "-c", temporalping);

                    } else if (operativeSistem.contains("mac")) {
                        pb.command("/bin/sh", "-c", temporalping);
                    }
                    try {
                        Process process = pb.start();
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String s = null;
                        while ((s = stdInput.readLine()) != null) {
                            System.out.println(s);
                        }
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                    addCommandHistory(command);
                    break;
                case "ipconfig":
                    pb = new ProcessBuilder();
                    String temporalip = new String();
                    for (String i : secondComandFinal) {
                        temporalip += " " + i;
                    }

                    if (operativeSistem.equals("windows")) {
                        pb.command("c:\\cygwin64\\bin\\bash", "-c", temporalip);

                    } else if (operativeSistem.equals("linux")) {
                        pb.command("/bin/bash", "-c", temporalip);

                    } else if (operativeSistem.equals("mac")) {
                        pb.command("/bin/sh", "-c", temporalip);
                    }
                    try {
                        Process process = pb.start();
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String s = null;
                        while ((s = stdInput.readLine()) != null) {
                            System.out.println(s);
                        }
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                    addCommandHistory(command);
                    break;
                case "ifconfig":
                    pb = new ProcessBuilder();
                    String temporalif = new String();
                    for (String i : secondComandFinal) {
                        temporalif += " " + i;
                    }

                    if (operativeSistem.contains("indows")) {
                        pb.command("c:\\cygwin64\\bin\\bash", "-c", temporalif);

                    } else if (operativeSistem.contains("inux")) {
                        pb.command("/bin/bash", "-c", temporalif);

                    } else if (operativeSistem.contains("mac")) {
                        pb.command("/bin/sh", "-c", temporalif);
                    }
                    try {
                        Process process = pb.start();
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String s = null;
                        while ((s = stdInput.readLine()) != null) {
                            System.out.println(s);
                        }
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
                case "pwd":
                    ProcessBuilder pb1 = new ProcessBuilder();

                    // testOS();

                    if (operativeSistem.contains("indows")) {
                        pb1.command("c:\\cygwin64\\bin\\bash", "-c", "pwd");
                        //pb1.directory(new File("c:\\Users\\Ramir\\Desktop"));
                        //cdDirection= System.getProperty("user.dir");
                        //System.out.println(cdDirection);
                    } else {
                        pb1.command("/bin/sh", "-c", "pwd");
                    }

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
                    break;
                default:
                    String commandForHistory = secondComandFinal.get(0);
                    if (commandForHistory.startsWith("!")) {
                        int historyNum = Integer.parseInt(String.valueOf(commandForHistory.charAt(1)));
                        int historySize = commandHistory.size();

                        if (historyNum > historySize) {
                            System.err.println("Comando Invalido!");
                        } else {
                            String historyCommand = commandHistory.get(historySize - historyNum);
                            commandParser(historyCommand, cdDirection);
                        }
                    } else {
                        System.out.println("Comando invalido: " + secondComandFinal);
                    }
            }
            ;
        }
    }

    public void addCommandHistory(String command) {
        int size = commandHistory.size();

        if (size == 20) {
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

    public static void testOS() {
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            operativeSistem = "windows";
        } else if (System.getProperty("os.name").toLowerCase().equals("mac")) {
            operativeSistem = "mac";
        } else {
            operativeSistem = "linux";
        }
        System.out.println(operativeSistem);
    }

    public static void main(String[] args) {
        Shell shell = new Shell();
        Scanner input = new Scanner(System.in);
        String commandLine = "";
        String starRepo = "pwd";
        ProcessBuilder pb1 = new ProcessBuilder();

        // determines the operating sistem
        testOS();

        // replaces path if windows is detected
        if (operativeSistem.equals("windows")) {
            pb1.command("c:\\cygwin64\\bin\\bash", "-c", starRepo);
        } else {
            pb1.command("/bin/sh", "-c", starRepo);
        }

        // startinf the process
        try {
            // starts the process and continues until user types exit command
            Process process = pb1.start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s = null;
            String temp = null;
            while ((s = stdInput.readLine()) != null) {
                cdDirection = s;

                if(operativeSistem.equals("windows")){
                    temp = s;
                    System.out.println("Current Directory: " + temp.replace("/cygdrive/c", "c:"));
                } else {
                    System.out.println(" Currect Directory: " + s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        do {
            System.out.println(">");
            commandLine = input.nextLine();

            shell.commandParser(commandLine, cdDirection);
        } while (!commandLine.equals("exit"));
    }
}