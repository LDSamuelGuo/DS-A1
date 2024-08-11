import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface defines the remote methods available for client-server interaction
 * in the distributed calculator application. Clients can invoke these methods on 
 * the server to perform various stack-based operations.
 */
public interface Calculator extends Remote {

    /**
     * Creates a unique user ID for the client, which is then used to manage
     * the client's stack on the server. Each user ID is mapped to an individual
     * stack, ensuring that operations are performed on the correct stack instance.
     * 
     * Returns:
     *     A unique user ID as a String.
     * 
     * Throws:
     *     RemoteException if a remote communication error occurs.
     */
    public String createUserID() throws RemoteException;

    /**
     * Pushes a value onto the stack associated with the specified user ID.
     * 
     * Parameters:
     *     id - The unique user ID corresponding to the client's stack.
     *     val - The integer value to be pushed onto the stack.
     * 
     * Throws:
     *     RemoteException if a remote communication error occurs.
     */
    public void pushValue(String id, Integer val) throws RemoteException;

    /**
     * Pushes an operator onto the stack associated with the specified user ID.
     * The operator may be one of the following: "min", "max", "lcm", or "gcd".
     * 
     * Parameters:
     *     id - The unique user ID corresponding to the client's stack.
     *     operator - The operator to be pushed onto the stack.
     * 
     * Throws:
     *     RemoteException if a remote communication error occurs.
     */
    public void pushOperation(String id, String operator) throws RemoteException;

    /**
     * Pops the top value from the stack associated with the specified user ID and
     * returns it to the client.
     * 
     * Parameters:
     *     id - The unique user ID corresponding to the client's stack.
     * 
     * Returns:
     *     The integer value popped from the stack.
     * 
     * Throws:
     *     RemoteException if a remote communication error occurs.
     */
    public Integer pop(String id) throws RemoteException;

    /**
     * Checks whether the stack associated with the specified user ID is empty.
     * 
     * Parameters:
     *     id - The unique user ID corresponding to the client's stack.
     * 
     * Returns:
     *     True if the stack is empty, false otherwise.
     * 
     * Throws:
     *     RemoteException if a remote communication error occurs.
     */
    public boolean isEmpty(String id) throws RemoteException;

    /**
     * Delays the pop operation for a specified number of milliseconds before
     * popping the top value from the stack associated with the specified user ID.
     * 
     * Parameters:
     *     id - The unique user ID corresponding to the client's stack.
     *     millis - The number of milliseconds to delay the pop operation.
     * 
     * Returns:
     *     The integer value popped from the stack after the delay.
     * 
     * Throws:
     *     RemoteException if a remote communication error occurs.
     */
    public Integer delayPop(String id, Integer millis) throws RemoteException;
}
