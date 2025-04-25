package entities.enums;

public enum ChucVu {
	QUẢNLÝ("Quản lý"),
	TIẾPTÂN("Tiếp tân"),
	PHỤCVỤ("Phục vụ");
	private String chucVu;
	ChucVu(String chucVu) {
		this.chucVu = chucVu;
	}

	public String getChucVu() {
		return chucVu;
	}

	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}
}
