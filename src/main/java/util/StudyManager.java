// src/util/StudyManager.java
package util;

import java.util.ArrayList;
import java.util.List;
import model.KanjiDTO;

public class StudyManager {
    
    private static final int KANJI_PER_GROUP = 2;  // 그룹당 한자 개수
    
    // 전체 그룹 수 계산
    public static int getTotalGroups(List<KanjiDTO> allKanji) {
        return (int) Math.ceil((double) allKanji.size() / KANJI_PER_GROUP);
    }
    
    // 특정 그룹의 한자 가져오기 (그룹 번호는 1부터 시작)
    public static List<KanjiDTO> getKanjiByGroup(List<KanjiDTO> allKanji, int groupNumber) {
        int startIndex = (groupNumber - 1) * KANJI_PER_GROUP;
        int endIndex = Math.min(startIndex + KANJI_PER_GROUP, allKanji.size());
        
        if (startIndex >= allKanji.size()) {
            return new ArrayList<>();
        }
        
        return allKanji.subList(startIndex, endIndex);
    }
}

//N5 전체 한자 (10개)
//List<KanjiDTO> allKanji = KanjiRepository.findByLevel("N5");

//그룹 1번만 가져오기
//List<KanjiDTO> group1 = StudyManager.getKanjiByGroup(allKanji, 1);
//→ 결과: [場, 会] (2개)

//그룹 2번만 가져오기
//List<KanjiDTO> group2 = StudyManager.getKanjiByGroup(allKanji, 2);
//→ 결과: [事, 思] (2개)