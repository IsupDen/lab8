package gui.filter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TableFilter <T>{

    private final TableView<T> table;
    private final ObservableList<T> list;
    private final Map<TableColumn<T,?>, FilterArg> columnsFilters;
    public TableFilter(TableView<T> tab, ObservableList<T> l){
        table = tab;
        list = l;
        columnsFilters = new HashMap<>();
    }
    public TableFilter<T> addFilter(TableColumn<T,?> col, Converter<T> converter){

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setAutoHide(true);
        contextMenu.setHideOnEscape(true);
        contextMenu.setStyle("-fx-max-height: 300;");


        TextField filter = new TextField();
        CustomMenuItem filterItem = new CustomMenuItem(filter);
        filterItem.setHideOnClick(false);

        MenuItem ok = new MenuItem();
        ok.setText("OK");


        ok.setOnAction((event)->{
            String condition = filter.getText();

            if(condition!=null&&!condition.equals("")) {
                columnsFilters.put(col, new FilterArg(condition,converter));

                updateFilters();
                col.getStyleClass().add("filtered");
            }
        });

        MenuItem reset = new MenuItem();
        reset.setText("reset");

        reset.setOnAction(event-> resetFilter(col));


        contextMenu.getItems().addAll(filterItem,ok,reset);
        col.setContextMenu(contextMenu);
        return this;
    }
    public void resetFilter(TableColumn<T,?> col){
        col.getStyleClass().remove("filtered");
        columnsFilters.remove(col);
        updateFilters();
    }
    public void updateFilters(){
        Stream<T> stream = list.stream();
        for (Map.Entry<TableColumn<T,?>, FilterArg> entry : columnsFilters.entrySet()) {
            TableColumn<T,?> col = entry.getKey();
            String condition = entry.getValue().condition;
            Converter<T> converter = entry.getValue().converter;
            if(condition!=null&&!condition.equals("")) {
                Pattern pattern = Pattern.compile(condition, Pattern.CASE_INSENSITIVE);

                stream = stream.filter(p -> pattern.matcher(converter.convert(p)!=null? converter.convert(p):"").find());
                col.getStyleClass().add("filtered");
            }

        }
        ObservableList<T> filteredList = stream.collect(Collectors.toCollection(FXCollections::observableArrayList));
        table.setItems(filteredList);
    }

    private class FilterArg{
        public final String condition;
        public final Converter<T> converter;
        public FilterArg(String cond, Converter<T> conv){
            converter = conv;
            condition = cond;
        }
    }
}

