import generated_classes.Node;

import java.sql.*;
import java.util.List;

public class DatabaseController {
    private final String _url = "jdbc:postgresql://localhost/task2";
    private final String _user = "postgres";
    private final String _password = "1234";
    private final Connection _connection;

    public DatabaseController() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
        _connection = DriverManager.getConnection(_url, _user, _password);
    }

    public void clearTable() throws SQLException {
        Statement stmt = _connection.createStatement();
        String sql = "DELETE from public.\"Node\"";
        stmt.executeUpdate(sql);
    }

    public void loadByExecuteQuery(List<Node> nodes) throws SQLException {
        for (var node:nodes) {
            Statement stmt = _connection.createStatement();

            String sql = "INSERT INTO public.\"Node\" " +
                    "(node_id, id, tag, lat, lon, username, uid, visible, version, changeset, timestamp)" +
                    "values (" + node.getId() + "," + node.getId() + ", null, " +
                    node.getLat() + ", " + node.getLon() + ",'" +
                    node.getUser() + "', " + node.getUid() + ", " +
                    node.isVisible() + "," + node.getVersion() + ", " +
                    node.getChangeset() + ",'" +
                    new Timestamp(node.getTimestamp().toGregorianCalendar().getTime().getTime()) + "')";

            stmt.executeUpdate(sql);
        }
    }

    public void loadByPreparedStatement(List<Node> nodes) throws SQLException {
        for (var node:nodes) {
            String sql = "INSERT INTO public.\"Node\" " +
                    "(node_id, id, tag, lat, lon, username, uid, visible, version, changeset, timestamp)" +
                    "values (?, ?, null, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = _connection.prepareStatement(sql);
            preparedStatement.setObject(1, node.getId());
            preparedStatement.setObject(2, node.getId());

            preparedStatement.setDouble(3, node.getLat());
            preparedStatement.setDouble(4, node.getLon());
            preparedStatement.setString(5, node.getUser());

            preparedStatement.setObject(6, node.getUid());
            preparedStatement.setObject(7, node.isVisible());
            preparedStatement.setObject(8, node.getVersion());
            preparedStatement.setObject(9, node.getChangeset());
            preparedStatement.setTimestamp(10, new Timestamp(node.getTimestamp().toGregorianCalendar().getTime().getTime()));

            preparedStatement.executeUpdate();
        }
    }

    public void loadByBatch(List<Node> nodes) throws SQLException {
        String sql = "INSERT INTO public.\"Node\" " +
                "(node_id, id, tag, lat, lon, username, uid, visible, version, changeset, timestamp)" +
                "values (?, ?, null, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = _connection.prepareStatement(sql);
        int cnt = 0;

        for (var node:nodes) {
            preparedStatement.setObject(1, node.getId());
            preparedStatement.setObject(2, node.getId());

            preparedStatement.setDouble(3, node.getLat());
            preparedStatement.setDouble(4, node.getLon());
            preparedStatement.setString(5, node.getUser());

            preparedStatement.setObject(6, node.getUid());
            preparedStatement.setObject(7, node.isVisible());
            preparedStatement.setObject(8, node.getVersion());
            preparedStatement.setObject(9, node.getChangeset());
            preparedStatement.setTimestamp(10, new Timestamp(node.getTimestamp().toGregorianCalendar().getTime().getTime()));

            preparedStatement.addBatch();
            if(++cnt > 5000) {
                preparedStatement.executeBatch();
            }
        }
        preparedStatement.executeBatch();
    }

    public void init() throws SQLException {
        Initiator init = new Initiator(_connection);
        init.initNd();
        init.initTag();
        init.initWay();
        init.initMember();
        init.initRelation();
        init.initBound();
        init.initNode();
        init.initOsm();
    }
}
