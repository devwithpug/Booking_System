### 도메인 변경점

### 추가

* `Admin.java`
* `User.java`
* `Grade.java`
* 'ReservationHistory.java'

### 제거

* `BookingObserver.java`
* `BookingImp.java`

### 변경

* `BookingSystem.java` -> service 클래스로 이동 & 리팩터링
* `Restaurant.java` -> service 클래스로 이동 & 리팩터링
* `Table.java` -> `Tables.java` 이름 변경

### 유지

* `Booking.java`
* `Customer.java`
* `Reservation.java`
* `WalkIn.java`

### 연관 관계(21.05.27)

![스크린샷 2021-05-27 20 19 31](https://user-images.githubusercontent.com/69145799/119817405-e6fefe00-bf28-11eb-9ddf-326d8f1d7f5e.png)

### TO-DO LIST

* [x] 다대다 관계로 변경(WalkIn, Reservation, Tables)
* [x] 일대다 관계로 변경(Customer, Reservation)
* [x] 예약 기록(통계 관련) 추가