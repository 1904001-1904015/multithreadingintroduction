public class Servers extends Main implements Runnable {
    public int threadIndex;
    public Servers(){
        threadIndex = threadNumber.getAndIncrement();
    }
    @Override
    public void run() {
        while(mainQueue.isEmpty()){continue;}
        Elements el = mainQueue.peek();
        mainQueue.remove();
        try {
            Thread.sleep(el.get_time());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Serviced Elements of "+ el.get_time()+"\n");
        //servicedCount[threadIndex-1]+=1;
    }
}
