public class Script {

    public static String defenitionHit(int x, int y, float r) {

        String str = "Точка не попадает в область.";

        if (x <= 0 && y >= 0 && (x * x + y * y <= (r / 2) * (r / 2))) {
            str = "Точка попадает в область.";
        }

        if (x >= 0 && x <= r / 2 && y >= 0 && y <= r) {
            str = "Точка попадает в область.";
        }

        if (x <= 0 && y <= 0 && y >= 2 * x - r) {
            str = "Точка попадает в область.";
        }

        return str;
    }

}
