package db;

import androidx.room.TypeConverter;

import model.Source;

public class Converter {
    @TypeConverter
    public String fromSource(Source source){
        return source.getName();
    }

    @TypeConverter
    public Source toSource(String name){
        Source source = new Source();
        source.setId(name);
        source.setName(name);
        return source;
    }
}
