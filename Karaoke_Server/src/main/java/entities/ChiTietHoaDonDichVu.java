package entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Table(name = "chitiethoadondichvu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietHoaDonDichVu implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7931839827203259879L;

	@Column(name = "dongia", columnDefinition = "bigint")
	private long donGia;
	
	@Column(name = "soluong", columnDefinition = "tinyint")
	private int soLuong;
	
	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mahd",columnDefinition = "bigint")
	private HoaDon hoaDon;
	
	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "madv",columnDefinition = "bigint")
	private DichVu dichVu;

	
	public long getTongTien() {
		// TODO Auto-generated method stub
		return donGia * soLuong;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dichVu, hoaDon);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChiTietHoaDonDichVu other = (ChiTietHoaDonDichVu) obj;
		return Objects.equals(dichVu, other.dichVu) && Objects.equals(hoaDon, other.hoaDon);
	}

	@Override
	public String toString() {
		return "ChiTietHoaDonDichVu [donGia=" + donGia + ", soLuong=" + soLuong + ", dichVu=" + dichVu + "]";
	}
	
	
}
