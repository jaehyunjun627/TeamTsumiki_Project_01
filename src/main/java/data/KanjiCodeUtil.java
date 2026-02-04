package data;

public class KanjiCodeUtil {

	public static String code(String level, int group, int index) {

		return level + "-" + two(group) + "-" + two(index);

	}// 코드로 바꿔주는 부분, "-"를 넣는 이유는 sql하기 위해서

	private static String two(int n) {
		if (n < 0)
			throw new IllegalArgumentException("n must be >= 0");
		return (n < 10) ? "0" + n : String.valueOf(n);
	}// two()는 두자리로 고정하기 위해 필요한 것이며, 음수 입력을 막아준다고 하는데, 일단 쓰라고 해서 썼음

	public static String groupKey(String level, int group) {
		return level + "-" + two(group);

	}// 레벨과 섹터를 입력하면 반환해주는 메소드
		// prefix: "N5-" / "N5-10-"

	public static String levelPrefix(String level) {
		return level + "-";
	}// 앞으로 kanU.startwith(levelPrefix("N5")) 이런 식으로 호출 가능하게 해주는 메소드래요

	public static String groupPrefix(String level, int group) {
		return groupKey(level, group) + "-";
	}

}
