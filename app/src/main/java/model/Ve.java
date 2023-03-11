package model;

public class Ve {
    private String mave;
    private String loaiVe;
    private String ngayBay;
    private boolean thanhToan;

    public Ve(String mave, String loaiVe, String ngayBay, boolean thanhToan) {
        this.mave = mave;
        this.loaiVe = loaiVe;
        this.ngayBay = ngayBay;
        this.thanhToan = thanhToan;
    }

    public String getMave() {
        return mave;
    }

    public void setMave(String mave) {
        this.mave = mave;
    }

    public String getLoaiVe() {
        return loaiVe;
    }

    public void setLoaiVe(String loaiVe) {
        this.loaiVe = loaiVe;
    }

    public String getNgayBay() {
        return ngayBay;
    }

    public void setNgayBay(String ngayBay) {
        this.ngayBay = ngayBay;
    }

    public boolean isThanhToan() {
        return thanhToan;
    }

    public void setThanhToan(boolean thanhToan) {
        this.thanhToan = thanhToan;
    }
}
