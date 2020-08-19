package com.example.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text


/*
1. Movie Data Class 만들기
2. 영화 정보를 담은 ArrayList 만들기
3. RecyclerView Adapter 만들기
 */


data class Movie(
    val title : String,//영화제목
    val popularity : Double,//인기도
    val description : String,// 상영
    val openDate : String,// 개봉일
    val posterUrl : Int// 포스터 Url <- Int : drawble에 있는 이미지를 가져다 쓰기 위함
)

//RecyclerView Adapter
class MovieAdapter(val context: Context, val movieList:ArrayList<Movie>) : RecyclerView.Adapter<MovieAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // 셀 레이아웃을 불러오는 역할
        val view = LayoutInflater.from(context).inflate(R.layout.cell, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        //셀 갯수를 설정하는 역할, 셀 갯수를 반환하는 함수
        return movieList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        //각 셀에 맞는 정보를 넣는 역할
        holder.bind(movieList[position])
    }

    //셀
   inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){

        //셀의 구성요소를 불러오는 역할
        val imgPoster = itemView.findViewById<ImageView>(R.id.imgPoster)
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvPopularity = itemView.findViewById<TextView>(R.id.tvPopularity)
        val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
        val tvOpenDate = itemView.findViewById<TextView>(R.id.tvOpenDate)
        val container = itemView.findViewById<ConstraintLayout>(R.id.container) //셀을 둘러싼 부분

        //데이터를 셀에 넣는 역할
        fun bind(movie:Movie){
            imgPoster.setImageResource(movie.posterUrl)
            tvTitle.text = movie.title
            tvPopularity.text = "인기도  : "+movie.popularity
            tvDescription.text = "설명 : "+movie.description
            tvOpenDate.text = "개봉일 : "+movie.openDate

            container.setOnClickListener{//셀을 클릭했을 때

                Toast.makeText(context, movie.title, Toast.LENGTH_SHORT).show() //영화 제목을 토스트 메세지로 띄움
            }


        }


    }
}