package entities;

import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import entities.enums.TrangThaiHoaDon;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "hoadon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoaDon implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "mahd", columnDefinition = "bigint")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long maHD;

	@Column(name = "thoigianlap", columnDefinition = "datetime")
	private LocalDateTime thoiGianLap = LocalDateTime.now();

	@Column(name = "thoihan", columnDefinition = "datetime")
	private LocalDateTime thoiHan = thoiGianLap.plusDays(7);
	
	@Column(name = "thoigianthanhtoan", columnDefinition = "datetime")
	private LocalDateTime thoiGianThanhToan;

	@Enumerated(EnumType.STRING)
	@Column(name = "trangthai", nullable = false)
	private TrangThaiHoaDon trangThai = TrangThaiHoaDon.CHƯATHANHTOÁN;
	
	@OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<ChiTietHoaDonDichVu> dsCTHDDV = new HashSet<ChiTietHoaDonDichVu>();

	@OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<ChiTietHoaDonPhong> dsCTHDP = new HashSet<ChiTietHoaDonPhong>();
	
	@ManyToOne
	@JoinColumn(name="manvtt", columnDefinition = "bigint")
	private NhanVien tiepTan;
	
	@ManyToOne
	@JoinColumn(name="manvpv",columnDefinition = "bigint")
	private NhanVien phucVu;
	
	@ManyToOne
	@JoinColumn(name = "makh", columnDefinition = "bigint")
	private KhachHang khachHang;
	
	@Column(name = "khuyenmai", columnDefinition = "bigint")
	private long khuyenMai = 0L;
	
	@Column(name = "tienkhachdua", columnDefinition = "bigint")
	private long tienKhachDua = 0L;

	public long getTongTien() {
		// TODO Auto-generated method stub
		return dsCTHDDV.stream().map(ChiTietHoaDonDichVu::getTongTien).reduce(0L, Long::sum)
				+dsCTHDP.stream().map(ChiTietHoaDonPhong::getDonGia).reduce(0L, Long::sum);
	}

	@Override
	public String toString() {
		return "HoaDon [maHD=" + maHD + ", thoiGianLap=" + thoiGianLap + ", thoiHan=" + thoiHan + ", thoiGianThanhToan="
				+ thoiGianThanhToan + ", trangThai=" + trangThai + ", tiepTan=" + tiepTan + ", phucVu=" + phucVu
				+ ", khachHang=" + khachHang + ", khuyenMai=" + khuyenMai + ", tienKhachDua=" + tienKhachDua + "]";
	}
}
