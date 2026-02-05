package model.dao;

import model.dto.KanjiDTO;
import util.KanjiCodeUtil;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * KanjiDAO.java - 한자 DB 접근 클래스
 *
 * [DB 기반 메서드]
 * - findById()         : ID로 한자 조회
 * - findByIndex()      : 인덱스로 조회 (예: "N5-01-01")
 * - findByJlptLevel()  : JLPT 레벨별 조회 (예: "N5")
 * - findBySector()     : 레벨+섹터별 조회
 * - findAll()          : 전체 조회
 *
 * [인메모리 캐시 메서드] (기존 KanjiRepository에서 통합)
 * - findByCode()       : 코드로 한자 조회 (인메모리)
 * - findByLevel()      : 레벨별 조회 (인메모리)
 * - findBySectorCache(): 섹터별 조회 (인메모리)
 */
public class KanjiDAO {

    // ========== Oracle 접속 정보 ==========
    String url = "jdbc:oracle:thin:@localhost:1521:xe";
    String user = "system";
    String pass = "12345";

    Connection con;          // DB 연결 객체
    PreparedStatement pstmt; // SQL 실행 객체
    ResultSet rs;            // 결과 반환 객체

    // ========== 인메모리 캐시 (기존 KanjiRepository 통합) ==========
    private static final Map<String, KanjiDTO> KANJI_MAP = new HashMap<>();

    static {
        loadN5();
        loadN4();
        loadN3();
    }

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

    // ==========================================================
    // 인메모리 캐시 메서드 (기존 KanjiRepository에서 통합)
    // ==========================================================

    private static void put(String level, int sector, int index, KanjiDTO dto) {
        String code = KanjiCodeUtil.code(level, sector, index);
        dto.setKanjiCode(code);
        KANJI_MAP.put(code, dto);
    }

    public static KanjiDTO findByCode(String kanjiCode) {
        return KANJI_MAP.get(kanjiCode);
    }

    public List<KanjiDTO> findByLevel(String level) {
        String prefix = KanjiCodeUtil.levelPrefix(level);
        return KANJI_MAP.entrySet().stream()
                .filter(e -> e.getKey().startsWith(prefix))
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public static List<KanjiDTO> findBySectorCache(String level, int sector) {
        String prefix = KanjiCodeUtil.sectorPrefix(level, sector);
        return KANJI_MAP.entrySet().stream()
                .filter(e -> e.getKey().startsWith(prefix))
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public static boolean existsInCache(String kanjiCode) {
        return KANJI_MAP.containsKey(kanjiCode);
    }

    public static int cacheSize() {
        return KANJI_MAP.size();
    }

    // ========== 인메모리 데이터 로드 ==========

    private static void loadN5() {
        int sector = 1;

        put("N5", sector, 1, new KanjiDTO("場", "ジョウ", null, null, null,
                "ば", null, null, null, "마당 장", "장소, 상황", "場所", "工場", "会場"));

        put("N5", sector, 2, new KanjiDTO("会", "カイ", "エ", null, null,
                "あ（う）", null, null, null, "모일 회", "만나다, 모임", "会社", "会議", null));

        put("N5", sector, 3, new KanjiDTO("事", "ジ", null, null, null,
                "こと", null, null, null, "일 사", "일, 사건, 사실", "仕事", "事件", "大事"));

        put("N5", sector, 4, new KanjiDTO("思", "シ", null, null, null,
                "おも（う）", null, null, null, "생각 사", "생각하다, 마음에 두다", "思う", "思想", "不思議"));

        put("N5", sector, 5, new KanjiDTO("者", "シャ", null, null, null,
                "もの", null, null, null, "놈 자", "사람, ~하는 자", "医者", "若者", "記者"));

        put("N5", sector, 6, new KanjiDTO("地", "チ", "ジ", null, null,
                null, null, null, null, "땅 지", "땅, 지역, 위치", "地図", "地下", "地域"));

        put("N5", sector, 7, new KanjiDTO("定", "テイ", "ジョウ", null, null,
                "さだ（める）", null, null, null, "정할 정", "정하다, 결정하다", "予定", "決定", "定員"));

        put("N5", sector, 8, new KanjiDTO("動", "ドウ", null, null, null,
                "うご（く）", null, null, null, "움직일 동", "움직이다, 활동", "動く", "運動", "活動"));

        put("N5", sector, 9, new KanjiDTO("使", "シ", null, null, null,
                "つか（う）", null, null, null, "하여금 사", "사용하다, 부리다", "使う", "使用", "大使"));

        put("N5", sector, 10, new KanjiDTO("発", "ハツ", "ホツ", null, null,
                null, null, null, null, "필 발", "나가다, 발생하다, 출발", "発見", "出発", "発表"));
    }

    private static void loadN4() {
        int sector = 1;

        put("N4", sector, 1, new KanjiDTO("場", "ジョウ", null, null, null,
                "ば", null, null, null, "마당 장", "장소, 상황", "場所", "工場", "会場"));

        put("N4", sector, 2, new KanjiDTO("新", "シン", null, null, null,
                "あたら（しい）", null, null, null, "새로울 신", "새롭다", "新聞", "新しい", null));

        put("N4", sector, 3, new KanjiDTO("多", "タ", null, null, null,
                "おお（い）", null, null, null, "많을 다", "많다", "多分", "多い", null));

        put("N4", sector, 4, new KanjiDTO("者", "シャ", null, null, null,
                "もの", null, null, null, "놈 자", "사람, 자", "医者", "学者", null));

        put("N4", sector, 5, new KanjiDTO("古", "コ", null, null, null,
                "ふる（い）", null, null, null, "옛 고", "오래되다", "古本", "古い", null));

        put("N4", sector, 6, new KanjiDTO("少", "ショウ", null, null, null,
                "すこ（し）", null, null, null, "적을 소", "적다, 조금", "少し", "少年", null));

        put("N4", sector, 7, new KanjiDTO("事", "ジ", null, null, null,
                "こと", null, null, null, "일 사", "일, 사건, 사항", "仕事", "大事", null));

        put("N4", sector, 8, new KanjiDTO("安", "アン", null, null, null,
                "やす（い）", null, null, null, "편안할 안", "싸다, 편안하다", "安心", "安い", null));

        put("N4", sector, 9, new KanjiDTO("社", "シャ", null, null, null,
                "やしろ", null, null, null, "모실 사", "회사, 사원, 사회", "社会", "会社", null));

        put("N4", sector, 10, new KanjiDTO("電", "デン", null, null, null,
                null, null, null, null, "번개 전", "전기", "電車", "電話", null));

        put("N4", sector, 11, new KanjiDTO("校", "コウ", null, null, null,
                null, null, null, null, "학교 교", "학교", "学校", "高校", null));
    }

    private static void loadN3() {
        int sector = 1;

        put("N3", sector, 1, new KanjiDTO("情", "ジョウ", "セイ", null, null,
                "なさ（け）", null, null, null, "뜻 정", "정, 정서, 마음, 인정", "情報", null, null));

        put("N3", sector, 2, new KanjiDTO("報", "ホウ", null, null, null,
                "むく（いる）", null, null, null, "갚을/알릴 보", "갚다, 알리다", "情報", null, null));

        put("N3", sector, 3, new KanjiDTO("信", "シン", null, null, null,
                "しんじ（る）", null, null, null, "믿을 신", "믿음, 신뢰, 소식", "信用", null, null));

        put("N3", sector, 4, new KanjiDTO("意", "イ", null, null, null,
                null, null, null, null, "뜻 의", "생각, 의지, 의미", "意味", null, null));

        put("N3", sector, 5, new KanjiDTO("感", "カン", null, null, null,
                null, null, null, null, "느낄 감", "느낌, 감정, 감동", "感情", null, null));

        put("N3", sector, 6, new KanjiDTO("心", "シン", null, null, null,
                "こころ", null, null, null, "마음 심", "마음, 정신, 중심", "安心", null, null));

        put("N3", sector, 7, new KanjiDTO("考", "コウ", null, null, null,
                "かんが（える）", null, null, null, "생각할 고", "생각하다, 판단하다", "考え", null, null));

        put("N3", sector, 8, new KanjiDTO("想", "ソウ", null, null, null,
                null, null, null, null, "생각 상", "상상, 생각, 마음속 이미지", "想像", null, null));

        put("N3", sector, 9, new KanjiDTO("念", "ネン", null, null, null,
                null, null, null, null, "생각 념", "마음에 두다, 기억, 주의", "記念", null, null));
    }
}
