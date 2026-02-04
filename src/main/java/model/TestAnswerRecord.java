// src/main/java/model/TestAnswerRecord.java
package model;

public class TestAnswerRecord {
    private KanjiDTO kanji;
    private String correctAnswer;
    private String userAnswer;
    private boolean isCorrect;
    private boolean isSkipped;  // 모르겠어요 클릭 여부
    
    public TestAnswerRecord(KanjiDTO kanji, String correctAnswer, String userAnswer, boolean isCorrect, boolean isSkipped) {
        this.kanji = kanji;
        this.correctAnswer = correctAnswer;
        this.userAnswer = userAnswer;
        this.isCorrect = isCorrect;
        this.isSkipped = isSkipped;
    }
    
    public KanjiDTO getKanji() {
        return kanji;
    }
    
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    
    public String getUserAnswer() {
        return userAnswer;
    }
    
    public boolean isCorrect() {
        return isCorrect;
    }
    
    public boolean isSkipped() {
        return isSkipped;
    }
}