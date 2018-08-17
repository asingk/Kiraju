/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.interfaces;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import kiraju.model.Users;
import kiraju.property.UsersProperty;

/**
 *
 * @author arvita
 */
public interface IUsers {
    ObservableList<UsersProperty> getAll();
    boolean insert(Users users, Stage stage);
    boolean update(Users users, Stage stage);
    void delete(int id);
    Users selectByUsername(String userName);
    List<Users> getAllWithin99();
    Users selectByUsernameIncludeStaff(String userName);
}
