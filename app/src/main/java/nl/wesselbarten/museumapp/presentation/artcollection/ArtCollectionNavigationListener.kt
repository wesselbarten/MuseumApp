package nl.wesselbarten.museumapp.presentation.artcollection

import nl.wesselbarten.museumapp.domain.model.ArtObject

interface ArtCollectionNavigationListener {

    fun showArtList()

    fun showArtDetail(artObject: ArtObject)
}