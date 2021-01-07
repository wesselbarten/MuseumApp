package nl.wesselbarten.museumapp.presentation.artcollection.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import nl.wesselbarten.museumapp.databinding.FragmentArtDetailBinding
import nl.wesselbarten.museumapp.presentation.viewmodel.ArtCollectionViewModel
import nl.wesselbarten.museumapp.presentation.viewmodel.state.GetArtObjectByObjectNumberState

@AndroidEntryPoint
class ArtDetailFragment : Fragment() {

    private val viewModel: ArtCollectionViewModel by viewModels({ requireActivity() })

    private lateinit var binding: FragmentArtDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArtDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.layoutToolbar.btnBack.setOnClickListener { activity?.onBackPressed() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUi()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Glide.with(this)
            .clear(binding.ivArtObjectImage)
    }

    private fun subscribeUi() {
        viewModel.getArtObjectByObjectNumberState.observe(viewLifecycleOwner, { state ->
            binding.pbLoadingItem.visibility = View.GONE
            binding.layoutError.visibility = View.GONE

            when (state) {
                is GetArtObjectByObjectNumberState.Finished -> {
                    binding.artObject = state.artObject

                    state.artObject.webImage?.let { image ->
                        Glide.with(this)
                            .load(image.url)
                            .into(binding.ivArtObjectImage)
                    }
                }
                is GetArtObjectByObjectNumberState.Error -> {
                    binding.layoutError.visibility = View.VISIBLE
                    binding.tvError.text = state.errorMessage
                    binding.btnRetry.setOnClickListener {
                        viewModel.retryGetArtObjectByObjectNumber()
                    }
                }
                is GetArtObjectByObjectNumberState.Loading -> {
                    binding.pbLoadingItem.visibility = View.VISIBLE
                }
            }
        })
    }
}