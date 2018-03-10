package com.zzy.commonlib.core;

import com.hwangjr.rxbus.Bus;
public final class BusHelper {
    private Bus bus;
    private static class Holder {
        private final static BusHelper instance = new BusHelper();
    }
    private BusHelper() {
        bus = new Bus();
    }
    public static Bus getBus() {
        return Holder.instance.bus;
    }
}