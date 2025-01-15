package entities;

import java.io.Serializable;

import entities.enums.LoaiPhong;
import entities.enums.TrangThaiPhong;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "phong")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Phong implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "maphong", columnDefinition = "bigint")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long maPhong;
	
	@Column(name = "tenphong", columnDefinition = "nvarchar(255)", nullable = false)
	private String tenPhong;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "trangthai", nullable = false)
	private TrangThaiPhong trangThai = TrangThaiPhong.ĐANGBẢOTRÌ;
	
	@Column(name = "mota",columnDefinition = "longtext", nullable = true)
	private String moTa;
	
	@Column(name = "giaphong", columnDefinition = "bigint", nullable = false)
	private long giaPhong;
	
	@Column(name = "publicid", columnDefinition = "nvarchar(255)")
	private String publicID;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "loaiphong", nullable = false)
	private LoaiPhong loaiPhong;


}
