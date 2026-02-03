// src/main/java/model/TestAnswerRecord.java
package model;

public class TestAnswerRecord {
    private KanjiDTO kanji;
    private String correctAnswer;
    private String userAnswer;
    private boolean isCorrect;
    
    public TestAnswerRecord(KanjiDTO kanji, String correctAnswer, String userAnswer, boolean isCorrect) {
        this.kanji = kanji;
        this.correctAnswer = correctAnswer;
        this.userAnswer = userAnswer;
        this.isCorrect = isCorrect;
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
}