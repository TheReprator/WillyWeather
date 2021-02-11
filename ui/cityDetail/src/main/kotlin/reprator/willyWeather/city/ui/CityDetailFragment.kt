package reprator.willyWeather.city.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import reprator.willyWeather.base.util.isNull
import reprator.willyWeather.city.CityDetailViewModal
import reprator.willyWeather.city.R
import reprator.willyWeather.city.databinding.FragmentCityDetailBinding
import reprator.willyWeather.navigation.CityDetailNavigator
import javax.inject.Inject

@AndroidEntryPoint
class CityDetailFragment : Fragment(R.layout.fragment_city_detail) {

    private var _binding: FragmentCityDetailBinding? = null
    private val binding get() = _binding!!

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    val args: CityDetailFragmentArgs by navArgs()

    @Inject
    lateinit var cityDetailNavigator: CityDetailNavigator

    private val viewModel by viewModels<CityDetailViewModal>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCityDetailBinding.bind(view)

        setToolBar()
        setObserver()

        if (savedInstanceState.isNull())
            viewModel.getWeatherDetailUseCase()
    }

    private fun setToolBar() {

        binding.cityDetailToolBar.title = args.locationName

        binding.cityDetailToolBar.setOnClickListener {
            cityDetailNavigator.navigateToBack(findNavController())
        }
    }

    private fun setObserver() {
        viewModel.foreCastWeatherList.observe(viewLifecycleOwner, {
            binding.locationModal = it
        })
    }

}