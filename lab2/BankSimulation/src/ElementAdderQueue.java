import java.util.Random;

public class ElementAdderQueue extends Main implements Runnable {
    private int timeLimit;

    public ElementAdderQueue(int tle){
        this.timeLimit = tle;
    }

    @Override
    public void run(){
        Random random = new Random();
        while (timeLimit>0){
            int x = (int) (Math.random()*30);
            Elements el = new Elements();
            mainQueue.add(el);
            timeLimit-=x;
            try {
                Thread.sleep(x);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
