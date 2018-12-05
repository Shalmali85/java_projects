import java.util.Queue;
import java.util.concurrent.*;

public class MyThreadPoolExecutor {

    public ExecutorService executorService = Executors.newFixedThreadPool(2);


    public <obj extends Runnable> void processQueue (obj obj){

        executorService.execute(obj);
    }


}
