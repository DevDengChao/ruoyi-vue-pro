package cn.iocoder.yudao.framework.common.util.date;

import org.springframework.stereotype.Component;

@Component
public class DefaultTimeStampSupplier implements TimeStampSupplier {

    @Override
    public Long get() {
        return System.currentTimeMillis();
    }
}
