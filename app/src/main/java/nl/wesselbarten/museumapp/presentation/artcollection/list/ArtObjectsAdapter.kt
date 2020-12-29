package nl.wesselbarten.museumapp.presentation.artcollection.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import nl.wesselbarten.museumapp.databinding.ItemArtObjectBinding
import nl.wesselbarten.museumapp.domain.model.ArtObject

class ArtObjectsAdapter(
    private val onClickListener: OnClickListener
) : ListAdapter<ArtObject, ArtObjectsAdapter.ArtObjectViewHolder>(Differ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtObjectViewHolder {
        return ArtObjectViewHolder(
            ItemArtObjectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArtObjectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ArtObjectViewHolder(
        private val itemArtObjectBinding: ItemArtObjectBinding
    ) : RecyclerView.ViewHolder(itemArtObjectBinding.root) {

        fun bind(artObject: ArtObject) {
            itemArtObjectBinding.artObject = artObject
            itemArtObjectBinding.root.setOnClickListener {
                onClickListener.onArtObjectClick(artObject)
            }

            artObject.webImage?.let { image ->
                Glide.with(itemView)
                    .load(image.url)
                    .thumbnail(0.25f)
                    .into(itemArtObjectBinding.ivArtObjectImage)
            }
        }
    }

    object Differ : DiffUtil.ItemCallback<ArtObject>() {

        override fun areItemsTheSame(oldItem: ArtObject, newItem: ArtObject): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.objectNumber == newItem.objectNumber
        }

        override fun areContentsTheSame(oldItem: ArtObject, newItem: ArtObject): Boolean {
            return oldItem.links == newItem.links &&
                    oldItem.title == newItem.title &&
                    oldItem.hasImage == newItem.hasImage &&
                    oldItem.principalOrFirstMaker == newItem.principalOrFirstMaker &&
                    oldItem.longTitle == newItem.longTitle &&
                    oldItem.showImage == newItem.showImage &&
                    oldItem.permitDownload == newItem.permitDownload &&
                    oldItem.webImage == newItem.webImage &&
                    oldItem.headerImage == newItem.headerImage &&
                    oldItem.productionPlaces.size == newItem.productionPlaces.size
        }
    }

    interface OnClickListener {
        fun onArtObjectClick(artObject: ArtObject)
    }
}