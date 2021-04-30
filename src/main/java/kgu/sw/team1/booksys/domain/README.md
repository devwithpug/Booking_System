### 도메인 변경점

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

### 연관 관계

`Customer` 1<->1 `Reservation` 1<->1 `Tables` 1<->1 `WalkIn`