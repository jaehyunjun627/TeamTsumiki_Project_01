package data;

import model.KanjiDTO;
import java.util.*;
import java.util.stream.Collectors;

public class KanjiRepository {

    // 한자 데이터를 저장하는 메모리 맵
    private static final Map<String, KanjiDTO> KANJI_MAP = new HashMap<>();

    static {
        // 프로그램 시작 시 웹에서 데이터를 자동으로 긁어와 로드합니다.
        loadN5();
        loadN4();
        loadN3();
    }

    private static void loadN5() {
        int sector = 1;
        
        // [자동화] nihongokanji.com의 한자 고유 번호 리스트
        // 예: 111(耳), 112(花)... 등 사이트 번호를 추가하면 자동으로 저장됩니다.
        int[] n5WebNumbers = {111, 112, 113, 114, 115, 116, 117, 118, 119, 120};
        
        for (int i = 0; i < n5WebNumbers.length; i++) {
            // KanjiCrawler 클래스를 호출하여 웹 데이터를 DTO로 변환
            KanjiDTO dto = KanjiCrawler.crawl(n5WebNumbers[i]);
            
            if (dto != null) {
                // 기존 put 메서드를 사용하여 "N5-1-1" 형태의 코드로 저장
                put("N5", sector, (i + 1), dto);
            }
            
            // 사이트 차단 방지를 위한 미세 대기 (0.5초)
            try { Thread.sleep(500); } catch (Exception e) {}
        }
        
        // 기존처럼 수동으로 넣고 싶은 데이터가 있다면 아래에 유지해도 됩니다.
        /*
        put("N5", sector, 11, new KanjiDTO("場", "ジョ우", ...));
        */
    }

    private static void loadN4() {
        int sector = 1;
        // N4 레벨에 해당하는 웹사이트 번호들
        int[] n4WebNumbers = {201, 202, 203, 204, 205}; 
        
        for (int i = 0; i < n4WebNumbers.length; i++) {
            KanjiDTO dto = KanjiCrawler.crawl(n4WebNumbers[i]);
            if (dto != null) {
                put("N4", sector, (i + 1), dto);
            }
            try { Thread.sleep(500); } catch (Exception e) {}
        }
    }

    private static void loadN3() {
        int sector = 1;
        // N3 레벨에 해당하는 웹사이트 번호들
        int[] n3WebNumbers = {301, 302, 303};
        
        for (int i = 0; i < n3WebNumbers.length; i++) {
            KanjiDTO dto = KanjiCrawler.crawl(n3WebNumbers[i]);
            if (dto != null) {
                put("N3", sector, (i + 1), dto);
            }
            try { Thread.sleep(500); } catch (Exception e) {}
        }
    }

    /**
     * 데이터를 맵에 넣으면서 고유 코드(N5-1-1 등)를 생성하고 DTO에 설정합니다.
     */
    private static void put(String level, int sector, int index, KanjiDTO dto) {
        String code = KanjiCodeUtil.code(level, sector, index);
        dto.setKanjiCode(code); 
        KANJI_MAP.put(code, dto);
    }

    // ========== 기존 조회 메서드 (유지) ==========

    public static KanjiDTO findByCode(String kanjiCode) {
        return KANJI_MAP.get(kanjiCode);
    }

    public static List<KanjiDTO> findByLevel(String level) {
        String prefix = KanjiCodeUtil.levelPrefix(level);
        return KANJI_MAP.entrySet().stream()
                .filter(e -> e.getKey().startsWith(prefix))
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public static List<KanjiDTO> findBySector(String level, int sector) {
        String prefix = KanjiCodeUtil.sectorPrefix(level, sector);
        return KANJI_MAP.entrySet().stream()
                .filter(e -> e.getKey().startsWith(prefix))
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public static boolean exists(String kanjiCode) {
        return KANJI_MAP.containsKey(kanjiCode);
    }

    public static int size() {
        return KANJI_MAP.size();
    }
}