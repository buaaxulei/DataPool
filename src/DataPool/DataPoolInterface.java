package DataPool;

import java.util.List;

public interface DataPoolInterface<T> {

    void initDataPool(int partCount, int partVolume, DataSetCallBack callBack);

    boolean checkIn(T data);

    T checkOut() throws InterruptedException;
}
