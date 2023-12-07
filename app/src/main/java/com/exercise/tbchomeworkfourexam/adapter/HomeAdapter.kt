package com.exercise.tbchomeworkfourexam.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.exercise.tbchomeworkfourexam.databinding.FileTypeLayoutBinding
import com.exercise.tbchomeworkfourexam.databinding.TextTypeLayoutBinding
import com.exercise.tbchomeworkfourexam.databinding.VoiceTypeLayoutBinding
import com.exercise.tbchomeworkfourexam.model.UserData

class HomeAdapter :
    ListAdapter<UserData, RecyclerView.ViewHolder>(ItemDiffCallBack()) {

    companion object {
        private const val VIEW_TYPE_TEXT = 0
        private const val VIEW_TYPE_FILE = 1
        private const val VIEW_TYPE_VOICE = 2
    }

    class ItemDiffCallBack : DiffUtil.ItemCallback<UserData>() {
        override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_TEXT -> {
                val binding = TextTypeLayoutBinding.inflate(inflater, parent, false)
                TextTypeViewHolder(binding)
            }
            VIEW_TYPE_FILE -> {
                val binding = FileTypeLayoutBinding.inflate(inflater, parent, false)
                FileTypeViewHolder(binding)
            }
            VIEW_TYPE_VOICE -> {
                val binding = VoiceTypeLayoutBinding.inflate(inflater, parent, false)
                VoiceTypeViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = getItem(position)
        when (holder) {
            is TextTypeViewHolder -> holder.bind(model)
            is FileTypeViewHolder -> holder.bind(model)
            is VoiceTypeViewHolder -> holder.bind(model)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val model = getItem(position)
        return when (model.initial) {
            UserData.Type.TEXT -> VIEW_TYPE_TEXT
            UserData.Type.FILE -> VIEW_TYPE_FILE
            UserData.Type.VOICE -> VIEW_TYPE_VOICE
        }
    }

    inner class TextTypeViewHolder(private var binding: TextTypeLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserData) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(item.image)
                    .into(ivImage)
                tvNameSurname.text = item.owner
                tvTextMassage.text = item.last_message
                time.text = item.last_active
                unreadMessages.text = item.unread_messages.toString()
            }
        }
    }

    inner class FileTypeViewHolder(private var binding: FileTypeLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserData) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(item.image)
                    .into(ivImage)
                tvNameSurname.text = item.owner
                fileText.text = item.last_message
                time.text = item.last_active
                unreadMessages.text = item.unread_messages.toString()
            }
        }
    }

    inner class VoiceTypeViewHolder(private var binding: VoiceTypeLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserData) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(item.image)
                    .into(ivImage)
                tvNameSurname.text = item.owner
                voiceText.text = item.last_message
                time.text = item.last_active
                unreadMessages.text = item.unread_messages.toString()
            }
        }
    }
}
