package com.poleschuk.cafe.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.poleschuk.cafe.exception.DaoException;
import com.poleschuk.cafe.exception.ServiceException;
import com.poleschuk.cafe.model.dao.EntityTransaction;
import com.poleschuk.cafe.model.dao.impl.OrderDaoImpl;
import com.poleschuk.cafe.model.dao.impl.UserDaoImpl;
import com.poleschuk.cafe.model.entity.CompleteOrder;
import com.poleschuk.cafe.model.entity.Order;
import com.poleschuk.cafe.model.entity.OrderComponent;
import com.poleschuk.cafe.model.entity.OrderState;
import com.poleschuk.cafe.model.entity.User;
import com.poleschuk.cafe.model.entity.UserRole;
import com.poleschuk.cafe.model.entity.UserState;
import com.poleschuk.cafe.service.UserService;
import com.poleschuk.cafe.util.PasswordEncryption;
import com.poleschuk.cafe.util.mail.Mail;
import com.poleschuk.cafe.validator.Validator;
import com.poleschuk.cafe.validator.impl.ValidatorImpl;

import static com.poleschuk.cafe.controller.Parameter.*;

/**
 * UserServiceImpl class implements UserService interface and contains
 * business logic for user processing.
 */
public class UserServiceImpl implements UserService {
     private static final Logger logger = LogManager.getLogger();
     private static final String REGISTRATION_SUBJECT = "JCafe Registration";
     private static final String REGISTRATION_BODY = "Registration was successful";
     private static final UserServiceImpl instance = new UserServiceImpl();
     private final Validator validator = ValidatorImpl.getInstance();

     private UserServiceImpl() {}

     public static UserServiceImpl getInstance() {
         return instance;
     }

     public Optional<User> authenticate(String login, String password) throws ServiceException {
         UserDaoImpl userDao = new UserDaoImpl();
         EntityTransaction transaction = new EntityTransaction();
         transaction.init(userDao);
         String encryptPassword = PasswordEncryption.md5Apache(password);
         try {
             return userDao.findUserByLoginAndPassword(login, encryptPassword);
         } catch (DaoException e) {
             throw new ServiceException("Exception in a signIn service method" , e);
         } finally {
        	 transaction.end();
         }
     }

     public boolean userRegistration(Map<String,String> mapData, UserRole role) throws ServiceException{
         UserDaoImpl userDao = new UserDaoImpl();
         EntityTransaction transaction = new EntityTransaction();
         transaction.init(userDao);
         try {
             boolean commonResult = validator.checkRegistration(mapData);
             logger.log(Level.INFO, "Validator result " + commonResult);
             if (!commonResult) {
                 return false;
             }
             String firstName = mapData.get(USER_FIRST_NAME);
             String lastName = mapData.get(USER_LAST_NAME);
             String login = mapData.get(LOGIN);
             String email = mapData.get(USER_EMAIL);
             String phone = mapData.get(USER_PHONE_NUMBER);
             String password = mapData.get(PASSWORD);
             String birthday = mapData.get(USER_BIRTHDAY);
             String repeatPassword = mapData.get(REPEAT_PASSWORD);
             int phoneNumber = Integer.parseInt(phone);
             LocalDate date = LocalDate.parse(birthday);
             boolean uniqResult = true;
             if (userDao.findUserByLogin(login).isPresent()) {
                 mapData.put(LOGIN, NOT_UNIQ_LOGIN);
                 uniqResult = false;
             }
             if (userDao.findUserByEmail(email).isPresent()) {
                 mapData.put(USER_EMAIL, NOT_UNIQ_EMAIL);
                 uniqResult = false;
             }
             if (userDao.findUserByPhoneNumber(phoneNumber).isPresent()) {
                 mapData.put(USER_PHONE_NUMBER, NOT_UNIQ_PHONE);
                 uniqResult = false;
             }
             if (!password.equals(repeatPassword)) {
                 mapData.put(REPEAT_PASSWORD, INVALID_REPEAT_PASSWORD);
                 uniqResult = false;
             }
             if (!uniqResult) {
                 return false;
             }
             String encryptPassword = PasswordEncryption.md5Apache(password);
             User user = new User(firstName, lastName, login, encryptPassword, email,
                     phoneNumber, date, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                     role, UserState.ACTIVE);
             boolean isUserCreate = userDao.insert(user);
             if (isUserCreate) {
                 Mail.createMail(email, REGISTRATION_SUBJECT, REGISTRATION_BODY);
             }
             return isUserCreate;
         } catch (DaoException e) {
             throw new ServiceException("Add user error: userRegistration service method ", e);
         } finally {
        	 transaction.end();
         }
     }

     @Override
     public List<User> findClientSublist(int pageSize, int offset) throws ServiceException {
         UserDaoImpl userDao = new UserDaoImpl();
         EntityTransaction transaction = new EntityTransaction();
         transaction.initTransaction(userDao);
         List<User> clientList = new ArrayList<>();
         try {
             clientList = userDao.findClientSublist(pageSize, offset);
             transaction.commit();
         } catch (DaoException e) {
             transaction.rollback();
             throw new ServiceException("Exception in a findClientSublist method. ", e);
         } finally {
             transaction.endTransaction();
         }
         return clientList;
     }

     @Override
     public List<User> findAllAdmins() throws ServiceException {
         UserDaoImpl userDao = new UserDaoImpl();
         EntityTransaction transaction = new EntityTransaction();
         transaction.init(userDao);
         try {
             return userDao.findAllAdmins();
         } catch (DaoException e) {
             throw new ServiceException("Exception in a findAllAdmins method. ", e);
         } finally {
             transaction.end();
         }
     }

     @Override
     public int readClientRowCount() throws ServiceException {
         UserDaoImpl userDao = new UserDaoImpl();
         EntityTransaction transaction = new EntityTransaction();
         transaction.init(userDao);
         try {
             return userDao.readClientRowCount();
         } catch (DaoException e) {
             throw new ServiceException("Exception in a readClientRowCount service method. ", e);
         } finally {
             transaction.end();
         }
     }

     @Override
     public Optional<User> updateUserProfile(User user, Map<String, String> updateData) throws ServiceException {
         UserDaoImpl userDao = new UserDaoImpl();
         EntityTransaction transaction = new EntityTransaction();
         transaction.init(userDao);
         try{
             if (!validator.checkUpdateProfile(updateData)) {
                 logger.log(Level.INFO, "checkUpdateProfile is false");
                 return Optional.empty();
             }
             String firstName = updateData.get(USER_FIRST_NAME);
             String lastName = updateData.get(USER_LAST_NAME);
             String email = updateData.get(USER_EMAIL);
             String phone = updateData.get(USER_PHONE_NUMBER);
             String birthday = updateData.get(USER_BIRTHDAY);
             logger.log(Level.INFO, "birthday - " + birthday);
             int phoneNumber = Integer.parseInt(phone);
             LocalDate date = LocalDate.parse(birthday);
             logger.log(Level.INFO, "LocalDate birthday - " + birthday);
             boolean uniqResult = true;
             if (userDao.findUserByEmail(email).isPresent() && !email.equals(user.getEmail())) {
                 updateData.put(USER_EMAIL, NOT_UNIQ_EMAIL);
                 uniqResult = false;
             }

             if (userDao.findUserByPhoneNumber(phoneNumber).isPresent() && phoneNumber != user.getPhoneNumber()) {
                 updateData.put(USER_PHONE_NUMBER, NOT_UNIQ_PHONE);
                 uniqResult = false;
             }

             if (!uniqResult) {
                 return Optional.empty();
             }

             User newUser = new User(user.getUserId(), firstName, lastName, user.getLogin(),
                     null, email, phoneNumber, date, user.getCash(), user.getLoyalScore(),
                     user.getAccumulatedCash(), user.getRole(), user.getState());

             Optional<User> optionalUser = userDao.update(newUser);

             return optionalUser.isPresent() ? Optional.of(newUser) : Optional.empty();
         } catch (DaoException e) {
             throw new ServiceException("Exception in a updateUserProfile service method ", e);
         } finally {
             transaction.end();
         }
     }

     @Override
     public boolean changePasswordByOldPassword(Map<String, String> map, User user) throws ServiceException {
         UserDaoImpl userDao = new UserDaoImpl();
         EntityTransaction transaction = new EntityTransaction();
         transaction.init(userDao);
         String oldPassword = map.get(OLD_PASSWORD);
         String newPassword = map.get(NEW_PASSWORD);
         String repeatPassword = map.get(REPEAT_PASSWORD);
         try {
             Optional<String> optionalPassword = userDao.findPasswordByLogin(user.getLogin());
             if (optionalPassword.isPresent()) {
                 if (!optionalPassword.get().equals(PasswordEncryption.md5Apache(oldPassword))) {
                     map.put(OLD_PASSWORD, INVALID_OLD_PASSWORD);
                     return false;
                 }
             }
             if (!validator.isCorrectPassword(newPassword)) {
                 map.put(NEW_PASSWORD, INVALID_NEW_PASSWORD);
                 return false;
             }
             if (oldPassword.equals(newPassword)) {
                 map.put(NEW_PASSWORD, INVALID_NEW_UNIQ_PASSWORD);
                 return false;
             }
             if (!newPassword.equals(repeatPassword)) {
                 map.put(REPEAT_PASSWORD, INVALID_REPEAT_PASSWORD);
                 return false;
             }
             String encryptNewPassword = PasswordEncryption.md5Apache(newPassword);
             boolean result = userDao.updatePasswordByLogin(encryptNewPassword, user.getLogin());
             user.setPassword(encryptNewPassword);
             return result;
         } catch (DaoException e) {
             throw new ServiceException("Exception in a changePasswordByOldPassword service method ", e);
         } finally {
             transaction.end();
         }
     }

     @Override
     public boolean changeUserStateById(UserState state, long id) throws ServiceException {
         UserDaoImpl userDao = new UserDaoImpl();
         EntityTransaction transaction = new EntityTransaction();
         transaction.init(userDao);
         try {
             return userDao.updateUserState(id, (long) state.ordinal() + 1);
         } catch (DaoException e) {
             throw new ServiceException("Exception in a ChangeUserStateById method ", e);
         } finally {
             transaction.end();
         }
     }

     @Override
     public boolean deleteAdmin(long id) throws ServiceException{
         UserDaoImpl userDao = new UserDaoImpl();
         EntityTransaction transaction = new EntityTransaction();
         transaction.init(userDao);
         try {
             return userDao.delete(id);
         } catch (DaoException e) {
             throw new ServiceException("Exception in a deleteUser service method ", e);
         } finally {
             transaction.end();
         }
     }

     @Override
     public boolean topUpBalance(User user, BigDecimal amount) throws ServiceException {
         UserDaoImpl userDao = new UserDaoImpl();
         EntityTransaction transaction = new EntityTransaction();
         transaction.init(userDao);
         try {
        	 if (validator.checkToppingUpBalance(amount)) {
        		 boolean result = userDao.updateCashById(user.getUserId(), user.getCash()
    					 .add(amount));
        		 if (result) {
        			 user.setCash(user.getCash()
        					 .add(amount));
        		 }
        		 return result;
        	 } else {
        		 return false;
        	 }
         } catch (DaoException e) {
             throw new ServiceException("Exception in a deleteUser service method ", e);
         } finally {
             transaction.end();
         }
     }

     @Override
     public Optional<User> decreaseLoyalScoreByOrderId(long orderId, BigDecimal amount) throws ServiceException {
         UserDaoImpl userDao = new UserDaoImpl();
         OrderDaoImpl orderDao = new OrderDaoImpl();
         EntityTransaction transaction = new EntityTransaction();
         transaction.initTransaction(userDao, orderDao);
         Optional<User> optionalUser = Optional.empty();
         try {
        	 if (validator.checkLoyalScoreDecreasing(amount)) {
        		 Optional<Order> optionalOrder = orderDao.findEntityById(orderId);
        		 if (optionalOrder.isPresent()) {
        			 Order order = optionalOrder.get();
            		 optionalUser = userDao.findEntityById(order.getUserId());
            		 if (optionalUser.isPresent()) {
            			 User user = optionalUser.get();
    	        		 if (userDao.updateLoyalScoreById(user.getUserId(), amount.add(user.getLoyalScore()))) {
		        			 order.setOrderState(OrderState.CANCELLED_FINISHED);
		        			 orderDao.update(order);
    	        		 } else {
    	        			 optionalUser = Optional.empty();
    	        		 }
            		 } else {
            			 logger.error("User is not found when decreasing loyal score");
            		 }
        		 }
        	 }
        	 if (!optionalUser.isPresent()) {
        		 transaction.rollback();
        	 }
         } catch (DaoException e) {
        	 transaction.rollback();
             throw new ServiceException("Exception in a deleteUser service method ", e);
         } finally {
             transaction.endTransaction();
         }
         return optionalUser;
     }
}
