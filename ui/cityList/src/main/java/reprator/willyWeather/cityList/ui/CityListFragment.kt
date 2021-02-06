package reprator.willyWeather.cityList.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import reprator.willyWeather.base_android.util.ItemOffsetDecoration
import reprator.willyWeather.cityList.CityListViewModal
import reprator.willyWeather.cityList.R
import reprator.willyWeather.cityList.databinding.FragmentCitylistBinding
import reprator.willyWeather.cityList.modals.LocationModal
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class CityListFragment : Fragment(R.layout.fragment_citylist), CityItemClickListener {

    private var _binding: FragmentCitylistBinding? = null
    private val binding get() = _binding!!

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    /*@Inject
    lateinit var cityListAdapter: CityListAdapter*/

     @Inject
     lateinit var cityListAdapterFactory: CityListAdapterFactory

     private val cityListAdapter: CityListAdapter by lazy {
         cityListAdapterFactory.create(this)
     }

    private val viewModel: CityListViewModal by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCitylistBinding.bind(view).also {
            it.cityAdapter = cityListAdapter
            it.cityListViewModal = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }

        setUpRecyclerView()
        initializeObserver()
    }

    private fun setUpRecyclerView() {
        with(binding.cityListForecastRec) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            addItemDecoration(
                ItemOffsetDecoration(requireContext(), R.dimen.list_item_padding)
            )
        }
    }

    private fun initializeObserver() {
        viewModel._foreCastWeatherList.observe(viewLifecycleOwner) {
            cityListAdapter.submitList(it)
        }
    }

    override fun itemClicked(locationModal: LocationModal) {
        Timber.e("click: ${locationModal}")
        Timber.e("click detail: ${locationModal.maxTemperature}")

       /* val direction =
            CityListFragmentDirections.nnavigationHomeListToNavigationCityDetail(locationModal.id.toString())
        findNavController().navigate(direction)*/
    }

}