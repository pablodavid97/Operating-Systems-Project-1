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
                    commandHistory.add(command);
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
                    else {
                        cdDirection = secondCommand[1];
                    }
                    System.out.println(cdDirection);
                    commandHistory.add(command);
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
                    commandHistory.add(command);
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
                    commandHistory.add(command);
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
                    commandHistory.add(command);
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
                    commandHistory.add(command);
                    break;
                case "history":

                    getCommandHistory();
                    break;

                case "exit":
                    break;
                default:
                    System.err.println("Comando Invalido!");
            }
            ;
        }
    }

    public void getCommandHistory() {
        Iterator<String> iterator = commandHistory.descendingIterator();

        int i = 0;
        while (iterator.hasNext() && i < 20) {
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
