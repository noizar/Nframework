package id.co.nlab.nframework.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.lang.RuntimeException

/** Custom Adapter for RecyclerViewAdapter*/
abstract  class RecyclerViewAdapter <DataClass,ViewHolder : RecyclerView.ViewHolder>
constructor(val mLayout:Int,val mViewHolderClass: Class<ViewHolder>,val mDataClass: Class<DataClass>,
            val mData:List<DataClass>): RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(mLayout,parent,false)

        return try {
            var constructor= mViewHolderClass.getConstructor(View::class.java)
            constructor.newInstance(view)
        }catch (error:Exception){
            throw RuntimeException(error)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = getItem(position)
        bindView(holder,model,position)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    abstract fun bindView( holder:ViewHolder,model:DataClass,position:Int)
    private fun getItem(position:Int):DataClass{ return mData.get(position)}
}