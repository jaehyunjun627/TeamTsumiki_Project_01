package model;

public class KanjiDTO {


    private String kanjiCode;
	private String kanji;
	private String unyomi_1;
	private String unyomi_2;
	private String unyomi_3;
	private String unyomi_4;
	private String kunyomi_1;
	private String kunyomi_2;
	private String kunyomi_3;
	private String kunyomi_4;
	private String explanation;
	private String korean;
	private String example_word_1;
	private String example_word_2;
	private String example_word_3;

	public KanjiDTO() {}
	
	public KanjiDTO(String kanji,String unyomi_1, String unyomi_2, String unyomi_3, String unyomi_4,
			String kunyomi_1, String kunyomi_2, String kunyomi_3, String kunyomi_4, String explanation, String korean,
			String example_word_1, String example_word_2, String example_word_3) {
		super();
		this.kanji = kanji;
		this.unyomi_1 = unyomi_1;
		this.unyomi_2 = unyomi_2;
		this.unyomi_3 = unyomi_3;
		this.unyomi_4 = unyomi_4;
		this.kunyomi_1 = kunyomi_1;
		this.kunyomi_2 = kunyomi_2;
		this.kunyomi_3 = kunyomi_3;
		this.kunyomi_4 = kunyomi_4;
		this.explanation = explanation;
		this.korean = korean;
		this.example_word_1 = example_word_1;
		this.example_word_2 = example_word_2;
		this.example_word_3 = example_word_3;

	}
	public String getKanjiCode() { return kanjiCode; }
    public void setKanjiCode(String kanjiCode) { this.kanjiCode = kanjiCode; }

	public String getKanji() {
		return kanji;
	}

	public void setKanji(String kanji) {
		this.kanji = kanji;
	}


	public String getUnyomi_1() {
		return unyomi_1;
	}

	public void setUnyomi_1(String unyomi_1) {
		this.unyomi_1 = unyomi_1;
	}

	public String getUnyomi_2() {
		return unyomi_2;
	}

	public void setUnyomi_2(String unyomi_2) {
		this.unyomi_2 = unyomi_2;
	}

	public String getUnyomi_3() {
		return unyomi_3;
	}

	public void setUnyomi_3(String unyomi_3) {
		this.unyomi_3 = unyomi_3;
	}

	public String getUnyomi_4() {
		return unyomi_4;
	}

	public void setUnyomi_4(String unyomi_4) {
		this.unyomi_4 = unyomi_4;
	}

	public String getKunyomi_1() {
		return kunyomi_1;
	}

	public void setKunyomi_1(String kunyomi_1) {
		this.kunyomi_1 = kunyomi_1;
	}

	public String getKunyomi_2() {
		return kunyomi_2;
	}

	public void setKunyomi_2(String kunyomi_2) {
		this.kunyomi_2 = kunyomi_2;
	}

	public String getKunyomi_3() {
		return kunyomi_3;
	}

	public void setKunyomi_3(String kunyomi_3) {
		this.kunyomi_3 = kunyomi_3;
	}

	public String getKunyomi_4() {
		return kunyomi_4;
	}

	public void setKunyomi_4(String kunyomi_4) {
		this.kunyomi_4 = kunyomi_4;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public String getKorean() {
		return korean;
	}

	public void setKorean(String korean) {
		this.korean = korean;
	}

	public String getExample_word_1() {
		return example_word_1;
	}

	public void setExample_word_1(String example_word_1) {
		this.example_word_1 = example_word_1;
	}

	public String getExample_word_2() {
		return example_word_2;
	}

	public void setExample_word_2(String example_word_2) {
		this.example_word_2 = example_word_2;
	}

	public String getExample_word_3() {
		return example_word_3;
	}

	public void setExample_word_3(String example_word_3) {
		this.example_word_3 = example_word_3;
	}

}
