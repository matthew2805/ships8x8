import java.math.BigInteger;

public class battle8x8 {
    private final long ships;
    private long shots = 0L;

    public battle8x8(final long ships) {
        this.ships = ships;
    }

    public boolean shoot(String shot) {
        var x = shot.charAt(0) - 65;
        var y = Character.getNumericValue(shot.charAt(1)) - 1;

        updateShots(x, y);

        return getBinaryMap(ships)
                .charAt(x + y * 8) == '1';
    }

    public String state() {
        var tempMap = getBinaryMap(ships)
                .replaceAll("1", "☐")
                .replaceAll("0", ".");
        StringBuilder map = new StringBuilder(tempMap);
        StringBuilder mapOfShots = new StringBuilder(getBinaryMap(shots));
        for (int i = 0; i < map.length(); i++) {
            if (map.charAt(i) == '☐' && mapOfShots.charAt(i) == '1') map.setCharAt(i, '☒');
            if (map.charAt(i) == '.' && mapOfShots.charAt(i) == '1') map.setCharAt(i, '×');
        }
        return String.join("\n", map.toString().split("(?<=\\G.{8})"));
    }

    private void updateShots(int x, int y) {
        var shotsBinary = "0".repeat(64);
        StringBuilder currentShot = new StringBuilder(shotsBinary);
        currentShot.setCharAt(x + y * 8, '1');
        BigInteger previousShot = new BigInteger(Long.toBinaryString(shots), 2);
        shots = new BigInteger(currentShot.toString(), 2)
                .or(previousShot).longValue();
    }

    private String getBinaryMap(long n) {
        var leadingZeros = Long.numberOfLeadingZeros(n);
        return "0".repeat(leadingZeros) + Long.toBinaryString(n);
    }
}
