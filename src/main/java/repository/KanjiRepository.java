// src/repository/KanjiRepository.java
package repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.KanjiDTO;

public class KanjiRepository {

    private static final Map<String, List<KanjiDTO>> DATA;

    static {
        Map<String, List<KanjiDTO>> map = new HashMap<>();

        map.put("N5", List.of(
            new KanjiDTO("場", 5, "ジョウ", null, null, null, "ば", null, null, null,
                    "마당 장", "장소, 상황", "場所、工場、会場"),

            new KanjiDTO("会", 5, "カイ", "エ", null, null, "あ（う）", null, null, null,
                    "모일 회", "만나다, 모임", "会社、会議"),

            new KanjiDTO("事", 5, "ジ", null, null, null, "こと", null, null, null,
                    "일 사", "일, 사건, 사실", "仕事、事件、大事"),

            new KanjiDTO("思", 5, "シ", null, null, null, "おも（う）", null, null, null,
                    "생각 사", "생각하다, 마음에 두다", "思う、思想、不思議"),

            new KanjiDTO("者", 5, "シャ", null, null, null, "もの", null, null, null,
                    "놈 자", "사람, ~하는 자", "医者、若者、記者"),

            new KanjiDTO("地", 5, "チ", "ジ", null, null, null, null, null, null,
                    "땅 지", "땅, 지역, 위치", "地図、地下、地域"),

            new KanjiDTO("定", 5, "テイ", "ジョウ", null, null, "さだ（める）", null, null, null,
                    "정할 정", "정하다, 결정하다", "予定、決定、定員"),

            new KanjiDTO("動", 5, "ドウ", null, null, null, "うご（く）", null, null, null,
                    "움직일 동", "움직이다, 활동", "動く、運動、活動"),

            new KanjiDTO("使", 5, "シ", null, null, null, "つか（う）", null, null, null,
                    "하여금 사", "사용하다, 부리다", "使う、使用、大使"),

            new KanjiDTO("発", 5, "ハツ", "ホツ", null, null, null, null, null, null,
                    "필 발", "나가다, 발생하다, 출발", "発見、出発、発表")
        ));

        map.put("N4", List.of(
            new KanjiDTO("場", 5, "ジョウ", null, null, null, "ば", null, null, null,
                    "마당 장", "장소, 상황", "場所、工場、会場"),

            new KanjiDTO("新", 4, "シン", null, null, null, "あたら（しい）", null, null, null,
                    "새로울 신", "새롭다", "新聞、新しい"),

            new KanjiDTO("多", 4, "タ", null, null, null, "おお（い）", null, null, null,
                    "많을 다", "많다", "多分、多い"),

            new KanjiDTO("者", 4, "シャ", null, null, null, "もの", null, null, null,
                    "놈 자", "사람, 자", "医者、学者"),

            new KanjiDTO("古", 4, "コ", null, null, null, "ふる（い）", null, null, null,
                    "옛 고", "오래되다", "古本、古い"),

            new KanjiDTO("少", 4, "ショウ", null, null, null, "すこ（し）", null, null, null,
                    "적을 소", "적다, 조금", "少し、少年"),

            new KanjiDTO("事", 4, "ジ", null, null, null, "こと", null, null, null,
                    "일 사", "일, 사건, 사항", "仕事、大事"),

            new KanjiDTO("安", 4, "アン", null, null, null, "やす（い）", null, null, null,
                    "편안할 안", "싸다, 편안하다", "安心、安い"),

            new KanjiDTO("社", 4, "シャ", null, null, null, "やしろ", null, null, null,
                    "모실 사", "회사, 사원, 사회", "社会、会社"),

            new KanjiDTO("電", 4, "デン", null, null, null, null, null, null, null,
                    "번개 전", "전기", "電車、電話"),

            new KanjiDTO("校", 4, "コウ", null, null, null, null, null, null, null,
                    "학교 교", "학교", "学校、高校")
        ));

        map.put("N3", List.of(
            new KanjiDTO("情", 2, "ジョウ", "セイ", null, null, "なさ（け）", null, null, null,
                    "뜻 정", "정, 정서, 마음, 인정", "情報"),

            new KanjiDTO("報", 2, "ホウ", null, null, null, "むく（いる）", null, null, null,
                    "갚을/알릴 보", "갚다, 알리다", "情報"),

            new KanjiDTO("信", 3, "シン", null, null, null, "しんじ（る）", null, null, null,
                    "믿을 신", "믿음, 신뢰, 소식", "信用"),

            new KanjiDTO("意", 2, "イ", null, null, null, null, null, null, null,
                    "뜻 의", "생각, 의지, 의미", "意味"),

            new KanjiDTO("感", 2, "カン", null, null, null, null, null, null, null,
                    "느낄 감", "느낌, 감정, 감동", "感情"),

            new KanjiDTO("心", 2, "シン", null, null, null, "こころ", null, null, null,
                    "마음 심", "마음, 정신, 중심", "安心"),

            new KanjiDTO("考", 2, "コウ", null, null, null, "かんが（える）", null, null, null,
                    "생각할 고", "생각하다, 판단하다", "考え"),

            new KanjiDTO("想", 2, "ソウ", null, null, null, null, null, null, null,
                    "생각 상", "상상, 생각, 마음속 이미지", "想像"),

            new KanjiDTO("念", 2, "ネン", null, null, null, null, null, null, null,
                    "생각 념", "마음에 두다, 기억, 주의", "記念")
        ));

        DATA = Collections.unmodifiableMap(map);
    }

    public static List<KanjiDTO> findByLevel(String level) {
        return DATA.getOrDefault(level, List.of());
    }
}