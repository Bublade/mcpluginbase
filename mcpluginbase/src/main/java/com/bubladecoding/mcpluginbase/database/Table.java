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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class Table<T extends IDed<I>, I> {

    private final PluginBase pluginBase;
    private final Connection connection;
    private final String tableName;
    private final Class<? extends T> objectClass;

    public Table(PluginBase pluginBase, Connection connection, String tableName, Class<T> objectClass) {
        this(pluginBase, connection, tableName, objectClass, null);
    }

    public Table(PluginBase pluginBase, Connection connection, String tableName, Class<? extends T> objectClass, I idDefault) {
        this.pluginBase = pluginBase;
        this.connection = connection;
        this.tableName = tableName;
        this.objectClass = objectClass;
        setIdField(T::getId, T::setId, idDefault);
    }

    protected List<DBField<T, ?>> fields = new ArrayList<>();
    protected DBField<T, I> idField;
    protected I idDefault;

    protected void setIdField(Function<T, I> get, BiConsumer<T, I> set, I idDefault) {
        this.idDefault = idDefault;
        idField = new DBField<>("id", get, set);
    }

    protected <R> void addField(String dbName, Function<T, R> get, BiConsumer<T, R> set) {
        fields.add(new DBField<>(dbName, get, set));
    }

    public boolean save(T object) {
        return object.getId() == null || object.getId() == idDefault ? saveNew(object) : saveExisting(object);
    }

    private boolean saveNew(T object) {
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

        try (PreparedStatement statement = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS)) {

            for (int i = 1; i <= fields.size(); i++) {
                statement.setObject(i, fields.get(i - 1).getValue(object));
            }

            System.out.println(statement);
            statement.executeQuery();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    object.setId((I) generatedKeys.getObject(idField.dbName));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    private boolean saveExisting(T object) {
        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");

        for (int i = 0; i < fields.size(); i++) {
            sql.append(fields.get(i).dbName).append(" = ?");
            if (i < fields.size() - 1) {
                sql.append(", ");
            }
        }

        sql.append(" where id = ?");

        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            for (int i = 1; i <= fields.size(); i++) {
                statement.setObject(i, fields.get(i - 1).getValue(object));
            }

            statement.setObject(fields.size() + 1, idField.getValue(object));

            System.out.println(statement);
            statement.executeQuery();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public <R extends T> R load(I idValue) {
        R instance;
        try {
            Constructor<R> constructor = (Constructor<R>) objectClass.getConstructor();
            instance = constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }

        String sql = "SELECT * FROM " + tableName + " where " + idField.dbName + " = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, idValue);
            System.out.println(statement);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                for (DBField<T, ?> field : this.fields) {
                    field.setValue(instance, resultSet.getObject(field.dbName));
                }
                idField.setValue(instance, resultSet.getObject(idField.dbName));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return instance;
    }

    public <R extends T> R loadRaw(String sqlWhere, Object... preparedObjects) {
        R instance = pluginBase.createInjectedClass((Class<R>) objectClass);

        String sql = "SELECT * FROM " + tableName + " where " + sqlWhere;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < preparedObjects.length; i++) {
                statement.setObject(i + 1, preparedObjects[i]);
            }

            System.out.println(statement);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                for (DBField<T, ?> field : this.fields) {
                    field.setValue(instance, resultSet.getObject(field.dbName));
                }

                idField.setValue(instance, resultSet.getObject(idField.dbName));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return instance;
    }

    public static final class DBField<T, R> {
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
