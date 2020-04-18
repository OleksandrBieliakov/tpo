package DAL;

import DTOs.Requests.ListResourcesReq;
import DTOs.Requests.LoginReq;
import DTOs.Requests.ResourceDetailsReq;
import DTOs.Responses.ListResourcesRes;
import DTOs.Responses.LoginRes;
import DTOs.Responses.ResourceDetailsRes;
import Models.ResourceModel;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PostgresRepository implements IRepository {

    private static final String TABLE_USER = "SystemUser";
    private static final String COLUMN_ID_USER = "idUser";
    private static final String COLUMN_USER_NAME = "UserName";
    private static final String COLUMN_USER_PASSWORD = "UserPassword";
    private static final String COLUMN_FIRST_NAME = "FirstName";
    private static final String COLUMN_LAST_NAME = "LastName";

    private static final String TABLE_USER_RESOURCES = "UsersResources";

    private static final String TABLE_RESOURCE = "Resource";
    private static final String COLUMN_ID_RESOURCE = "IdResource";
    private static final String COLUMN_RESOURCE_NAME = "ResourceName";
    private static final String COLUMN_CONTENT = "Content";

    private DataSource dataSource;

    public PostgresRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public LoginRes login(LoginReq request) throws Exception {
        LoginRes response = null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT " + COLUMN_ID_USER + ", " + COLUMN_FIRST_NAME + ", " + COLUMN_LAST_NAME +
                            " FROM " + TABLE_USER +
                            " WHERE " + COLUMN_USER_NAME + "=? AND " + COLUMN_USER_PASSWORD + "=?");
            statement.setString(1, request.getUserName());
            statement.setString(2, request.getUserPassword());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int idUser = resultSet.getInt(COLUMN_ID_USER);
                String firstName = resultSet.getString(COLUMN_FIRST_NAME);
                String lastName = resultSet.getString(COLUMN_LAST_NAME);
                response = new LoginRes(idUser, firstName, lastName);
            }
            resultSet.close();
        }
        return response;
    }

    @Override
    public ListResourcesRes listResources(ListResourcesReq request) throws Exception {
        ListResourcesRes response = new ListResourcesRes();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT " + TABLE_RESOURCE + "." + COLUMN_ID_RESOURCE + ", " + COLUMN_RESOURCE_NAME +
                            " FROM " + TABLE_USER_RESOURCES +
                            " JOIN " + TABLE_RESOURCE +
                            " ON " + TABLE_USER_RESOURCES + "." + COLUMN_ID_RESOURCE + "=" + TABLE_RESOURCE + "." + COLUMN_ID_RESOURCE +
                            " WHERE " + COLUMN_ID_USER + "=?");
            statement.setInt(1, request.getIdUser());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idResource = resultSet.getInt(COLUMN_ID_RESOURCE);
                String resourceName = resultSet.getString(COLUMN_RESOURCE_NAME);
                response.addResource(new ResourceModel(idResource, resourceName));
            }
            resultSet.close();
        }
        return response;
    }

    @Override
    public ResourceDetailsRes getResourceDetails(ResourceDetailsReq request) throws Exception {
        ResourceDetailsRes response = null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT " + COLUMN_CONTENT + "," + COLUMN_RESOURCE_NAME +
                            " FROM " + TABLE_RESOURCE +
                            " JOIN " + TABLE_USER_RESOURCES +
                            " ON " + TABLE_USER_RESOURCES + "." + COLUMN_ID_RESOURCE + "=" + TABLE_RESOURCE + "." + COLUMN_ID_RESOURCE +
                            " WHERE " + TABLE_RESOURCE + "." + COLUMN_ID_RESOURCE + "=? AND " + COLUMN_ID_USER + "=?");
            statement.setInt(1, request.getIdResource());
            statement.setInt(2, request.getIdUser());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String resourceName = resultSet.getString(COLUMN_RESOURCE_NAME);
                String content = resultSet.getString(COLUMN_CONTENT);
                response = new ResourceDetailsRes(resourceName, content);
            }
            resultSet.close();
        }
        return response;
    }

}
