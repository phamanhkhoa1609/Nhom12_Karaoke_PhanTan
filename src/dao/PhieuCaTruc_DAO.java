package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate; // Import kiểu dữ liệu LocalDate

import connectDB.ConnectDB;
import entity.PhieuCaTruc;

public class PhieuCaTruc_DAO {
	private static PhieuCaTruc_DAO instance = new PhieuCaTruc_DAO();

	public static PhieuCaTruc_DAO getInstance() {
		if (instance == null)
			instance = new PhieuCaTruc_DAO();
		return instance;
	}

	public List<PhieuCaTruc> getAllPhieuCaTruc() {
		List<PhieuCaTruc> dsPCT = new ArrayList<PhieuCaTruc>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		try (Statement statement = con.createStatement()) {
			String sql = "SELECT *\r\n" + "FROM PhieuCaTruc pct\r\n"
					+ "INNER JOIN CaTruc ct ON pct.IDCaTruc = ct.IDCaTruc\r\n"
					+ "INNER JOIN NhanVien nv ON pct.IDNhanVien = nv.IDNhanVien";
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String iDCaTruc = rs.getString("IDCaTruc");
				String idNV = rs.getString("IDNhanVien");
				String tenNV = rs.getString("TenNV");
				String caTruc = rs.getString("CaTruc");
				LocalDate ngayTruc = rs.getDate("NgayTruc").toLocalDate();
				PhieuCaTruc pct = new PhieuCaTruc(iDCaTruc, idNV, tenNV, caTruc, ngayTruc);
				dsPCT.add(pct);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsPCT;
	}

	public void addCaTruc(PhieuCaTruc pct) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("insert into PhieuCaTruc values(?,?,?)");
			stmt.setString(1, pct.getIDCaTruc());
			stmt.setString(2, pct.getIDNhanVien());
			Date date = Date.valueOf(pct.getNgayTruc());
			Timestamp timestamp = new Timestamp(date.getTime());
			stmt.setTimestamp(3, timestamp);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
	}

	public void updatePhieuCaTruc(PhieuCaTruc pct) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(
					"update PhieuCaTruc " + "set [NgayTruc] = ? " + "where IDCaTruc = ? and IDNhanVien = ?");

			Date date = Date.valueOf(pct.getNgayTruc());
			Timestamp timestamp = new Timestamp(date.getTime());
			stmt.setTimestamp(1, timestamp);
			stmt.setString(2, pct.getIDCaTruc());
			stmt.setString(3, pct.getIDNhanVien());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
	}

	public void deletePhieuCaTruc(String IDCaTruc, String IDNhanVien) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		String sql = "delete from PhieuCaTruc where IDCaTruc = ? and IDNhanVien = ?";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, IDCaTruc);
			stmt.setString(2, IDNhanVien);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
	}

	private void close(PreparedStatement stmt) {
		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
