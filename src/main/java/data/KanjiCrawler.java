package data;

import model.KanjiDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class KanjiCrawler {

    public static KanjiDTO crawl(int number) {
        String url = "https://nihongokanji.com/" + number;
        
        try {
            Document doc = Jsoup.connect(url).get();

            // 1. 한자 및 기본 정보 추출
            String kanji = doc.select("h1").first().text().substring(0, 1);
            String explanation = doc.select("ul li:contains(의미)").text().replace("의미", "").trim();
            String onyomiRaw = doc.select("ul li:contains(음독)").text().replace("음독", "").trim();
            String kunyomiRaw = doc.select("ul li:contains(훈독)").text().replace("훈독", "").trim();

            // 2. 음독/훈독 분리 (콤마 기준으로 잘라서 배열에 담기)
            String[] onyomis = onyomiRaw.split(",\\s*");
            String[] kunyomis = kunyomiRaw.split(",\\s*");

            // 3. 대표 단어 추출 (최대 3개)
            Elements wordElements = doc.select("h3:contains(대표단어) + ul li");
            String[] words = new String[3];
            for (int i = 0; i < wordElements.size() && i < 3; i++) {
                words[i] = wordElements.get(i).text().split(" ")[0];
            }

            // 4. KanjiDTO 생성 (생성자 파라미터 순서 엄수!)
            return new KanjiDTO(
                kanji, 
                getSafe(onyomis, 0), getSafe(onyomis, 1), getSafe(onyomis, 2), getSafe(onyomis, 3), // 음독 1~4
                getSafe(kunyomis, 0), getSafe(kunyomis, 1), getSafe(kunyomis, 2), getSafe(kunyomis, 3), // 훈독 1~4
                explanation, // 의미 (마당 장 등)
                "",          // korean 필드 (필요시 추가)
                words[0], words[1], words[2] // 예시 단어 1~3
            );

        } catch (Exception e) {
            System.err.println(number + "번 크롤링 실패: " + e.getMessage());
            return null;
        }
    }

    // 배열 범위 체크용 편의 메서드
    private static String getSafe(String[] arr, int index) {
        return (arr != null && index < arr.length) ? arr[index] : null;
    }
}