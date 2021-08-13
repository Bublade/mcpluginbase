package com.bubladecoding.mcpluginbase.database;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

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
class TableTest {

//    @Test
    void save() {
        Table<TestC, Integer> testTableTable = new TestTable();

        testTableTable.save(new TestC(null, "test1", false));
    }

//    @Test
    void save2() {
        Table<TestC, Integer> testTableTable = new TestTable();

        testTableTable.save(new TestC(1, "test2", true));
    }

    class TestTable extends Table<TestC, Integer> {

        public TestTable() {
            super(null, null, "test", TestC.class);
            addField("name", TestC::getName, TestC::setName);
            addField("smth", TestC::isSmth, TestC::setSmth);
        }
    }


    class TestC implements IDed<Integer> {

        private Integer id;
        private String name;
        private boolean smth;

        public TestC(Integer id, String name, boolean smth) {
            this.id = id;
            this.name = name;
            this.smth = smth;
        }

        @Override
        public Integer getId() {
            return id;
        }

        @Override
        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSmth() {
            return smth;
        }

        public void setSmth(boolean smth) {
            this.smth = smth;
        }
    }
}
