project01/
├── src/main/java/
│   ├── servlet/          # 컨트롤러 (로직 처리)
│   │   ├── GroupSelectServlet.java
│   │   ├── StartGroupServlet.java
│   │   ├── StudyServlet.java
│   │   ├── FinishGroupServlet.java
│   │   └── ResetProgressServlet.java
│   │
│   ├── model/            # 데이터 모델
│   │   ├── KanjiDTO.java - 한자 데이터
│   │   └── StudyProgressDTO.java - 학습 진행 상태 (완료한 그룹, 한자 번호들)
│   │
│   ├── repository/       # 데이터 저장소
│   │   └── KanjiRepository.java - 한자 데이터 저장
│   │
│   └── util/             # 유틸리티
│       └── StudyManager.java
│
└── WebContent/
    ├── index.jsp         # 시작 페이지. 레벨 선택 화면
    ├── jsp/              # 화면 (View)
    │   ├── groupSelect.jsp - 그룹 선택 화면 (01, 02, 03...)
    │   └── study.jsp - 한자 학습 화면
    └── css/              # 스타일
        ├── main.css - index.jsp
        ├── group.css - groupSelect.jsp
        └── style.css - study.jsp
