package data;

import model.KanjiDTO;

import java.util.*;
import java.util.stream.Collectors;

public class KanjiRepository_KANJIDAO {

    private static final Map<String, KanjiDTO> KANJI_MAP = new HashMap<>();

    static {
        loadN5();
        loadN4();
        loadN3();
    }

    private static void loadN5() {
        int sector = 1;

        put("N5", sector, 1, new KanjiDTO("場",  "ジョウ", null, null, null, "ば", null, null, null,
                "마당 장", "장소, 상황", "場所", "工場", "会場"));

        put("N5", sector, 2, new KanjiDTO("会", "カイ", "エ", null, null, "あ（う）", null, null, null,
                "모일 회", "만나다, 모임", "会社", "会議", null));

        put("N5", sector, 3, new KanjiDTO("事", "ジ", null, null, null, "こと", null, null, null,
                "일 사", "일, 사건, 사실", "仕事", "事件", "大事"));

        put("N5", sector, 4, new KanjiDTO("思", "シ", null, null, null, "おも（う）", null, null, null,
                "생각 사", "생각하다, 마음에 두다", "思う", "思想", "不思議"));

        put("N5", sector, 5, new KanjiDTO("者", "シャ", null, null, null, "もの", null, null, null,
                "놈 자", "사람, ~하는 자", "医者", "若者", "記者"));

        put("N5", sector, 6, new KanjiDTO("地", "チ", "ジ", null, null, null, null, null, null,
                "땅 지", "땅, 지역, 위치", "地図", "地下", "地域"));

        put("N5", sector, 7, new KanjiDTO("定", "テイ", "ジョウ", null, null, "さだ（める）", null, null, null,
                "정할 정", "정하다, 결정하다", "予定", "決定", "定員"));

        put("N5", sector, 8, new KanjiDTO("動", "ドウ", null, null, null, "うご（く）", null, null, null,
                "움직일 동", "움직이다, 활동", "動く", "運動", "活動"));

        put("N5", sector, 9, new KanjiDTO("使", "シ", null, null, null, "つか（う）", null, null, null,
                "하여금 사", "사용하다, 부리다", "使う", "使用", "大使"));

        put("N5", sector, 10, new KanjiDTO("発", "ハツ", "ホツ", null, null, null, null, null, null,
                "필 발", "나가다, 발생하다, 출발", "発見", "出発", "発表"));
    }

    private static void loadN4() {
        int sector = 1;

        put("N4", sector, 1, new KanjiDTO("場", "ジョウ", null, null, null, "ば", null, null, null,
                "마당 장", "장소, 상황", "場所", "工場", "会場"));

        put("N4", sector, 2, new KanjiDTO("新", "シン", null, null, null, "あたら（しい）", null, null, null,
                "새로울 신", "새롭다", "新聞", "新しい", null));

        put("N4", sector, 3, new KanjiDTO("多", "タ", null, null, null, "おお（い）", null, null, null,
                "많을 다", "많다", "多分", "多い", null));

        put("N4", sector, 4, new KanjiDTO("者", "シャ", null, null, null, "もの", null, null, null,
                "놈 자", "사람, 자", "医者", "学者", null));

        put("N4", sector, 5, new KanjiDTO("古", "コ", null, null, null, "ふる（い）", null, null, null,
                "옛 고", "오래되다", "古本", "古い", null));

        put("N4", sector, 6, new KanjiDTO("少", "ショウ", null, null, null, "すこ（し）", null, null, null,
                "적을 소", "적다, 조금", "少し", "少年", null));

        put("N4", sector, 7, new KanjiDTO("事", "ジ", null, null, null, "こと", null, null, null,
                "일 사", "일, 사건, 사항", "仕事", "大事", null));

        put("N4", sector, 8, new KanjiDTO("安", "アン", null, null, null, "やす（い）", null, null, null,
                "편안할 안", "싸다, 편안하다", "安心", "安い", null));

        put("N4", sector, 9, new KanjiDTO("社", "シャ", null, null, null, "やしろ", null, null, null,
                "모실 사", "회사, 사원, 사회", "社会", "会社", null));

        put("N4", sector, 10, new KanjiDTO("電", "デン", null, null, null, null, null, null, null,
                "번개 전", "전기", "電車", "電話", null));

        put("N4", sector, 11, new KanjiDTO("校", "コウ", null, null, null, null, null, null, null,
                "학교 교", "학교", "学校", "高校", null));
    }

    private static void loadN3() {
        int sector = 1;

        put("N3", sector, 1, new KanjiDTO("情", "ジョウ", "セイ", null, null, "なさ（け）", null, null, null,
                "뜻 정", "정, 정서, 마음, 인정", "情報", null, null));

        put("N3", sector, 2, new KanjiDTO("報", "ホウ", null, null, null, "むく（いる）", null, null, null,
                "갚을/알릴 보", "갚다, 알리다", "情報", null, null));

        put("N3", sector, 3, new KanjiDTO("信", "シン", null, null, null, "しんじ（る）", null, null, null,
                "믿을 신", "믿음, 신뢰, 소식", "信用", null, null));

        put("N3", sector, 4, new KanjiDTO("意", "イ", null, null, null, null, null, null, null,
                "뜻 의", "생각, 의지, 의미", "意味", null, null));

        put("N3", sector, 5, new KanjiDTO("感", "カン", null, null, null, null, null, null, null,
                "느낄 감", "느낌, 감정, 감동", "感情", null, null));

        put("N3", sector, 6, new KanjiDTO("心", "シン", null, null, null, "こころ", null, null, null,
                "마음 심", "마음, 정신, 중심", "安心", null, null));

        put("N3", sector, 7, new KanjiDTO("考", "コウ", null, null, null, "かんが（える）", null, null, null,
                "생각할 고", "생각하다, 판단하다", "考え", null, null));

        put("N3", sector, 8, new KanjiDTO("想", "ソウ", null, null, null, null, null, null, null,
                "생각 상", "상상, 생각, 마음속 이미지", "想像", null, null));

        put("N3", sector, 9, new KanjiDTO("念", "ネン", null, null, null, null, null, null, null,
                "생각 념", "마음에 두다, 기억, 주의", "記念", null, null));
    }

    private static void put(String level, int sector, int index, KanjiDTO dto) {
        String code = KanjiCodeUtil_KANJIDAO.code(level, sector, index);
        dto.setKanjiCode(code); // DTO에 kanjiCode 필드+setter가 있어야 함
        KANJI_MAP.put(code, dto);
    }

    public static KanjiDTO findByCode(String kanjiCode) {
        return KANJI_MAP.get(kanjiCode);
    }

    public static List<KanjiDTO> findByLevel(String level) {
        String prefix = KanjiCodeUtil_KANJIDAO.levelPrefix(level);
        return KANJI_MAP.entrySet().stream()
                .filter(e -> e.getKey().startsWith(prefix))
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public static List<KanjiDTO> findBySector(String level, int sector) {
        String prefix = KanjiCodeUtil_KANJIDAO.sectorPrefix(level, sector);
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
