import net.datafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import entities.*;
import entities.enums.*;

public class Run {
    public static void main(String[] args) {
        // Khởi tạo Faker và EntityManager
        Faker faker = new Faker();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA ORM MariaDB");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Tạo dữ liệu giả cho khách hàng
            for (int i = 0; i < 10; i++) {
                KhachHang khachHang = new KhachHang();
                khachHang.setHoTen(faker.name().fullName());
                khachHang.setSdt(faker.phoneNumber().cellPhone());
                khachHang.setGioiTinh(GioiTinh.values()[faker.number().numberBetween(0, GioiTinh.values().length)]);

                // Tạo tài khoản liên kết
                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.setSdt(khachHang.getSdt());
                taiKhoan.setMatKhau(faker.internet().password(8, 16));
                khachHang.setTaiKhoan(taiKhoan);

                em.persist(khachHang);
                em.persist(taiKhoan); // Nếu cần lưu riêng
            }

            // Tạo dữ liệu giả cho nhân viên
            for (int i = 0; i < 10; i++) {
                NhanVien nhanVien = new NhanVien();
                nhanVien.setHoTen(faker.name().fullName());
                nhanVien.setSdt(faker.phoneNumber().cellPhone());
                nhanVien.setGioiTinh(GioiTinh.values()[faker.number().numberBetween(0, GioiTinh.values().length)]);

                // Tạo ngày sinh
                LocalDate minDate = LocalDate.of(1960, 1, 1);
                LocalDate maxDate = LocalDate.of(2005, 1, 1);
                long randomDays = faker.number().numberBetween(minDate.toEpochDay(), maxDate.toEpochDay());
                nhanVien.setNgaySinh(LocalDate.ofEpochDay(randomDays));

                nhanVien.setChucVu(ChucVu.values()[faker.number().numberBetween(0, ChucVu.values().length)]);
                nhanVien.setTrangThai(TrangThaiNhanVien.values()[faker.number().numberBetween(0, TrangThaiNhanVien.values().length)]);
                nhanVien.setPublicID(faker.internet().uuid());
                nhanVien.setCccd(faker.idNumber().valid());
                nhanVien.setDiaChi(faker.address().fullAddress());

                em.persist(nhanVien);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
