package Pedro.Paulo.Halisson.Ricardo.ChefEmCasa.Model;

/*
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import kotlinx.coroutines.CoroutineScope;


public class MainViewModel extends AndroidViewModel {
    int navigationOpSelected = R.id.gridViewOp;
    LiveData<PagingData<ImageData>> pageLv;


    public MainViewModel(@NonNull Application application) {
        super(application);
        GalleryRepository galleryRepository = new GalleryRepository(application);
        GalleryPagingSource galleryPagingSource = new GalleryPagingSource(galleryRepository);
        Pager<Integer, ImageData> pager = new Pager(new PagingConfig(10), () -> galleryPagingSource);
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        pageLv = PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);
    }


    public int getNavigationOpSelected() {
        return navigationOpSelected;
    }
    public void setNavigationOpSelected(int navigationOpSelected) {
        this.navigationOpSelected = navigationOpSelected;
    }
    public LiveData<PagingData<ImageData>> getPageLv() {
        return pageLv;
    }
}
*/