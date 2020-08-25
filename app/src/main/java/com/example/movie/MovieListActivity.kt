package com.example.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_movie_list.*
import org.json.JSONException

/*
1. Movie Data Class 만들기
2. 영화 정보를 담은 ArrayList 만들기
3. RecyclerView Adapter 만들기
 */


/*
0. Manifest에 Internet 퍼미션 적용 + Volley, GSON, Glide 라이브러리를 프로젝트에 추가.
1. Volley 라이브러리를 이용해서 TMDB로부터 현재 상영 중인 영화 정보를 받아오기
2. GSON 라이브러리를 이용해서 JSON->Data Class로 변환
3. Glide 라이브러리를 이용해서 포스터 URL로부터 이미지를 받아서 ImageVie에 적용
 */

class MovieListActivity : AppCompatActivity() {

    //영화 정보를 담고 있는 ArrayList 만들기. -> 정보를 받아오면서 필요없게 되어 삭제

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        //Volley에 RequestQueue 생성하기
        var requestQueue: RequestQueue = Volley.newRequestQueue(this)

        //Gson 객제 선언
        var gson: Gson = Gson()

        //API 주소 선언
        val url =
            "https://api.themoviedb.org/3/movie/now_playing?" + "api_key=72fab48cf7adad01dfc48e7a1c39e242" + "&language=ko-KR" + "&region=KR"


        //API를 호출함

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener {//데이터가 정상적으로 호출됐을 때 처리하는 부분
                //받아온 json 데이터를 Toast message로 출력
                    response ->
                try { //response(영화 JSON 데이터)가 정상적으로 넘어온 경우
                    // resoponse(영화 JSON 데이터) -> MovieList Data Class로 변환 (by GSON)
                    val data: MovieList =
                        gson.fromJson<MovieList>(response.toString(), MovieList::class.java)

                    val adapter = MovieAdapter(this, data.results) //Adapter 선언
                    movieRecycler.adapter = adapter //RecyclerView에 만든 MovieAdapter 셋팅

                    val lm = LinearLayoutManager(this) //LinearLayoutManager 선언
                    movieRecycler.layoutManager = lm //RecyclerView에 LinearLayoutManager 셋팅 (방향)
                } catch (e: JSONException) {
                    //response가 정상적으로 넘어오지 않은 경우 4
                    Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }, Response.ErrorListener { //데이터가 정상적으로 호출되지 않았을 때 처리하는 부분
                    error ->
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            })

        requestQueue.add(request)

    }
}