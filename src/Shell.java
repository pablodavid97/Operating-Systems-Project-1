import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Shell {
    private static LinkedList<String> commandHistory;

    public Shell(){
        commandHistory = new LinkedList<>();
    }

    public void commandParser(String commandLine){
        String[] commands = commandLine.split(" ");

        for(String command : commands){

            switch (command){
                case "ls":
                    commandHistory.add(command);
                    break;
                case "cd":
                    commandHistory.add(command);
                    break;
                case "echo":
                    commandHistory.add(command);
                    break;
                case "ping":
                    commandHistory.add(command);
                    break;
                case "ipconfig":
                    commandHistory.add(command);
                    break;
                case "ifconfig":
                    commandHistory.add(command);
                    break;
                case "history":
                    getCommandHistory();
                    break;
                case "exit":
                    break;
                default:
                    System.err.println("Comando Invalido!");
            };
        }
    }

    public void getCommandHistory(){
        Iterator<String> iterator = commandHistory.descendingIterator();

        int i = 0;
        while(iterator.hasNext() && i < 20){
            i++;
            System.out.println(i + " " + iterator.next());
        }
    }

    public static void main(String[] args) {
        Shell shell = new Shell();
        Scanner input = new Scanner(System.in);
        String commandLine = "";

        do{
            System.out.println(">");

            commandLine = input.nextLine();

            shell.commandParser(commandLine);
        }
        while(!commandLine.equals("exit"));
    }

}
