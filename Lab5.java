import java.util.Scanner;

public class Lab5 {

    static final int CAPACITY = 5;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayStack typed = new ArrayStack(CAPACITY);
        ArrayQueue queue = new ArrayQueue(CAPACITY);
        System.out.println("CpE211 Lab5 - Array Stack, Array Queue, Undo Simulation");
        while (true) {
            System.out.println();
            System.out.println("==== Main Menu ====");
            System.out.println("[1] Type a number  (stack push)  -> 1 <num>");
            System.out.println("[2] UNDO           (stack pop)");
            System.out.println("[3] Peek last typed");
            System.out.println("[4] Display typed history");
            System.out.println("[5] Customer arrives (queue enqueue) -> 5 <num>");
            System.out.println("[6] Serve customer   (queue dequeue)");
            System.out.println("[7] Peek next in line");
            System.out.println("[8] Display queue");
            System.out.println("[9] Run test script (T1-T5)");
            System.out.println("[0] Exit");
            System.out.print("Choice: ");
            if (!in.hasNextInt()) {
                System.out.println("Invalid input - enter a number.");
                in.next();
                continue;
            }
            int choice = in.nextInt();
            if (choice == 0) {
                System.out.println("Goodbye!");
                break;
            }
            switch (choice) {
                case 1:
                    if (!in.hasNextInt()) { System.out.println("Enter a number after 1."); in.next(); break; }
                    typed.push(in.nextInt());
                    break;
                case 2:
                    int undone = typed.pop();
                    if (undone != -1) System.out.println("Undo removed last typed: " + undone);
                    break;
                case 3:
                    int lastTyped = typed.peek();
                    if (lastTyped != -1) System.out.println("Last typed: " + lastTyped);
                    break;
                case 4:
                    typed.display();
                    break;
                case 5:
                    if (!in.hasNextInt()) { System.out.println("Enter a number after 5."); in.next(); break; }
                    queue.enqueue(in.nextInt());
                    break;
                case 6:
                    int served = queue.dequeue();
                    if (served != -1) System.out.println("Served customer: " + served);
                    break;
                case 7:
                    int next = queue.peek();
                    if (next != -1) System.out.println("Next in line: " + next);
                    break;
                case 8:
                    queue.display();
                    break;
                case 9:
                    runTests();
                    break;
                default:
                    System.out.println("Unknown option: " + choice);
            }
        }
        in.close();
    }

    static void section(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }

    static boolean check(String label, boolean ok) {
        System.out.println((ok ? "PASS" : "FAIL") + " - " + label);
        return ok;
    }

    static void runTests() {
        boolean pass = true;

        section("T1 Standard ops");
        ArrayStack s1 = new ArrayStack(CAPACITY);
        s1.push(10);
        s1.push(20);
        pass &= check("stack pop returns 20 (LIFO)", s1.pop() == 20);
        s1.display();
        ArrayQueue q1 = new ArrayQueue(CAPACITY);
        q1.enqueue(1);
        q1.enqueue(2);
        pass &= check("queue dequeue returns 1 (FIFO)", q1.dequeue() == 1);
        q1.display();

        section("T2 Underflow on empty");
        ArrayQueue q2 = new ArrayQueue(CAPACITY);
        pass &= check("dequeue on empty queue returns -1", q2.dequeue() == -1);
        ArrayStack s2 = new ArrayStack(CAPACITY);
        pass &= check("pop on empty stack returns -1", s2.pop() == -1);

        section("T3 Fill to capacity + wrap");
        ArrayStack s3 = new ArrayStack(CAPACITY);
        for (int i = 1; i <= 5; i++) s3.push(i);
        pass &= check("stack isFull after 5 pushes", s3.isFull());
        pass &= check("stack peek is 5", s3.peek() == 5);
        s3.push(6);
        pass &= check("6th push rejected, top unchanged (pop gives 5)", s3.pop() == 5);
        ArrayQueue q3 = new ArrayQueue(CAPACITY);
        for (int i = 1; i <= 5; i++) q3.enqueue(i);
        pass &= check("queue isFull after 5 enqueues", q3.isFull());
        q3.enqueue(6);
        pass &= check("6th enqueue rejected (count still full)", q3.isFull());
        pass &= check("dequeue serves 1", q3.dequeue() == 1);
        pass &= check("dequeue serves 2", q3.dequeue() == 2);
        q3.enqueue(6);
        q3.enqueue(7);
        pass &= check("rear wrapped 4 to 0, queue full again", q3.isFull());
        pass &= check("fill order preserved, dequeue serves 3", q3.dequeue() == 3);

        section("T4 Inspect does not modify");
        ArrayStack s4 = new ArrayStack(CAPACITY);
        s4.push(10);
        s4.push(20);
        int stackBefore = s4.peek();
        s4.display();
        pass &= check("stack peek yields 20", stackBefore == 20);
        pass &= check("stack peek repeatable, still 20", s4.peek() == 20 && !s4.isEmpty());
        ArrayQueue q4 = new ArrayQueue(CAPACITY);
        q4.enqueue(1);
        q4.enqueue(2);
        int queueBefore = q4.peek();
        q4.display();
        pass &= check("queue peek yields 1", queueBefore == 1);
        pass &= check("queue peek repeatable, still 1", q4.peek() == 1 && !q4.isEmpty());

        section("T5 Drain order");
        ArrayStack s5 = new ArrayStack(CAPACITY);
        for (int i = 1; i <= 5; i++) s5.push(i);
        boolean lifo = s5.pop() == 5 && s5.pop() == 4 && s5.pop() == 3 && s5.pop() == 2 && s5.pop() == 1;
        pass &= check("stack drains 5 4 3 2 1 (LIFO)", lifo && s5.isEmpty());
        ArrayQueue q5 = new ArrayQueue(CAPACITY);
        for (int i = 1; i <= 5; i++) q5.enqueue(i);
        boolean fifo = q5.dequeue() == 1 && q5.dequeue() == 2 && q5.dequeue() == 3 && q5.dequeue() == 4 && q5.dequeue() == 5;
        pass &= check("queue drains 1 2 3 4 5 (FIFO)", fifo && q5.isEmpty());

        System.out.println();
        System.out.println(pass ? "ALL TESTS PASSED" : "SOME TESTS FAILED - check FAIL lines above");
    }
}

class ArrayStack {

    private final int[] data;
    private final int capacity;
    private int top;

    ArrayStack(int capacity) {
        this.capacity = capacity;
        data = new int[capacity];
        top = -1;
    }

    boolean isEmpty() {
        return top == -1;
    }

    boolean isFull() {
        return top == capacity - 1;
    }

    void push(int x) {
        if (isFull()) {
            System.out.println("Stack Overflow - cannot push " + x);
            return;
        }
        top = top + 1;
        data[top] = x;
    }

    int pop() {
        if (isEmpty()) {
            System.out.println("Stack Underflow - returning -1");
            return -1;
        }
        int value = data[top];
        top = top - 1;
        return value;
    }

    int peek() {
        if (isEmpty()) {
            System.out.println("Stack empty - peek returning -1");
            return -1;
        }
        return data[top];
    }

    void display() {
        if (isEmpty()) {
            System.out.println("Stack is empty");
            return;
        }
        System.out.print("Stack (top -> bottom):");
        for (int i = top; i >= 0; i--) {
            System.out.print(" " + data[i]);
        }
        System.out.println();
    }
}

class ArrayQueue {

    private final int[] data;
    private final int capacity;
    private int front;
    private int rear;
    private int count;

    ArrayQueue(int capacity) {
        this.capacity = capacity;
        data = new int[capacity];
        front = 0;
        rear = -1;
        count = 0;
    }

    boolean isEmpty() {
        return count == 0;
    }

    boolean isFull() {
        return count == capacity;
    }

    void enqueue(int x) {
        if (isFull()) {
            System.out.println("Queue Overflow - cannot enqueue " + x);
            return;
        }
        rear = (rear + 1) % capacity;
        data[rear] = x;
        count = count + 1;
    }

    int dequeue() {
        if (isEmpty()) {
            System.out.println("Queue Underflow - returning -1");
            return -1;
        }
        int value = data[front];
        front = (front + 1) % capacity;
        count = count - 1;
        return value;
    }

    int peek() {
        if (isEmpty()) {
            System.out.println("Queue empty - peek returning -1");
            return -1;
        }
        return data[front];
    }

    void display() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return;
        }
        System.out.print("Queue (front -> rear):");
        for (int k = 0; k < count; k++) {
            System.out.print(" " + data[(front + k) % capacity]);
        }
        System.out.println();
    }
}
