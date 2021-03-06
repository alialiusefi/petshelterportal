package by.training.finaltask.service;

import by.training.finaltask.dao.pool.PetPooledConnection;
import by.training.finaltask.entity.Role;
import by.training.finaltask.entity.User;
import by.training.finaltask.exception.InvalidFormDataException;
import by.training.finaltask.exception.PersistentException;
import by.training.finaltask.service.serviceinterface.UserService;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserServiceImplTest {

    private PetPooledConnection connection;
    private UserService service;

    @BeforeClass
    public void setUp() throws SQLException, ClassNotFoundException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                "servletconfig");
        Class.forName(resourceBundle.getString("dbDriverClass"));
        connection = new PetPooledConnection(DriverManager.
                getConnection(resourceBundle.getString("dbURL"),
                        resourceBundle.getString("dbUser"), resourceBundle.getString("dbPassword")));
        service = new UserServiceImpl(connection);
    }

    @AfterClass
    public void tearDown() throws SQLException {
        connection.getConnection().close();
    }

    @Test
    public void testGetAll() throws PersistentException {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User(10,"kjaeggi9",
                "5f4dcc3b5aa765d61d8327deb882cf99",Role.STAFF));
        expectedUsers.add(new User(11,"nmaxsteda",
                "5f4dcc3b5aa765d61d8327deb882cf99",Role.STAFF));
        List<User> actual = service.getAll(9,2);
        Assert.assertEquals(expectedUsers,actual);
    }

    @Test
    public void testGet() throws PersistentException {
        User actual = service.get(10);
        User expected = new User(10,"kjaeggi9",
                "5f4dcc3b5aa765d61d8327deb882cf99",Role.STAFF);
        Assert.assertEquals(actual,expected);
    }

    @Test
    public void testFindByUserNameAndPassword() throws PersistentException{
        User actual = service.findByUserNameAndPassword("kjaeggi9","password");
        User expected = new User(10,"kjaeggi9",
                "5f4dcc3b5aa765d61d8327deb882cf99",Role.STAFF);
        Assert.assertEquals(actual,expected);
    }

    @Test
    public void testAdd() throws PersistentException, InvalidFormDataException {
        User expected = new User(null, "alifff",
                "5f4dcc3b5aa765d61d8327deb882cf99", Role.GUEST);
        int idGenerated = service.register(expected);
        expected.setId(idGenerated);
        User actual = service.get(idGenerated);
        Assert.assertEquals(actual,expected);
    }

    @Test
    public void testUpdate() {
    }

    @Test
    public void testDelete() throws PersistentException, InvalidFormDataException {
        User expected = new User(null, "alifff",
                "5f4dcc3b5aa765d61d8327deb882cf99", Role.ADMINISTRATOR);
        int userId = service.register(expected);
        service.delete(userId);
        Assert.assertNull(service.get(userId));
    }

    @Test
    public void testGetAmountOfAllStaff() {
    }

    @Test
    public void testGetAllStaff() {
    }

    @Test
    public void testGetAllStaffByFirstName() {
    }

    @Test
    public void testGetAllStaffByPhone() {
    }

    @Test
    public void testGetAmountOfAllStaffByFirstName() {
    }

    @Test
    public void testGetAmountOfAllStaffByPhone() {
    }
}