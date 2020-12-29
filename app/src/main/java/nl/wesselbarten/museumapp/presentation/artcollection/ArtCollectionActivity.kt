package nl.wesselbarten.museumapp.presentation.artcollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import nl.wesselbarten.museumapp.R
import nl.wesselbarten.museumapp.databinding.ActivityArtCollectionBinding
import nl.wesselbarten.museumapp.domain.model.ArtObject
import nl.wesselbarten.museumapp.presentation.artcollection.detail.ArtDetailFragment
import nl.wesselbarten.museumapp.presentation.artcollection.list.ArtListFragment
import nl.wesselbarten.museumapp.presentation.viewmodel.ArtCollectionViewModel
import kotlin.reflect.KClass

@AndroidEntryPoint
class ArtCollectionActivity : AppCompatActivity(), ArtCollectionNavigationListener {

    private val viewModel: ArtCollectionViewModel by viewModels()

    private lateinit var binding: ActivityArtCollectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_art_collection)

        showArtList()
    }

    override fun showArtList() {
        showFragment(ArtListFragment::class, replace = true)
    }

    override fun showArtDetail(artObject: ArtObject) {
        viewModel.showArtObjectDetails(artObject.objectNumber)
        showFragment(ArtDetailFragment::class)
    }

    private fun <T : Fragment> showFragment(fragmentClass: KClass<T>, replace: Boolean = false) {
        supportFragmentManager.beginTransaction()
            .apply {
                if (replace) {
                    replace(binding.fragmentContainerArtCollection.id, fragmentClass.java, null, fragmentClass::class.simpleName)
                } else {
                    add(binding.fragmentContainerArtCollection.id, fragmentClass.java, null, fragmentClass::class.simpleName)
                    addToBackStack(null)
                }
            }
            .commit()
    }
}