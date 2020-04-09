package DAL;

import DTOs.Requests.ListResourcesReq;
import DTOs.Requests.LoginReq;
import DTOs.Requests.ResourceDetailsReq;
import DTOs.Responses.ListResourcesRes;
import DTOs.Responses.LoginRes;
import DTOs.Responses.ResourceDetailsRes;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresRepository implements IRepository {

    private static final String TABLE_USER = "SystemUser";
    private static final String COLUMN_ID = "idUser";
    private static final String COLUMN_USER_NAME = "UserName";
    private static final String COLUMN_USER_PASSWORD = "UserPassword";

    @Resource(name = "jdbc/postgres")
    private DataSource _dataSource;

    @Override
    public LoginRes login(LoginReq request) {
        LoginRes response = new LoginRes();
        if(_dataSource == null) {
            response.set_id(-1);
            return response;
        }
        try (Connection connection = _dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT " + COLUMN_ID +
                            " FROM " + TABLE_USER +
                            " WHERE " + COLUMN_USER_NAME + "=? AND " + COLUMN_USER_PASSWORD + "=?");
            statement.setString(1, request.get_userName());
            statement.setString(2, request.get_userPassword());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                response.set_id(resultSet.getInt(COLUMN_ID));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO
        }
        return response;
    }

    @Override
    public ListResourcesRes listResources(ListResourcesReq request) {
        return null;
    }

    @Override
    public ResourceDetailsRes getResourceDetails(ResourceDetailsReq request) {
        return null;
    }

}
