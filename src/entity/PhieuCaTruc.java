package entity;

import java.time.LocalDate;

public class PhieuCaTruc {
	private String IDCaTruc, IDNhanVien, tenNV,caTruc;
	private LocalDate NgayTruc;
	public PhieuCaTruc() {
		super();
	}
	
	public PhieuCaTruc(String iDCaTruc, String iDNhanVien, String tenNV, String caTruc, LocalDate ngayTruc) {
		super();
		IDCaTruc = iDCaTruc;
		IDNhanVien = iDNhanVien;
		this.tenNV = tenNV;
		this.caTruc = caTruc;
		NgayTruc = ngayTruc;
	}

	public String getTenNV() {
		return tenNV;
	}

	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}

	public String getIDCaTruc() {
		return IDCaTruc;
	}
	public void setIDCaTruc(String iDCaTruc) {
		IDCaTruc = iDCaTruc;
	}
	public String getIDNhanVien() {
		return IDNhanVien;
	}
	public void setIDNhanVien(String iDNhanVien) {
		IDNhanVien = iDNhanVien;
	}
	public String getCaTruc() {
		return caTruc;
	}
	public void setCaTruc(String caTruc) {
		this.caTruc = caTruc;
	}
	public LocalDate getNgayTruc() {
		return NgayTruc;
	}
	public void setNgayTruc(LocalDate ngayTruc) {
		NgayTruc = ngayTruc;
	}
	
	
	
}
