package com.vifrn.mygamesreviews.suggestions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vifrn.mygamesreviews.databinding.GameListItemBinding
import com.vifrn.mygamesreviews.model.Game

class GameListAdapter (val clickListener: GameClickListener) : ListAdapter<Game, GameListAdapter.ViewHolder> (GameDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))

    }

    class ViewHolder (val binding : GameListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: GameClickListener, item : Game) {
            binding.game = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GameListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }
}

class GameDiffCallback : DiffUtil.ItemCallback<Game>() {
    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.id == newItem.id
    }
}

class GameClickListener(val clickListener : (game: Game) -> Unit) {
    fun onClick(game : Game) = clickListener(game)
}