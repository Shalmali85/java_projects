import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class Producer implements Runnable {
    LinkedBlockingQueue queue;
    public void run() {
        for (int i =1; i< 10 ;i++){
            try {
                this.queue.put(i);
                System.out.println("Message produced"+i);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Producer(LinkedBlockingQueue queue){
        this.queue = queue;
    }

}
