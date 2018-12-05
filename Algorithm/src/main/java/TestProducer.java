import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class TestProducer {

    public static void main (String args[]){
        LinkedBlockingQueue synchronousQueue = new LinkedBlockingQueue();
        MyThreadPoolExecutor myThreadPoolExecutor= new MyThreadPoolExecutor();
        Producer producer = new Producer(synchronousQueue);
        myThreadPoolExecutor.processQueue(producer);
        Consumer consumer = new Consumer(synchronousQueue);
        myThreadPoolExecutor.processQueue(consumer);

    }
}
