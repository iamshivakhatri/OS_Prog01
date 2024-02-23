import java.util.Random;
import java.util.Vector;
import java.util.Iterator;

class NoSynch extends Thread {
    protected static int total = 0;
    private int value;

    public NoSynch(int value) {
        this.value = value;
    }

    public void run() {
        int local;
        local = total;
        local += value;
        total = local;
    }

    public static int getTotal() {
        return total;
    }
    public static void main(String[] args) {
        Random random = new Random();
        int trueCount = 0;
        Vector<NoSynch> vector = new Vector<>();

        for (int i = 0; i < 100; i++) { // Create 100 threads
            int randomValue = random.nextInt(201) - 100; // Generate random integer between -100 and 100
            trueCount += randomValue;
            NoSynch noSynch = new NoSynch(randomValue);
            vector.add(noSynch);
            noSynch.start();
        }

        try {
            Iterator<NoSynch> iterator = vector.iterator();
            while (iterator.hasNext()) {
                NoSynch thread = iterator.next();
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("True count: " + trueCount);
        System.out.println("Total from NoSynch class: " + NoSynch.getTotal());
    }
}

