import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Initiator {
    private final Connection _connection;

    public Initiator(Connection connection) {
        _connection = connection;
    }

    public void initTag() throws SQLException {
        Statement stmt = _connection.createStatement();

        String sql = "CREATE TABLE public.\"Tag\"\n" +
                "(\n" +
                "    k text NOT NULL,\n" +
                "    v text NOT NULL,\n" +
                "    id integer NOT NULL,"+
                "    PRIMARY KEY (id)\n" +
                ")\n" +
                "WITH (\n" +
                "    OIDS = FALSE\n" +
                ");\n" +
                "\n" +
                "ALTER TABLE public.\"Tag\"\n" +
                "    OWNER to postgres;";

        stmt.executeUpdate(sql);
    }

    public void initNode() throws SQLException {
        Statement stmt = _connection.createStatement();

        String sql = "CREATE TABLE public.\"Node\"\n" +
                "(\n" +
                "    node_id integer NOT NULL,\n" +
                "    tag integer,\n" +
                "    id bigint NOT NULL,\n" +
                "    lat double precision NOT NULL,\n" +
                "    lon double precision NOT NULL,\n" +
                "    \"user\" text NOT NULL,\n" +
                "    uid bigint NOT NULL,\n" +
                "    visible boolean,\n" +
                "    version bigint NOT NULL,\n" +
                "    changeset bigint NOT NULL,\n" +
                "    \"timestamp\" date NOT NULL,\n" +
                "    PRIMARY KEY (node_id),\n" +
                "    CONSTRAINT tag FOREIGN KEY (tag)\n" +
                "        REFERENCES public.\"Tag\" (id) MATCH SIMPLE\n" +
                "        ON UPDATE NO ACTION\n" +
                "        ON DELETE NO ACTION\n" +
                "        NOT VALID\n" +
                ")\n" +
                "WITH (\n" +
                "    OIDS = FALSE\n" +
                ");\n" +
                "\n" +
                "ALTER TABLE public.\"Node\"\n" +
                "    OWNER to postgres;";

        stmt.executeUpdate(sql);
    }

    public void initBound() throws SQLException {
        Statement stmt = _connection.createStatement();

        String sql = "CREATE TABLE public.\"Bounds\"\n" +
                "(\n" +
                "    minlat double precision NOT NULL,\n" +
                "    minlon double precision NOT NULL,\n" +
                "    maxlat double precision NOT NULL,\n" +
                "    maxlon double precision NOT NULL,\n" +
                "    id integer NOT NULL,\n" +
                "    PRIMARY KEY (id)\n" +
                ")\n" +
                "WITH (\n" +
                "    OIDS = FALSE\n" +
                ");\n" +
                "\n" +
                "ALTER TABLE public.\"Bounds\"\n" +
                "    OWNER to postgres;";

        stmt.executeUpdate(sql);
    }

    public void initNd() throws SQLException {
        Statement stmt = _connection.createStatement();

        String sql = "CREATE TABLE public.\"Nd\"\n" +
                "(ref bigint NOT NULL,\n" +
                "id integer NOT NULL,"+
                "PRIMARY KEY (id)\n" +
                ")\n" +
                "WITH (\n" +
                "    OIDS = FALSE\n" +
                ");\n" +
                "\n" +
                "ALTER TABLE public.\"Nd\"\n" +
                "    OWNER to postgres;";

        stmt.executeUpdate(sql);
    }

    public void initWay() throws SQLException {
        Statement stmt = _connection.createStatement();

        String sql = "CREATE TABLE public.\"Way\"\n" +
                "(\n" +
                "    id bigint NOT NULL,\n" +
                "    \"user\" text NOT NULL,\n" +
                "    uid bigint NOT NULL,\n" +
                "    visible boolean NOT NULL,\n" +
                "    version bigint NOT NULL,\n" +
                "    changeset bigint NOT NULL,\n" +
                "    \"timestamp\" date NOT NULL,\n" +
                "    nd integer NOT NULL,\n" +
                "    tag integer NOT NULL,\n" +
                "    id_node integer NOT NULL,"+
                "    PRIMARY KEY (id_node)\n" +
                ")\n" +
                "WITH (\n" +
                "    OIDS = FALSE\n" +
                ");\n" +
                "\n" +
                "ALTER TABLE public.\"Way\"\n" +
                "    OWNER to postgres;\n" +
                "\n" +
                "ALTER TABLE public.\"Way\"\n" +
                "    ADD CONSTRAINT nd FOREIGN KEY (nd)\n" +
                "    REFERENCES public.\"Nd\" (id) MATCH SIMPLE\n" +
                "    ON UPDATE NO ACTION\n" +
                "    ON DELETE NO ACTION\n" +
                "    NOT VALID;\n" +
                "\n" +
                "ALTER TABLE public.\"Way\"\n" +
                "    ADD CONSTRAINT tag FOREIGN KEY (tag)\n" +
                "    REFERENCES public.\"Tag\" (id) MATCH SIMPLE\n" +
                "    ON UPDATE NO ACTION\n" +
                "    ON DELETE NO ACTION\n" +
                "    NOT VALID;";

        stmt.executeUpdate(sql);
    }
    public void initRelation() throws SQLException {
        Statement stmt = _connection.createStatement();

        String sql = "CREATE TABLE public.\"Relation\"\n" +
                "(\n" +
                "    member integer NOT NULL,\n" +
                "    tag integer NOT NULL,\n" +
                "    id bigint NOT NULL,\n" +
                "    \"user\" text NOT NULL,\n" +
                "    uid bigint NOT NULL,\n" +
                "    visible boolean NOT NULL,\n" +
                "    version bigint NOT NULL,\n" +
                "    changeset bigint NOT NULL,\n" +
                "    \"timestamp\" date NOT NULL,\n" +
                "    id_member integer NOT NULL,\n" +
                "    PRIMARY KEY (id),\n" +
                "    CONSTRAINT member FOREIGN KEY (member)\n" +
                "        REFERENCES public.\"Member\" (id) MATCH SIMPLE\n" +
                "        ON UPDATE NO ACTION\n" +
                "        ON DELETE NO ACTION\n" +
                "        NOT VALID,\n" +
                "    CONSTRAINT tag FOREIGN KEY (tag)\n" +
                "        REFERENCES public.\"Tag\" (id) MATCH SIMPLE\n" +
                "        ON UPDATE NO ACTION\n" +
                "        ON DELETE NO ACTION\n" +
                "        NOT VALID\n" +
                ")\n" +
                "WITH (\n" +
                "    OIDS = FALSE\n" +
                ");\n" +
                "\n" +
                "ALTER TABLE public.\"Relation\"\n" +
                "    OWNER to postgres;";

        stmt.executeUpdate(sql);
    }

    public void initMember() throws SQLException {
        Statement stmt = _connection.createStatement();

        String sql = "CREATE TABLE public.\"Member\"\n" +
                "(\n" +
                "    id integer NOT NULL,\n" +
                "    type text NOT NULL,\n" +
                "    ref bigint NOT NULL,\n" +
                "    role text NOT NULL,\n" +
                "    PRIMARY KEY (id)\n" +
                ")\n" +
                "WITH (\n" +
                "    OIDS = FALSE\n" +
                ");\n" +
                "\n" +
                "ALTER TABLE public.\"Member\"\n" +
                "    OWNER to postgres;\n";

        stmt.executeUpdate(sql);
    }

    public void initOsm() throws SQLException {
        Statement stmt = _connection.createStatement();

        String sql = "CREATE TABLE public.\"Osm\"\n" +
                "(\n" +
                "    id integer NOT NULL,\n" +
                "    bounds integer,\n" +
                "    node integer NOT NULL,\n" +
                "    way integer NOT NULL,\n" +
                "    relation integer NOT NULL,\n" +
                "    version real NOT NULL,\n" +
                "    generator text NOT NULL,\n" +
                "    PRIMARY KEY (id),\n" +
                "    CONSTRAINT bounds FOREIGN KEY (bounds)\n" +
                "        REFERENCES public.\"Bounds\" (id) MATCH SIMPLE\n" +
                "        ON UPDATE NO ACTION\n" +
                "        ON DELETE NO ACTION\n" +
                "        NOT VALID,\n" +
                "    CONSTRAINT node FOREIGN KEY (node)\n" +
                "        REFERENCES public.\"Node\" (node_id) MATCH SIMPLE\n" +
                "        ON UPDATE NO ACTION\n" +
                "        ON DELETE NO ACTION\n" +
                "        NOT VALID,\n" +
                "    CONSTRAINT way FOREIGN KEY (way)\n" +
                "        REFERENCES public.\"Way\" (id_node) MATCH SIMPLE\n" +
                "        ON UPDATE NO ACTION\n" +
                "        ON DELETE NO ACTION\n" +
                "        NOT VALID,\n" +
                "    CONSTRAINT relation FOREIGN KEY (relation)\n" +
                "        REFERENCES public.\"Relation\" (id) MATCH SIMPLE\n" +
                "        ON UPDATE NO ACTION\n" +
                "        ON DELETE NO ACTION\n" +
                "        NOT VALID\n" +
                ")\n" +
                "WITH (\n" +
                "    OIDS = FALSE\n" +
                ");\n" +
                "\n" +
                "ALTER TABLE public.\"Osm\"\n" +
                "    OWNER to postgres;";

        stmt.executeUpdate(sql);
    }
}
