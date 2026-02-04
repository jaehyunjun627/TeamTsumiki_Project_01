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