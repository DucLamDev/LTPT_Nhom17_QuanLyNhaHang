package common;

public enum TableStatusEnum {
    AVAILABLE("Bàn trống"),
    OCCUPIED("Đang phục vụ");

    private String tableStatus;

    private TableStatusEnum(String tableStatus) {
        this.tableStatus = tableStatus;
    }

    public String getTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(String tableStatus) {
        this.tableStatus = tableStatus;
    }
}
