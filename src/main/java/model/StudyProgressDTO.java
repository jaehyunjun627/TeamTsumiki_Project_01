// src/model/StudyProgressDTO.java
package model;

import java.util.HashSet;
import java.util.Set;

public class StudyProgressDTO {
    private String level; // "N5", "N4", "N3", "N2", "N1"
    private Set<Integer> completedGroups;      // 완료한 그룹 번호
    private Set<String> learnedKanji;          // 학습한 한자들
    
    public StudyProgressDTO(String level) {
        this.level = level;
        this.completedGroups = new HashSet<>();
        this.learnedKanji = new HashSet<>();
    }
    
    public String getLevel() {
        return level;
    }
    
    // 그룹 관련
    public Set<Integer> getCompletedGroups() {
        return completedGroups;
    }
    
    public void addCompletedGroup(int groupNumber) {
        completedGroups.add(groupNumber);
    }
    
    public boolean isGroupCompleted(int groupNumber) {
        return completedGroups.contains(groupNumber);
    }
    
    public int getCompletedCount() {
        return completedGroups.size();
    }
    
    // 한자 관련 (NEW!)
    public Set<String> getLearnedKanji() {
        return learnedKanji;
    }
    
    public void addLearnedKanji(String kanji) {
        learnedKanji.add(kanji);
    }
    
    public void addAllLearnedKanji(Set<String> kanjis) {
        learnedKanji.addAll(kanjis);
    }
    
    public boolean isKanjiLearned(String kanji) {
        return learnedKanji.contains(kanji);
    }
    
    public int getLearnedKanjiCount() {
        return learnedKanji.size();
    }
    
    // 초기화
    public void resetProgress() {
        completedGroups.clear();
        learnedKanji.clear();
    }
}