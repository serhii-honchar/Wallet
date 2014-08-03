package ua.kiev.sa;


import java.util.Arrays;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class SortFilterModel extends AbstractTableModel {

    SortFilterModel(TableModel p0) {
        model = p0;
        rows = new Row[model.getRowCount()];
        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Row();
            rows[i].index = i;
        }
    }

    public void sort(int c) {
        sortColumn = c;
        Arrays.sort(rows);
        fireTableDataChanged();
    }

    public Object getValueAt(int r, int c) {
        return model.getValueAt(rows[r].index, c);
    }

    public boolean isCellEditable(int r, int c) {
        return model.isCellEditable(rows[r].index, c);
    }

    public void setValueAt(Object aValue, int r, int c) {
        model.setValueAt(aValue, rows[r].index, c);
    }


    public int getRowCount() {
        return model.getRowCount();
    }

    public int getColumnCount() {
        return model.getColumnCount();
    }

    public String getColumnName(int c) {
        return model.getColumnName(c);
    }

    public Class getColumnClass(int c) {
        return model.getColumnClass(c);
    }


    private class Row implements Comparable<Row> {
        public int index;

        public int compareTo(Row other) {
            Object a = model.getValueAt(index, sortColumn);
            Object b = model.getValueAt(other.index, sortColumn);
            if (a instanceof Comparable)
                return ((Comparable)a).compareTo(b);
            else
                return a.toString().compareTo(b.toString());

        }

    }

    private TableModel model;
    private int sortColumn;
    private Row[] rows;

}
