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

### 연관 관계(21.05.16)

![image](https://user-images.githubusercontent.com/69145799/118388633-9d78fe00-b660-11eb-8688-a044fdf7ab87.png)

### TO-DO LIST

* [x] 다대다 관계로 변경(WalkIn, Reservation, Tables)
* [x] 일대다 관계로 변경(Customer, Reservation)