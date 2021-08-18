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
package com.bubladecoding.developertools.database.user;

import com.bubladecoding.developertools.permissions.interfaces.IUser;
import com.bubladecoding.mcpluginbase.PluginBase;
import com.bubladecoding.mcpluginbase.database.Table;

import java.sql.Connection;
import java.util.UUID;

public class UsersTable extends Table<IUser, Long> {

    public UsersTable(PluginBase pluginBase, Connection connection) {
        super(pluginBase, connection, "users", User.class, null);
        addField("uuid", user -> user.getUniqueId().toString(), (user, uuid) -> user.loadPlayer(UUID.fromString(uuid)));
        addField("note", IUser::getNote, IUser::setNote);
    }
}
