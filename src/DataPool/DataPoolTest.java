package DataPool;

import java.util.ArrayList;
import java.util.List;

public class DataPoolTest {
    private  static BaseDataPool dataPool = new BaseDataPool();
    private static long start;
    public static void main(String[] args){
        try {
            //noDataPoolRun();
            withDataPoolRun();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void noDataPoolRun() throws Exception{
        List<Object> data = new ArrayList<>();
        for(int i = 0; i < 2000; i ++){
            data.add(new Object());
        }
        System.out.println(data.size());
        start = System.currentTimeMillis();

        for(int i = 0; i < 2000; i++){
            Thread.sleep(20);
            data.get(0);
            data.remove(0);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void withDataPoolRun() throws Exception{

        dataPool.initDataPool(10,200,new DataSetCallBack());
        for(int i = 0; i < 2000; i++){
            dataPool.checkIn(i + "");
        }
        start = System.currentTimeMillis();
        System.out.println(start);

        for(int i = 0; i < 10; i ++){
            new Thread(new CheckOutTask()).start();
        }
    }

    static class CheckOutTask implements Runnable{

        @Override
        public void run() {
            while(dataPool.checkOut() != null) {

            }
            System.out.println(System.currentTimeMillis() - start);
        }
    }
}
