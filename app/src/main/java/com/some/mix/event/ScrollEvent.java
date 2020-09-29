package com.some.mix.event;

/**
 * @author cyl
 * @date 2020/9/29
 */
public class ScrollEvent {

    private boolean isUp;

    public ScrollEvent(boolean isUp){
        this.isUp = isUp;
    }

    public boolean isUp() {
        return isUp;
    }
}
