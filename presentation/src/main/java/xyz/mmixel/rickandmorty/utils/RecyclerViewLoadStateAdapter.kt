package xyz.mmixel.rickandmorty.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.mmixel.rickandmorty.databinding.ProgressBarViewBinding

class RecyclerViewLoadStateAdapter :
    LoadStateAdapter<RecyclerViewLoadStateAdapter.ProgressViewHolder>() {

    override fun onBindViewHolder(holder: ProgressViewHolder, loadState: LoadState) {}

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ProgressViewHolder {
        return ProgressViewHolder(
            ProgressBarViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class ProgressViewHolder(private val binding: ProgressBarViewBinding) :
        RecyclerView.ViewHolder(binding.root)
}