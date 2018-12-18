package lucky_videoview;


import lucky_videoview.LuckyFrameLayout;

public class LuckyManger {

    public static LuckyFrameLayout firstFloorLuckyFrameLayout;
    public static LuckyFrameLayout secondFloorLuckyFrameLayout;

    public static LuckyFrameLayout getFirstFloor() {
        return firstFloorLuckyFrameLayout;
    }

    public static void setFirstFloor(LuckyFrameLayout luckyFrameLayout) {
        firstFloorLuckyFrameLayout = luckyFrameLayout;
    }

    public static LuckyFrameLayout getSecondFloor() {
        return secondFloorLuckyFrameLayout;
    }

    public static void setSecondFloor(LuckyFrameLayout luckyFrameLayout) {
        secondFloorLuckyFrameLayout = luckyFrameLayout;
    }

    public static LuckyFrameLayout getCurrentJzvd() {
        if (getSecondFloor() != null) {
            return getSecondFloor();
        }
        return getFirstFloor();
    }

    public static void completeAll() {
        if (secondFloorLuckyFrameLayout != null) {
            secondFloorLuckyFrameLayout.onCompletion();
            secondFloorLuckyFrameLayout = null;
        }
        if (firstFloorLuckyFrameLayout != null) {
            firstFloorLuckyFrameLayout.onCompletion();
            firstFloorLuckyFrameLayout = null;
        }
    }
}
