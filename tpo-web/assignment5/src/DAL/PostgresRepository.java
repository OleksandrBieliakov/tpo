package DAL;

import DTOs.Requests.ListResourcesReq;
import DTOs.Requests.LoginReq;
import DTOs.Requests.ResourceDetailsReq;
import DTOs.Responses.ListResourcesRes;
import DTOs.Responses.LoginRes;
import DTOs.Responses.ResourceDetailsRes;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresRepository implements IRepository {

    private static final String TABLE_USER = "SystemUser";
    private static final String COLUMN_ID_USER = "idUser";
    private static final String COLUMN_USER_NAME = "UserName";
    private static final String COLUMN_USER_PASSWORD = "UserPassword";

    private DataSource dataSource;

    public PostgresRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public LoginRes login(LoginReq request) {
        LoginRes response = new LoginRes(-1);
        if (dataSource == null) {
            return response;
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT " + COLUMN_ID_USER +
                            " FROM " + TABLE_USER +
                            " WHERE " + COLUMN_USER_NAME + "=? AND " + COLUMN_USER_PASSWORD + "=?");
            statement.setString(1, request.getUserName());
            statement.setString(2, request.getUserPassword());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                response.setIdUser(resultSet.getInt(COLUMN_ID_USER));
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
