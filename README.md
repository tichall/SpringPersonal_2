
### 일정 관련 API

| 기능 | Method | URL | Request | Response | 기타 |
| --- | --- | --- | --- | --- | --- |
| 일정 작성 | POST | /api/schedules | {
"title" : "제목",
"contents" : "내용",
"owner" : "담당자",
"password" : "비밀번호"
} | {
‘title’ : ‘제목’,
‘contents’ : ‘내용’,
‘owner’ : ‘담당자’,
’createdAt’ : 생성 날짜,
’modifiedAt’ : 수정 날짜
} |  |
| 전체 일정 조회 | GET | /api/schedules |  | 모든 일정 정보 | 작성일 기준으로 내림차순 정렬 |
| 선택 일정 조회 | GET | /api/schedules/{Id} |  | 선택한 일정 정보 |  |
| 일정 수정 | PUT | /api/schedules/{Id} | {
"title" : "수정 제목",
"contents" : "수정 내용",
"owner" : "수정 담당자",
"password" : "비밀번호"
} | {
"id" : 아이디,
"title" : "수정 제목",
"contents" : "수정 내용",
"owner" : "수정 담당자",
"createdAt" : 생성 날짜,
"modifiedAt" : 수정 날짜
} | 해당 일정에 저장된 비밀번호와 request body로 전달된 비밀번호가 일치해야 일정 수정 가능 |
| 일정 삭제 | DELETE | /api/schedules/{Id} | {
”id” : 아이디,
”password” : “비밀번호”
} | 삭제 성공 여부 | 해당 일정에 저장된 비밀번호와 request body로 전달된 비밀번호가 일치해야 일정 삭제 가능 |
