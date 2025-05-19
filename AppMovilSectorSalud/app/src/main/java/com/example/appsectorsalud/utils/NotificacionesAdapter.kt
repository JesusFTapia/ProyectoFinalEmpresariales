package com.example.appsectorsalud

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appsectorsalud.data.Notificacion
import com.example.appsectorsalud.databinding.ItemNotificationBinding

class NotificacionesAdapter(
    private val items: MutableList<Notificacion>,
    private val onAccept: (Notificacion) -> Unit,
    private val onReject: (Notificacion) -> Unit
) : RecyclerView.Adapter<NotificacionesAdapter.VH>() {

    inner class VH(val bind: ItemNotificationBinding) : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemNotificationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = with(holder.bind) {
        val n = items[position]
        val doctor = if (n.nombre.isNotBlank()) n.nombre else "Cédula ${n.cedulaProfesional}"
        tvNotificationMessage.text =
            "$doctor solicita acceso a tu expediente. ${n.fechaLegible()}"

        btnAccept.setOnClickListener { onAccept(n) }
        btnReject.setOnClickListener { onReject(n) }
    }

    override fun getItemCount() = items.size
}
