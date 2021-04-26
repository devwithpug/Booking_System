# Contribution Guide

## 1. 절차

1. https://github.com/devwithpug/Booking_System `fork` 하기
2. `fork` 한 리포지토리 `clone`
3. 새로운 이슈 생성 or 기존 이슈 선택 `Label 설정 필수`
4. 작업중인 이슈에 댓글로 표시하고 단톡방에 해당 작업중 알리기
5. 기존 `develop` 브랜치 `rebase`
6. 브랜치 이름 규칙에 따라 작업할 브랜치 생성 `git branch 이름`
7. 작업 수행(단위 테스트 필요시 작성)
8. 테스트 이상 없을 시 작업한 이슈를 `develop` 브랜치에 Pull Request
9. 문제 없으면 PM은 `develop` 브랜치에 `squash and merge`
10. `main` 은 매주 스프린트가 종료될 때 마다 결과물이 완성된 경우 `merge`

## 2. 브랜치 관리 규칙

- `main` : 모든 기능이 작동하는 브랜치
- `develop` : 테스트 완료된 개발 코드 브랜치

## 3. 브랜치 이름 전략

1. 형식

   > [타입]/[이슈 번호]

2. 예시

   > feature/#1 
   >
   > refactor/#5

## 4. 커밋 메시지 규칙

1. 문장의 끝에 `.` 를 붙이지 말기

2. 이슈 번호를 커밋 메시지 끝에 붙이기

3. 형식

   > [타입]: [이슈 번호]

4. 예시

   > docs: OO메소드 관련 설명 주석 [#3]
   >
   > feature: 예약 시스템의 add() [#6]

5. 타입 종류

   > \- feature : 새로운 기능
   >
   > \- fix : 버그 대처
   >
   > \- refactor : 코드 수정
   >
   > \- test : 테스트 추가
   >
   > \- docs : 문서 작성
   >
   > \- chore : 기타 이슈

## 궁금한 점 있으면 언제든지 질문해주세요!