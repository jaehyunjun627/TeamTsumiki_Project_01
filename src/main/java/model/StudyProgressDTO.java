// src/model/StudyProgressDTO.java
package model;

import java.util.HashSet;
import java.util.Set;

public class StudyProgressDTO {
    private String level;
    private Set<Integer> completedGroups;  // 완료한 그룹 번호들
    
    public StudyProgressDTO(String level) {
        this.level = level;
        this.completedGroups = new HashSet<>();
    }
    
    public String getLevel() {
        return level;
    }
    
    public Set<Integer> getCompletedGroups() {
        return completedGroups;
    }
    
    public void addCompletedGroup(int groupNumber) {
        completedGroups.add(groupNumber);
    }
    
    public boolean isGroupCompleted(int groupNumber) {
        return completedGroups.contains(groupNumber);
    }
    
    public void resetProgress() {
        completedGroups.clear();
    }
    
    public int getCompletedCount() {
        return completedGroups.size();
    }
}