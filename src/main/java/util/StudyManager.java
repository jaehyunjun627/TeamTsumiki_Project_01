// src/util/StudyManager.java
package util;

import data.KanjiRepository;
import java.util.List;
import model.KanjiDTO;

public class StudyManager {
    
	
	public static int getTotalGroup(String level) {
		return KanjiRepository.getMaxGroup(level);
		
	}
    // 특정 그룹의 한자 가져오기 (그룹 번호는 1부터 시작)

	public static List<KanjiDTO> getKanjiByGroup(String level, int group) {
        return KanjiRepository.findByGroup(level, group);
    }
	
   
}