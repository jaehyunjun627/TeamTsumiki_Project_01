package model.dao;

import model.dto.KanjiDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * KanjiDAO.java - 한자 DB 접근 클래스
 * 
 * kanji 테이블에 대한 조회 처리
 * - findById()         : ID로 한자 조회
 * - findByIndex()      : 인덱스로 조회 (예: "N5-01-01")
 * - findByJlptLevel()  : JLPT 레벨별 조회 (예: "N5")
 * - findBySector()     : 레벨+섹터별 조회
 * - findAll()          : 전체 조회
 */
public class KanjiDAO {

    // ========== Oracle 접속 정보 ==========
    String url = "jdbc:oracle:thin:@localhost:1521:xe";
    String user = "system";
    String pass = "12345";

    Connection con;          // DB 연결 객체
    PreparedStatement pstmt; // SQL 실행 객체
    ResultSet rs;            // 결과 반환 객체

    // ========== DB 연결 ==========
    public void getCon() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========== 1. ID로 한자 조회 ==========
    public KanjiDTO findById(int kanjiID) {
        KanjiDTO dto = null;
        try {
            getCon();
            String sql = "SELECT * FROM kanji WHERE kanjiID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, kanjiID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dto = mapResultSetToDTO(rs);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }

    // ========== 2. 인덱스로 조회 (예: "N5-01-01") ==========
    public KanjiDTO findByIndex(String kanjiindex) {
        KanjiDTO dto = null;
        try {
            getCon();
            String sql = "SELECT * FROM kanji WHERE kanjiindex = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, kanjiindex);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dto = mapResultSetToDTO(rs);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }

    // ========== 3. JLPT 레벨별 조회 ==========
    public List<KanjiDTO> findByJlptLevel(String jlptLevel) {
        List<KanjiDTO> list = new ArrayList<>();
        try {
            getCon();
            String sql = "SELECT * FROM kanji WHERE jlpt_level = ? ORDER BY sector, index_num";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, jlptLevel);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToDTO(rs));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ========== 4. 레벨+섹터별 조회 ==========
    public List<KanjiDTO> findBySector(String jlptLevel, int sector) {
        List<KanjiDTO> list = new ArrayList<>();
        try {
            getCon();
            String sql = "SELECT * FROM kanji WHERE jlpt_level = ? AND sector = ? ORDER BY index_num";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, jlptLevel);
            pstmt.setInt(2, sector);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToDTO(rs));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ========== 5. 전체 조회 ==========
    public List<KanjiDTO> findAll() {
        List<KanjiDTO> list = new ArrayList<>();
        try {
            getCon();
            String sql = "SELECT * FROM kanji ORDER BY jlpt_level, sector, index_num";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToDTO(rs));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ========== 6. 한자 검색 ==========
    public List<KanjiDTO> searchByKanji(String kanji) {
        List<KanjiDTO> list = new ArrayList<>();
        try {
            getCon();
            String sql = "SELECT * FROM kanji WHERE kanji = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, kanji);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToDTO(rs));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ========== 7. 한국어 뜻으로 검색 ==========
    public List<KanjiDTO> searchByKoreanMeaning(String keyword) {
        List<KanjiDTO> list = new ArrayList<>();
        try {
            getCon();
            String sql = "SELECT * FROM kanji WHERE korean_meaning LIKE ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToDTO(rs));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ========== 8. JLPT 레벨별 한자 개수 ==========
    public int countByJlptLevel(String jlptLevel) {
        int count = 0;
        try {
            getCon();
            String sql = "SELECT COUNT(*) FROM kanji WHERE jlpt_level = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, jlptLevel);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    // ========== 9. 한자 존재 여부 확인 ==========
    public boolean exists(String kanjiindex) {
        boolean result = false;
        try {
            getCon();
            String sql = "SELECT kanjiindex FROM kanji WHERE kanjiindex = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, kanjiindex);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = true;
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // ========== ResultSet을 KanjiDTO로 변환 ==========
    private KanjiDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        KanjiDTO dto = new KanjiDTO();

        dto.setKanjiID(rs.getInt("kanjiID"));
        dto.setKanjiindex(rs.getString("kanjiindex"));
        dto.setKanji(rs.getString("kanji"));

        dto.setOnyomi1(rs.getString("onyomi1"));
        dto.setOnyomi2(rs.getString("onyomi2"));
        dto.setOnyomi3(rs.getString("onyomi3"));

        dto.setKunyomi1(rs.getString("kunyomi1"));
        dto.setKunyomi2(rs.getString("kunyomi2"));
        dto.setKunyomi3(rs.getString("kunyomi3"));

        dto.setKoreanMeaning(rs.getString("korean_meaning"));
        dto.setMeaningDescription(rs.getString("meaning_description"));

        dto.setExample1(rs.getString("example1"));
        dto.setExample2(rs.getString("example2"));
        dto.setExample3(rs.getString("example3"));

        dto.setJlptLevel(rs.getString("jlpt_level"));
        dto.setSector(rs.getInt("sector"));
        dto.setIndexNum(rs.getInt("index_num"));

        dto.setCreatedAt(rs.getTimestamp("created_at"));
        dto.setUpdatedAt(rs.getTimestamp("updated_at"));

        return dto;
    }
}