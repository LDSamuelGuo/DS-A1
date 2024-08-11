import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.io.File;

public class CalculatorClient implements Runnable {
    Calculator stub;

    // Constructs a CalculatorClient instance that extends Runnable.
    // The constructor takes the client's individual stub session, 
    // which is stored for use in multithreaded testing.
    public CalculatorClient(Calculator stub) {
        this.stub = stub;
    }

    // Executes the commands specified by the user, utilizing the user's ID,
    // the stub, and the provided commands. The method processes commands such as 
    // 'pushOperation' and 'pushValue' and performs the corresponding actions.
    private void commandExecutor(String[] cmds, String id) {
        if (cmds[0].contains("pushOperation")) {
            try {
                this.stub.pushOperation(id, cmds[1]);
            } catch (Exception e) {
                System.err.println("Error: " + e.toString());
            }
        } else if (cmds[0].contains("pushValue")) {
            try {
                this.stub.pushValue(id, Integer.valueOf(cmds[1]));
            } catch (Exception e) {
                System.err.println("Error: " + e.toString());
            }
        } else if (cmds[0].contains("pop")) {
            try {
                System.out.println(this.stub.pop(id));
            } catch (Exception e) {
                System.err.println("Error: " + e.toString());
            }
        } else if (cmds[0].contains("isEmpty")) {
            try {
                System.out.println(this.stub.isEmpty(id));
            } catch (Exception e) {
                System.err.println("Error: " + e.toString());
            }
        } else if (cmds[0].contains("delayPop")) {
            try {
                System.out.println(this.stub.delayPop(id, Integer.valueOf(cmds[1])));
            } catch (Exception e) {
                System.err.println("Error: " + e.toString());
            }
        }
    }

    // Processes the input file for each user's commands, facilitating automated testing.
    // The method reads commands from a text file, processes them, and passes the user's 
    // ID and commands to the commandExecutor method.
    public void fileInputProcessor(String id) {
        try {
            // Constructs the file name based on the thread number, which is used to associate
            // the correct test input file with the respective thread.
            String file = "./TestInput" + Thread.currentThread().getName() + ".txt";
            file = file.replace("Thread-", "");

            // Initializes the scanner to read from the associated input file.
            Scanner in = new Scanner(new File(file));
            in.useDelimiter("\n");

            // Processes the commands from the input file until the exit command is encountered.
            boolean exit = false;
            while (!exit) {
                String cmd = in.nextLine();
                String[] cmds = cmd.split(" ");

                // Exits the loop if the "exit" command is encountered.
                if (cmds[0].contains("exit")) {
                    exit = true;
                }

                // Passes the user ID and command to the commandExecutor method for processing.
                commandExecutor(cmds, id);
            }

            // Closes the input handler after reading the entire input file.
            in.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.toString());
        }
    }

    // The run method generates a unique user ID for each thread (representing each client),
    // and passes the ID to the fileInputProcessor method to execute the user's commands.
    public void run() {
        String id = "empty";
        try {
            // Generates a unique user ID so that each user can access only their stack.
            id = this.stub.createUserID();
        } catch (Exception e) {
            System.err.println("Error: " + e.toString());
        }

        // Passes the generated user ID to the fileInputProcessor to execute commands.
        fileInputProcessor(id);
    }

    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];
        try {
            // Locates the RMI registry and retrieves the stub for the "Calculator" service.
            Registry registry = LocateRegistry.getRegistry(host);
            Calculator stub = (Calculator) registry.lookup("Calculator");

            // Starts multiple clients (represented by threads) for testing purposes.
            for (int i = 0; i < 4; i++) {
                Thread t = new Thread(new CalculatorClient(stub));
                t.start();
            }
        } catch (Exception e) {
            System.err.println("CalculatorClient exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
