package reprator.willyWeather

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.scopes.ViewModelScoped
import timber.log.Timber


class TestAssist @AssistedInject constructor(
    @Assisted val cityItemClickListener: String
) {
    fun test() {
        Timber.e("vikram Call $cityItemClickListener")
    }
}


@AssistedFactory
interface TestAssistRandomFactory {
    fun create(cityItemClickListener: String): TestAssist
}
