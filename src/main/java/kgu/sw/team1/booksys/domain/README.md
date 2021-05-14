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

### 연관 관계(21.05.14)

![image](https://user-images.githubusercontent.com/69145799/118272943-4b1fcc00-b4fe-11eb-896b-99b03be5f48c.png)

### TO-DO LIST

* [x] 다대다 관계로 변경(WalkIn, Reservation, Tables)