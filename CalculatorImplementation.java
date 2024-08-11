import java.rmi.RemoteException;
import java.util.*;

public class CalculatorImplementation implements Calculator {

    // The HashMap is used to map a user's ID to their respective stack of integers.
    // This allows each client to have a unique stack for their calculations.
    private Map<String, Stack<Integer>> values = new HashMap<>();

    // Constructor
    public CalculatorImplementation() throws RemoteException {
        super();
    }

    // PRIVATE METHODS FOR INTERNAL CALCULATIONS

    // Helper function for calculating the GCD (Greatest Common Divisor) of two numbers.
    // This method recursively finds the GCD using the Euclidean algorithm.
    private static int gcdHelper(int x, int y) {   
        if (y == 0) {
            return x;
        }
        return gcdHelper(y, x % y);
    }

    // Helper function for calculating the LCM (Least Common Multiple) of two numbers.
    // It uses the relationship LCM(x, y) = (x * y) / GCD(x, y).
    private static int lcmHelper(int x, int y) {
        return (x * y) / gcdHelper(x, y);
    }

    // Calculates the LCM of all elements in a stack.
    // It iteratively computes the LCM of the accumulated result and the next element in the stack.
    private static int lcm(Stack<Integer> stack) {
        int ans = stack.get(0);
        for (int i = 1; i < stack.size(); i++) {
            ans = lcmHelper(ans, stack.get(i));
        }
        return ans;
    }

    // Calculates the GCD of all elements in a stack.
    // It iteratively computes the GCD of the accumulated result and the next element in the stack.
    private static int gcd(Stack<Integer> stack) {   
        int ans = stack.get(0);
        for (int i = 1; i < stack.size(); i++) {
            ans = gcdHelper(ans, stack.get(i));
        }
        return ans;
    }

    // PUBLIC METHODS ACCESSIBLE TO CLIENT

    // Generates a unique user ID and associates it with a new stack in the map.
    // This method should be called by the client before using any other methods,
    // as it ensures the client has their own stack for operations.
    public String createUserID() {
        String id = UUID.randomUUID().toString();
        this.values.put(id, new Stack<>());
        return id;
    }

    // Pushes a value onto the stack associated with the provided user ID.
    public void pushValue(String id, Integer val) {
        this.values.get(id).push(val);
    }

    // Performs an operation (min, max, gcd, lcm) on the stack associated with the user ID.
    // After the operation, the stack is cleared, and the result is pushed back onto the stack.
    public void pushOperation(String id, String operator) {
        if (this.values.get(id).size() > 0) {
            int ans;
            if (operator.contains("min")) {
                ans = Collections.min(this.values.get(id));
            } else if (operator.contains("max")) {
                ans = Collections.max(this.values.get(id));
            } else if (operator.contains("gcd")) {
                ans = gcd(this.values.get(id));
            } else {
                ans = lcm(this.values.get(id));
            }
            this.values.get(id).clear();
            this.values.get(id).add(ans);
        }
    }

    // Pops a value from the stack associated with the provided user ID.
    // Returns null if the stack is empty.
    public Integer pop(String id) {
        if (this.values.get(id).size() == 0) {
            return null;
        } else {
            return this.values.get(id).pop();
        }
    }
    
    // Checks if the stack associated with the provided user ID is empty.
    // Returns true if the stack is empty, and false otherwise.
    public boolean isEmpty(String id) {
        return this.values.get(id).isEmpty();
    }

    // Pops a value from the stack associated with the provided user ID after a specified delay.
    // The delay is specified in milliseconds. Returns null if the stack is empty.
    public Integer delayPop(String id, Integer millis) {
        if (this.values.get(id).size() > 0) { 
            int ans = -1;
            try {
                Thread.sleep(millis);
                ans = this.values.get(id).pop();
            } catch (Exception e) {
                System.err.println("Server exception: " + e.toString());
                e.printStackTrace();
            }
            return ans;
        } else {
            return null;
        }
    }
}
