package cleancode.minesweeper.tobe.minesweeper.board.cell;

import java.util.Objects;

public class CellSnapshot {

    private final CellSnapShotStatus status;
    private final int nearbyLandMineCount;

    private CellSnapshot(CellSnapShotStatus status, int nearbyLandMineCount) {
        this.status = status;
        this.nearbyLandMineCount = nearbyLandMineCount;
    }

    public static CellSnapshot of(CellSnapShotStatus status, int nearbyLandMineCount) {
        return new CellSnapshot(status, nearbyLandMineCount);
    }

    public static CellSnapshot ofEmpty() {
        return CellSnapshot.of(CellSnapShotStatus.EMPTY, 0);
    }

    public static CellSnapshot ofFlag() {
        return CellSnapshot.of(CellSnapShotStatus.FLAG, 0);
    }

    public static CellSnapshot ofLandMine() {
        return CellSnapshot.of(CellSnapShotStatus.LAND_MINE, 0);
    }

    public static CellSnapshot ofNumber(int nearbyLandMineCount) {
        return CellSnapshot.of(CellSnapShotStatus.NUMBER, nearbyLandMineCount);
    }

    public static CellSnapshot ofUnchecked() {
        return CellSnapshot.of(CellSnapShotStatus.UNCHECKED, 0);
    }

    public CellSnapShotStatus getStatus() {
        return status;
    }

    public int getNearbyLandMineCount() {
        return nearbyLandMineCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CellSnapshot that = (CellSnapshot) o;
        return getNearbyLandMineCount() == that.getNearbyLandMineCount() && getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatus(), getNearbyLandMineCount());
    }

    public boolean isSameStatus(CellSnapShotStatus cellSnapShotStatus) {
        return status == cellSnapShotStatus;
    }
}

