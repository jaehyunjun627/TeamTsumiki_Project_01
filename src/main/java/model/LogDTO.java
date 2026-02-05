package model;

import java.sql.Timestamp;

public class LogDTO {

    private int logID;          // 로그 ID (PK)
    private int accID;          // 회원 ID
    private int kanjiID;        // 한자 ID
    private boolean isCorrect;  // 정답 여부
    private Timestamp studiedAt; // 학습 시간

    // 기본 생성자 (필수)
    public LogDTO() {
    }

    // 로그 조회용 생성자
    public LogDTO(int logID, int accID, int kanjiID, boolean isCorrect, Timestamp studiedAt) {
        this.logID = logID;
        this.accID = accID;
        this.kanjiID = kanjiID;
        this.isCorrect = isCorrect;
        this.studiedAt = studiedAt;
    }

    // 로그 저장용 생성자 (INSERT)
    public LogDTO(int accID, int kanjiID, boolean isCorrect) {
        this.accID = accID;
        this.kanjiID = kanjiID;
        this.isCorrect = isCorrect;
    }

    // --------------------
    // Getter / Setter
    // --------------------
    public int getLogID() {
        return logID;
    }

    public void setLogID(int logID) {
        this.logID = logID;
    }

    public int getAccID() {
        return accID;
    }

    public void setAccID(int accID) {
        this.accID = accID;
    }

    public int getKanjiID() {
        return kanjiID;
    }

    public void setKanjiID(int kanjiID) {
        this.kanjiID = kanjiID;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Timestamp getStudiedAt() {
        return studiedAt;
    }

    public void setStudiedAt(Timestamp studiedAt) {
        this.studiedAt = studiedAt;
    }
}
