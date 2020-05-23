class SleepyThread extends Thread {
    @Override
    public void run() {
        System.out.println("I'm thread with id = " + this.getId() + " and I'm going to sleep");
        try {
            Thread.sleep(2000);
            System.out.println("I'm thread with id = " + this.getId() + " and I'm waking up");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class SleepJoin {
    public static void main(String[] args) throws InterruptedException {
        SleepyThread t1 = new SleepyThread();
        SleepyThread t2 = new SleepyThread();
        SleepyThread t3 = new SleepyThread();
        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();
        t3.join();
    }
}
