### 도메인 변경점

### 추가

* `Admin.java`
* `User.java`
* `Grade.java`

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

### 연관 관계(21.05.09)

![image](https://user-images.githubusercontent.com/69145799/117565749-de679480-b0ed-11eb-9581-ea8c6fa70989.png)

### TO-DO LIST

* [x] 다대다 관계로 변경(WalkIn, Reservation, Tables)