public class Shell {
    public Shell(){
    }

    public static void commandParser(String commandLine){
        String[] commands = commandLine.split(" ");
        System.out.println(commands.length);

        for(String command : commands){
            System.out.println(command);
        }
    }

    public static void main(String[] args) {
        commandParser("Hello World!");
    }

}
