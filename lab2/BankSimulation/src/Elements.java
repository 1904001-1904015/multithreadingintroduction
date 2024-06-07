public class Elements extends Main{
    private static int time;

    public Elements(){
        time = (int) (Math.random()*(t2-t1)+t1);
    }
    public int get_time(){
        return time;
    }
}
