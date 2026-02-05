package util;

/**
 * KanjiCodeUtil.java - 한자 코드 생성 유틸리티
 *
 * 한자 인덱스 코드 생성 및 파싱
 * - code()         : "N5-01-01" 형식 코드 생성
 * - sectorKey()    : "N5-01" 형식 섹터 키 생성
 * - levelPrefix()  : "N5-" 형식 레벨 프리픽스
 * - sectorPrefix() : "N5-01-" 형식 섹터 프리픽스
 */
public class KanjiCodeUtil {

    // 코드 생성: "N5-01-01" 형식
    public static String code(String level, int sector, int index) {
        return level + "-" + two(sector) + "-" + two(index);
    }

    // 두 자리 숫자로 포맷팅 (01, 02, ... 10, 11)
    private static String two(int n) {
        if (n < 0)
            throw new IllegalArgumentException("n must be >= 0");
        return (n < 10) ? "0" + n : String.valueOf(n);
    }

    // 섹터 키: "N5-01" 형식
    public static String sectorKey(String level, int sector) {
        return level + "-" + two(sector);
    }

    // 레벨 프리픽스: "N5-" 형식
    public static String levelPrefix(String level) {
        return level + "-";
    }

    // 섹터 프리픽스: "N5-01-" 형식
    public static String sectorPrefix(String level, int sector) {
        return sectorKey(level, sector) + "-";
    }
}
