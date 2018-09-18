package DataPool;

import java.util.ArrayList;
import java.util.List;

public class BaseDataPool implements DataPoolInterface {

    private List<List<Object>> lockedPart;
    private List<List<Object>> unlockedPart;

    private int partCount;
    private int partVolume;
    private DataSetCallBack callBack;

    @Override
    public void initDataPool(int partCount, int partVolume, DataSetCallBack callBack) {
        lockedPart = new ArrayList<>();
        unlockedPart = new ArrayList<>();

        this.partCount = partCount;
        this.partVolume = partVolume;
        this.callBack = callBack;
//        for(int i = 0; i < partCount; i ++){
//            unlockedPart.add(new ArrayList<>(partVolume));
//            lockedPart.add(new ArrayList<>(partVolume));
//        }
    }

    @Override
    public boolean checkIn(Object data) {
        List addToList = getCanCheckInUnlockedPart();

        if(addToList == null) {
            addToList = new ArrayList();
            synchronized (unlockedPart) {
                if (unlockedPart.size() < partCount)
                    unlockedPart.add(addToList);
                else
                    return false;
            }
        }
        //moveDataBetweenTwoPart(unlockedPart, lockedPart, addToList);
        addToList.add(data);
        //moveDataBetweenTwoPart(lockedPart, unlockedPart, addToList);
        return true;
    }

    @Override
    public Object checkOut() {
        List getFromList = null;
        synchronized (unlockedPart) {
            for (int index = 0; index < unlockedPart.size(); index++) {
                if (unlockedPart.get(index).size() > 0) {
                    getFromList = unlockedPart.get(index);
                    break;
                }
            }
            if (getFromList == null)
                return null;
            moveDataBetweenTwoPart(unlockedPart, lockedPart, getFromList);
        }
        Object data;
        synchronized (getFromList) {
            data = getFromList.get(0);
            getFromList.remove(0);
            System.out.println(data.toString());
            try {
                Thread.sleep(20);
            } catch (Exception e) {
            }
        }
//        if(getFromList.size() == 0){
//            this.callBack.setData(getFromList, partVolume);
//        }
        moveDataBetweenTwoPart(lockedPart, unlockedPart, getFromList);
        return data;
    }

    private List getCanCheckInUnlockedPart(){
        int index;
        for (index = 0; index < unlockedPart.size(); index++) {
            if (unlockedPart.get(index).size() < partVolume)
                return unlockedPart.get(index);
        }
        return null;
    }

    private List getCanCheckOutUnlockedPart(){
        synchronized (unlockedPart) {
            for (int index = 0; index < unlockedPart.size(); index++) {
                if (unlockedPart.get(index).size() > 0)
                    return unlockedPart.get(index);
            }
        }
        System.out.println(System.currentTimeMillis());
        return null;
    }

    private synchronized void moveDataBetweenTwoPart(List ordinaryPart, List destinationPart, List data){
        ordinaryPart.remove(data);
        destinationPart.add(data);
    }
}
