/*
 * Copyright (c) 2021 bublade
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.bubladecoding.mcpluginbase.database;

import com.bubladecoding.mcpluginbase.PluginBase;

import java.lang.constant.ConstantDesc;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @param <T> Type of the object this table manges.
 * @param <I> ID type of {@link T}
 */
public abstract class Table<T extends IDed<I>, I extends ConstantDesc> {

    protected final PluginBase pluginBase;
    protected final Connection connection;
    protected final String tableName;
    protected final Callable<T> create;

    protected List<DBField<T, ?>> fields = new ArrayList<>();
    protected DBField<T, I> idField;
    protected I idDefault;

    private String saveExistingQuery;
    private String saveNewQuery;

    /**
     * Constructor.
     *
     * @param pluginBase The instance of a plugin extending pluginBase.
     * @param connection The connection with the database.
     * @param tableName  The name of the table.
     * @param create     Creation method used for loading.
     */
    public Table(PluginBase pluginBase, Connection connection, String tableName, Callable<T> create) {
        this(pluginBase, connection, tableName, create, null);
    }

    /**
     * Constructor.
     *
     * @param pluginBase The instance of a plugin extending pluginBase.
     * @param connection The connection with the database.
     * @param tableName  The name of the table.
     * @param create     Creation method used for loading.
     * @param idDefault  The default value of ids.
     */
    public Table(PluginBase pluginBase, Connection connection, String tableName, Callable<T> create, I idDefault) {
        this.pluginBase = pluginBase;
        this.connection = connection;
        this.tableName = tableName;
        this.create = create;
        setIdField(T::getId, T::setId, idDefault);
    }

    /**
     * Set the field that represents the id of the manged object type.
     * This by default will be 'id'.
     *
     * @param get       The get {@link Function} to retrieve the id from the object.
     * @param set       The set {@link BiConsumer} to set the id of the object.
     * @param idDefault The default value for the id field.
     */
    protected void setIdField(Function<T, I> get, BiConsumer<T, I> set, I idDefault) {
        this.idDefault = idDefault;
        idField = new DBField<>("id", get, set);
    }

    /**
     * Set the field that represents the id of the manged object type.
     * This by default will be 'id'.
     *
     * @param dbName    name of the column in the database.
     * @param get       The get {@link Function} to retrieve the id from the object.
     * @param set       The set {@link BiConsumer} to set the id of the object.
     * @param idDefault The default value for the id field.
     */
    protected void setIdField(String dbName, Function<T, I> get, BiConsumer<T, I> set, I idDefault) {
        this.idDefault = idDefault;
        idField = new DBField<>(dbName, get, set);
    }

    /**
     * Set the field that represents the id of the manged object type.
     * This by default will be 'id'.
     *
     * @param dbName name of the column in the database.
     * @param get    The get {@link Function} to retrieve the value from the object.
     * @param set    The set {@link BiConsumer} to set the id of the object.
     * @param <R>    Type to make restrict the {@param get} and {@param set} to the same type.
     */
    protected <R> void addField(String dbName, Function<T, R> get, BiConsumer<T, R> set) {
        fields.add(new DBField<>(dbName, get, set));
    }

    /**
     * Save the {@param object}.
     * If ID is {@code null} or equal to the default id value (if not set also {@code null}) it will be inserted.
     * Otherwise the existing record with the same id will be updated.
     *
     * @param object The object to be saved.
     * @return Whether the object has been saved or not.
     */
    public boolean save(T object) {
        return object.getId() == null || object.getId() == idDefault ? saveNew(object) : saveExisting(object);
    }

    /**
     * Load the object with the given id.
     *
     * @param idValue The id of the object that has to be loaded.
     * @return The loaded object.
     */
    public T load(I idValue) {
        return loadWhere(idField.dbName + " = ?", idValue);
    }

    /**
     * Load from the table based on a where statement.
     *
     * @param sqlWhere The where statement to use (without 'where').
     * @return The loaded object.
     */
    public T loadWhere(String sqlWhere) {
        return loadWhere(sqlWhere, new ArrayList<>());
    }

    /**
     * Load from the table based on a where statement.
     *
     * @param sqlWhere        The where statement to use (without 'where').
     * @param preparedObjects The objects to inject.
     * @return The loaded object.
     */
    public T loadWhere(String sqlWhere, Object... preparedObjects) {
        return loadWhere(sqlWhere, Arrays.asList(preparedObjects));
    }

    /**
     * Load from the table based on a where statement.
     *
     * @param sqlWhere        The where statement to use (without 'where').
     * @param preparedObjects The objects to inject.
     * @return The loaded object.
     */
    public T loadWhere(String sqlWhere, List<Object> preparedObjects) {
        T instance = null;
        try {
            instance = create.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = "SELECT * FROM " + tableName + " where " + sqlWhere;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < preparedObjects.size(); i++) {
                statement.setObject(i + 1, preparedObjects.get(i));
            }

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                setValues(instance, resultSet);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return instance;
    }

    /**
     * Load all objects out of the table.
     *
     * @return All the objects in the table.
     */
    public List<T> loadList() {
        List<T> list = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                T instance = createNew();
                setValues(instance, resultSet);
                list.add(instance);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;
    }

    /**
     * Load a list of objects based on the where statement.
     *
     * @param sqlWhere The where statement (without 'where').
     * @return The list of loaded objects.
     */
    public List<T> loadListWhere(String sqlWhere) {
        return loadListWhere(sqlWhere, new ArrayList<>());
    }

    /**
     * Load a list of objects based on the where statement.
     *
     * @param sqlWhere        The where statement (without 'where').
     * @param preparedObjects The objects to inject.
     * @return The list of loaded objects.
     */
    public List<T> loadListWhere(String sqlWhere, Object... preparedObjects) {
        return loadListWhere(sqlWhere, Arrays.asList(preparedObjects));
    }

    /**
     * Load a list of objects based on the where statement.
     *
     * @param sqlWhere        The where statement (without 'where').
     * @param preparedObjects The objects to inject.
     * @return The list of loaded objects.
     */
    public List<T> loadListWhere(String sqlWhere, List<Object> preparedObjects) {
        List<T> list = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " where " + sqlWhere;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < preparedObjects.size(); i++) {
                statement.setObject(i + 1, preparedObjects.get(i));
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                T instance = createNew();
                setValues(instance, resultSet);
                list.add(instance);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;
    }

    /**
     * Delete from the table based on the given id.
     *
     * @param idValue The id of the record that must be deleted.
     * @return Whether anything has been deleted.
     */
    public boolean delete(I idValue) {
        return deleteWhere(idField.dbName + " = ?", idValue);
    }

    /**
     * Delete from the table based on a where statement.
     *
     * @param sqlWhere The where statement to use (without 'where').
     * @return Whether anything has been deleted.
     */
    public boolean deleteWhere(String sqlWhere) {
        return deleteWhere(sqlWhere, new ArrayList<>());
    }

    /**
     * Delete from the table based on a where statement.
     *
     * @param sqlWhere        The where statement to use (without 'where').
     * @param preparedObjects The objects to inject.
     * @return Whether anything has been deleted.
     */
    public boolean deleteWhere(String sqlWhere, Object... preparedObjects) {
        return deleteWhere(sqlWhere, Arrays.asList(preparedObjects));
    }

    /**
     * Delete from the table based on a where statement.
     *
     * @param sqlWhere        The where statement to use (without 'where').
     * @param preparedObjects The objects to inject.
     * @return Whether anything has been deleted.
     */
    public boolean deleteWhere(String sqlWhere, List<Object> preparedObjects) {
        String sql = "DELETE FROM " + tableName + " where " + sqlWhere;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < preparedObjects.size(); i++) {
                statement.setObject(i + 1, preparedObjects.get(i));
            }

            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    private boolean saveNew(T object) {
        String sql = saveNewQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            for (int i = 1; i <= fields.size(); i++) {
                statement.setObject(i, fields.get(i - 1).getValue(object));
            }

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    object.setId((I) generatedKeys.getObject(idField.dbName));
                } else {
                    throw new SQLException("Row creation failed, no ID obtained.");
                }
            }

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    private boolean saveExisting(T object) {
        String sql = saveExistingQuery();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            for (int i = 1; i <= fields.size(); i++) {
                statement.setObject(i, fields.get(i - 1).getValue(object));
            }

            statement.setObject(fields.size() + 1, idField.getValue(object));
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    private <R extends T> void setValues(R instance, ResultSet resultSet) throws SQLException {
        for (DBField<T, ?> field : this.fields) {
            field.setValue(instance, convertValue(resultSet.getObject(field.dbName)));
        }
        idField.setValue(instance, convertValue(resultSet.getObject(idField.dbName)));
    }

    private Object convertValue(Object value) {
        if (value instanceof BigDecimal bigDecimal) {
            return bigDecimal.doubleValue();
        }

        if (value instanceof BigInteger bigInteger) {
            return bigInteger.longValue();
        }

        return value;
    }

    private T createNew() {
        try {
            return create.call();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String saveNewQuery() {
        if (saveNewQuery != null) {
            return saveNewQuery;
        }

        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName);
        StringBuilder names = new StringBuilder();
        StringBuilder preps = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            names.append(fields.get(i).dbName);
            preps.append("?");
            if (i < fields.size() - 1) {
                names.append(", ");
                preps.append(", ");
            }
        }
        sql.append(" (").append(names).append(")");
        sql.append(" VALUES");
        sql.append(" (").append(preps).append(")");

        saveNewQuery = sql.toString();
        return saveNewQuery;
    }

    private String saveExistingQuery() {
        if (saveExistingQuery != null) {
            return saveExistingQuery;
        }

        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");

        for (int i = 0; i < fields.size(); i++) {
            sql.append(fields.get(i).dbName).append(" = ?");
            if (i < fields.size() - 1) {
                sql.append(", ");
            }
        }

        sql.append(" where id = ?");

        saveExistingQuery = sql.toString();
        return saveExistingQuery;
    }


    private static final class DBField<T, R> {
        private final String dbName;
        private final Function<T, R> get;
        private final BiConsumer<T, R> set;

        public DBField(String dbName, Function<T, R> get, BiConsumer<T, R> set) {
            this.dbName = dbName;
            this.get = get;
            this.set = set;
        }

        public R getValue(T object) {
            return get.apply(object);
        }

        @SuppressWarnings("unchecked")
        public void setValue(T object, Object value) {
            set.accept(object, (R) value);
        }

        public String dbName() {
            return dbName;
        }

        public Function<T, R> get() {
            return get;
        }

        public BiConsumer<T, R> set() {
            return set;
        }

        @SuppressWarnings("rawtypes")
        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (DBField) obj;
            return Objects.equals(this.dbName, that.dbName) &&
                    Objects.equals(this.get, that.get) &&
                    Objects.equals(this.set, that.set);
        }

        @Override
        public int hashCode() {
            return Objects.hash(dbName, get, set);
        }

        @Override
        public String toString() {
            return "DBField[" +
                    "dbName=" + dbName + ", " +
                    "get=" + get + ", " +
                    "set=" + set + ']';
        }
    }
}
