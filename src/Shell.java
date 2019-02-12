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

            System.out.println("\n" + secondComandFinal + ":");



            switch (secondComandFinal.get(0)) {
                case "ls":
                    //rocessBuilder pb = new ProcessBuilder(secondCommand);
                    if(secondComandFinal.size()<=2)
                    secondComandFinal.add(cdDirection);

                    ProcessBuilder pb = new ProcessBuilder(secondComandFinal);
                    //pb.command(secondCommand);
                    try {
                        Process process = pb.start();
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String s = null;
                        while ((s = stdInput.readLine()) != null) {
                            System.out.println(s);
                        }
                    } catch (IOException e) {
                        //e.printStackTrace();
                    }
                    addCommandHistory(command);
                    break;
                case "cd":

                    pb = new ProcessBuilder(secondComandFinal);
                    //pb.command(secondCommand);
                    try {
                        Process process = pb.start();
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String s = null;
                        while ((s = stdInput.readLine()) != null) {
                            System.out.println(s);
                        }
                    } catch (IOException e) {
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
                        if(secondComandFinal.get(1).contains("/")){
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
                    else {
                        cdDirection += "/" + secondCommand[1];
                        pb.directory(new File(cdDirection));
                    }
                    System.out.println(cdDirection);
                    addCommandHistory(command);
                    break;

                case "echo":
                    //rocessBuilder pb = new ProcessBuilder(secondCommand);
                    pb = new ProcessBuilder(secondComandFinal);
                    //pb.command(secondCommand);
                    try {
                        Process process = pb.start();
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String s = null;
                        while ((s = stdInput.readLine()) != null) {
                            System.out.println(s);
                        }
                    } catch (IOException e) {
                        //e.printStackTrace();
                    }
                    addCommandHistory(command);
                    break;
                case "ping":
                    //rocessBuilder pb = new ProcessBuilder(secondCommand);
                    pb = new ProcessBuilder(secondComandFinal);
                    //pb.command(secondCommand);
                    try {
                        Process process = pb.start();
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String s = null;
                        while ((s = stdInput.readLine()) != null) {
                            System.out.println(s);
                        }
                    } catch (IOException e) {
                        //e.printStackTrace();
                    }
                    addCommandHistory(command);
                    break;
                case "ipconfig":
                    pb = new ProcessBuilder(secondComandFinal);

                    //pb.command(secondCommand);
                    try {
                        Process process = pb.start();
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String s = null;
                        while ((s = stdInput.readLine()) != null) {
                            System.out.println(s);
                        }
                    } catch (IOException e) {
                        //e.printStackTrace();
                    }
                    addCommandHistory(command);
                    break;
                case "ifconfig":
                    //rocessBuilder pb = new ProcessBuilder(secondCommand);
                    pb = new ProcessBuilder(secondComandFinal);
                    //pb.command(secondCommand);
                    try {
                        Process process = pb.start();
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String s = null;
                        while ((s = stdInput.readLine()) != null) {
                            System.out.println(s);
                        }
                    } catch (IOException e) {
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
                        System.err.println("Comando Invalido!");
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

    public void getCommandHistory() {
        Iterator<String> iterator = commandHistory.descendingIterator();

        int i = 0;
        while (iterator.hasNext()) {
            i++;
            System.out.println(i + " " + iterator.next());
        }
    }

    public static void main(String[] args) {
        Shell shell = new Shell();
        Scanner input = new Scanner(System.in);
        String commandLine = "";
        String starRepo = "pwd";
        ProcessBuilder pb1 = new ProcessBuilder(starRepo);

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
