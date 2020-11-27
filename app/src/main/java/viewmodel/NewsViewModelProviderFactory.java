package viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class NewsViewModelProviderFactory implements ViewModelProvider.Factory {

    private NewsRepository newsRepository;

    public NewsViewModelProviderFactory(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NewsViewModel(newsRepository);
    }
}
