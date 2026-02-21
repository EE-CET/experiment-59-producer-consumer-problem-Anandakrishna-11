class SharedResource {
    int item;
    boolean available = false;

    synchronized void put(int item) {
        try {
            while (available) wait();
            this.item = item;
            available = true;
            System.out.println("Produced: " + item);
            notify();
        } catch (Exception e) { }
    }

    synchronized void get() {
        try {
            while (!available) wait();
            System.out.println("Consumed: " + item);
            available = false;
            notify();
        } catch (Exception e) { }
    }
}

class Producer extends Thread {
    SharedResource r;

    Producer(SharedResource r) {
        this.r = r;
    }

    public void run() {
        for (int i = 1; i < 6; i++) {
            r.put(i);
        }
    }
}

class Consumer extends Thread {
    SharedResource r;

    Consumer(SharedResource r) {
        this.r = r;
    }

    public void run() {
        for (int i = 1; i < 6; i++) {
            r.get();
        }
    }
}

public class ProducerConsumer {
    public static void main(String[] args) {
        SharedResource r = new SharedResource();
        Producer p = new Producer(r);
        Consumer c = new Consumer(r);
        p.start();
        c.start();
    }
}