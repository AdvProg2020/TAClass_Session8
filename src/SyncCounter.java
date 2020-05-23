class Counter {
    public synchronized void count(boolean countEven) {
        for (int i = (countEven ? 0 : 1); i < 100; i+=2) {
            System.out.println("I'm in thread with id = " + Thread.currentThread().getId() + " and counting = " + i);
            notify();
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println("Problem here");
                e.printStackTrace();
            }
        }
        notify();
    }
}

class TestThread extends Thread {
    private Counter counter;
    private boolean countEven;

    public TestThread(Counter counter, boolean countEven) {
        this.counter = counter;
        this.countEven = countEven;
    }

    @Override
    public void run() {
        counter.count(countEven);
    }
}


public class SyncCounter {
    public static void main(String[] args) {
        System.out.println("I'm in main thread with id = " + Thread.currentThread().getId());
        Counter counter = new Counter();
        TestThread t1 = new TestThread(counter, true);
        TestThread t2 = new TestThread(counter, false);
        t1.start();
        t2.start();
    }
}
