package db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import model.Article;

@Dao
public interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Article article);

    @Query("SELECT * FROM articles")
    LiveData<List<Article>> getAllArticles();

    @Delete
    void delete(Article article);
}
