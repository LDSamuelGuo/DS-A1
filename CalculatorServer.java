import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

// The CalculatorServer class serves as the entry point for the server-side application.
// It initializes and registers a remote object that clients can invoke methods on.
public class CalculatorServer {
    public static void main(String args[]) {
        try {
            // Create an instance of the implementation of the Calculator interface.
            CalculatorImplementation obj = new CalculatorImplementation();
            
            // Export the object to make it available to receive incoming calls.
            // This step generates a stub for the remote object, which the client will use to invoke methods.
            Calculator stub = (Calculator) UnicastRemoteObject.exportObject(obj, 0);

            // Get a reference to the RMI registry running on the local host.
            Registry registry = LocateRegistry.getRegistry();

            // Bind the stub to a name in the registry so that clients can look it up by this name.
            registry.rebind("Calculator", stub);

            // Indicate that the server is ready and waiting for client connections.
            System.err.println("Server ready");
        } catch (Exception e) {
            // Handle any exceptions that occur during the server setup.
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

