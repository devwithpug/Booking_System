## Param 클래스

도메인의 DTO 역할을 하는 클래스

* CustomerParam
```json
{
  "id": "회원_아이디",
  "pw": "회원_비밀번호",
  "name": "회원_이름",
  "phoneNumber": "회원_전화번호",
  "email": "회원 이메일"
}
```

* ReservationParam
```json
{
  "customerOid": 1,
  "date": "예약_날짜",
  "time": "예약_시간",
  "covers": 4,
  "tablesOid": [1, 2, 3]
}
```

* TablesParam
```json
{
  "number": 11,
  "places": "테이블_구역"
}
```

* WalkInParam
```json
{
  "date": "예약_날짜",
  "time": "예약_시간",
  "covers": 4,
  "tablesOid": [1, 2, 3]
}
```