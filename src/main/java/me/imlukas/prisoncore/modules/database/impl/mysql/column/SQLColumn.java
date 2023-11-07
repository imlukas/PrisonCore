package me.imlukas.prisoncore.modules.database.impl.mysql.column;

import lombok.Getter;
import me.imlukas.prisoncore.modules.database.impl.mysql.table.SQLTable;

@Getter
public class SQLColumn {

    private final SQLTable table;
    private final ColumnData data;

    public SQLColumn(SQLTable table, ColumnData data) {
        this.table = table;
        this.data = data;
    }
}