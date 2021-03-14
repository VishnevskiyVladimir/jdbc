package com.vishdev.jdbc.starter;

import com.vishdev.jdbc.starter.util.PropertiesUtil;
import org.intellij.lang.annotations.Language;

public interface Queries {
    String SQL_DIALECT = "PostgreSQL";

    /*================== DDL operations=======================  */

    @Language(SQL_DIALECT)
    String CREATE_TABLE_INFO = """
            CREATE TABLE IF NOT EXISTS info (
            id SERIAL PRIMARY KEY ,
            data TEXT NOT NULL );
                        """;


    /*================== DML (UPDATE, INSERT, DELETE)=======================  */
    @Language(SQL_DIALECT)
    String INSERT_1_RECORD_EXAMPLE = """
               INSERT INTO info (data)
               VALUES
               ('TEST1');
            """;
    @Language(SQL_DIALECT)
    String INSERT_EXAMPLE = """
               INSERT INTO info (data)
               VALUES
               ('TEST1'),
               ('TEST2'),
               ('TEST3'),
               ('TEST4');
            """;
    @Language(SQL_DIALECT)
    String UPDATE_EXAMPLE = """
               UPDATE info 
               SET data = 'testTest'
               WHERE id = 3;
            """;
    /*================== DML (SELECT)=======================  */
    @Language(SQL_DIALECT)
    String SELECT_EXAMPLE = """
            SELECT *
            FROM info
            ORDER BY id;
            """;


}
