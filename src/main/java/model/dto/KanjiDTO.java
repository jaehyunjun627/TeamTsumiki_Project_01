package model.dto;

import java.sql.Timestamp;

public class KanjiDTO {
    
    // PK
    private int kanjiID;
    
    // 한자 기본 정보
    private String kanjiindex;  // 고유 인덱스 (예: "N5-01-001")
    private String kanji;       // 한자 1글자
    
    // 음독
    private String onyomi1;
    private String onyomi2;
    private String onyomi3;
    
    // 훈독
    private String kunyomi1;
    private String kunyomi2;
    private String kunyomi3;
    
    // 의미
    private String koreanMeaning;        // 한국어 뜻
    private String meaningDescription;   // 상세 설명
    
    // 예시 단어
    private String example1;
    private String example2;
    private String example3;
    
    // 분류
    private String jlptLevel;   // N5, N4, N3, N2, N1
    private int sector;         // 섹터 번호
    private int indexNum;       // 인덱스 번호
    
    // 시간 정보 (선택)
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // 호환성 필드 (KanjiRepository_KANJIDAO용)
    private String kanjiCode;
    private String onyomi4;  // 4번째 음독
    private String kunyomi4; // 4번째 훈독


    // ========== 생성자 ==========

    public KanjiDTO() {}
    
    // 필수 필드만 (DB INSERT용)
    public KanjiDTO(String kanjiindex, String kanji, 
                    String koreanMeaning, String jlptLevel, 
                    int sector, int indexNum) {
        this.kanjiindex = kanjiindex;
        this.kanji = kanji;
        this.koreanMeaning = koreanMeaning;
        this.jlptLevel = jlptLevel;
        this.sector = sector;
        this.indexNum = indexNum;
    }
    
    // 전체 필드 (DB SELECT용)
    public KanjiDTO(int kanjiID, String kanjiindex, String kanji,
                    String onyomi1, String onyomi2, String onyomi3,
                    String kunyomi1, String kunyomi2, String kunyomi3,
                    String koreanMeaning, String meaningDescription,
                    String example1, String example2, String example3,
                    String jlptLevel, int sector, int indexNum) {
        this.kanjiID = kanjiID;
        this.kanjiindex = kanjiindex;
        this.kanji = kanji;
        this.onyomi1 = onyomi1;
        this.onyomi2 = onyomi2;
        this.onyomi3 = onyomi3;
        this.kunyomi1 = kunyomi1;
        this.kunyomi2 = kunyomi2;
        this.kunyomi3 = kunyomi3;
        this.koreanMeaning = koreanMeaning;
        this.meaningDescription = meaningDescription;
        this.example1 = example1;
        this.example2 = example2;
        this.example3 = example3;
        this.jlptLevel = jlptLevel;
        this.sector = sector;
        this.indexNum = indexNum;
    }

    // KanjiRepository_KANJIDAO용 생성자 (호환성)
    public KanjiDTO(String kanji, String onyomi1, String onyomi2, String onyomi3, String onyomi4,
                    String kunyomi1, String kunyomi2, String kunyomi3, String kunyomi4,
                    String koreanMeaning, String meaningDescription,
                    String example1, String example2, String example3) {
        this.kanji = kanji;
        this.onyomi1 = onyomi1;
        this.onyomi2 = onyomi2;
        this.onyomi3 = onyomi3;
        this.onyomi4 = onyomi4;
        this.kunyomi1 = kunyomi1;
        this.kunyomi2 = kunyomi2;
        this.kunyomi3 = kunyomi3;
        this.kunyomi4 = kunyomi4;
        this.koreanMeaning = koreanMeaning;
        this.meaningDescription = meaningDescription;
        this.example1 = example1;
        this.example2 = example2;
        this.example3 = example3;
    }
    
    
    // ========== Getter/Setter ==========
    
    public int getKanjiID() { return kanjiID; }
    public void setKanjiID(int kanjiID) { this.kanjiID = kanjiID; }
    
    public String getKanjiindex() { return kanjiindex; }
    public void setKanjiindex(String kanjiindex) { this.kanjiindex = kanjiindex; }
    
    public String getKanji() { return kanji; }
    public void setKanji(String kanji) { this.kanji = kanji; }
    
    public String getOnyomi1() { return onyomi1; }
    public void setOnyomi1(String onyomi1) { this.onyomi1 = onyomi1; }
    
    public String getOnyomi2() { return onyomi2; }
    public void setOnyomi2(String onyomi2) { this.onyomi2 = onyomi2; }
    
    public String getOnyomi3() { return onyomi3; }
    public void setOnyomi3(String onyomi3) { this.onyomi3 = onyomi3; }
    
    public String getKunyomi1() { return kunyomi1; }
    public void setKunyomi1(String kunyomi1) { this.kunyomi1 = kunyomi1; }
    
    public String getKunyomi2() { return kunyomi2; }
    public void setKunyomi2(String kunyomi2) { this.kunyomi2 = kunyomi2; }
    
    public String getKunyomi3() { return kunyomi3; }
    public void setKunyomi3(String kunyomi3) { this.kunyomi3 = kunyomi3; }
    
    public String getKoreanMeaning() { return koreanMeaning; }
    public void setKoreanMeaning(String koreanMeaning) { this.koreanMeaning = koreanMeaning; }
    
    public String getMeaningDescription() { return meaningDescription; }
    public void setMeaningDescription(String meaningDescription) { 
        this.meaningDescription = meaningDescription; 
    }
    
    public String getExample1() { return example1; }
    public void setExample1(String example1) { this.example1 = example1; }
    
    public String getExample2() { return example2; }
    public void setExample2(String example2) { this.example2 = example2; }
    
    public String getExample3() { return example3; }
    public void setExample3(String example3) { this.example3 = example3; }
    
    public String getJlptLevel() { return jlptLevel; }
    public void setJlptLevel(String jlptLevel) { this.jlptLevel = jlptLevel; }
    
    public int getSector() { return sector; }
    public void setSector(int sector) { this.sector = sector; }
    
    public int getIndexNum() { return indexNum; }
    public void setIndexNum(int indexNum) { this.indexNum = indexNum; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    // ========== 호환성 Getter/Setter (기존 코드 호환용) ==========

    // kanjiCode (KanjiRepository_KANJIDAO용)
    public String getKanjiCode() { return kanjiCode; }
    public void setKanjiCode(String kanjiCode) { this.kanjiCode = kanjiCode; }

    // 4번째 음독/훈독
    public String getOnyomi4() { return onyomi4; }
    public void setOnyomi4(String onyomi4) { this.onyomi4 = onyomi4; }
    public String getKunyomi4() { return kunyomi4; }
    public void setKunyomi4(String kunyomi4) { this.kunyomi4 = kunyomi4; }

    // StudyCon 호환용 별칭 메서드 (unyomi_1 형식)
    public String getUnyomi_1() { return onyomi1; }
    public String getUnyomi_2() { return onyomi2; }
    public String getUnyomi_3() { return onyomi3; }
    public String getUnyomi_4() { return onyomi4; }

    public String getKunyomi_1() { return kunyomi1; }
    public String getKunyomi_2() { return kunyomi2; }
    public String getKunyomi_3() { return kunyomi3; }
    public String getKunyomi_4() { return kunyomi4; }

    public String getExample_word_1() { return example1; }
    public String getExample_word_2() { return example2; }
    public String getExample_word_3() { return example3; }

    // JSP 호환용 (getKorean -> getKoreanMeaning)
    public String getKorean() { return koreanMeaning; }
}




