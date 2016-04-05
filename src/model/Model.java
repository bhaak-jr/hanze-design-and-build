package src.model;

/**
 * Created by Bas Haaksema on 05-Apr-16.
 */
public class Model extends AbstractModel implements Runnable {
    private boolean run;

    public Model() {}

    public void start() {
        new Thread(this).start();
    }

    public void stop() {
        run=false;
    }

    @Override
    public void run() {
        run = true;
        while(run) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {}
        }
    }
}