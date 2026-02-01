// src/model/KanjiDTO.java
package model;

public class KanjiDTO {
    private String kanji;
    private int level;
    private String korean;
    private String unyomi_1;
    private String unyomi_2;
    private String unyomi_3;
    private String unyomi_4;
    private String kunyomi_1;
    private String kunyomi_2;
    private String kunyomi_3;
    private String kunyomi_4;
    private String explantion;
    private String example_word;
    
    public KanjiDTO(String kanji, int level, String korean, String unyomi_1, String unyomi_2, String unyomi_3, String unyomi_4,
            String kunyomi_1, String kunyomi_2, String kunyomi_3, String kunyomi_4, String explantion,
            String example_word) {
        super();
        this.kanji = kanji;
        this.level = level;
        this.korean = korean;
        this.unyomi_1 = unyomi_1;
        this.unyomi_2 = unyomi_2;
        this.unyomi_3 = unyomi_3;
        this.unyomi_4 = unyomi_4;
        this.kunyomi_1 = kunyomi_1;
        this.kunyomi_2 = kunyomi_2;
        this.kunyomi_3 = kunyomi_3;
        this.kunyomi_4 = kunyomi_4;
        this.explantion = explantion;
        this.example_word = example_word;
    }
    
    // Getter 메서드들
    public String getKanji() { return kanji; }
    public int getLevel() { return level; }
    public String getKorean() { return korean; }  // getMeaning()에서 getKorean()으로 변경
    public String getUnyomi_1() { return unyomi_1; }
    public String getUnyomi_2() { return unyomi_2; }
    public String getUnyomi_3() { return unyomi_3; }
    public String getUnyomi_4() { return unyomi_4; }
    public String getKunyomi_1() { return kunyomi_1; }
    public String getKunyomi_2() { return kunyomi_2; }
    public String getKunyomi_3() { return kunyomi_3; }
    public String getKunyomi_4() { return kunyomi_4; }
    public String getExplantion() { return explantion; }
    public String getExample_word() { return example_word; }
    
    // 음독을 하나의 문자열로 합치기
    public String getOnyomiCombined() {
        StringBuilder sb = new StringBuilder();
        if (unyomi_1 != null && !unyomi_1.isEmpty()) sb.append(unyomi_1);
        if (unyomi_2 != null && !unyomi_2.isEmpty()) {
            if (sb.length() > 0) sb.append("、");
            sb.append(unyomi_2);
        }
        if (unyomi_3 != null && !unyomi_3.isEmpty()) {
            if (sb.length() > 0) sb.append("、");
            sb.append(unyomi_3);
        }
        if (unyomi_4 != null && !unyomi_4.isEmpty()) {
            if (sb.length() > 0) sb.append("、");
            sb.append(unyomi_4);
        }
        return sb.toString();
    }
    
    // 훈독을 하나의 문자열로 합치기
    public String getKunyomiCombined() {
        StringBuilder sb = new StringBuilder();
        if (kunyomi_1 != null && !kunyomi_1.isEmpty()) sb.append(kunyomi_1);
        if (kunyomi_2 != null && !kunyomi_2.isEmpty()) {
            if (sb.length() > 0) sb.append("、");
            sb.append(kunyomi_2);
        }
        if (kunyomi_3 != null && !kunyomi_3.isEmpty()) {
            if (sb.length() > 0) sb.append("、");
            sb.append(kunyomi_3);
        }
        if (kunyomi_4 != null && !kunyomi_4.isEmpty()) {
            if (sb.length() > 0) sb.append("、");
            sb.append(kunyomi_4);
        }
        return sb.toString();
    }
}