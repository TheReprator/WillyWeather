package reprator.willyWeather.cityList.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import reprator.willyWeather.base_android.util.GeneralDiffUtil
import reprator.willyWeather.cityList.databinding.RowWeatherDataBinding
import reprator.willyWeather.cityList.modals.LocationModal

class CityListAdapter @AssistedInject constructor(
    @Assisted val cityItemClickListener: CityItemClickListener
) :
    ListAdapter<LocationModal, VHCityItem>(GeneralDiffUtil<LocationModal>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHCityItem {
        val binding = RowWeatherDataBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return VHCityItem(binding)
    }

    override fun onBindViewHolder(holder: VHCityItem, position: Int) {
        holder.binding.locationModal = getItem(position)
        holder.binding.clickHandler = cityItemClickListener
        holder.binding.executePendingBindings()
    }
}

class VHCityItem(val binding: RowWeatherDataBinding) :
    RecyclerView.ViewHolder(binding.root)

@AssistedFactory
interface CityListAdapterFactory {
    fun create(cityItemClickListener: CityItemClickListener): CityListAdapter
}
