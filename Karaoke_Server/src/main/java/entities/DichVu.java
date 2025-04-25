package entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;

import entities.enums.LoaiDichVu;
import entities.enums.TrangThaiDichVu;
import entities.enums.TrangThaiPhong;
import jakarta.persistence.AssociationOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "dichvu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DichVu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "madv", columnDefinition = "bigint")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long maDV;
	
	@Column(name = "tendv", columnDefinition = "nvarchar(255)", nullable = false)
	private String tenDV;
	
	@Column(name = "mota", columnDefinition = "longtext")
	private String moTa;
	
	@Column(name = "soluong", columnDefinition = "mediumint default 0", nullable = false)
	private int soLuong;
	
	@Column(name = "publicid", columnDefinition = "nvarchar(255)")
	private String publicID;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "loaidv", nullable = true)
	private LoaiDichVu loaiDV;
	
	@Column(name = "giadv", columnDefinition = "bigint default 0")
	private long giaDV;
	
	@Enumerated(value =  EnumType.STRING)
	@Column(name = "trangthai", nullable = false)
	private TrangThaiDichVu trangThai;
	
	@OneToMany(mappedBy = "dichVu")
	private Set<ChiTietHoaDonDichVu> dsCTHDDV = new HashSet<ChiTietHoaDonDichVu>();


	
}
