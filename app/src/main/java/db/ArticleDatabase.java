package db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import model.Article;

@Database(
        entities = {Article.class},
        version = 1
)
@TypeConverters(Converter.class)
public abstract class ArticleDatabase extends RoomDatabase {
    public abstract ArticleDao articleDao();

    private static ArticleDatabase instance = null;

    public static synchronized ArticleDatabase getDataBaseInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), ArticleDatabase.class, "article_databse")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
