import java.util.Scanner;
import java.util.Vector;
import java.util.Iterator;
import java.util.Random;

class ProgState {
    private int totalObjects; // to count the total number of objects
    private int Acount; // to count the number of objects of class A
    private int Bcount; // to count the number of objects of class B
    private int Ccount; // to count the number of objects of class C
    private int Dcount; // to count the number of objects of class D

    public ProgState() {
        totalObjects = 0;
        Acount = 0;
        Bcount = 0;
        Ccount = 0;
        Dcount = 0;
    }

    public synchronized void incA() {
        Acount++;
        totalObjects++;
    }

    public synchronized void decA() {
        Acount--;
        totalObjects--;
    }

    public synchronized void incB() {
        Bcount++;
        totalObjects++;
    }

    public synchronized void decB() {
        Bcount--;
        totalObjects--;
    }

    public synchronized void incC() {
        Ccount++;
        totalObjects++;
    }

    public synchronized void decC() {
        Ccount--;
        totalObjects--;
    }

    public synchronized void incD() {
        Dcount++;
        totalObjects++;
    }

    public synchronized void decD() {
        Dcount--;
        totalObjects--;
    }

    public synchronized void printTotal() {
        int total = Acount + Bcount + Ccount + Dcount;
        synchronized (System.out) {
            
            System.out.println("Total is " + total);
            System.out.println("***************\n");

            System.out.println(Acount + "  ClassA objects " );
            System.out.println(Bcount + "  ClassB objects " );
            System.out.println(Ccount + "  ClassC objects " );
            System.out.println(Dcount + "  ClassD objects\n" );
            
            System.out.flush();
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProgState state = new ProgState();
        Vector<Thread> V = new Vector<>();
        
        System.out.println("Please enter the path to the input file:");
        String filePath = scanner.nextLine();
        state.printTotal();

        try (Scanner fileScanner = new Scanner(new java.io.File(filePath))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(" ");
                    char letter = parts[0].charAt(0);
                    long sleepTime = Long.parseLong(parts[1]);

                    Thread thread = null;

                    switch (letter) {
                        case 'A':
                            thread = new Aclass(state, sleepTime);
                            break;
                        case 'B':
                            thread = new Bclass(state, sleepTime);
                            break;
                        case 'C':
                            thread = new Thread(new Cclass(state, sleepTime));
                            break;
                        case 'D':
                            thread = new Thread(new Dclass(state, sleepTime));
                            break;
                        case 'P':
                            state.printTotal();
                            break;
                        default:
                            System.out.println("Invalid input: " + line);
                            break;
                    }

                    if (thread != null) {
                        V.add(thread);
                        thread.start();
                    }
                }
            }

            Iterator<Thread> iterate = V.iterator();
            while (iterate.hasNext()) {
                Thread thread = iterate.next();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("All threads have completed. Moving to Part 3.");
            // Start executing Part 3 code here...
        } catch (java.io.FileNotFoundException e) {
            System.out.println("File not found!");
        }
        // Continue with Part 3 implementation here
    }
}

class Aclass extends Thread {
    private ProgState count;
    protected long time_to_sleep;

    public Aclass(ProgState count, long time_to_sleep) {
        this.count = count;
        this.time_to_sleep = time_to_sleep;
    }

    public void run() {
        System.out.println("Class A " + getName() + " is INCrementing");
        count.incA();
        
        System.out.println("Class A " + getName() + " Preparing to sleep for " + time_to_sleep + " milliseconds");
        try {
            Thread.sleep(time_to_sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Class A " + getName() + " is now AWAKE");
        System.out.println("Class A " + getName() + " is DECrementing");
        count.decA();
        
        System.out.println("Class A " + getName() + " EXITING!!!");
    }
}

class Bclass extends Thread {
    private ProgState count;
    protected long time_to_sleep;

    public Bclass(ProgState count, long time_to_sleep) {
        this.count = count;
        this.time_to_sleep = time_to_sleep;
    }

    public void run() {
        System.out.println("Class B " + getName() + " is INCrementing");
        count.incB();
        
        System.out.println("Class B " + getName() + " Preparing to sleep for " + time_to_sleep + " milliseconds");
        try {
            Thread.sleep(time_to_sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Class B " + getName() + " is now AWAKE");
        
        System.out.println("Class B " + getName() + " is DECrementing");
        count.decB();
        System.out.println("Class B " + getName() + " EXITING!!!");
    }
}

class Cclass implements Runnable {
    private ProgState count;
    protected long time_to_sleep;

    public Cclass(ProgState count, long time_to_sleep) {
        this.count = count;
        this.time_to_sleep = time_to_sleep;
    }

    public void run() {
        
        System.out.println("Class C " + Thread.currentThread().getName() + " is INCrementing");
        count.incC();
        System.out.println("Class C " + Thread.currentThread().getName() + " Preparing to sleep for " + time_to_sleep + " milliseconds");
        try {
            Thread.sleep(time_to_sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Class C " + Thread.currentThread().getName() + " is now AWAKE");
        
        System.out.println("Class C " + Thread.currentThread().getName() + " is DECrementing");
        count.decC();
        System.out.println("Class C " + Thread.currentThread().getName() + " EXITING!!!");
    }
}

class Dclass implements Runnable {
    private ProgState count;
    protected long time_to_sleep;

    public Dclass(ProgState count, long time_to_sleep) {
        this.count = count;
        this.time_to_sleep = time_to_sleep;
    }

    public void run() {
        
        System.out.println("Class D " + Thread.currentThread().getName() + " is INCrementing");
        count.incD();
        System.out.println("Class D " + Thread.currentThread().getName() + " Preparing to sleep for " + time_to_sleep + " milliseconds");
        try {
            Thread.sleep(time_to_sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Class D " + Thread.currentThread().getName() + " is now AWAKE");
        
        System.out.println("Class D " + Thread.currentThread().getName() + " is DECrementing");
        count.decD();
        System.out.println("Class D " + Thread.currentThread().getName() + " EXITING!!!");
    }
}
