# EPAM JWD final task - jcafe
### *Task*
Cafe. The Client makes an Order for lunch (chooses from the menu) and indicates the time when he would like to receive the order. The system shows the price of the Order and offers to pay from the client's account or in cash upon receipt of the order. Loyal score are awarded to the client for pre-orders. If the Client places an order and does not pick it up, then loyal score decreases until client is blocked. The Client can evaluate each Order and leave feedback. The Administrator manages the menu, sets/removes bans/bonuses/score for Clients.
***
### *Guest:*
- changes language
- registers
- signs in
- finds menu
- sorts menu by popularity and price

### *Client:*
- adds food dishes to cart
- deletes food dishes from cart
- changes language
- finds menu by section name
- sorts menu by popularity and price
- signs out
- updates profile
- changes password
- creates an order
- cancels an order
- views list of all his/her orders
- evaluates an order and gives a feedback
- views his/her bonuses
- views his/her loyalty points
- adds cash to his/her account

### *Admin:*
- changes language
- finds menu by section name
- sorts menu by popularity and price
- signs out
- updates profile
- changes password
- views all orders
- views cancelled orders
- decreases loyalty points for cancelled orders
- changes an order state
- blocks\unblocks clients
- manages menu
- manages sections
***
### Project technology stack:
- Java 18
- Jakarta EE: Servlet, JSP, JSTL
- Servlet container: Tomcat 10
- Database: PostgreSQL
- JDBC
- HTML 5, CSS 3, JS
- Logger: Log4J2
- Tests: TestNG
- Maven
- Git
***
![Database diagram](https://github.com/gibbonsite/jcafe/blob/master/jcafeDb.png)
