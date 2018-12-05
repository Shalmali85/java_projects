import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class Consumer implements Runnable {
    LinkedBlockingQueue queue;
    public void run() {

            while(true){
            try {
                if(!queue.isEmpty()) {
                    System.out.println("Message consumed" + queue.element().toString());
                    queue.poll();
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Consumer(LinkedBlockingQueue queue){
        this.queue = queue;
    }

}
