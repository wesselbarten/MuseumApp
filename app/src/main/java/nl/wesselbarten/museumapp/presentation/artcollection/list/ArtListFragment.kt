package nl.wesselbarten.museumapp.presentation.artcollection.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import nl.wesselbarten.museumapp.databinding.FragmentArtListBinding
import nl.wesselbarten.museumapp.domain.model.ArtObject
import nl.wesselbarten.museumapp.presentation.artcollection.ArtCollectionNavigationListener
import nl.wesselbarten.museumapp.presentation.viewmodel.ArtCollectionViewModel
import nl.wesselbarten.museumapp.presentation.viewmodel.action.FetchNextArtObjectsPageAction
import nl.wesselbarten.museumapp.presentation.viewmodel.action.RefreshArtObjectsAction
import nl.wesselbarten.museumapp.presentation.viewmodel.state.GetArtObjectsState
import nl.wesselbarten.museumapp.util.event.EventObserver
import nl.wesselbarten.museumapp.util.pagination.PaginationOnScrollListener
import nl.wesselbarten.museumapp.util.recyclerview.DividerItemDecoration

@AndroidEntryPoint
class ArtListFragment : Fragment(), ArtObjectsAdapter.OnClickListener {

    private val viewModel: ArtCollectionViewModel by viewModels({ requireActivity() })
    private val artObjectsAdapter: ArtObjectsAdapter = ArtObjectsAdapter(this)

    private lateinit var binding: FragmentArtListBinding
    private lateinit var navigationListener: ArtCollectionNavigationListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            navigationListener = context as ArtCollectionNavigationListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                "Context must implement ${ArtCollectionNavigationListener::class.simpleName}"
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getArtObjects()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArtListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.rcvArtCollection.apply {
            adapter = artObjectsAdapter
            addItemDecoration(DividerItemDecoration(requireContext()))
            addOnScrollListener(PaginationOnScrollListener(
                getNextPage = { viewModel.getNextPage() }
            ) { viewModel.fetchNextArtObjectsPageAction.value?.peekContent() is FetchNextArtObjectsPageAction.Loading })
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshArtObjects()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUi()
    }

    override fun onArtObjectClick(artObject: ArtObject) {
        navigationListener.showArtDetail(artObject)
    }

    private fun subscribeUi() {
        viewModel.getArtObjectsState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is GetArtObjectsState.Loading -> {
                    binding.pbLoadingItems.visibility = View.VISIBLE
                }
                is GetArtObjectsState.Success -> {
                    artObjectsAdapter.submitList(state.items)
                    binding.pbLoadingItems.visibility = View.GONE
                }
                is GetArtObjectsState.Error -> {
                    binding.pbLoadingItems.visibility = View.GONE
                    showErrorMessage(state.errorMessage)
                }
            }
        })

        viewModel.refreshArtObjectsAction.observe(viewLifecycleOwner, EventObserver { action ->
            binding.swipeRefresh.isRefreshing = false
            if (action is RefreshArtObjectsAction.Error) {
                showErrorMessage(action.errorMessage)
            }
        })

        viewModel.fetchNextArtObjectsPageAction.observe(
            viewLifecycleOwner,
            EventObserver { action ->
                binding.pbLoadingMoreItems.visibility =
                    if (action is FetchNextArtObjectsPageAction.Loading) View.VISIBLE else View.GONE

                if (action is FetchNextArtObjectsPageAction.Error) {
                    showErrorMessage(action.errorMessage)
                }
            })
    }

    private fun showErrorMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}