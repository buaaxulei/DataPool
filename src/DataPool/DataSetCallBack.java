package DataPool;

import javax.security.auth.callback.Callback;
import java.util.List;

public class DataSetCallBack implements Callback {
    void setData(List data, int count){
        for(int i = 0; i < count; i++)
            data.add(new Object());
    }
}
